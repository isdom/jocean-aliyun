package org.jocean.aliyun.ecs.internal;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;
import java.util.HashMap;
import java.util.Map;

import javax.inject.Inject;
import javax.ws.rs.QueryParam;

import org.jocean.aliyun.ecs.EcsAPI;
import org.jocean.aliyun.ecs.MetadataAPI;
import org.jocean.aliyun.sign.SignerV1;
import org.jocean.http.ContentUtil;
import org.jocean.http.RpcRunner;
import org.jocean.idiom.BeanFinder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;

import io.netty.handler.codec.http.HttpMethod;
import rx.Observable;
import rx.Observable.Transformer;
import rx.functions.Func1;

public class DefaultEcsAPI implements EcsAPI {
    private static final Logger LOG = LoggerFactory.getLogger(DefaultEcsAPI.class);

    // https://help.aliyun.com/document_detail/102988.html?spm=a2c4g.11186623.6.1069.118a79e0WI5Er2
    @Override
    public Transformer<RpcRunner, DescribeInstancesResponse> describeInstances(final String regionId,
            final String vpcId,
            final String instanceName
            ) {
        return runners -> {
            if (null != _ak_id && null != _ak_secret) {
                LOG.info("using ak_id:{} execute describeInstances", _ak_id);
                return doDescribeInstances(_ak_id, _ak_secret, null, regionId, vpcId, instanceName, runners);
            }
            else if (null != _roleName) {
                LOG.info("using roleName:{} execute describeInstances", _roleName);
                return _finder.find(MetadataAPI.class).flatMap(metaapi -> runners.compose(metaapi.getSTSToken(_roleName)))
                        .flatMap(stsresp -> doDescribeInstances(stsresp.getAccessKeyId(),
                                stsresp.getAccessKeySecret(), stsresp.getSecurityToken(),
                                regionId, vpcId, instanceName, runners));
            }
            else {
                return Observable.error(new RuntimeException("no valid ak_id/ak_secret or roleName"));
            }
        };
    }

    private Observable<DescribeInstancesResponse> doDescribeInstances(
            final String ak_id, final String ak_secret, final String ststoken,
            final String regionId, final String vpcId, final String instanceName,
            final Observable<RpcRunner> runners) {
        return runners.flatMap(runner -> runner.name("aliyun.ecs.describeInstances").execute(
                interact -> {
                    interact = interact.method(HttpMethod.GET)
                        .uri("https://ecs.aliyuncs.com")
                        .path("/")
                        .paramAsQuery("Action", "DescribeInstances")
                        .paramAsQuery("Version", "2014-05-26")
                        .paramAsQuery("RegionId", regionId);
                    if (null != vpcId) {
                        interact = interact.paramAsQuery("VpcId", vpcId);
                    }
                    if (null != instanceName) {
                        interact = interact.paramAsQuery("InstanceName", instanceName);
                    }

                    if (null != ststoken) {
                        return interact.onrequest(SignerV1.signRequest(regionId, ak_id, ak_secret, ststoken))
                                .responseAs(ContentUtil.ASJSON, DescribeInstancesResponse.class);
                    }
                    else {
                        return interact.onrequest(SignerV1.signRequest(regionId, ak_id, ak_secret))
                                .responseAs(ContentUtil.ASJSON, DescribeInstancesResponse.class);
                    }
                }
        ));
    }

    @Override
    public DescribeSpotPriceHistoryBuilder describeSpotPriceHistory() {
        return delegate(DescribeSpotPriceHistoryBuilder.class,
                new Func1<Map<String, Object>, Transformer<RpcRunner, DescribeSpotPriceHistoryResponse>>() {
                    @Override
                    public Transformer<RpcRunner, DescribeSpotPriceHistoryResponse> call(final Map<String, Object> params) {
                        return runners -> runners.flatMap(runner -> runner.name("aliyun.ecs.describeSpotPriceHistory").execute(
                                interact -> {
                                    interact = interact.method(HttpMethod.GET)
                                        .uri("https://ecs.aliyuncs.com")
                                        .path("/")
                                        .paramAsQuery("Action", "DescribeSpotPriceHistory")
                                        .paramAsQuery("Version", "2014-05-26");

                                    for (final Map.Entry<String, Object> entry : params.entrySet()) {
                                        interact = interact.paramAsQuery(entry.getKey(), entry.getValue().toString());
                                    }
//                                    if (null != ststoken) {
//                                        return interact.onrequest(SignerV1.signRequest(regionId, _ak_id, ak_secret, ststoken))
//                                                .responseAs(ContentUtil.ASJSON, DescribeSpotPriceHistoryResponse.class);
//                                    }
//                                    else {
                                        return interact.onrequest(SignerV1.signRequest(params.get("RegionId").toString(), _ak_id, _ak_secret))
                                                .responseAs(ContentUtil.ASJSON, DescribeSpotPriceHistoryResponse.class);
//                                    }
                                }
                        ));
                    }
        });
    }

    @SuppressWarnings("unchecked")
    private static <T, R> T delegate(final Class<T> intf, final Func1<Map<String, Object>, R> api) {
        final Map<String, Object> params = new HashMap<>();

        return (T) Proxy.newProxyInstance(
            Thread.currentThread().getContextClassLoader(),
            new Class<?>[]{intf},
            new InvocationHandler() {
                @Override
                public Object invoke(final Object proxy, final Method method, final Object[] args) throws Throwable {
                    if (null != args && args.length == 1) {
                        final QueryParam queryParam = method.getAnnotation(QueryParam.class);
                        if (null != queryParam) {
                            params.put(queryParam.value(), args[0]);
                        }
                        return proxy;
                    }
                    else if (null == args || args.length == 0) {
                        return api.call(params);
                    }

                    return null;
                }});
    }

    @Override
    public CreateInstanceBuilder createInstance() {
        return delegate(CreateInstanceBuilder.class,
                new Func1<Map<String, Object>, Transformer<RpcRunner, CreateInstanceResponse>>() {
                    @Override
                    public Transformer<RpcRunner, CreateInstanceResponse> call(final Map<String, Object> params) {
                        return runners -> runners.flatMap(runner -> runner.name("aliyun.ecs.createInstanceResponse").execute(
                                interact -> {
                                    interact = interact.method(HttpMethod.GET)
                                        .uri("https://ecs.aliyuncs.com")
                                        .path("/")
                                        .paramAsQuery("Action", "CreateInstance")
                                        .paramAsQuery("Version", "2014-05-26");

                                    for (final Map.Entry<String, Object> entry : params.entrySet()) {
                                        interact = interact.paramAsQuery(entry.getKey(), entry.getValue().toString());
                                    }
//                                    if (null != ststoken) {
//                                        return interact.onrequest(SignerV1.signRequest(regionId, _ak_id, ak_secret, ststoken))
//                                                .responseAs(ContentUtil.ASJSON, DescribeSpotPriceHistoryResponse.class);
//                                    }
//                                    else {
                                        return interact.onrequest(SignerV1.signRequest(params.get("RegionId").toString(), _ak_id, _ak_secret))
                                                .responseAs(ContentUtil.ASJSON, CreateInstanceResponse.class);
//                                    }
                                }));
                    }}
            );
    }

    @Value("${ak_id}")
    String _ak_id = null;

    @Value("${ak_secret}")
    String _ak_secret = null;

    @Inject
    BeanFinder _finder;

    @Value("${role}")
    String _roleName = null;
}
