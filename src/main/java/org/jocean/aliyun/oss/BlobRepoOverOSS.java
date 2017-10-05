package org.jocean.aliyun.oss;

import java.io.IOException;
import java.io.InputStream;
import java.net.InetSocketAddress;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;
import java.util.TimeZone;

import javax.inject.Inject;

import org.jocean.aliyun.oss.internal.OSSRequestSigner;
import org.jocean.http.Feature;
import org.jocean.http.client.HttpClient;
import org.jocean.http.client.HttpClient.HttpInitiator;
import org.jocean.http.util.HttpMessageHolder;
import org.jocean.http.util.RxNettys;
import org.jocean.idiom.ExceptionUtils;
import org.jocean.netty.BlobRepo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.aliyun.oss.OSSClient;
import com.aliyun.oss.model.CopyObjectResult;
import com.aliyun.oss.model.OSSObject;
import com.aliyun.oss.model.ObjectMetadata;
import com.aliyun.oss.model.PutObjectResult;
import com.google.common.io.ByteStreams;

import io.netty.buffer.ByteBuf;
import io.netty.handler.codec.http.DefaultHttpRequest;
import io.netty.handler.codec.http.FullHttpResponse;
import io.netty.handler.codec.http.HttpHeaderNames;
import io.netty.handler.codec.http.HttpMethod;
import io.netty.handler.codec.http.HttpObject;
import io.netty.handler.codec.http.HttpRequest;
import io.netty.handler.codec.http.HttpVersion;
import io.netty.handler.codec.http.LastHttpContent;
import rx.Observable;
import rx.Observable.OnSubscribe;
import rx.Subscriber;
import rx.functions.Func0;
import rx.functions.Func1;

public class BlobRepoOverOSS implements BlobRepo {
    
    private static final Logger LOG = 
        LoggerFactory.getLogger(BlobRepoOverOSS.class);
    
    /*
    private static final Func1<ByteBuf, Observable<HttpContent>> _BUF2CONTENT = 
        new Func1<ByteBuf, Observable<HttpContent>>() {
        @Override
        public Observable<HttpContent> call(final ByteBuf buf) {
            return Observable.unsafeCreate(new Observable.OnSubscribe<HttpContent>() {
                @Override
                public void call(final Subscriber<? super HttpContent> subscriber) {
                    if (!subscriber.isUnsubscribed()) {
                        try {
                            if (null!=buf.retainedSlice()) {
                                final HttpContent content = new DefaultHttpContent(buf);
                                subscriber.add(Subscriptions.create(new Action0() {
                                    @Override
                                    public void call() {
                                        final boolean released = content.release();
                                        LOG.debug("{} unsubscribe cause call {}(HttpContent)'s release with return {}", 
                                                subscriber, content, released);
                                    }
                                }));
                                subscriber.onNext(content);
                                subscriber.onCompleted();
                            } else {
                                subscriber.onError(new RuntimeException("retain buf BUT return null"));
                            }
                        } catch (Exception e) {
                            subscriber.onError(e);
                        }
                    }
                }
            });
        }};
        */
        
    public Observable<String> putObject(
            final String objname,
            final ObjectMetadata meta,
            final Observable<? extends ByteBuf> content) {
        final String host = hostWithBucketname();
        return this._httpclient.initiator()
            .remoteAddress(new InetSocketAddress(host, 80))
            .feature(Feature.ENABLE_LOGGING)
            .build()
            .flatMap(callOSSAPI(
                buildObsRequest(buildPutObjectRequest(host, objname, meta), content)));
    }
    
    private HttpRequest buildPutObjectRequest(final String host, 
            final String objname, 
            final ObjectMetadata meta) {
        final HttpRequest request = new DefaultHttpRequest(HttpVersion.HTTP_1_1, HttpMethod.PUT,
                "/" + objname);

        // Host header
        request.headers().set(HttpHeaderNames.HOST, host);

        // Date header
        request.headers().set(HttpHeaderNames.DATE, buildGMT4Now(new Date()));

        // Content-Length header
        request.headers().set(HttpHeaderNames.CONTENT_LENGTH, meta.getContentLength());

        // Content-Type header
        request.headers().set(HttpHeaderNames.CONTENT_TYPE, meta.getContentType());
        
        new OSSRequestSigner( "/" + this._bucketName + "/" + objname, 
                this._ossclient.getCredentialsProvider().getCredentials()).sign(request);
        return request;
    }

    private static Func1<HttpInitiator, Observable<String>> callOSSAPI(final Observable<? extends Object> obsRequest) {
        return new Func1<HttpInitiator, Observable<String>>() {
            @Override
            public Observable<String> call(final HttpInitiator initiator) {
                final HttpMessageHolder holder = new HttpMessageHolder();
                initiator.doOnTerminate(holder.closer());
                return initiator.defineInteraction(obsRequest)
                        .compose(holder.<HttpObject>assembleAndHold())
                        .last()
                        .flatMap(new Func1<HttpObject, Observable<String>>() {
                            @Override
                            public Observable<String> call(final HttpObject any) {
                                final FullHttpResponse fhr = holder.fullOf(RxNettys.BUILD_FULL_RESPONSE).call();
                                if (null != fhr) {
                                    try {
//                                        return Observable.just(new String(
//                                            ByteStreams.toByteArray(new ByteBufInputStream(fhr.content())), 
//                                            CharsetUtil.UTF_8));
                                        return Observable.just(fhr.headers().get(HttpHeaderNames.ETAG));
                                    }
//                                    catch (IOException e) {
//                                        return Observable.error(e);
//                                    }
                                    finally {
                                        fhr.release();
                                    }
                                }
                                return Observable.error(new RuntimeException("can't get response"));
                            }})
                        .doOnUnsubscribe(initiator.closer());
            }};
    }

    private String hostWithBucketname() {
        return this._bucketName + "." + this._ossclient.getEndpoint().getHost();
    }

    private static Observable<? extends Object> buildObsRequest(
            final HttpRequest request, 
            final Observable<? extends ByteBuf> content) {
        return Observable.concat(
            Observable.just(request), 
            content,
            Observable.just(LastHttpContent.EMPTY_LAST_CONTENT));
    }

    private static String buildGMT4Now(final Date date) {
        final SimpleDateFormat sdf = new SimpleDateFormat("EEE, dd MMM yyyy HH:mm:ss 'GMT'", Locale.US);
        sdf.setTimeZone(TimeZone.getTimeZone("GMT")); // 设置时区为GMT
        return sdf.format(date);
    }

    @Override
    public Observable<PutResult> putBlob(final String key, 
            final Blob blob) {
        return Observable.unsafeCreate(new OnSubscribe<PutResult>() {
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
    public Observable<String> putBlob(final String key, final Func0<? extends Blob> blobProducer) {
        return Observable.unsafeCreate(new OnSubscribe<String>() {
            @Override
            public void call(Subscriber<? super String> subscriber) {
                if (!subscriber.isUnsubscribed()) {
                    final Blob blob = blobProducer.call();
                    
                    if (null != blob) {
                        try (final InputStream is = blob.inputStream()) {
                            final ObjectMetadata meta = new ObjectMetadata();
                            // 必须设置ContentLength
                            meta.setContentLength(blob.contentLength());
                            meta.setContentType(blob.contentType());
                            final PutObjectResult result = _ossclient.putObject(
                                    _bucketName, 
                                    key, 
                                    is, 
                                    meta);
                            LOG.info("blob stored as {}, and ETag is {}", key, result.getETag());
                        } catch (IOException e) {
                            LOG.warn("exception when close inputStream, detail: {}", 
                                ExceptionUtils.exception2detail(e));
                            subscriber.onError(e);
                            return;
                        } finally {
                            blob.release();
                        }
                        subscriber.onNext(key);
                        subscriber.onCompleted();
                    } else {
                        subscriber.onError(new RuntimeException("can't produce blob"));
                    }
                }
            }});
    }
    
    @Override
    public Observable<String> copyBlob(final String sourceKey, final String destinationKey) {
        return Observable.unsafeCreate(new OnSubscribe<String>() {
            @Override
            public void call(Subscriber<? super String> subscriber) {
                if (!subscriber.isUnsubscribed()) {
                    try {
                        final CopyObjectResult result = 
                                _ossclient.copyObject(_bucketName, sourceKey, _bucketName, destinationKey);
                        
                        LOG.info("blob {} copied as {}, and new ETag is {}", sourceKey, destinationKey, 
                                result.getETag());
                        subscriber.onNext(destinationKey);
                        subscriber.onCompleted();
                    } catch (Exception e) {
                        LOG.warn("exception when copy blob {} to {}, detail: {}", sourceKey, destinationKey,
                            ExceptionUtils.exception2detail(e));
                        subscriber.onError(e);
                    }
                }
            }});
    }
    

    @Override
    public Observable<String> deleteBlob(final String key) {
        return Observable.unsafeCreate(new OnSubscribe<String>() {
            @Override
            public void call(Subscriber<? super String> subscriber) {
                if (!subscriber.isUnsubscribed()) {
                    try {
                        _ossclient.deleteObject(_bucketName, key);
                        
                        LOG.info("blob {} deleted", key);
                        subscriber.onNext(key);
                        subscriber.onCompleted();
                    } catch (Exception e) {
                        LOG.warn("exception when delete blob {}, detail: {}", key,
                            ExceptionUtils.exception2detail(e));
                        subscriber.onError(e);
                    }
                }
            }});
    }
    
    @Override
    public Observable<Blob> getBlob(final String key) {
        return Observable.unsafeCreate(new OnSubscribe<Blob>() {
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
    
    @Inject
    private HttpClient _httpclient;
    
    private String _bucketName;
}
