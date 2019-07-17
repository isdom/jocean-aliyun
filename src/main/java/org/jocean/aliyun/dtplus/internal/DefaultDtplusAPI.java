package org.jocean.aliyun.dtplus.internal;

import java.security.MessageDigest;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;
import javax.ws.rs.core.MediaType;

import org.jocean.aliyun.dtplus.DtplusAPI;
import org.jocean.http.ByteBufSlice;
import org.jocean.http.ContentUtil;
import org.jocean.http.MessageBody;
import org.jocean.http.RpcRunner;
import org.jocean.svr.ByteBufSliceUtil;
import org.springframework.beans.factory.annotation.Value;

import com.alibaba.fastjson.JSON;
import com.google.common.base.Charsets;
import com.google.common.io.BaseEncoding;

import io.netty.handler.codec.http.EmptyHttpHeaders;
import io.netty.handler.codec.http.HttpHeaderNames;
import io.netty.handler.codec.http.HttpHeaders;
import io.netty.handler.codec.http.HttpMethod;
import io.netty.handler.codec.http.HttpRequest;
import rx.Observable;
import rx.Observable.Transformer;

public class DefaultDtplusAPI implements DtplusAPI {

    @Override
    public Transformer<RpcRunner, ImageTagResponse> imagetag(final String imgurl) {
        return runners -> runners.flatMap(runner -> runner.name("aliyun.imagetag").execute(interact -> {
            try {
                final ImageTagRequest req = new ImageTagRequest();

                req.setUrl(imgurl);

                final String body = JSON.toJSONString(req);
                final byte[] bodyBytes = body.getBytes(Charsets.UTF_8);
                final ByteBufSlice bbs = ByteBufSliceUtil.wrappedSlice(bodyBytes);

                final MessageBody msgbody = new MessageBody() {

                    @Override
                    public HttpHeaders headers() {
                        return EmptyHttpHeaders.INSTANCE;
                    }

                    @Override
                    public String contentType() {
                        return MediaType.APPLICATION_JSON;
                    }

                    @Override
                    public int contentLength() {
                        return bodyBytes.length;
                    }

                    @Override
                    public Observable<? extends ByteBufSlice> content() {
                        return Observable.just(bbs);
                    }};

                /*
                 * http header 参数
                 */
                final String method = "POST";
                final String accept = MediaType.APPLICATION_JSON;
                final String content_type = MediaType.APPLICATION_JSON;
                final String path = "/image/tag";
                final String date = toGMTString(new Date());
                // 1.对body做MD5+BASE64加密
                final String bodyMd5 = MD5Base64(body);
                final String stringToSign = method + "\n" + accept + "\n" + bodyMd5 + "\n" + content_type + "\n" + date + "\n"
                        + path;
                // 2.计算 HMAC-SHA1
                final String signature = HMACSha1(stringToSign, this._ak_secret);
                // 3.得到 authorization header
                final String authHeader = "Dataplus " + this._ak_id + ":" + signature;

                return interact.method(HttpMethod.POST)
                        .uri("https://dtplus-cn-shanghai.data.aliyuncs.com")
                        .path(path)
                        .body(Observable.just(msgbody))
                        .onrequest(obj -> {
                            if (obj instanceof HttpRequest) {
                                final HttpRequest httpreq = (HttpRequest)obj;
                                httpreq.headers().set(HttpHeaderNames.ACCEPT, accept);
                                httpreq.headers().set(HttpHeaderNames.DATE, date);
                                httpreq.headers().set(HttpHeaderNames.AUTHORIZATION, authHeader);
                            }
                        })
                        .responseAs(ContentUtil.ASJSON, ImageTagResponse.class);
            } catch (final Exception e) {
                return Observable.error(e);
            }
        }));
    }

    /*
     * 计算MD5+BASE64
     */
    private static String MD5Base64(final String s) {
        if (s == null)
            return null;
        String encodeStr = "";
        final byte[] utfBytes = s.getBytes();
        MessageDigest mdTemp;
        try {
            mdTemp = MessageDigest.getInstance("MD5");
            mdTemp.update(utfBytes);
            encodeStr = BaseEncoding.base64().encode(mdTemp.digest());
        } catch (final Exception e) {
            throw new Error("Failed to generate MD5 : " + e.getMessage());
        }
        return encodeStr;
    }

    /*
     * 计算 HMAC-SHA1
     */
    private static String HMACSha1(final String data, final String key) {
        String result;
        try {
            final SecretKeySpec signingKey = new SecretKeySpec(key.getBytes(), "HmacSHA1");
            final Mac mac = Mac.getInstance("HmacSHA1");
            mac.init(signingKey);
            result = BaseEncoding.base64().encode(mac.doFinal(data.getBytes()));
        } catch (final Exception e) {
            throw new Error("Failed to generate HMAC : " + e.getMessage());
        }
        return result;
    }

    /*
     * 等同于javaScript中的 new Date().toUTCString();
     */
    private static String toGMTString(final Date date) {
        final SimpleDateFormat df = new SimpleDateFormat("E, dd MMM yyyy HH:mm:ss z", Locale.UK);
        df.setTimeZone(new java.util.SimpleTimeZone(0, "GMT"));
        return df.format(date);
    }

    @Value("${ak_secret}")
    private String _ak_secret;

    @Value("${ak_id}")
    private String _ak_id;
}
