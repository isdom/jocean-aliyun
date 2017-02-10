package org.jocean.aliyun.oss;

import java.io.IOException;
import java.io.InputStream;

import javax.inject.Inject;

import org.jocean.idiom.ExceptionUtils;
import org.jocean.netty.BlobRepo;
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

public class BlobRepoOverOSS implements BlobRepo {
    
    private static final Logger LOG = 
        LoggerFactory.getLogger(BlobRepoOverOSS.class);
    
    @Override
    public Observable<PutResult> putBlob(final String key, 
            final Blob blob) {
        return Observable.create(new OnSubscribe<PutResult>() {
            @Override
            public void call(Subscriber<? super PutResult> subscriber) {
                if (!subscriber.isUnsubscribed()) {
                    final ObjectMetadata meta = new ObjectMetadata();
                    // 必须设置ContentLength
                    meta.setContentLength(blob.contentLength());
                    meta.setContentType(blob.contentType());
                    try (final InputStream is = blob.inputStream()) {
                        final PutObjectResult result = _ossclient.putObject(
                                _bucketName, 
                                key, 
                                is, 
                                meta);
                        LOG.info("blob stored as {}, and ETag is {}", key, result.getETag());
                    } catch (IOException e) {
                        LOG.warn("exception when close inputStream, detail: {}", 
                            ExceptionUtils.exception2detail(e));
                    }
                    subscriber.onNext(new PutResult() {
                        @Override
                        public String key() {
                            return key;
                        }

                        @Override
                        public Blob blob() {
                            return blob;
                        }});
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
                        subscriber.onNext(Blob.Util.fromByteArray(blob, contentType, null, null));
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
