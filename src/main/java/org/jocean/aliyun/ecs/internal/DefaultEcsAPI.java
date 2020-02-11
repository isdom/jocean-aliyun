package org.jocean.aliyun.ecs.internal;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;
import java.net.URI;
import java.util.HashMap;
import java.util.Map;

import javax.ws.rs.Path;
import javax.ws.rs.QueryParam;

import org.jocean.aliyun.annotation.ConstParams;
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
    public static <T, R> T delegate(final Class<T> intf,
            final String apiname,
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
                            final ConstParams constParams = method.getAnnotation(ConstParams.class);
                            // add const params mark by XXXBuilder interface
                            if (null != constParams) {
                                final String keyValues[] = constParams.value();
                                for (int i = 0; i < keyValues.length-1; i+=2) {
                                    params.put(keyValues[i], keyValues[i+1]);
                                }
                            }

                            return callapi(method, apiname, api, params);
                        }

                        return null;
                    }
                });
    }

    private static <R> Transformer<RpcRunner, R> callapi(
            final Method method,
            final String apiname,
            final Func1<Interact, Observable<R>> api,
            final Map<String, Object> params) {
        return (Transformer<RpcRunner, R>) runners -> runners.flatMap(runner -> runner.name(apiname).execute(
                interact -> {
                    final Path path = method.getAnnotation(Path.class);
                    if (null != path) {
                        try {
                            final URI uri = new URI(path.value());
                            final String colonWithPort = uri.getPort() > 0 ? ":" + uri.getPort() : "";
                            LOG.info("uri-- {}://{}{}{}", uri.getScheme(), uri.getHost(), colonWithPort, uri.getPath());
                            interact = interact.uri(uri.getScheme() + "://" + uri.getHost() + colonWithPort).path(uri.getPath());
                        } catch (final Exception e) {
                            return Observable.error(e);
                        }
                    }

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
        return delegate(AttachInstanceRamRoleBuilder.class,
                "aliyun.ecs.attachInstanceRamRole",
                interact -> interact.method(HttpMethod.GET)
                    .uri("https://ecs.aliyuncs.com")
                    .path("/")
                    .paramAsQuery("Action", "AttachInstanceRamRole")
                    .paramAsQuery("Version", "2014-05-26")
                    .responseAs(ContentUtil.ASJSON, AttachInstanceRamRoleResponse.class)
            );
    }

    @Override
    public DetachInstanceRamRoleBuilder detachInstanceRamRole() {
        return delegate(DetachInstanceRamRoleBuilder.class,
                "aliyun.ecs.detachInstanceRamRole",
                interact -> interact.method(HttpMethod.GET)
                    .uri("https://ecs.aliyuncs.com")
                    .path("/")
                    .paramAsQuery("Action", "DetachInstanceRamRole")
                    .paramAsQuery("Version", "2014-05-26")
                    .responseAs(ContentUtil.ASJSON, DetachInstanceRamRoleResponse.class)
            );
    }

    @Override
    public DescribeInstanceStatusBuilder describeInstanceStatus() {
        return delegate(DescribeInstanceStatusBuilder.class,
                "aliyun.ecs.describeInstanceStatus",
                interact -> interact.method(HttpMethod.GET)
                    .uri("https://ecs.aliyuncs.com")
                    .path("/")
                    .paramAsQuery("Action", "DescribeInstanceStatus")
                    .paramAsQuery("Version", "2014-05-26")
                    .responseAs(ContentUtil.ASJSON, DescribeInstanceStatusResponse.class)
                );
    }

    @Override
    public RenewInstanceBuilder renewInstance() {
        return delegate(RenewInstanceBuilder.class,
                "aliyun.ecs.renewInstance",
                interact -> interact.method(HttpMethod.GET)
                    .uri("https://ecs.aliyuncs.com")
                    .path("/")
                    .paramAsQuery("Action", "RenewInstance")
                    .paramAsQuery("Version", "2014-05-26")
                    .responseAs(ContentUtil.ASJSON, RenewInstanceResponse.class)
                );
    }

    @Override
    public ReactivateInstancesBuilder reactivateInstances() {
        return delegate(ReactivateInstancesBuilder.class,
                "aliyun.ecs.reactivateInstances",
                interact -> interact.method(HttpMethod.GET)
                    .uri("https://ecs.aliyuncs.com")
                    .path("/")
                    .paramAsQuery("Action", "ReactivateInstances")
                    .paramAsQuery("Version", "2014-05-26")
                    .responseAs(ContentUtil.ASJSON, ReactivateInstancesResponse.class)
                );
    }

    @Override
    public RedeployInstanceBuilder redeployInstance() {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public DescribeInstanceVncUrlBuilder describeInstanceVncUrl() {
        return delegate(DescribeInstanceVncUrlBuilder.class,
                "aliyun.ecs.describeInstanceVncUrl",
                interact -> interact.method(HttpMethod.GET)
                    .uri("https://ecs.aliyuncs.com")
                    .path("/")
                    .paramAsQuery("Action", "DescribeInstanceVncUrl")
                    .paramAsQuery("Version", "2014-05-26")
                    .responseAs(ContentUtil.ASJSON, DescribeInstanceVncUrlResponse.class)
                );
    }

    @Override
    public DescribeUserDataBuilder describeUserData() {
        return delegate(DescribeUserDataBuilder.class,
                "aliyun.ecs.describeUserData",
                interact -> interact.method(HttpMethod.GET)
                    .uri("https://ecs.aliyuncs.com")
                    .path("/")
                    .paramAsQuery("Action", "DescribeUserData")
                    .paramAsQuery("Version", "2014-05-26")
                    .responseAs(ContentUtil.ASJSON, DescribeUserDataResponse.class)
                );
    }

    @Override
    public DescribeInstanceAutoRenewAttributeBuilder describeInstanceAutoRenewAttribute() {
        return delegate(DescribeInstanceAutoRenewAttributeBuilder.class,
                "aliyun.ecs.describeInstanceAutoRenewAttribute",
                interact -> interact.method(HttpMethod.GET)
                    .uri("https://ecs.aliyuncs.com")
                    .path("/")
                    .paramAsQuery("Action", "DescribeInstanceAutoRenewAttribute")
                    .paramAsQuery("Version", "2014-05-26")
                    .responseAs(ContentUtil.ASJSON, DescribeInstanceAutoRenewAttributeResponse.class)
                );
    }

    @Override
    public DescribeInstanceRamRoleBuilder describeInstanceRamRole() {
        return delegate(DescribeInstanceRamRoleBuilder.class,
                "aliyun.ecs.describeInstanceRamRole",
                interact -> interact.method(HttpMethod.GET)
                    .uri("https://ecs.aliyuncs.com")
                    .path("/")
                    .paramAsQuery("Action", "DescribeInstanceRamRole")
                    .paramAsQuery("Version", "2014-05-26")
                    .responseAs(ContentUtil.ASJSON, DescribeInstanceRamRoleResponse.class)
                );
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

    @Override
    public DeleteSecurityGroupBuilder deleteSecurityGroup() {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public DescribeSecurityGroupAttributeBuilder describeSecurityGroupAttribute() {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public DescribeSecurityGroupsBuilder describeSecurityGroups() {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public DescribeSecurityGroupReferencesBuilder describeSecurityGroupReferences() {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public ModifySecurityGroupAttributeBuilder modifySecurityGroupAttribute() {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public ModifySecurityGroupEgressRuleBuilder modifySecurityGroupEgressRule() {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public ModifySecurityGroupPolicyBuilder modifySecurityGroupPolicy() {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public ModifySecurityGroupRuleBuilder modifySecurityGroupRule() {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public CreateImageBuilder createImage() {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public ImportImageBuilder importImage() {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public CopyImageBuilder copyImage() {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public CancelCopyImageBuilder cancelCopyImage() {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public ExportImageBuilder exportImage() {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public DescribeImagesBuilder describeImages() {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public DeleteImageBuilder deleteImage() {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public DescribeImageSharePermissionBuilder describeImageSharePermission() {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public ModifyImageAttributeBuilder modifyImageAttribute() {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public ModifyImageSharePermissionBuilder modifyImageSharePermission() {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public DescribeImageSupportInstanceTypesBuilder describeImageSupportInstanceTypes() {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public CreateSnapshotBuilder createSnapshot() {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public DeleteSnapshotBuilder deleteSnapshot() {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public DescribeSnapshotsBuilder describeSnapshots() {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public DescribeSnapshotsUsageBuilder describeSnapshotsUsage() {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public DescribeSnapshotPackageBuilder describeSnapshotPackage() {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public DescribeSnapshotLinksBuilder describeSnapshotLinks() {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public ModifySnapshotAttributeBuilder modifySnapshotAttribute() {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public CreateAutoSnapshotPolicyBuilder createAutoSnapshotPolicy() {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public ApplyAutoSnapshotPolicyBuilder applyAutoSnapshotPolicy() {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public CancelAutoSnapshotPolicyBuilder cancelAutoSnapshotPolicy() {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public DeleteAutoSnapshotPolicyBuilder deleteAutoSnapshotPolicy() {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public DescribeAutoSnapshotPolicyEXBuilder describeAutoSnapshotPolicyEX() {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public ModifyAutoSnapshotPolicyExBuilder modifyAutoSnapshotPolicyEx() {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public ListTagResourcesBuilder listTagResources() {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public TagResourcesBuilder tagResources() {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public UntagResourcesBuilder untagResources() {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public CancelTaskBuilder cancelTask() {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public DescribeTasksBuilder describeTasks() {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public DescribeTaskAttributeBuilder describeTaskAttribute() {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public DescribeAccountAttributesBuilder describeAccountAttributes() {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public DescribePriceBuilder describePrice() {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public JoinResourceGroupBuilder joinResourceGroup() {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public CreateCommandBuilder createCommand() {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public InstallCloudAssistantBuilder installCloudAssistant() {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public InvokeCommandBuilder invokeCommand() {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public StopInvocationBuilder stopInvocation() {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public DeleteCommandBuilder deleteCommand() {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public DescribeCloudAssistantStatusBuilder describeCloudAssistantStatus() {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public DescribeCommandsBuilder describeCommands() {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public DescribeInvocationsBuilder describeInvocations() {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public DescribeInvocationResultsBuilder describeInvocationResults() {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public RunCommandBuilder runCommand() {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public CreateNetworkInterfaceBuilder createNetworkInterface() {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public AttachNetworkInterfaceBuilder attachNetworkInterface() {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public AssignPrivateIpAddressesBuilder assignPrivateIpAddresses() {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public UnassignPrivateIpAddressesBuilder unassignPrivateIpAddresses() {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public DetachNetworkInterfaceBuilder detachNetworkInterface() {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public DeleteNetworkInterfaceBuilder deleteNetworkInterface() {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public DescribeNetworkInterfacesBuilder describeNetworkInterfaces() {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public ModifyNetworkInterfaceAttributeBuilder modifyNetworkInterfaceAttribute() {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public AssignIpv6AddressesBuilder assignIpv6Addresses() {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public UnassignIpv6AddressesBuilder unassignIpv6Addresses() {
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
