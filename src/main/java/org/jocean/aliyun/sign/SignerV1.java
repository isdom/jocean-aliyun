package org.jocean.aliyun.sign;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.SimpleTimeZone;
import java.util.UUID;

import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;
import javax.xml.bind.DatatypeConverter;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import io.netty.handler.codec.http.HttpMethod;
import io.netty.handler.codec.http.HttpRequest;
import io.netty.handler.codec.http.QueryStringDecoder;
import io.netty.handler.codec.http.QueryStringEncoder;
import rx.functions.Action1;

public class SignerV1 {

    private static final String[] EMPTY_STRS = new String[0];
    private static final Logger LOG = LoggerFactory.getLogger(SignerV1.class);

    public static Action1<Object> signRequest(final String region,  final String ak_id, final String ak_secret) {
        return obj -> {
            if (obj instanceof HttpRequest) {
                doSign((HttpRequest) obj, region, ak_id, ak_secret, null);
            }};
    }

    public static Action1<Object> signRequest(final String region,  final String ak_id, final String ak_secret, final String ststoken) {
        return obj -> {
            if (obj instanceof HttpRequest) {
                doSign((HttpRequest) obj, region, ak_id, ak_secret, ststoken);
            }};
    }

    private final static String TIME_ZONE = "GMT";
    private final static String FORMAT_ISO8601 = "yyyy-MM-dd'T'HH:mm:ss'Z'";

    static String getISO8601Time(final Date date) {
        final SimpleDateFormat df = new SimpleDateFormat(FORMAT_ISO8601);
        df.setTimeZone(new SimpleTimeZone(0, TIME_ZONE));
        return df.format(date);
    }

    static String getUniqueNonce() {
        final StringBuffer uniqueNonce = new StringBuffer();
        final UUID uuid = UUID.randomUUID();
        uniqueNonce.append(uuid.toString());
        uniqueNonce.append(System.currentTimeMillis());
        uniqueNonce.append(Thread.currentThread().getId());
        return uniqueNonce.toString();
    }

    private final static String SEPARATOR = "&";
    public final static String URL_ENCODING = "UTF-8";

    static String percentEncode(final String value) throws UnsupportedEncodingException {
        return value != null ? URLEncoder.encode(value, URL_ENCODING)
                .replace("+", "%20")
                .replace("*", "%2A")
                .replace("%7E", "~")
                : null;
    }

    static String composeStringToSign(final HttpMethod method, final Map<String, String> queries) {
        final String[] sortedKeys = queries.keySet().toArray(EMPTY_STRS);
        Arrays.sort(sortedKeys);
        final StringBuilder canonicalizedQueryString = new StringBuilder();
        try {
            for (final String key : sortedKeys) {
                canonicalizedQueryString.append("&").append(percentEncode(key)).append("=")
                        .append(percentEncode(queries.get(key)));
            }

            final StringBuilder stringToSign = new StringBuilder();
            stringToSign.append(method.toString());
            stringToSign.append(SEPARATOR);
            stringToSign.append(percentEncode("/"));
            stringToSign.append(SEPARATOR);
            stringToSign.append(percentEncode(canonicalizedQueryString.toString().substring(1)));

            return stringToSign.toString();
        } catch (final UnsupportedEncodingException exp) {
            throw new RuntimeException("UTF-8 encoding is not supported.");
        }
    }

    public static final String ENCODING = "UTF-8";
    private static final String ALGORITHM_NAME = "HmacSHA1";

    static String signStringByHmacSHA1(final String stringToSign, final String accessKeySecret) {
        try {
            final Mac mac = Mac.getInstance(ALGORITHM_NAME);
            mac.init(new SecretKeySpec(accessKeySecret.getBytes(ENCODING), ALGORITHM_NAME));
            final byte[] signData = mac.doFinal(stringToSign.getBytes(ENCODING));
            return DatatypeConverter.printBase64Binary(signData);
        } catch (final NoSuchAlgorithmException e) {
            throw new IllegalArgumentException(e.toString());
        } catch (final UnsupportedEncodingException e) {
            throw new IllegalArgumentException(e.toString());
        } catch (final InvalidKeyException e) {
            throw new IllegalArgumentException(e.toString());
        }
    }

    private static void doSign(final HttpRequest req, final String region,  final String ak_id, final String ak_secret,
            final String ststoken) {
        final QueryStringDecoder decoder = new QueryStringDecoder(req.uri());

        final Map<String, String> paramsToSign = new HashMap<String, String>();

        for (final Map.Entry<String, List<String>> entry : decoder.parameters().entrySet()) {
            paramsToSign.put(entry.getKey(), entry.getValue().get(0));
        }

        paramsToSign.put("Timestamp", getISO8601Time(new Date()));
        paramsToSign.put("SignatureMethod", "HMAC-SHA1" /*signer.getSignerName()*/ );
        paramsToSign.put("SignatureVersion", "1.0" /*signer.getSignerVersion()*/);
        paramsToSign.put("SignatureNonce", getUniqueNonce());
        paramsToSign.put("AccessKeyId", ak_id);
        paramsToSign.put("Format", "json"); // TBD: or "application/json" ?
//        immutableMap.put("SignatureType", signer.getSignerType());

        paramsToSign.put("RegionId", region);
//        paramsToSign.putAll(bodyParams);
        if (null != ststoken) {
            paramsToSign.put("SecurityToken", ststoken);
        }

        final String strToSign = composeStringToSign(req.method(), paramsToSign);
        final String signature = signStringByHmacSHA1(strToSign, ak_secret + "&");

        final QueryStringEncoder encoder = new QueryStringEncoder(decoder.rawPath());
        for (final Map.Entry<String, String> entry : paramsToSign.entrySet()) {
            encoder.addParam(entry.getKey(), entry.getValue());
            LOG.info("add params {}/{}", entry.getKey(), entry.getValue());
        }
        encoder.addParam("Signature", signature);
        final String signedUri = encoder.toString();
        LOG.info("signedUri: {}", signedUri);
        req.setUri(signedUri);
    }
}
