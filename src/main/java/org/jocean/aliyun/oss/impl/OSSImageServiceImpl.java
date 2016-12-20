package org.jocean.aliyun.oss.impl;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;

import javax.inject.Inject;
import javax.ws.rs.GET;

import org.jocean.aliyun.oss.OSSImageService;
import org.jocean.aliyun.oss.spi.GetImageContentResponse;
import org.jocean.aliyun.oss.spi.GetImageInfoResponse;
import org.jocean.aliyun.oss.spi.GetImageWithProcessRequest;
import org.jocean.http.Feature;
import org.jocean.http.rosa.SignalClient;
import org.jocean.idiom.ExceptionUtils;
import org.jocean.idiom.store.BlobRepo.Blob;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.google.common.collect.Lists;

import rx.Observable;
import rx.functions.Func1;

public class OSSImageServiceImpl implements OSSImageService {
    private static final Logger LOG = 
            LoggerFactory.getLogger(OSSImageServiceImpl.class);

    @Override
    public Observable<? extends ImageInfo> info(final String pathToImage) {
        try {
            final GetImageWithProcessRequest req = new GetImageWithProcessRequest();
            req.setPathToImage(pathToImage);
            req.setProcessActions(actions().info().and().build());
            
            return _signalClient.<GetImageInfoResponse>defineInteraction(req, 
                    Feature.ENABLE_LOGGING,
                    Feature.ENABLE_COMPRESSOR,
                    new SignalClient.UsingMethod(GET.class),
                    new SignalClient.UsingUri(new URI("http://" + _bucketName + "." + _endpoint)),
                    new SignalClient.DecodeResponseBodyAs(GetImageInfoResponse.class))
                    .map(new Func1<GetImageInfoResponse, ImageInfo>() {
                        @Override
                        public ImageInfo call(final GetImageInfoResponse resp) {
                            return new ImageInfo() {
                                @Override
                                public String toString() {
                                    final StringBuilder builder = new StringBuilder();
                                    builder.append("ImageInfo [fileSize=").append(fileSize())
                                            .append(", format=").append(format())
                                            .append(", imageWidth=").append(imageWidth())
                                            .append(", imageHeight=").append(imageHeight())
                                            .append("]");
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
                                }};
                        }});
        } catch (URISyntaxException e) {
            LOG.warn("exception when signalClient.defineInteraction, detail: {}",
                    ExceptionUtils.exception2detail(e));
            return Observable.error(e);
        }
    }

    public Observable<? extends Blob> process(final String pathToImage, final String actions) {
        try {
            final GetImageWithProcessRequest req = new GetImageWithProcessRequest();
            req.setPathToImage(pathToImage);
            req.setProcessActions(actions);
            
            return _signalClient.<GetImageContentResponse>defineInteraction(req, 
                    Feature.ENABLE_LOGGING,
                    Feature.ENABLE_COMPRESSOR,
                    new SignalClient.UsingMethod(GET.class),
                    new SignalClient.UsingUri(new URI("http://" + _bucketName + "." + _endpoint)),
                    new SignalClient.ConvertResponseTo(GetImageContentResponse.class))
                    .map(new Func1<GetImageContentResponse, Blob>() {
                        @Override
                        public Blob call(final GetImageContentResponse resp) {
                            final byte[] content = resp.content();
                            final String contentType = resp.contentType();
                            final String contentDisposition = resp.contentDisposition();
                            return new Blob() {
                                @Override
                                public String toString() {
                                    final StringBuilder builder = new StringBuilder();
                                    builder.append("Blob [contentSize=").append(content.length)
                                            .append(", contentType=").append(contentType)
                                            .append(", contentDisposition=").append(contentDisposition)
                                            .append("]");
                                    return builder.toString();
                                }
                                @Override
                                public String name() {
                                    return null;
                                }
                                @Override
                                public String filename() {
                                    return null;
                                }
                                @Override
                                public String contentType() {
                                    return contentType;
                                }
                                @Override
                                public byte[] content() {
                                    return content;
                                }};
                        }});
        } catch (URISyntaxException e) {
            LOG.warn("exception when signalClient.defineInteraction, detail: {}",
                    ExceptionUtils.exception2detail(e));
            return Observable.error(e);
        }
    }
    
    public void setEndpoint(final String endpoint) {
        this._endpoint = endpoint;
    }

    public void setBucketName(final String bucketName) {
        this._bucketName = bucketName;
    }

    @Inject
    private SignalClient _signalClient;
    
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
            for (String actionAndParams : this._actions) {
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
