package org.jocean.aliyun.ivision.internal;

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

import org.jocean.aliyun.ivision.IvisionAPI;
import org.jocean.http.ContentUtil;
import org.jocean.http.RpcRunner;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;

import com.aliyuncs.DefaultAcsClient;
import com.aliyuncs.http.FormatType;
import com.aliyuncs.ivision.model.v20190308.CreateProjectRequest;
import com.aliyuncs.ivision.model.v20190308.CreateProjectResponse;
import com.aliyuncs.ivision.model.v20190308.DeleteIterationRequest;
import com.aliyuncs.ivision.model.v20190308.DeleteIterationResponse;
import com.aliyuncs.ivision.model.v20190308.DeletePredictDatasRequest;
import com.aliyuncs.ivision.model.v20190308.DeletePredictDatasResponse;
import com.aliyuncs.ivision.model.v20190308.DescribeIterationsRequest;
import com.aliyuncs.ivision.model.v20190308.DescribeIterationsResponse;
import com.aliyuncs.ivision.model.v20190308.DescribePredictDatasRequest;
import com.aliyuncs.ivision.model.v20190308.DescribePredictDatasResponse;
import com.aliyuncs.ivision.model.v20190308.PredictImageResponse;
import com.aliyuncs.profile.DefaultProfile;

import io.netty.handler.codec.http.HttpMethod;
import io.netty.handler.codec.http.HttpRequest;
import io.netty.handler.codec.http.QueryStringDecoder;
import io.netty.handler.codec.http.QueryStringEncoder;
import rx.Observable;
import rx.Observable.Transformer;
import rx.functions.Action1;

public class DefaultIvisionAPI implements IvisionAPI {

    private static final Logger LOG = LoggerFactory.getLogger(DefaultIvisionAPI.class);

    private Action1<Object> signRequest(){
        return obj -> {
            if (obj instanceof HttpRequest) {
                doSign((HttpRequest) obj);
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
        return value != null ? URLEncoder.encode(value, URL_ENCODING).replace("+", "%20")
                .replace("*", "%2A").replace("%7E", "~") : null;
    }

    String composeStringToSign(final HttpMethod method, final Map<String, String> queries) {
        final String[] sortedKeys = queries.keySet().toArray(new String[] {});
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

    private void doSign(final HttpRequest req) {
        final QueryStringDecoder decoder = new QueryStringDecoder(req.uri());

        final Map<String, String> paramsToSign = new HashMap<String, String>();

        for (final Map.Entry<String, List<String>> entry : decoder.parameters().entrySet()) {
            paramsToSign.put(entry.getKey(), entry.getValue().get(0));
        }

        paramsToSign.put("Timestamp", getISO8601Time(new Date()));
        paramsToSign.put("SignatureMethod", "HMAC-SHA1" /*signer.getSignerName()*/ );
        paramsToSign.put("SignatureVersion", "1.0" /*signer.getSignerVersion()*/);
        paramsToSign.put("SignatureNonce", getUniqueNonce());
        paramsToSign.put("AccessKeyId", _ak_id);
        paramsToSign.put("Format", "json"); // TBD: or "application/json" ?
//        immutableMap.put("SignatureType", signer.getSignerType());

        paramsToSign.put("RegionId", _region);
//        paramsToSign.putAll(bodyParams);

        final String strToSign = composeStringToSign(req.method(), paramsToSign);
        final String signature = signStringByHmacSHA1(strToSign, _ak_secret + "&");

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


    @Override
    public Transformer<RpcRunner, CreateProjectResponse> createProject(final String name, final String description, final String type) {

        return runners -> runners.flatMap(runner -> {
            final DefaultAcsClient client = new DefaultAcsClient(DefaultProfile.getProfile(_region, _ak_id, _ak_secret));
            final CreateProjectRequest request = new CreateProjectRequest();
            request.setAcceptFormat(FormatType.JSON);
            request.setName(name);
            request.setDescription(description);
            request.setProType(type);

            // If an error occurs, a ClientException or ServerException may be thrown.
            try {
                final CreateProjectResponse response = client.getAcsResponse(request);
                return Observable.just(response);
            } catch (final Exception e) {
                return Observable.error(e);
            }
        });
    }

    @Override
    public Transformer<RpcRunner, DescribeIterationsResponse> describeIterations(final String projectId) {

        return runners -> runners.flatMap(runner -> {
            final DefaultAcsClient client = new DefaultAcsClient(DefaultProfile.getProfile(_region, _ak_id, _ak_secret));
            final DescribeIterationsRequest request = new DescribeIterationsRequest();
            request.setAcceptFormat(FormatType.JSON);
            request.setProjectId(projectId);

            // If an error occurs, a ClientException or ServerException may be thrown.
            try {
                final DescribeIterationsResponse response = client.getAcsResponse(request);
                return Observable.just(response);
            } catch (final Exception e) {
                return Observable.error(e);
            }
        });
    }

    @Override
    public Transformer<RpcRunner, DeleteIterationResponse> deleteIteration(final String projectId, final String iterationId) {
        return runners -> runners.flatMap(runner -> {
            final DefaultAcsClient client = new DefaultAcsClient(DefaultProfile.getProfile(_region, _ak_id, _ak_secret));
            final DeleteIterationRequest request = new DeleteIterationRequest();

            request.setAcceptFormat(FormatType.JSON);
            request.setProjectId(projectId);
            request.setIterationId(iterationId);

            // If an error occurs, a ClientException or ServerException may be thrown.
            try {
                final DeleteIterationResponse response = client.getAcsResponse(request);
                return Observable.just(response);
            } catch (final Exception e) {
                return Observable.error(e);
            }
        });
    }

    @Override
    public Transformer<RpcRunner, PredictImageResponse> predictImage(final String projectId, final String iterationId, final String imgurl) {
        return runners -> runners.flatMap(runner ->
            /*
            final DefaultAcsClient client = new DefaultAcsClient(DefaultProfile.getProfile(_region, _ak_id, _ak_secret));

            final PredictImageRequest request = new PredictImageRequest();
            request.setAcceptFormat(FormatType.JSON);

            request.setProjectId(projectId);
            request.setIterationId(iterationId);
            request.setDataUrls(imgurl);

            // If an error occurs, a ClientException or ServerException may be thrown.
            try {
                final PredictImageResponse response = client.getAcsResponse(request);
                return Observable.just(response);
            } catch (final Exception e) {
                return Observable.error(e);
            }
            */
            runner.name("ivision.predictimage").execute(interact -> interact.method(HttpMethod.GET)
                            .uri("http://ivision.cn-beijing.aliyuncs.com")
                            .path("/")
                            .paramAsQuery("Action", "PredictImage")
                            .paramAsQuery("Version", "2019-03-08")
                            .paramAsQuery("ProjectId", projectId)
                            .paramAsQuery("IterationId", iterationId)
                            .paramAsQuery("DataUrls", imgurl)
                            .onrequest(signRequest())
                            .responseAs(ContentUtil.ASJSON, PredictImageResponse.class)
            ));
    }

    @Override
    public Transformer<RpcRunner, DescribePredictDatasResponse> describePredictDatas(final String projectId, final String iterationId) {
        return runners -> runners.flatMap(runner -> {
            final DefaultAcsClient client = new DefaultAcsClient(DefaultProfile.getProfile(_region, _ak_id, _ak_secret));

            final DescribePredictDatasRequest request = new DescribePredictDatasRequest();
            request.setAcceptFormat(FormatType.JSON);

            request.setProjectId(projectId);
            request.setIterationId(iterationId);

            // If an error occurs, a ClientException or ServerException may be thrown.
            try {
                final DescribePredictDatasResponse response = client.getAcsResponse(request);
                return Observable.just(response);
            } catch (final Exception e) {
                return Observable.error(e);
            }
        });
    }

    @Override
    public Transformer<RpcRunner, DeletePredictDatasResponse> deletePredictDatas(final String projectId, final String dataIds) {
        return runners -> runners.flatMap(runner -> {
            final DefaultAcsClient client = new DefaultAcsClient(DefaultProfile.getProfile(_region, _ak_id, _ak_secret));

            final DeletePredictDatasRequest request = new DeletePredictDatasRequest();
            request.setAcceptFormat(FormatType.JSON);

            request.setProjectId(projectId);
            request.setDataIds(dataIds);

            // If an error occurs, a ClientException or ServerException may be thrown.
            try {
                final DeletePredictDatasResponse response = client.getAcsResponse(request);
                return Observable.just(response);
            } catch (final Exception e) {
                return Observable.error(e);
            }
        });
    }

    @Value("${regionid}")
    String _region;

    @Value("${ak_id}")
    String _ak_id;

    @Value("${ak_secret}")
    String _ak_secret;
}
