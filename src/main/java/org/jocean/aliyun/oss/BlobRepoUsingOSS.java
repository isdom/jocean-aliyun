package org.jocean.aliyun.oss;

import java.io.ByteArrayInputStream;

import javax.inject.Inject;

import org.jocean.aliyun.BlobRepo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.aliyun.oss.OSSClient;
import com.aliyun.oss.model.ObjectMetadata;
import com.aliyun.oss.model.PutObjectResult;

import rx.Observable;
import rx.Observable.OnSubscribe;
import rx.Subscriber;

public class BlobRepoUsingOSS implements BlobRepo {
    
    private static final Logger LOG = 
        LoggerFactory.getLogger(BlobRepoUsingOSS.class);
    
    @Override
    public Observable<String> putBlob(final String key, 
            final String contentType,
            final byte[] content) {
        return Observable.create(new OnSubscribe<String>() {
            @Override
            public void call(Subscriber<? super String> subscriber) {
                if (!subscriber.isUnsubscribed()) {
                    final ObjectMetadata meta = new ObjectMetadata();
                    // 必须设置ContentLength
                    meta.setContentLength(content.length);
                    meta.setContentType(contentType);
                    final PutObjectResult result = _ossclient.putObject(_bucketName, key, 
                            new ByteArrayInputStream(content), meta);
                    LOG.info("blob stored as {}, and ETag is {}", key, result.getETag());
                    subscriber.onNext(key);
                    subscriber.onCompleted();
                }
            }});
    }
    
    public void setBucketName(final String bucketName) {
        this._bucketName = bucketName;
    }
    
    public void setOSSClient(final OSSClient ossclient) {
        this._ossclient = ossclient;
    }
    
    @Inject
    private OSSClient _ossclient;
    
    private String _bucketName;
}
