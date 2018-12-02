package org.jocean.aliyun.oss;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;
import java.util.TimeZone;
import java.util.concurrent.atomic.AtomicReference;

import javax.inject.Inject;

import org.jocean.aliyun.BlobRepo;
import org.jocean.aliyun.CopyObjectResult;
import org.jocean.aliyun.oss.internal.OSSRequestSigner;
import org.jocean.http.MessageBody;
import org.jocean.http.MessageUtil;
import org.jocean.http.RpcRunner;
import org.jocean.idiom.ExceptionUtils;
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
import rx.Observable.Transformer;
import rx.functions.Action1;

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
            public Transformer<RpcRunner, PutObjectResult> build() {
                if (null == objnameRef.get() || null == bodyRef.get()) {
                    throw new RuntimeException("invalid put object parameters.");
                }
                return putObject(objnameRef.get(), bodyRef.get());
            }
        };
    }

    public Transformer<RpcRunner, PutObjectResult> putObject(final String objname, final MessageBody body) {
        return runners -> runners.flatMap( run -> run.name("oss.putObject").execute(
                interact->interact.method(HttpMethod.PUT).uri(uri4bucket())
                .path("/" + objname).body(Observable.just(body))
                .onrequest(signRequest(objname))
                .execution()
                .flatMap(execution -> execution.execute())
                .<HttpResponse>flatMap(resp -> {
                    // https://help.aliyun.com/document_detail/32005.html?spm=a2c4g.11186623.6.1090.DeJEv5
                    final String contentType = resp.message().headers().get(HttpHeaderNames.CONTENT_TYPE);
                    if (null != contentType && contentType.startsWith("application/xml")) {
                        return OSSUtil.extractAndReturnOSSError(resp, "putObject error");
                    } else {
                        return Observable.just(resp.message());
                    }
                })
                .map(resp -> resp.headers().get(HttpHeaderNames.ETAG)).<PutObjectResult>map(etag -> {
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
                })));
    }

    @Override
    public Transformer<RpcRunner, MessageBody> getObject(final String objname) {
        return runners -> runners.flatMap( run -> run.name("oss.getObject").execute(
                interact->interact.method(HttpMethod.GET).uri(uri4bucket())
                .path("/" + objname)
                .onrequest(signRequest(objname))
                .execution()
                .flatMap(execution -> execution.execute())
                .<MessageBody>flatMap(resp -> {
                    // https://help.aliyun.com/document_detail/32005.html?spm=a2c4g.11186623.6.1090.DeJEv5
                    final String contentType = resp.message().headers().get(HttpHeaderNames.CONTENT_TYPE);
                    if (null != contentType && contentType.startsWith("application/xml")) {
                        return OSSUtil.extractAndReturnOSSError(resp, "getObject error");
                    } else {
                        return resp.body();
                    }
                })));
    }

    @Override
    public Transformer<RpcRunner, SimplifiedObjectMeta> getSimplifiedObjectMeta(final String objectName) {
        return runners -> runners.flatMap( run -> run.name("oss.getSimplifiedObjectMeta").execute(
                interact->interact.method(HttpMethod.GET).uri(uri4bucket())
                .path("/" + objectName + "?objectMeta")
                .onrequest(signRequest(objectName))
                .execution()
                .flatMap(execution -> execution.execute())
                .<SimplifiedObjectMeta>flatMap(resp -> {
                    // https://help.aliyun.com/document_detail/32005.html?spm=a2c4g.11186623.6.1090.DeJEv5
                    final String contentType = resp.message().headers().get(HttpHeaderNames.CONTENT_TYPE);
                    if (null != contentType && contentType.startsWith("application/xml")) {
                        return OSSUtil.extractAndReturnOSSError(resp, "getSimplifiedObjectMeta error");
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
                })));
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
    public Transformer<RpcRunner, CopyObjectResult> copyObject(final String sourceObjectName, final String destObjectName) {
        return runners -> runners.flatMap( run -> run.name("oss.copyObject").execute(
                interact->interact.method(HttpMethod.PUT).uri(uri4bucket())
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
                .<CopyObjectResult>flatMap(resp -> {
                    // https://help.aliyun.com/document_detail/32005.html?spm=a2c4g.11186623.6.1090.DeJEv5
                    final String contentType = resp.message().headers().get(HttpHeaderNames.CONTENT_TYPE);
                    final String etag = resp.message().headers().get(HttpHeaderNames.ETAG);
                    if (null != contentType && contentType.startsWith("application/xml")) {
                        if (null != etag) {
                            LOG.info("object {} copied as {}, and new ETag is {}", sourceObjectName, destObjectName, etag);
                            return resp.body().flatMap(body -> MessageUtil.<CopyObjectResult>decodeXmlAs(body, CopyObjectResult.class));
                        } else {
                            return OSSUtil.extractAndReturnOSSError(resp, "copyObject error");
                        }
                    } else {
                        return Observable.error(new RuntimeException("invalid response:" + resp.message().toString()));
                    }
                }))).doOnNext(result -> {
                    result.setETag(result.getETag().replaceAll("\"", ""));
                });
    }

    @Override
    public Transformer<RpcRunner, String> deleteObject(final String objectName) {
        return runners -> runners.flatMap( runner -> runner.name("oss.deleteObject").execute(
                interact->interact.method(HttpMethod.DELETE).uri(uri4bucket())
                .path("/" + objectName)
                .onrequest(signRequest(objectName))
                .execution()
                .flatMap(execution -> execution.execute())
                .<String>flatMap(resp -> {
                    // https://help.aliyun.com/document_detail/32005.html?spm=a2c4g.11186623.6.1090.DeJEv5
                    final String contentType = resp.message().headers().get(HttpHeaderNames.CONTENT_TYPE);
                    if (null != contentType && contentType.startsWith("application/xml")) {
                        return OSSUtil.extractAndReturnOSSError(resp, "deleteObject error");
                    } else {
                        LOG.info("object {} deleted", objectName);
                        return Observable.just(objectName);
                    }
                })));
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
