package org.jocean.aliyun.oss;

import java.io.IOException;
import java.io.InputStream;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;
import java.util.TimeZone;
import java.util.concurrent.atomic.AtomicReference;

import javax.inject.Inject;

import org.jocean.aliyun.oss.internal.OSSRequestSigner;
import org.jocean.http.Interact;
import org.jocean.http.MessageBody;
import org.jocean.idiom.ExceptionUtils;
import org.jocean.netty.BlobRepo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;

import com.aliyun.oss.OSSClient;
import com.aliyun.oss.common.utils.DateUtil;
import com.aliyun.oss.model.CopyObjectResult;
import com.aliyun.oss.model.OSSObject;
import com.aliyun.oss.model.ObjectMetadata;
import com.google.common.io.ByteStreams;

import io.netty.handler.codec.http.HttpHeaderNames;
import io.netty.handler.codec.http.HttpMethod;
import io.netty.handler.codec.http.HttpRequest;
import io.netty.handler.codec.http.HttpUtil;
import rx.Observable;
import rx.Observable.OnSubscribe;
import rx.Subscriber;
import rx.functions.Action1;
import rx.functions.Func1;

public class BlobRepoOverOSS implements BlobRepo {

    private static final Logger LOG =
        LoggerFactory.getLogger(BlobRepoOverOSS.class);

    @Override
    public PutObjectBuilder putObject() {
        final AtomicReference<MessageBody> bodyRef = new AtomicReference<>(null);
        final AtomicReference<String> objnameRef = new AtomicReference<>(null);

        return new PutObjectBuilder() {

            @Override
            public PutObjectBuilder objectName(final String objectName) {
                objnameRef.set(objectName);
                return this;
            }

            @Override
            public PutObjectBuilder content(final MessageBody body) {
                bodyRef.set(body);
                return this;
            }

            @Override
            public Func1<Interact, Observable<PutObjectResult>> build() {
                if (null == objnameRef.get() || null == bodyRef.get()) {
                    throw new RuntimeException("invalid put object parameters.");
                }
                return putObject(objnameRef.get(), bodyRef.get());
            }
        };
    }

    public Func1<Interact, Observable<PutObjectResult>> putObject(final String objname, final MessageBody body) {
        return interact->interact.method(HttpMethod.PUT).uri(uri4bucket())
                .path("/" + objname).body(Observable.just(body))
                .onrequest(signRequest(objname))
                .execution()
                .flatMap(execution -> execution.execute())
                .flatMap(resp -> resp.message())
                .doOnNext(resp -> {
                    // https://help.aliyun.com/document_detail/32005.html?spm=a2c4g.11186623.6.1090.DeJEv5
                    final String contentType = resp.headers().get(HttpHeaderNames.CONTENT_TYPE);
                    if (null != contentType && contentType.startsWith("application/xml")) {
                        throw new RuntimeException("error for putObject to oss");
                    }
                })
                .map(resp -> resp.headers().get(HttpHeaderNames.ETAG)).map(etag -> {
                    final String unquotes_etag = etag.replaceAll("\"", "");
                    return new PutObjectResult() {
                        @Override
                        public String objectName() {
                            return objname;
                        }

                        @Override
                        public String etag() {
                            return unquotes_etag;
                        }
                    };
                });
    }

    private void addDateAndSign(final HttpRequest request, final String objname) {
        // Date header
        request.headers().set(HttpHeaderNames.DATE, buildGMT4Now(new Date()));

        new OSSRequestSigner( "/" + this._bucketName + "/" + objname,
                this._ossclient.getCredentialsProvider().getCredentials()).sign(request);
    }

    private String uri4bucket() {
        return "http://" + this._bucketName + "." + this._ossclient.getEndpoint().getHost();
    }

    private static String buildGMT4Now(final Date date) {
        final SimpleDateFormat sdf = new SimpleDateFormat("EEE, dd MMM yyyy HH:mm:ss 'GMT'", Locale.US);
        sdf.setTimeZone(TimeZone.getTimeZone("GMT")); // 设置时区为GMT
        return sdf.format(date);
    }

    @Override
    public Func1<Interact, Observable<SimplifiedObjectMeta>> getSimplifiedObjectMeta(final String objectName) {
        return interact->interact.uri(uri4bucket())
                .path("/" + objectName + "?objectMeta")
                .onrequest(signRequest(objectName))
                .execution()
                .flatMap(execution -> execution.execute())
                // TODO: deal with error
                .flatMap(resp -> resp.message())
                .map(resp -> {
                    final String etag = resp.headers().get(HttpHeaderNames.ETAG);
                    final long size = HttpUtil.getContentLength(resp, -1);
                    final Date lastModified = parseRfc822Date(resp.headers().get(HttpHeaderNames.LAST_MODIFIED));

                    return new SimplifiedObjectMeta() {
                        @Override
                        public String getETag() {
                            return etag;
                        }

                        @Override
                        public long getSize() {
                            return size;
                        }

                        @Override
                        public Date getLastModified() {
                            return lastModified;
                        }

                        @Override
                        public String toString() {
                            final StringBuilder builder = new StringBuilder();
                            builder.append("SimplifiedObjectMeta [ETag=").append(etag)
                                    .append(", Size=").append(size)
                                    .append(", LastModified=").append(lastModified)
                                    .append("]");
                            return builder.toString();
                        }
                    };
                });
    }

    private static Date parseRfc822Date(final String lastModified) {
        try {
            return lastModified != null ? DateUtil.parseRfc822Date(lastModified) : null;
        } catch (final Exception e) {
            LOG.warn("exception when parseRfc822Date({}), detail: {}", lastModified, ExceptionUtils.exception2detail(e));
            return null;
        }
    }

    private Action1<Object> signRequest(final String objname){
        return obj -> {
            if (obj instanceof HttpRequest) {
                addDateAndSign((HttpRequest) obj, objname);
            }};
    }

    @Override
    public Observable<PutResult> putBlob(final String key,
            final Blob blob) {
        return Observable.unsafeCreate(new OnSubscribe<PutResult>() {
            @Override
            public void call(final Subscriber<? super PutResult> subscriber) {
                if (!subscriber.isUnsubscribed()) {
                    final ObjectMetadata meta = new ObjectMetadata();
                    // 必须设置ContentLength
                    meta.setContentLength(blob.contentLength());
                    meta.setContentType(blob.contentType());
                    try (final InputStream is = blob.inputStream()) {
                        final com.aliyun.oss.model.PutObjectResult result = _ossclient.putObject(
                                _bucketName,
                                key,
                                is,
                                meta);
                        LOG.info("blob stored as {}, and ETag is {}", key, result.getETag());
                    } catch (final IOException e) {
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
    public Observable<String> copyBlob(final String sourceKey, final String destinationKey) {
        return Observable.unsafeCreate(new OnSubscribe<String>() {
            @Override
            public void call(final Subscriber<? super String> subscriber) {
                if (!subscriber.isUnsubscribed()) {
                    try {
                        final CopyObjectResult result =
                                _ossclient.copyObject(_bucketName, sourceKey, _bucketName, destinationKey);

                        LOG.info("blob {} copied as {}, and new ETag is {}", sourceKey, destinationKey,
                                result.getETag());
                        subscriber.onNext(destinationKey);
                        subscriber.onCompleted();
                    } catch (final Exception e) {
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
            public void call(final Subscriber<? super String> subscriber) {
                if (!subscriber.isUnsubscribed()) {
                    try {
                        _ossclient.deleteObject(_bucketName, key);

                        LOG.info("blob {} deleted", key);
                        subscriber.onNext(key);
                        subscriber.onCompleted();
                    } catch (final Exception e) {
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
            public void call(final Subscriber<? super Blob> subscriber) {
                if (!subscriber.isUnsubscribed()) {
                    final OSSObject ossobj = _ossclient.getObject(_bucketName, key);
                    final ObjectMetadata meta = ossobj.getObjectMetadata();
                    final String contentType = meta.getContentType();
                    try (final InputStream is = ossobj.getObjectContent()) {
                        final byte[] blob = ByteStreams.toByteArray(is);
                        subscriber.onNext(Blob.Util.fromByteArray(blob, contentType, null, null));
                        subscriber.onCompleted();
                    } catch (final Exception e) {
                        LOG.warn("exception when get oss object {}, detail: {}",
                                key, ExceptionUtils.exception2detail(e));
                        subscriber.onError(e);
                    }
                }
            }});
    }

    @Inject
    private OSSClient _ossclient;

    @Value("${bucket.name}")
    private String _bucketName;
}
