package org.jocean.aliyun.sign;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;
import java.util.TimeZone;

import org.jocean.aliyun.oss.internal.OSSHeaders;
import org.jocean.aliyun.oss.internal.OSSUtils;
import org.jocean.aliyun.oss.internal.SignUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.aliyun.oss.common.auth.ServiceSignature;

import io.netty.buffer.ByteBufUtil;
import io.netty.buffer.Unpooled;
import io.netty.handler.codec.http.HttpHeaderNames;
import io.netty.handler.codec.http.HttpRequest;
import io.netty.handler.codec.http.QueryStringDecoder;
import io.netty.util.CharsetUtil;
import rx.functions.Action1;

public class Signer4OSS {

    private static final Logger LOG = LoggerFactory.getLogger(Signer4OSS.class);

    public static Action1<Object> signRequest(final String ak_id, final String ak_secret) {
        return obj -> {
            if (obj instanceof HttpRequest) {
                doSign((HttpRequest) obj, ak_id, ak_secret, null);
            }};
    }

    public static Action1<Object> signRequest(final String ak_id, final String ak_secret, final String ststoken) {
        return obj -> {
            if (obj instanceof HttpRequest) {
                doSign((HttpRequest) obj, ak_id, ak_secret, ststoken);
            }};
    }

    private static String buildGMT4Now(final Date date) {
        final SimpleDateFormat sdf = new SimpleDateFormat("EEE, dd MMM yyyy HH:mm:ss 'GMT'", Locale.US);
        sdf.setTimeZone(TimeZone.getTimeZone("GMT")); // 设置时区为GMT
        return sdf.format(date);
    }

    // https://help.aliyun.com/document_detail/31951.html?spm=a2c4g.11186623.6.1512.11096928pWjlYH
    private static void doSign(final HttpRequest req, final String ak_id, final String ak_secret,
            final String ststoken) {

        req.headers().set(HttpHeaderNames.DATE, buildGMT4Now(new Date()));

        // 如果以STS获得的AccessKeyId和AccessKeySecret发送请求时，
        //  还需要将获得的security-token值以x-oss-security-token:security-token的形式加入到签名字符串中。
        if (null != ststoken) {
            req.headers().set("x-oss-security-token", ststoken);
        }

        final String host = req.headers().get(HttpHeaderNames.HOST);
        LOG.debug("sign4oss: host:{}", host);

        final String bucketName = host.split("\\.")[0];

        final QueryStringDecoder decoder = new QueryStringDecoder(req.uri());
        final String objectName = decoder.path();
        final String resourcePath = "/" + bucketName + objectName;

        LOG.debug("sign4oss: bucket:{} objectName:{} => resourcePath:{}", bucketName, objectName, resourcePath);
        final String canonicalString = SignUtils.buildCanonicalString(req.method().toString(), resourcePath, req, null);

        if (LOG.isDebugEnabled()) {
            LOG.debug("sign: canonicalString is {} AS HEX\n{}", canonicalString,
                ByteBufUtil.prettyHexDump(Unpooled.wrappedBuffer(canonicalString.getBytes(CharsetUtil.UTF_8))));
        }

        final String signature = ServiceSignature.create().computeSignature(ak_secret, canonicalString);

        LOG.debug("sign: getAccessKeyId {} and signature {}", ak_id, signature);

        req.headers().add(OSSHeaders.AUTHORIZATION, OSSUtils.composeRequestAuthorization(ak_id, signature));
    }
}
