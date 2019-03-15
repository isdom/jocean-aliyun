package org.jocean.aliyun.ccs.internal;

import java.net.URI;
import java.util.Arrays;

import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;

import org.jocean.aliyun.ccs.CCSChatAPI;
import org.jocean.http.ByteBufSlice;
import org.jocean.http.ContentUtil;
import org.jocean.http.MessageBody;
import org.jocean.http.RpcRunner;
import org.jocean.idiom.DisposableWrapper;
import org.jocean.idiom.DisposableWrapperUtil;
import org.jocean.idiom.ExceptionUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;

import com.alibaba.fastjson.annotation.JSONField;
import com.google.common.base.Charsets;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.handler.codec.http.HttpHeaderNames;
import io.netty.handler.codec.http.HttpHeaderValues;
import io.netty.handler.codec.http.HttpMethod;
import io.netty.util.CharsetUtil;
import io.netty.util.internal.PlatformDependent;
import rx.Observable;
import rx.Observable.Transformer;
import rx.functions.Action1;

public class DefaultCCSChatAPI implements CCSChatAPI {
    private static final Logger LOG = LoggerFactory.getLogger(DefaultCCSChatAPI.class);

    @Override
    public Mac digestInstance() {
        try {
            final SecretKeySpec signingKey = new SecretKeySpec(this._keyBytes, "HmacSHA1");
            final Mac mac = Mac.getInstance("HmacSHA1");
            mac.init(signingKey);

            return mac;
        } catch (final Exception ex) {
            LOG.warn("exception when digestInstance, detail: {}", ExceptionUtils.exception2detail(ex));
            return null;
        }
    }

    /**
     * 二进制字节数组转十六进制
     *
     * @param v 二进制数组内容
     * @return 十六进制字符串
     */
    final static String _HEX_DIGITS = "0123456789abcdef";

    public static String toHexString(final byte[] v) {
        final StringBuilder sb = new StringBuilder(v.length * 2);
        for (int i = 0; i < v.length; i++) {
            final int b = v[i] & 0xFF;
            sb.append(_HEX_DIGITS.charAt(b >>> 4)).append(_HEX_DIGITS.charAt(b & 0xF));
        }
        return sb.toString();
    }

    public interface FetchFileResponse extends CCSResponse {
        @JSONField(name="url")
        public String getUrl();

        @JSONField(name="url")
        public void setUrl(final String url);

        @JSONField(name="fileKey")
        public String getFileKey();

        @JSONField(name="fileKey")
        public void setFileKey(final String filekey);

        @JSONField(name="timestamp")
        public long getTimestamp();

        @JSONField(name="timestamp")
        public void setTimestamp(final long timestamp);
    }

    // https://help.aliyun.com/document_detail/62586.html?spm=a2c4g.11186623.6.563.2b4c418adJDz1w#fetchfile
    @Override
    public Transformer<RpcRunner, MessageBody> fetchFile(
            final String tntInstId, final String scene, final long timestamp, final String fileKey) {
        return runners -> runners.flatMap(runner -> runner.name("aliyunccs.fetchFileUrl").execute(interact -> {
                final byte[] rawHmac = digestInstance().doFinal((fileKey + timestamp).getBytes(Charsets.UTF_8));
                return interact.method(HttpMethod.GET)
                        .uri("https://cschat-ccs.aliyun.com")
                        .path("/openapi/fetchFile")
                        .paramAsQuery("tntInstId", tntInstId)
                        .paramAsQuery("scene", scene)
                        .paramAsQuery("timestamp", Long.toString(timestamp))
                        .paramAsQuery("digest", toHexString(rawHmac))
                        .paramAsQuery("src", "outerservice")
                        .paramAsQuery("fileKey", fileKey)
                        .responseAs(ContentUtil.ASJSON, FetchFileResponse.class)
                        .doOnNext(resp -> {
                            if (resp.getCode() != null) {
                                throw new RuntimeException(resp.toString());
                            }
                        });
                }))
                .flatMap(resp -> runners.flatMap(runner -> runner.name("aliyunccs.downloadFileForUrl").execute(interact -> {
                    try {
                        final String url = resp.getUrl();
                        final URI uri = new URI(resp.getUrl());
                        final int pathBegin = url.indexOf(uri.getRawPath());

                        return interact.method(HttpMethod.GET).uri(url.substring(0, pathBegin)).path(url.substring(pathBegin))
                                .response().flatMap(fullresp -> fullresp.body());
                    } catch(final Exception e) {
                        return Observable.error(e);
                    }
                })));
    }

    @Override
    public Transformer<RpcRunner, UploadFileResponse> uploadFile(final String tntInstId, final String scene, final long timestamp,
            final String fileType, final String fileName,
            final Observable<? extends MessageBody> file,
            final Mac digestInstance) {

        final byte[] rawHmac = digestInstance.doFinal(String.valueOf(timestamp).getBytes(Charsets.UTF_8));

        return runners -> runners.flatMap(runner -> runner.name("aliyunccs.uploadFile").execute(interact ->
            interact.method(HttpMethod.POST)
                .uri("https://cschat-ccs.aliyun.com")
                .path("/openapi/uploadFile")
                .paramAsQuery("tntInstId", tntInstId)
                .paramAsQuery("scene", scene)
                .paramAsQuery("digest", toHexString(rawHmac))
                .paramAsQuery("timestamp", Long.toString(timestamp))
                .paramAsQuery("src", "outerservice")
                .body(file.compose(tomultipart(fileType, fileName, timestamp)))
                .responseAs(ContentUtil.ASJSON, UploadFileResponse.class)))
                .doOnNext(resp -> {
                    if (resp.getCode() != null) {
                        throw new RuntimeException(resp.toString());
                    }
                })
                ;
    }

    private static String getNewMultipartDelimiter() {
        // construct a generated delimiter
        return Long.toHexString(PlatformDependent.threadLocalRandom().nextLong());
    }

    private Transformer<MessageBody, MessageBody> tomultipart(final String fileType, final String fileName, final long timestamp) {
        return bodys -> bodys.flatMap(body -> {
            final String multipartBoundary = getNewMultipartDelimiter();
            final String contentType = HttpHeaderValues.MULTIPART_FORM_DATA + "; " + HttpHeaderValues.BOUNDARY + '=' + multipartBoundary;

            final byte[] typePart = stringPart("type", fileType, multipartBoundary);
            final byte[] filenamePart = stringPart("fileName", fileName, multipartBoundary);
            final byte[] timestampPart = stringPart("timestamp", Long.toString(timestamp), multipartBoundary);

            final byte[] filehead = headOf("file", fileName, body, multipartBoundary);
            final byte[] end = endOf(multipartBoundary);
            final int contentLength = typePart.length + filenamePart.length + timestampPart.length
                    + filehead.length + end.length + body.contentLength();

            return Observable.just((MessageBody)new MessageBody() {
                @Override
                public String contentType() {
                    return contentType;
                }
                @Override
                public int contentLength() {
                    return contentLength;
                }
                @Override
                public Observable<? extends ByteBufSlice> content() {
                    return Observable.concat(bytes2bbs(typePart), bytes2bbs(filenamePart), bytes2bbs(timestampPart),
                            bytes2bbs(filehead), body.content(), bytes2bbs(end));
                }});
        });
    }

    private byte[] stringPart(final String name, final String content, final String multipartBoundary) {
        final StringBuilder sb = new StringBuilder();

        sb.append("--");
        sb.append(multipartBoundary);
        sb.append("\r\n");

        sb.append(HttpHeaderNames.CONTENT_DISPOSITION);
        sb.append(": ");
        sb.append(HttpHeaderValues.FORM_DATA);
        sb.append("; ");
        sb.append(HttpHeaderValues.NAME);
        sb.append("=\"");
        sb.append(name);
        sb.append("\"\r\n");
        sb.append(HttpHeaderNames.CONTENT_TYPE);
        sb.append(": ");
        sb.append(HttpHeaderValues.TEXT_PLAIN);
        sb.append("; charset=UTF-8");
        sb.append("\r\n");
        sb.append(HttpHeaderNames.CONTENT_TRANSFER_ENCODING);
        sb.append(": 8bit");
        sb.append("\r\n");

        sb.append("\r\n");
        sb.append(content);
        sb.append("\r\n");

        return sb.toString().getBytes(CharsetUtil.UTF_8);
    }

    private byte[] endOf(final String multipartBoundary) {
        return ("\r\n--" + multipartBoundary + "--\r\n").getBytes(CharsetUtil.UTF_8);
    }

    private byte[] headOf(
            final String name,
            final String filename, final MessageBody body,
            final String multipartBoundary) {
        final StringBuilder sb = new StringBuilder();

        sb.append("--");
        sb.append(multipartBoundary);
        sb.append("\r\n");

        sb.append(HttpHeaderNames.CONTENT_DISPOSITION);
        sb.append(": ");
        sb.append(HttpHeaderValues.FORM_DATA);
        sb.append("; ");
        sb.append(HttpHeaderValues.NAME);
        sb.append("=\"");
        sb.append(name);
        sb.append("\"; ");
        sb.append(HttpHeaderValues.FILENAME);
        sb.append("=\"");
        sb.append(filename);
        sb.append("\"\r\n");

        if (body.contentLength() > 0) {
            sb.append(HttpHeaderNames.CONTENT_LENGTH);
            sb.append(": ");
            sb.append(body.contentLength());
            sb.append("\r\n");
        }

        sb.append(HttpHeaderNames.CONTENT_TYPE);
        sb.append(": ");
//        sb.append(body.contentType());
        sb.append(HttpHeaderValues.APPLICATION_OCTET_STREAM);
//        sb.append("; charset=ISO-8859-1");
        sb.append("\r\n");

        sb.append(HttpHeaderNames.CONTENT_TRANSFER_ENCODING);
        sb.append(": ");
        sb.append(HttpHeaderValues.BINARY);
        sb.append("\r\n\r\n");

        return sb.toString().getBytes(CharsetUtil.UTF_8);
    }

    private Observable<? extends ByteBufSlice> bytes2bbs(final byte[] bytes) {
        final Iterable<? extends DisposableWrapper<? extends ByteBuf>> elements = Arrays
                .asList(DisposableWrapperUtil.wrap(Unpooled.wrappedBuffer(bytes), (Action1<ByteBuf>) null));

        return Observable.just((ByteBufSlice) new ByteBufSlice() {
            @Override
            public void step() {
            }

            @Override
            public Iterable<? extends DisposableWrapper<? extends ByteBuf>> element() {
                return elements;
            }
        });
    }

    @Value("${ccs.key}")
    void setPrivateKey(final String key) {
        this._keyBytes = key.getBytes(Charsets.UTF_8);
    }

    byte[] _keyBytes;
}
