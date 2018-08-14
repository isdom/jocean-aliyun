package org.jocean.aliyun.oss;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;
import java.util.TimeZone;
import java.util.concurrent.atomic.AtomicReference;

import javax.inject.Inject;

import org.jocean.aliyun.oss.internal.OSSRequestSigner;
import org.jocean.http.FullMessage;
import org.jocean.http.Interact;
import org.jocean.http.MessageBody;
import org.jocean.http.MessageUtil;
import org.jocean.idiom.ExceptionUtils;
import org.jocean.netty.BlobRepo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;

import com.aliyun.oss.OSSClient;
import com.aliyun.oss.common.utils.DateUtil;

import io.netty.handler.codec.http.HttpHeaderNames;
import io.netty.handler.codec.http.HttpMethod;
import io.netty.handler.codec.http.HttpRequest;
import io.netty.handler.codec.http.HttpResponse;
import io.netty.handler.codec.http.HttpUtil;
import rx.Observable;
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
                .map(resp -> resp.message())
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

    @Override
    public Func1<Interact, Observable<MessageBody>> getObject(final String objname) {
        return interact->interact.method(HttpMethod.GET).uri(uri4bucket())
                .path("/" + objname)
                .onrequest(signRequest(objname))
                .execution()
                .flatMap(execution -> execution.execute())
                .flatMap(resp -> {
                    // https://help.aliyun.com/document_detail/32005.html?spm=a2c4g.11186623.6.1090.DeJEv5
                    final String contentType = resp.message().headers().get(HttpHeaderNames.CONTENT_TYPE);
                    if (null != contentType && contentType.startsWith("application/xml")) {
                        return extractAndReturnOSSError(resp, "getObject error");
                    } else {
                        return resp.body();
                    }
                });
    }

    @Override
    public Func1<Interact, Observable<SimplifiedObjectMeta>> getSimplifiedObjectMeta(final String objectName) {
        return interact->interact.method(HttpMethod.GET).uri(uri4bucket())
                .path("/" + objectName + "?objectMeta")
                .onrequest(signRequest(objectName))
                .execution()
                .flatMap(execution -> execution.execute())
                // TODO: deal with error
                .flatMap(resp -> {
                    // https://help.aliyun.com/document_detail/32005.html?spm=a2c4g.11186623.6.1090.DeJEv5
                    final String contentType = resp.message().headers().get(HttpHeaderNames.CONTENT_TYPE);
                    if (null != contentType && contentType.startsWith("application/xml")) {
                        return extractAndReturnOSSError(resp, "getSimplifiedObjectMeta error");
                    } else {
                        final String etag = resp.message().headers().get(HttpHeaderNames.ETAG);
                        final long size = HttpUtil.getContentLength(resp.message(), -1);
                        final Date lastModified = parseRfc822Date(resp.message().headers().get(HttpHeaderNames.LAST_MODIFIED));

                        return Observable.<SimplifiedObjectMeta>just(new SimplifiedObjectMeta() {
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
                        });
                    }
                });
    }

    /* REF: https://help.aliyun.com/document_detail/31979.html?spm=a2c4g.11186623.6.926.p75n2Q
     * API:
    PUT /DestObjectName HTTP/1.1
    Host: DestBucketName.oss-cn-hangzhou.aliyuncs.com
    Date: GMT Date
    Authorization: SignatureValue
    x-oss-copy-source: /SourceBucketName/SourceObjectName
    */
    @Override
    public Func1<Interact, Observable<String>> copyObject(final String sourceObjectName, final String destObjectName) {
        return interact->interact.method(HttpMethod.PUT).uri(uri4bucket())
                .path("/" + destObjectName)
                .onrequest(obj -> {
                    if (obj instanceof HttpRequest) {
                        // add source object info
                        final HttpRequest req = (HttpRequest)obj;
                        req.headers().set("x-oss-copy-source", "/" + this._bucketName + "/" + sourceObjectName);
                    }
                })
                .onrequest(signRequest(destObjectName))
                .execution()
                .flatMap(execution -> execution.execute())
                .flatMap(resp -> {
                    // https://help.aliyun.com/document_detail/32005.html?spm=a2c4g.11186623.6.1090.DeJEv5
                    final String contentType = resp.message().headers().get(HttpHeaderNames.CONTENT_TYPE);
                    if (null != contentType && contentType.startsWith("application/xml")) {
                        return extractAndReturnOSSError(resp, "copyObject error");
                    } else {
                        final String etag = resp.message().headers().get(HttpHeaderNames.ETAG);
                        LOG.info("object {} copied as {}, and new ETag is {}", sourceObjectName, destObjectName, etag);
                        return Observable.just(destObjectName);
                    }
                });
    }

    @Override
    public Func1<Interact, Observable<String>> deleteObject(final String objectName) {
        return interact->interact.method(HttpMethod.DELETE).uri(uri4bucket())
                .path("/" + objectName)
                .onrequest(signRequest(objectName))
                .execution()
                .flatMap(execution -> execution.execute())
                .flatMap(resp -> {
                    // https://help.aliyun.com/document_detail/32005.html?spm=a2c4g.11186623.6.1090.DeJEv5
                    final String contentType = resp.message().headers().get(HttpHeaderNames.CONTENT_TYPE);
                    if (null != contentType && contentType.startsWith("application/xml")) {
                        return extractAndReturnOSSError(resp, "deleteObject error");
                    } else {
                        LOG.info("object {} deleted", objectName);
                        return Observable.just(objectName);
                    }
                });
    }

    private <T> Observable<? extends T> extractAndReturnOSSError(final FullMessage<HttpResponse> resp,final String msg) {
        return resp.body().flatMap(body -> MessageUtil.<OSSError>decodeXmlAs(body, OSSError.class))
                .flatMap(error -> Observable.error(new RuntimeException(null != msg ? msg + "/" + error.toString() : error.toString())));
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

    @Inject
    private OSSClient _ossclient;

    @Value("${bucket.name}")
    private String _bucketName;
}
