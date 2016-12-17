package org.jocean.aliyun.oss.impl;

import java.net.URI;
import java.net.URISyntaxException;

import javax.inject.Inject;
import javax.ws.rs.GET;

import org.jocean.aliyun.oss.OSSImageService;
import org.jocean.aliyun.oss.spi.GetImageInfoResponse;
import org.jocean.aliyun.oss.spi.GetImageWithProcessRequest;
import org.jocean.aliyun.oss.spi.GetImageWithProcessRequest.ProcessAction;
import org.jocean.http.Feature;
import org.jocean.http.rosa.SignalClient;
import org.jocean.idiom.ExceptionUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

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
            req.process().add(ProcessAction.info);
            
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
}
