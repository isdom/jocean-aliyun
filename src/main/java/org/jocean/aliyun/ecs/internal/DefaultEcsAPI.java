package org.jocean.aliyun.ecs.internal;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;
import java.util.HashMap;
import java.util.Map;

import javax.ws.rs.QueryParam;

import org.jocean.aliyun.ecs.EcsAPI;
import org.jocean.http.ContentUtil;
import org.jocean.http.Interact;
import org.jocean.http.RpcRunner;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import io.netty.handler.codec.http.HttpMethod;
import rx.Observable;
import rx.Observable.Transformer;
import rx.functions.Func1;

public class DefaultEcsAPI implements EcsAPI {
    private static final Logger LOG = LoggerFactory.getLogger(DefaultEcsAPI.class);

    @SuppressWarnings("unchecked")
    public static <T, R> T delegate(final Class<T> intf, final String apiname,
            final Func1<Interact, Observable<R>> api) {
        final Map<String, Object> params = new HashMap<>();

        return (T) Proxy.newProxyInstance(Thread.currentThread().getContextClassLoader(), new Class<?>[] { intf },
                new InvocationHandler() {
                    @Override
                    public Object invoke(final Object proxy, final Method method, final Object[] args)
                            throws Throwable {
                        if (null != args && args.length == 1) {
                            final QueryParam queryParam = method.getAnnotation(QueryParam.class);
                            if (null != queryParam) {
                                params.put(queryParam.value(), args[0]);
                            }
                            return proxy;
                        } else if (null == args || args.length == 0) {
                            return callapi(apiname, api, params);
                        }

                        return null;
                    }
                });
    }

    private static <R> Transformer<RpcRunner, R> callapi(final String apiname,
            final Func1<Interact, Observable<R>> api,
            final Map<String, Object> params) {
        return (Transformer<RpcRunner, R>) runners -> runners.flatMap(runner -> runner.name(apiname).execute(
                interact -> {
                    for (final Map.Entry<String, Object> entry : params.entrySet()) {
                        if (entry.getKey() != null && entry.getValue() != null) {
                            interact = interact.paramAsQuery(entry.getKey(), entry.getValue().toString());
                        }
                    }
                    return api.call(interact);
                }));
    }

    // https://help.aliyun.com/document_detail/102988.html?spm=a2c4g.11186623.6.1069.118a79e0WI5Er2
    @Override
    public DescribeInstancesBuilder describeInstances() {
        return delegate(DescribeInstancesBuilder.class,
                "aliyun.ecs.describeInstances",
                interact -> interact.method(HttpMethod.GET)
                    .uri("https://ecs.aliyuncs.com")
                    .path("/")
                    .paramAsQuery("Action", "DescribeInstances")
                    .paramAsQuery("Version", "2014-05-26")
                    .responseAs(ContentUtil.ASJSON, DescribeInstancesResponse.class)
                );

    }

    @Override
    public DescribeSpotPriceHistoryBuilder describeSpotPriceHistory() {
        return delegate(DescribeSpotPriceHistoryBuilder.class,
                "aliyun.ecs.describeSpotPriceHistory",
                interact -> interact.method(HttpMethod.GET)
                    .uri("https://ecs.aliyuncs.com")
                    .path("/")
                    .paramAsQuery("Action", "DescribeSpotPriceHistory")
                    .paramAsQuery("Version", "2014-05-26")
                    .responseAs(ContentUtil.ASJSON, DescribeSpotPriceHistoryResponse.class)
                );
    }

    @Override
    public CreateInstanceBuilder createInstance() {
        return delegate(CreateInstanceBuilder.class,
                "aliyun.ecs.createInstance",
                interact -> interact.method(HttpMethod.GET)
                    .uri("https://ecs.aliyuncs.com")
                    .path("/")
                    .paramAsQuery("Action", "CreateInstance")
                    .paramAsQuery("Version", "2014-05-26")
                    .responseAs(ContentUtil.ASJSON, CreateInstanceResponse.class)
            );
    }

    @Override
    public StartInstanceBuilder startInstance() {
        return delegate(StartInstanceBuilder.class,
                "aliyun.ecs.startInstance",
                interact -> interact.method(HttpMethod.GET)
                    .uri("https://ecs.aliyuncs.com")
                    .path("/")
                    .paramAsQuery("Action", "StartInstance")
                    .paramAsQuery("Version", "2014-05-26")
                    .responseAs(ContentUtil.ASJSON, StartInstanceResponse.class)
            );
    }

    @Override
    public RebootInstanceBuilder rebootInstance() {
        return delegate(RebootInstanceBuilder.class,
                "aliyun.ecs.rebootInstance",
                interact -> interact.method(HttpMethod.GET)
                    .uri("https://ecs.aliyuncs.com")
                    .path("/")
                    .paramAsQuery("Action", "RebootInstance")
                    .paramAsQuery("Version", "2014-05-26")
                    .responseAs(ContentUtil.ASJSON, RebootInstanceResponse.class)
            );
    }

    @Override
    public StopInstanceBuilder stopInstance() {
        return delegate(StopInstanceBuilder.class,
                "aliyun.ecs.stopInstance",
                interact -> interact.method(HttpMethod.GET)
                    .uri("https://ecs.aliyuncs.com")
                    .path("/")
                    .paramAsQuery("Action", "StopInstance")
                    .paramAsQuery("Version", "2014-05-26")
                    .responseAs(ContentUtil.ASJSON, StopInstanceResponse.class)
            );
    }

    @Override
    public DeleteInstanceBuilder deleteInstance() {
        return delegate(DeleteInstanceBuilder.class,
                "aliyun.ecs.deleteInstance",
                interact -> interact.method(HttpMethod.GET)
                    .uri("https://ecs.aliyuncs.com")
                    .path("/")
                    .paramAsQuery("Action", "DeleteInstance")
                    .paramAsQuery("Version", "2014-05-26")
                    .responseAs(ContentUtil.ASJSON, DeleteInstanceResponse.class)
            );
    }

    @Override
    public AttachInstanceRamRoleBuilder attachInstanceRamRole() {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public DetachInstanceRamRoleBuilder detachInstanceRamRole() {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public DescribeInstanceStatusBuilder describeInstanceStatus() {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public RenewInstanceBuilder renewInstance() {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public ReactivateInstancesBuilder reactivateInstances() {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public RedeployInstanceBuilder redeployInstance() {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public DescribeInstanceVncUrlBuilder describeInstanceVncUrl() {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public DescribeUserDataBuilder describeUserData() {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public DescribeInstanceAutoRenewAttributeBuilder describeInstanceAutoRenewAttribute() {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public DescribeInstanceRamRoleBuilder describeInstanceRamRole() {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public DescribeInstanceTypeFamiliesBuilder describeInstanceTypeFamilies() {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public DescribeInstanceTypesBuilder describeInstanceTypes() {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public ModifyInstanceVpcAttributeBuilder modifyInstanceVpcAttribute() {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public ModifyInstanceAttributeBuilder modifyInstanceAttribute() {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public ModifyInstanceVncPasswdBuilder modifyInstanceVncPasswd() {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public ModifyInstanceAutoReleaseTimeBuilder modifyInstanceAutoReleaseTime() {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public ModifyInstanceAutoRenewAttributeBuilder modifyInstanceAutoRenewAttribute() {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public ModifyInstanceChargeTypeBuilder modifyInstanceChargeType() {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public ModifyInstanceSpecBuilder modifyInstanceSpec() {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public ModifyPrepayInstanceSpecBuilder modifyPrepayInstanceSpec() {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public DescribeInstancesFullStatusBuilder describeInstancesFullStatus() {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public DescribeInstanceHistoryEventsBuilder describeInstanceHistoryEvents() {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public DescribeDisksFullStatusBuilder describeDisksFullStatus() {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public CancelSimulatedSystemEventsBuilder cancelSimulatedSystemEvents() {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public CreateSimulatedSystemEventsBuilder createSimulatedSystemEvents() {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public DescribeEniMonitorDataBuilder describeEniMonitorData() {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public DescribeDiskMonitorDataBuilder describeDiskMonitorData() {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public DescribeInstanceMonitorDataBuilder describeInstanceMonitorData() {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public GetInstanceScreenshotBuilder getInstanceScreenshot() {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public GetInstanceConsoleOutputBuilder getInstanceConsoleOutput() {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public DescribeSnapshotMonitorDataBuilder describeSnapshotMonitorData() {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public DescribeRegionsBuilder describeRegions() {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public DescribeZonesBuilder describeZones() {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public DescribeResourcesModificationBuilder describeResourcesModification() {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public DescribeAvailableResourceBuilder describeAvailableResource() {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public CreateKeyPairBuilder createKeyPair() {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public ImportKeyPairBuilder importKeyPair() {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public AttachKeyPairBuilder attachKeyPair() {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public DetachKeyPairBuilder detachKeyPair() {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public DeleteKeyPairsBuilder deleteKeyPairs() {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public DescribeKeyPairsBuilder describeKeyPairs() {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public AllocatePublicIpAddressBuilder allocatePublicIpAddress() {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public ConvertNatPublicIpToEipBuilder convertNatPublicIpToEip() {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public AttachClassicLinkVpcBuilder attachClassicLinkVpc() {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public DetachClassicLinkVpcBuilder detachClassicLinkVpc() {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public DescribeBandwidthLimitationBuilder describeBandwidthLimitation() {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public DescribeClassicLinkInstancesBuilder describeClassicLinkInstances() {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public ModifyInstanceNetworkSpecBuilder modifyInstanceNetworkSpec() {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public CreateSecurityGroupBuilder createSecurityGroup() {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public AuthorizeSecurityGroupBuilder authorizeSecurityGroup() {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public AuthorizeSecurityGroupEgressBuilder authorizeSecurityGroupEgress() {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public RevokeSecurityGroupBuilder revokeSecurityGroup() {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public RevokeSecurityGroupEgressBuilder revokeSecurityGroupEgress() {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public JoinSecurityGroupBuilder joinSecurityGroup() {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public LeaveSecurityGroupBuilder leaveSecurityGroup() {
        // TODO Auto-generated method stub
        return null;
    }

//    @Value("${role}")
//    String _roleName = null;

    /*
    Transformer<RpcRunner, DescribeInstancesResponse> describeInstances(final String regionId,
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
                        .flatMap(stsresp -> doDescribeInstances(
                                stsresp.getAccessKeyId(),
                                stsresp.getAccessKeySecret(),
                                stsresp.getSecurityToken(),
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
                        return interact.onrequest(SignerV1.signRequest(ak_id, ak_secret, ststoken))
                                .responseAs(ContentUtil.ASJSON, DescribeInstancesResponse.class);
                    }
                    else {
                        return interact.onrequest(SignerV1.signRequest(ak_id, ak_secret))
                                .responseAs(ContentUtil.ASJSON, DescribeInstancesResponse.class);
                    }
                }
        ));
    }
    */
}
