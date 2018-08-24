package org.jocean.aliyun.oss.impl;

import java.io.InputStream;
import java.util.List;

import org.jocean.aliyun.oss.OSSImageService;
import org.jocean.aliyun.oss.OSSUtil;
import org.jocean.aliyun.oss.spi.GetImageInfoResponse;
import org.jocean.aliyun.oss.spi.GetImageWithProcessRequest;
import org.jocean.http.Interact;
import org.jocean.http.Interaction;
import org.jocean.http.MessageBody;
import org.jocean.http.MessageUtil;
import org.jocean.http.util.Nettys;
import org.jocean.idiom.DisposableWrapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.google.common.collect.Lists;

import io.netty.buffer.ByteBuf;
import io.netty.handler.codec.http.HttpHeaderNames;
import io.netty.handler.codec.http.HttpMethod;
import rx.Observable;
import rx.Observable.Transformer;
import rx.functions.Func1;
import rx.functions.Func2;

public class OSSImageServiceImpl implements OSSImageService {

    @SuppressWarnings("unused")
    private static final Logger LOG =
            LoggerFactory.getLogger(OSSImageServiceImpl.class);

    @Override
    public Func1<Interact, Observable<? extends ImageInfo>> info(final String pathToImage) {
        final GetImageWithProcessRequest req = new GetImageWithProcessRequest();
        req.setPathToImage(pathToImage);
        req.setProcessActions(actions().info().and().build());
        final String uri = "http://" + _bucketName + "." + _endpoint;
        return interact -> interact.reqbean(req).uri(uri).method(HttpMethod.GET).execution()
                .compose(checkErrorAndResponseAs(GetImageInfoResponse.class, MessageUtil::unserializeAsJson))
                .<ImageInfo>map(resp -> new ImageInfo() {
                    @Override
                    public String toString() {
                        final StringBuilder builder = new StringBuilder();
                        builder.append("ImageInfo [fileSize=").append(fileSize()).append(", format=").append(format())
                                .append(", imageWidth=").append(imageWidth()).append(", imageHeight=")
                                .append(imageHeight()).append("]");
                        return builder.toString();
                    }

                    @Override
                    public long fileSize() {
                        return resp.getFileSize();
                    }

                    @Override
                    public String format() {
                        return resp.getFormat();
                    }

                    @Override
                    public int imageWidth() {
                        return resp.getImageWidth();
                    }

                    @Override
                    public int imageHeight() {
                        return resp.getImageHeight();
                    }
                });
    }

    private static <RESP> Transformer<Interaction, RESP> checkErrorAndResponseAs(
            final Class<RESP> resptype,
            final Func2<InputStream, Class<RESP>, RESP> decoder) {
        return interactions -> interactions.flatMap(interaction -> interaction.execute().<DisposableWrapper<? extends ByteBuf>>flatMap(fullresp -> {
            final String contentType = fullresp.message().headers().get(HttpHeaderNames.CONTENT_TYPE);
            if (null != contentType && contentType.startsWith("application/xml")) {
                return OSSUtil.extractAndReturnOSSError(fullresp, "get info error");
            } else {
                return fullresp.body().flatMap(body -> body.content().compose(MessageUtil.AUTOSTEP2DWB));
            }
        }).doOnUnsubscribe(interaction.initiator().closer()).toList().map(dwbs -> {
            final ByteBuf content = Nettys.dwbs2buf(dwbs);
            try {
                return decoder.call(MessageUtil.contentAsInputStream(content), resptype);
            } finally {
                content.release();
            }
        }));
    }

    @Override
    public Func1<Interact, Observable<? extends MessageBody>> process(final String pathToImage, final String actions) {
            final GetImageWithProcessRequest req = new GetImageWithProcessRequest();
            req.setPathToImage(pathToImage);
            req.setProcessActions(actions);
            final String uri = "http://" + _bucketName + "." + _endpoint;

            return interact -> interact.reqbean(req).uri(uri).method(HttpMethod.GET).execution()
                    .flatMap(interaction->interaction.execute())
                    .<MessageBody>flatMap(fullmsg -> {
                        final String contentType = fullmsg.message().headers().get(HttpHeaderNames.CONTENT_TYPE);
                        if (null != contentType && contentType.startsWith("application/xml")) {
//                            throw new RuntimeException("error for process for oss object");
                            return OSSUtil.extractAndReturnOSSError(fullmsg, "process oss object error");
                        } else {
                            return fullmsg.body();
                        }
                    });
    }

    public void setEndpoint(final String endpoint) {
        this._endpoint = endpoint;
    }

    public void setBucketName(final String bucketName) {
        this._bucketName = bucketName;
    }

//    @Inject
//    private BeanFinder _finder;

    private String _endpoint;

    private String _bucketName;

    @Override
    public ActionSet actions() {
        return new DefaultActionSet();
    }

    static class DefaultActionSet implements ActionSet {

        @Override
        public Info info() {
            return new DefaultInfo(this);
        }

        @Override
        public Crop crop() {
            return new DefaultCrop(this);
        }

        @Override
        public Resize resize() {
            throw new UnsupportedOperationException();
        }

        @Override
        public String build() {
            final StringBuilder sb = new StringBuilder();
            sb.append("image");
            for (final String actionAndParams : this._actions) {
                sb.append('/');
                sb.append(actionAndParams);
            }
            return sb.toString();
        }

        void addAction(final String actionAndParams) {
            this._actions.add(actionAndParams);
        }

        final List<String> _actions = Lists.newArrayList();
    }

    static abstract class BaseAction implements ProcessAction {
        BaseAction(final DefaultActionSet actionSet) {
            this._actionSet = actionSet;
        }

        public ActionSet addToActionSet(final String actionAndParams) {
            this._actionSet.addAction(actionAndParams);
            return this._actionSet;
        }

        protected void appendParam(final StringBuilder sb, final String name, final String value) {
            if (null != value) {
                sb.append(',');
                sb.append(name);
                sb.append(value);
            }
        }

        private final DefaultActionSet _actionSet;
    }

    static class DefaultInfo extends BaseAction implements Info {
        DefaultInfo(final DefaultActionSet actionSet) {
            super(actionSet);
        }

        @Override
        public ActionSet and() {
            final StringBuilder sb = new StringBuilder();
            sb.append("info");
            return addToActionSet(sb.toString());
        }
    }

    static class DefaultCrop extends BaseAction
        implements Crop {
        DefaultCrop(final DefaultActionSet actionSet) {
            super(actionSet);
        }

        @Override
        public ActionSet and() {
            final StringBuilder sb = new StringBuilder();
            sb.append("crop");
            appendParam(sb, "x_", this._x);
            appendParam(sb, "y_", this._y);
            appendParam(sb, "w_", this._w);
            appendParam(sb, "h_", this._h);
            return addToActionSet(sb.toString());
        }

        @Override
        public Crop x(final int x) {
            this._x = Integer.toString(x);
            return this;
        }

        @Override
        public Crop y(final int y) {
            this._y = Integer.toString(y);
            return this;
        }

        @Override
        public Crop w(final int w) {
            this._w = Integer.toString(w);
            return this;
        }

        @Override
        public Crop h(final int h) {
            this._h = Integer.toString(h);
            return this;
        }

        private String _x = null;
        private String _y = null;
        private String _w = null;
        private String _h = null;
    }
}
