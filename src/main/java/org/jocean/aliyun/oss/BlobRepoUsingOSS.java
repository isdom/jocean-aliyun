package org.jocean.aliyun.oss;

import java.io.ByteArrayInputStream;
import java.io.InputStream;

import javax.inject.Inject;

import org.jocean.aliyun.BlobRepo;
import org.jocean.idiom.ExceptionUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.aliyun.oss.OSSClient;
import com.aliyun.oss.model.OSSObject;
import com.aliyun.oss.model.ObjectMetadata;
import com.aliyun.oss.model.PutObjectResult;
import com.google.common.io.ByteStreams;

import rx.Observable;
import rx.Observable.OnSubscribe;
import rx.Subscriber;

public class BlobRepoUsingOSS implements BlobRepo {
    
    private static final Logger LOG = 
        LoggerFactory.getLogger(BlobRepoUsingOSS.class);
    
    @Override
    public Observable<String> putBlob(final String key, 
            final Blob blob) {
        return Observable.create(new OnSubscribe<String>() {
            @Override
            public void call(Subscriber<? super String> subscriber) {
                if (!subscriber.isUnsubscribed()) {
                    final ObjectMetadata meta = new ObjectMetadata();
                    // 必须设置ContentLength
                    meta.setContentLength(blob.content().length);
                    meta.setContentType(blob.contentType());
                    final PutObjectResult result = _ossclient.putObject(_bucketName, key, 
                            new ByteArrayInputStream(blob.content()), meta);
                    LOG.info("blob stored as {}, and ETag is {}", key, result.getETag());
                    subscriber.onNext(key);
                    subscriber.onCompleted();
                }
            }});
    }
    
    @Override
    public Observable<Blob> getBlob(final String key) {
        return Observable.create(new OnSubscribe<Blob>() {
            @Override
            public void call(Subscriber<? super Blob> subscriber) {
                if (!subscriber.isUnsubscribed()) {
                    final OSSObject ossobj = _ossclient.getObject(_bucketName, key);
                    final ObjectMetadata meta = ossobj.getObjectMetadata();
                    final String contentType = meta.getContentType();
                    try (final InputStream is = ossobj.getObjectContent()) {
                        final byte[] blob = ByteStreams.toByteArray(is);
                        subscriber.onNext(new Blob() {
                            @Override
                            public byte[] content() {
                                return blob;
                            }

                            @Override
                            public String contentType() {
                                return contentType;
                            }});
                        subscriber.onCompleted();
                    } catch (Exception e) {
                        LOG.warn("exception when get oss object {}, detail: {}",
                                key, ExceptionUtils.exception2detail(e));
                        subscriber.onError(e);
                    }
                    
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
