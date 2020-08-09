package org.jocean.aliyun.ecs;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.QueryParam;

import org.jocean.rpc.annotation.ConstParams;
import org.jocean.rpc.annotation.RpcBuilder;

import com.alibaba.fastjson.annotation.JSONField;

import rx.Observable;

// https://help.aliyun.com/document_detail/102988.html?spm=a2c4g.11186623.6.1069.118a79e0WI5Er2

@Path("https://ecs.aliyuncs.com/")
@ConstParams({"Version", "2014-05-26"})
public interface EcsAPI {

    interface PageableBuilder<T> {
        @QueryParam("PageNumber")
        T pageNumber(final Integer pageNumber);

        @QueryParam("PageSize")
        T pageSize(final Integer pageSize);
    }

    interface ECSAPIResponse {
        @JSONField(name="RequestId")
        String getRequestId();

        @JSONField(name="RequestId")
        void setRequestId(final String requestId);

        @JSONField(name="Code")
        String getCode();

        @JSONField(name="Code")
        void setCode(final String code);

        @JSONField(name="Message")
        String getMessage();

        @JSONField(name="Message")
        void setMessage(final String message);
    }

    interface Pageable {
        @JSONField(name="PageNumber")
        Integer getPageNumber();

        @JSONField(name="PageNumber")
        void setPageNumber(final Integer pageNumber);

        @JSONField(name="PageSize")
        Integer getPageSize();

        @JSONField(name="PageSize")
        void setPageSize(final Integer pageSize);

        @JSONField(name="TotalCount")
        Integer getTotalCount();

        @JSONField(name="TotalCount")
        void setTotalCount(final Integer totalCount);
    }

//    "SecurityGroupIds":{
//    "SecurityGroupId":[
//        "sg-2ze7v8o1cbogte75c8xz"
//        ]
//    },
    interface SecurityGroupIds {
        @JSONField(name="SecurityGroupId")
        String[] getSecurityGroupIdArray();

        @JSONField(name="SecurityGroupId")
        void setSecurityGroupIdArray(final String[] securityGroupIdArray);
    }

    interface OperationLocks {
        @JSONField(name="LockMsg")
        String getLockMsg();

        @JSONField(name="LockMsg")
        void setLockMsg(final String lockMsg);

        @JSONField(name="LockReason")
        String[] getLockReason();

        @JSONField(name="LockReason")
        void setLockReason(final String[] lockReason);
    }
//    "NetworkInterfaces":{
//        "NetworkInterface":[
//            {
//                "MacAddress":"00:16:3e:14:1c:4b",
//                "PrimaryIpAddress":"172.22.174.249",
//                "NetworkInterfaceId":"eni-2ze0ls028rmgcg5l74r2"
//            }
//        ]
//    },

    interface NetworkInterface {
        @JSONField(name="MacAddress")
        String getMacAddress();

        @JSONField(name="MacAddress")
        void setMacAddress(final String macAddress);

        @JSONField(name="PrimaryIpAddress")
        String getPrimaryIpAddress();

        @JSONField(name="PrimaryIpAddress")
        void setPrimaryIpAddress(final String primaryIpAddress);

        @JSONField(name="NetworkInterfaceId")
        String getNetworkInterfaceId();

        @JSONField(name="NetworkInterfaceId")
        void setNetworkInterfaceId(final String networkInterfaceId);
    }

    interface NetworkInterfaces {
        @JSONField(name="NetworkInterface")
        NetworkInterface[] getNetworkInterfaceArray();

        @JSONField(name="NetworkInterface")
        void setNetworkInterfaceArray(final NetworkInterface[] networkInterface);
    }

//  "VpcAttributes":{
//  "NatIpAddress":"",
//  "PrivateIpAddress":{
//      "IpAddress":[
//          "172.22.174.249"
//      ]
//  },
//  "VSwitchId":"vsw-2zebudaamrzr8u2oi3bkz",
//  "VpcId":"vpc-2ze3ekg08hfc97uo72p50"
//},

    interface IpAddress {
        @JSONField(name="IpAddress")
        String[] getIpAddressArray();

        @JSONField(name="IpAddress")
        void setIpAddressArray(final String[] ipAddressArray);
    }

    interface VpcAttributes {
        @JSONField(name="NatIpAddress")
        String getNatIpAddress();

        @JSONField(name="NatIpAddress")
        void setNatIpAddress(final String natIpAddress);

        @JSONField(name="PrivateIpAddress")
        IpAddress getPrivateIpAddress();

        @JSONField(name="PrivateIpAddress")
        void setPrivateIpAddress(final IpAddress privateIpAddress);

        @JSONField(name="VSwitchId")
        String getVSwitchId();

        @JSONField(name="VSwitchId")
        void setVSwitchId(final String vSwitchId);

        @JSONField(name="VpcId")
        String getVpcId();

        @JSONField(name="VpcId")
        void setVpcId(final String vpcId);
    }

    interface InstanceAttributesType {

        @JSONField(name="AutoReleaseTime")
        String getAutoReleaseTime();

        @JSONField(name="AutoReleaseTime")
        void setAutoReleaseTime(final String autoReleaseTime);

        @JSONField(name="Cpu")
        int getCpu();

        @JSONField(name="Cpu")
        void setCpu(final int Cpu);

        @JSONField(name="CreationTime")
        String getCreationTime();

        @JSONField(name="CreationTime")
        void setCreationTime(final String creationTime);

        @JSONField(name="CreditSpecification")
        String getCreditSpecification();

        @JSONField(name="CreditSpecification")
        void setCreditSpecification(final String creditSpecification);

        @JSONField(name="DeletionProtection")
        boolean getDeletionProtection();

        @JSONField(name="DeletionProtection")
        void setDeletionProtection(final boolean deletionProtection);

        @JSONField(name="DeploymentSetId")
        String getDeploymentSetId();

        @JSONField(name="DeploymentSetId")
        void setDeploymentSetId(final String deploymentSetId);

        @JSONField(name="Description")
        String getDescription();

        @JSONField(name="Description")
        void setDescription(final String description);

        @JSONField(name="DeviceAvailable")
        boolean getDeviceAvailable();

        @JSONField(name="DeviceAvailable")
        void setDeviceAvailable(final boolean deviceAvailable);

        @JSONField(name="ExpiredTime")
        String getExpiredTime();

        @JSONField(name="ExpiredTime")
        void setExpiredTime(final String expiredTime);

        @JSONField(name="GPUAmount")
        int getGPUAmount();

        @JSONField(name="GPUAmount")
        void setGPUAmount(final int gpuAmount);

        @JSONField(name="GPUSpec")
        String getGPUSpec();

        @JSONField(name="GPUSpec")
        void setGPUSpec(final String gpuSpec);

        @JSONField(name="HostName")
        String getHostName();

        @JSONField(name="HostName")
        void setHostName(final String hostName);

        @JSONField(name="ImageId")
        String getImageId();

        @JSONField(name="ImageId")
        void setImageId(final String imageId);

        @JSONField(name="InnerIpAddress")
        IpAddress getInnerIpAddress();

        @JSONField(name="InnerIpAddress")
        void setInnerIpAddress(final IpAddress innerIpAddress);

        @JSONField(name="InstanceChargeType")
        String getInstanceChargeType();

        @JSONField(name="InstanceChargeType")
        void setInstanceChargeType(final String instanceChargeType);

        @JSONField(name="InstanceId")
        String getInstanceId();

        @JSONField(name="InstanceId")
        void setInstanceId(final String instanceId);

        @JSONField(name="InstanceName")
        String getInstanceName();

        @JSONField(name="InstanceName")
        void setInstanceName(final String instanceName);

        @JSONField(name="InstanceNetworkType")
        String getInstanceNetworkType();

        @JSONField(name="InstanceNetworkType")
        void setInstanceNetworkType(final String instanceNetworkType);

        @JSONField(name="InstanceType")
        String getInstanceType();

        @JSONField(name="InstanceType")
        void setInstanceType(final String instanceType);

        @JSONField(name="InstanceTypeFamily")
        String getInstanceTypeFamily();

        @JSONField(name="InstanceTypeFamily")
        void setInstanceTypeFamily(final String instanceTypeFamily);

        @JSONField(name="InternetChargeType")
        String getInternetChargeType();

        @JSONField(name="InternetChargeType")
        void setInternetChargeType(final String internetChargeType);

        @JSONField(name="IoOptimized")
        boolean getIoOptimized();

        @JSONField(name="IoOptimized")
        void setIoOptimized(final boolean ioOptimized);

        @JSONField(name="KeyPairName")
        String getKeyPairName();

        @JSONField(name="KeyPairName")
        void setKeyPairName(final String keyPairName);

        @JSONField(name="LocalStorageAmount")
        Integer getLocalStorageAmount();

        @JSONField(name="LocalStorageAmount")
        void setLocalStorageAmount(final Integer localStorageAmount);

        @JSONField(name="LocalStorageCapacity")
        Long getLocalStorageCapacity();

        @JSONField(name="LocalStorageCapacity")
        void setLocalStorageCapacity(final Long localStorageCapacity);

        @JSONField(name="Memory")
        int getMemory();

        @JSONField(name="Memory")
        void setMemory(final int memory);

        @JSONField(name="NetworkInterfaces")
        NetworkInterfaces getNetworkInterfaces();

        @JSONField(name="NetworkInterfaces")
        void setNetworkInterfaces(final NetworkInterfaces networkInterfaces);

        @JSONField(name="OSName")
        String getOSName();

        @JSONField(name="OSName")
        void setOSName(final String osName);

        @JSONField(name="OSNameEn")
        String getOSNameEn();

        @JSONField(name="OSNameEn")
        void setOSNameEn(final String osNameEn);

        @JSONField(name="OSType")
        String getOSType();

        @JSONField(name="OSType")
        void setOSType(final String osType);

        @JSONField(name="OperationLocks")
        OperationLocks getOperationLocks();

        @JSONField(name="OperationLocks")
        void setOperationLocks(final OperationLocks operationLocks);

        @JSONField(name="PublicIpAddress")
        IpAddress getPublicIpAddress();

        @JSONField(name="PublicIpAddress")
        void setPublicIpAddress(final IpAddress publicIpAddress);

        @JSONField(name="Recyclable")
        boolean getRecyclable();

        @JSONField(name="Recyclable")
        void setRecyclable(final boolean recyclable);

        @JSONField(name="RegionId")
        String getRegionId();

        @JSONField(name="RegionId")
        void setRegionId(final String regionId);

        @JSONField(name="ResourceGroupId")
        String getResourceGroupId();

        @JSONField(name="ResourceGroupId")
        void setResourceGroupId(final String resourceGroupId);

        @JSONField(name="SaleCycle")
        String getSaleCycle();

        @JSONField(name="SaleCycle")
        void setSaleCycle(final String saleCycle);

        @JSONField(name="SecurityGroupIds")
        SecurityGroupIds getSecurityGroupIds();

        @JSONField(name="SecurityGroupIds")
        void setSecurityGroupIds(final SecurityGroupIds securityGroupIds);

        @JSONField(name="SerialNumber")
        String getSerialNumber();

        @JSONField(name="SerialNumber")
        void setSerialNumber(final String serialNumber);

        @JSONField(name="SpotPriceLimit")
        float getSpotPriceLimit();

        @JSONField(name="SpotPriceLimit")
        void setSpotPriceLimit(final float spotPriceLimit);

        @JSONField(name="SpotStrategy")
        String getSpotStrategy();

        @JSONField(name="SpotStrategy")
        void setSpotStrategy(final String spotStrategy);

        @JSONField(name="StartTime")
        String getStartTime();

        @JSONField(name="StartTime")
        void setStartTime(final String startTime);

        @JSONField(name="Status")
        String getStatus();

        @JSONField(name="Status")
        void setStatus(final String status);

        @JSONField(name="StoppedMode")
        String getStoppedMode();

        @JSONField(name="StoppedMode")
        void setStoppedMode(final String stoppedMode);

//        @JSONField(name="Tags")
//        public String getTags();
//
//        @JSONField(name="Tags")
//        public void setTags(final String tags);

        @JSONField(name="VpcAttributes")
        VpcAttributes getVpcAttributes();

        @JSONField(name="VpcAttributes")
        void setVpcAttributes(final VpcAttributes vpcAttributes);

        @JSONField(name="ZoneId")
        String getZoneId();

        @JSONField(name="ZoneId")
        void setZoneId(final String zoneId);
    }

    interface InstanceSet {
        @JSONField(name="Instance")
        InstanceAttributesType[] getInstance();

        @JSONField(name="Instance")
        void setInstance(final InstanceAttributesType[] instances);
    }

    interface DescribeInstancesResponse extends ECSAPIResponse, Pageable {
        @JSONField(name="Instances")
        InstanceSet getInstances();

        @JSONField(name="Instances")
        void setInstances(final InstanceSet instanceSet);
    }

    @RpcBuilder
    interface DescribeInstancesBuilder {
        //  String   是   cn-hangzhou
        //  实例所属的地域ID。您可以调用DescribeRegions查看最新的阿里云地域列表。
        @QueryParam("RegionId")
        DescribeInstancesBuilder regionId(final String regionId);

        @QueryParam("VpcId")
        DescribeInstancesBuilder vpcId(final String vpcId);

        @QueryParam("InstanceName")
        DescribeInstancesBuilder instanceName(final String instanceName);

        @GET
        @ConstParams({"Action", "DescribeInstances"})
        Observable<DescribeInstancesResponse> call();
    }

    DescribeInstancesBuilder describeInstances();

    // DescribeSpotPriceHistory: https://help.aliyun.com/document_detail/60400.html?spm=a2c4g.11186623.6.1099.74662eafdAxS1p

    interface SpotPriceType {
        @JSONField(name="InstanceType")
        String getInstanceType();

        @JSONField(name="InstanceType")
        void setInstanceType(final String instanceType);

        @JSONField(name="IoOptimized")
        String getIoOptimized();

        @JSONField(name="IoOptimized")
        void setIoOptimized(final String ioOptimized);

        @JSONField(name="NetworkType")
        String getNetworkType();

        @JSONField(name="NetworkType")
        void setNetworkType(final String networkType);

        @JSONField(name="OriginPrice")
        float getOriginPrice();

        @JSONField(name="OriginPrice")
        void setOriginPrice(final float originPrice);

        @JSONField(name="SpotPrice")
        float getSpotPrice();

        @JSONField(name="SpotPrice")
        void setSpotPrice(final float spotPrice);

        @JSONField(name="Timestamp")
        String getTimestamp();

        @JSONField(name="Timestamp")
        void setTimestamp(final String timestamp);

        @JSONField(name="ZoneId")
        String getZoneId();

        @JSONField(name="ZoneId")
        void setZoneId(final String zoneId);
    }

    interface SpotPrices {
        @JSONField(name="SpotPriceType")
        SpotPriceType[] getSpotPriceTypeArray();

        @JSONField(name="SpotPriceType")
        void setSpotPriceTypeArray(final SpotPriceType[] spotPriceTypeArray);
    }

    interface DescribeSpotPriceHistoryResponse extends ECSAPIResponse {
        @JSONField(name="Currency")
        String getCurrency();

        @JSONField(name="Currency")
        void setCurrency(final String currency);

        @JSONField(name="NextOffset")
        Integer getNextOffset();

        @JSONField(name="NextOffset")
        void setNextOffset(final Integer nextOffset);

        @JSONField(name="SpotPrices")
        SpotPrices getSpotPrices();

        @JSONField(name="SpotPrices")
        void setSpotPrices(final SpotPrices spotPrices);
    }

    @RpcBuilder
    interface DescribeSpotPriceHistoryBuilder {
        //  String   是   cn-hangzhou
        //  实例所属的地域ID。您可以调用DescribeRegions查看最新的阿里云地域列表。
        @QueryParam("RegionId")
        DescribeSpotPriceHistoryBuilder regionId(final String regionId);

        //  String  是   ecs.t1.xsmall
        //  实例规格。
        @QueryParam("InstanceType")
        DescribeSpotPriceHistoryBuilder instanceType(final String instanceType);

        //  String  是   vpc
        //  抢占式实例网络类型。取值范围：
        //  classic：表示抢占式实例的网络类型为经典网络。
        //  vpc：表示抢占式实例的网络类型为专有网络。
        @QueryParam("NetworkType")
        DescribeSpotPriceHistoryBuilder networkType(final String networkType);

        @GET
        @ConstParams({"Action", "DescribeSpotPriceHistory"})
        Observable<DescribeSpotPriceHistoryResponse> call();
    }

    DescribeSpotPriceHistoryBuilder describeSpotPriceHistory();

    // CreateInstance: https://help.aliyun.com/document_detail/25499.html?spm=a2c4g.11186623.6.1083.73643ff5dezPxV
    interface CreateInstanceResponse extends ECSAPIResponse {
        @JSONField(name="InstanceId")
        String getInstanceId();

        @JSONField(name="InstanceId")
        void setInstanceId(final String instanceId);
    }

    @RpcBuilder
    interface CreateInstanceBuilder {
        @QueryParam("ClientToken")
        CreateInstanceBuilder clientToken(final String clientToken);

        @QueryParam("DryRun")
        CreateInstanceBuilder dryRun(final boolean dryRun);

        @QueryParam("ImageId")
        CreateInstanceBuilder imageId(final String imageId);

        @QueryParam("InstanceType")
        CreateInstanceBuilder instanceType(final String instanceType);

        @QueryParam("RegionId")
        CreateInstanceBuilder regionId(final String regionId);

        @QueryParam("SecurityGroupId")
        CreateInstanceBuilder securityGroupId(final String securityGroupId);

        @QueryParam("InstanceName")
        CreateInstanceBuilder instanceName(final String instanceName);

        @QueryParam("AutoRenew")
        CreateInstanceBuilder autoRenew(final boolean autoRenew);

        @QueryParam("AutoRenewPeriod")
        CreateInstanceBuilder autoRenewPeriod(final int autoRenewPeriod);

        @QueryParam("InternetChargeType")
        CreateInstanceBuilder internetChargeType(final String internetChargeType);

        @QueryParam("InternetMaxBandwidthIn")
        CreateInstanceBuilder internetMaxBandwidthIn(final int internetMaxBandwidthIn);

        @QueryParam("InternetMaxBandwidthOut")
        CreateInstanceBuilder internetMaxBandwidthOut(final int internetMaxBandwidthOut);

        @QueryParam("HostName")
        CreateInstanceBuilder hostName(final String hostName);

        @QueryParam("Password")
        CreateInstanceBuilder password(final String password);

        @QueryParam("PasswordInherit")
        CreateInstanceBuilder passwordInherit(final boolean passwordInherit);

        @QueryParam("DeploymentSetId")
        CreateInstanceBuilder deploymentSetId(final String deploymentSetId);

        @QueryParam("ZoneId")
        CreateInstanceBuilder zoneId(final String zoneId);

        @QueryParam("ClusterId")
        CreateInstanceBuilder clusterId(final String clusterId);

        @QueryParam("VlanId")
        CreateInstanceBuilder vlanId(final String vlanId);

        @QueryParam("InnerIpAddress")
        CreateInstanceBuilder innerIpAddress(final String innerIpAddress);

        @QueryParam("SystemDisk.Size")
        CreateInstanceBuilder systemDiskSize(final int systemDiskSize);

        @QueryParam("SystemDisk.Category")
        CreateInstanceBuilder systemDiskCategory(final String systemDiskCategory);

        @QueryParam("SystemDisk.DiskName")
        CreateInstanceBuilder systemDiskDiskName(final String systemDiskDiskName);

        @QueryParam("SystemDisk.Description")
        CreateInstanceBuilder systemDiskDescription(final String systemDiskDescription);

        @QueryParam("Description")
        CreateInstanceBuilder description(final String description);

        @QueryParam("VSwitchId")
        CreateInstanceBuilder vSwitchId(final String vSwitchId);

        @QueryParam("PrivateIpAddress")
        CreateInstanceBuilder privateIpAddress(final String privateIpAddress);

        @QueryParam("IoOptimized")
        CreateInstanceBuilder ioOptimized(final String ioOptimized);

        @QueryParam("UseAdditionalService")
        CreateInstanceBuilder useAdditionalService(final boolean useAdditionalService);

        @QueryParam("InstanceChargeType")
        CreateInstanceBuilder instanceChargeType(final String instanceChargeType);

        @QueryParam("Period")
        CreateInstanceBuilder period(final int period);

        @QueryParam("PeriodUnit")
        CreateInstanceBuilder periodUnit(final String periodUnit);

        @QueryParam("UserData")
        CreateInstanceBuilder userData(final String userData);

        @QueryParam("SpotStrategy")
        CreateInstanceBuilder spotStrategy(final String spotStrategy);

        @QueryParam("SpotPriceLimit")
        CreateInstanceBuilder spotPriceLimit(final float spotPriceLimit);

        @QueryParam("KeyPairName")
        CreateInstanceBuilder keyPairName(final String keyPairName);

        @QueryParam("SpotInterruptionBehavior")
        CreateInstanceBuilder spotInterruptionBehavior(final String spotInterruptionBehavior);

        @QueryParam("RamRoleName")
        CreateInstanceBuilder ramRoleName(final String ramRoleName);

        @QueryParam("SecurityEnhancementStrategy")
        CreateInstanceBuilder securityEnhancementStrategy(final String securityEnhancementStrategy);

        @QueryParam("ResourceGroupId")
        CreateInstanceBuilder resourceGroupId(final String resourceGroupId);

        @QueryParam("HpcClusterId")
        CreateInstanceBuilder hpcClusterId(final String hpcClusterId);

        @QueryParam("DedicatedHostId")
        CreateInstanceBuilder dedicatedHostId(final String dedicatedHostId);

        @QueryParam("CreditSpecification")
        CreateInstanceBuilder creditSpecification(final String creditSpecification);

        @QueryParam("DeletionProtection")
        CreateInstanceBuilder deletionProtection(final boolean deletionProtection);

        @QueryParam("SystemDisk.PerformanceLevel")
        CreateInstanceBuilder systemDiskPerformanceLevel(final String systemDiskPerformanceLevel);

        @QueryParam("Affinity")
        CreateInstanceBuilder affinity(final String affinity);

        @QueryParam("Tenancy")
        CreateInstanceBuilder tenancy(final String tenancy);

        @GET
        @ConstParams({"Action", "CreateInstance"})
        Observable<CreateInstanceResponse> call();
    }

    CreateInstanceBuilder createInstance();

    interface StartInstanceResponse extends ECSAPIResponse {
    }

    @RpcBuilder
    interface StartInstanceBuilder {
        @QueryParam("InstanceId")
        StartInstanceBuilder instanceId(final String instanceId);

        @QueryParam("InitLocalDisk")
        StartInstanceBuilder initLocalDisk(final boolean init);

        @QueryParam("OwnerAccount")
        StartInstanceBuilder ownerAccount(final String ownerAccount);

        @GET
        @ConstParams({"Action", "StartInstance"})
        Observable<StartInstanceResponse> call();
    }

    StartInstanceBuilder startInstance();

    interface RebootInstanceResponse extends ECSAPIResponse {
    }

    @RpcBuilder
    interface RebootInstanceBuilder {
        @QueryParam("InstanceId")
        RebootInstanceBuilder instanceId(final String instanceId);

        @QueryParam("DryRun")
        RebootInstanceBuilder dryRun(final boolean dryRun);

        @QueryParam("ForceStop")
        RebootInstanceBuilder forceStop(final boolean forceStop);

        @GET
        @ConstParams({"Action", "RebootInstance"})
        Observable<RebootInstanceResponse> call();
    }

    RebootInstanceBuilder rebootInstance();

    interface StopInstanceResponse extends ECSAPIResponse {
    }

    @RpcBuilder
    interface StopInstanceBuilder {
        @QueryParam("InstanceId")
        StopInstanceBuilder instanceId(final String instanceId);

        @QueryParam("StoppedMode")
        StopInstanceBuilder stoppedMode(final String stoppedMode);

        @QueryParam("ForceStop")
        StopInstanceBuilder forceStop(final boolean forceStop);

        @QueryParam("ConfirmStop")
        StopInstanceBuilder confirmStop(final boolean confirmStop);

        @GET
        @ConstParams({"Action", "StopInstance"})
        Observable<StopInstanceResponse> call();
    }

    StopInstanceBuilder stopInstance();

    interface DeleteInstanceResponse extends ECSAPIResponse {
    }

    @RpcBuilder
    interface DeleteInstanceBuilder {
        @QueryParam("InstanceId")
        DeleteInstanceBuilder instanceId(final String instanceId);

        @QueryParam("Force")
        DeleteInstanceBuilder force(final boolean force);

        @QueryParam("TerminateSubscription")
        DeleteInstanceBuilder terminateSubscription(final boolean terminateSubscription);

        @GET
        @ConstParams({"Action", "DeleteInstance"})
        Observable<DeleteInstanceResponse> call();
    }

    DeleteInstanceBuilder deleteInstance();

    interface AttachInstanceRamRoleResult {
        @JSONField(name="Success")
        boolean getSuccess();

        @JSONField(name="Success")
        void setSuccess(final boolean success);

        @JSONField(name="Code")
        String getCode();

        @JSONField(name="Code")
        void setCode(final String code);

        @JSONField(name="InstanceId")
        String getInstanceId();

        @JSONField(name="InstanceId")
        void setInstanceId(final String instanceId);

        @JSONField(name="Message")
        String getMessage();

        @JSONField(name="Message")
        void setMessage(final String message);
    }

    interface AttachInstanceRamRoleResults {
        @JSONField(name="AttachInstanceRamRoleResult")
        AttachInstanceRamRoleResult[] getAttachInstanceRamRoleResultArray();

        @JSONField(name="AttachInstanceRamRoleResult")
        void setAttachInstanceRamRoleResultArray(final AttachInstanceRamRoleResult[] attachInstanceRamRoleResults);
    }

    interface AttachInstanceRamRoleResponse extends ECSAPIResponse {
        @JSONField(name="AttachInstanceRamRoleResults")
        AttachInstanceRamRoleResults getAttachInstanceRamRoleResults();

        @JSONField(name="AttachInstanceRamRoleResults")
        void setAttachInstanceRamRoleResults(final AttachInstanceRamRoleResults attachInstanceRamRoleResults);

        @JSONField(name="RamRoleName")
        String getRamRoleName();

        @JSONField(name="RamRoleName")
        void setRamRoleName(final String ramRoleName);

        @JSONField(name="FailCount")
        Integer getFailCount();

        @JSONField(name="FailCount")
        void setFailCount(final Integer failCount);

        @JSONField(name="TotalCount")
        Integer getTotalCount();

        @JSONField(name="TotalCount")
        void setTotalCount(final Integer totalCount);
    }

    @RpcBuilder
    interface AttachInstanceRamRoleBuilder {
        //  必选   [“i-bp14ss25xca5ex1u****”, “i-bp154z5o1qjalfse****”, “i-bp10ws62o04ubhvi****”…]
        //  实例ID。取值可以由多个实例ID组成一个JSON数组，最多支持100个ID，ID之间用半角逗号（,）隔开。
        @QueryParam("InstanceIds")
        AttachInstanceRamRoleBuilder instanceIds(final String[] instanceIds);

        //  必选   RamRoleTest    实例RAM角色名称。您可以使用 RAM API ListRoles查询您已创建的实例RAM角色。
        @QueryParam("RamRoleName")
        AttachInstanceRamRoleBuilder ramRoleName(final String ramRoleName);

        //  必选   cn-hangzhou    地域ID。
        @QueryParam("RegionId")
        AttachInstanceRamRoleBuilder regionId(final String regionId);

        @GET
        @ConstParams({"Action", "AttachInstanceRamRole"})
        Observable<AttachInstanceRamRoleResponse> call();
    }

    AttachInstanceRamRoleBuilder attachInstanceRamRole();

    interface InstanceRamRoleSet {
        @JSONField(name="InstanceId")
        String getInstanceId();

        @JSONField(name="InstanceId")
        void setInstanceId(final String instanceId);

        @JSONField(name="RamRoleName")
        String getRamRoleName();

        @JSONField(name="RamRoleName")
        void setRamRoleName(final String ramRoleName);
    }

    interface DetachInstanceRamRoleResult {
        @JSONField(name="InstanceId")
        String getInstanceId();

        @JSONField(name="InstanceId")
        void setInstanceId(final String instanceId);

        @JSONField(name="Success")
        boolean getSuccess();

        @JSONField(name="Success")
        void setSuccess(final boolean success);

        @JSONField(name="Code")
        String getCode();

        @JSONField(name="Code")
        void setCode(final String code);

        @JSONField(name="Message")
        String getMessage();

        @JSONField(name="Message")
        void setMessage(final String message);

        @JSONField(name="InstanceRamRoleSet")
        InstanceRamRoleSet getInstanceRamRoleSets();

        @JSONField(name="InstanceRamRoleSet")
        void setInstanceRamRoleSets(final InstanceRamRoleSet instanceRamRoleSets);
    }

    interface DetachInstanceRamRoleResults {
        @JSONField(name="DetachInstanceRamRoleResult")
        DetachInstanceRamRoleResult[] getDetachInstanceRamRoleResult();

        @JSONField(name="DetachInstanceRamRoleResult")
        void setDetachInstanceRamRoleResult(final DetachInstanceRamRoleResult[] detachInstanceRamRoleResult);
    }

    interface DetachInstanceRamRoleResponse extends ECSAPIResponse {
        @JSONField(name="DetachInstanceRamRoleResults")
        DetachInstanceRamRoleResults getDetachInstanceRamRoleResults();

        @JSONField(name="DetachInstanceRamRoleResults")
        void setDetachInstanceRamRoleResults(final DetachInstanceRamRoleResults detachInstanceRamRoleResults);

        @JSONField(name="RamRoleName")
        String getRamRoleName();

        @JSONField(name="RamRoleName")
        void setRamRoleName(final String ramRoleName);

        @JSONField(name="FailCount")
        Integer getFailCount();

        @JSONField(name="FailCount")
        void setFailCount(final Integer failCount);

        @JSONField(name="TotalCount")
        Integer getTotalCount();

        @JSONField(name="TotalCount")
        void setTotalCount(final Integer totalCount);
    }

    @RpcBuilder
    interface DetachInstanceRamRoleBuilder {
        @QueryParam("InstanceIds")
        DetachInstanceRamRoleBuilder instanceIds(final String[] instanceIds);

        @QueryParam("RamRoleName")
        DetachInstanceRamRoleBuilder ramRoleName(final String ramRoleName);

        @QueryParam("RegionId")
        DetachInstanceRamRoleBuilder regionId(final String regionId);

        @GET
        @ConstParams({"Action", "DetachInstanceRamRole"})
        Observable<DetachInstanceRamRoleResponse> call();
    }

    DetachInstanceRamRoleBuilder detachInstanceRamRole();

    interface InstanceStatus {
        @JSONField(name="InstanceId")
        String getInstanceId();

        @JSONField(name="InstanceId")
        void setInstanceId(final String instanceId);

        @JSONField(name="Status")
        String getStatus();

        @JSONField(name="Status")
        void setStatus(final String status);
    }

    interface InstanceStatuses  {
        @JSONField(name="InstanceStatus")
        InstanceStatus[] getInstanceStatus();

        @JSONField(name="InstanceStatus")
        void setInstanceStatus(final InstanceStatus[] instanceStatuses);
    }

    interface DescribeInstanceStatusResponse extends ECSAPIResponse, Pageable {
        @JSONField(name="InstanceStatuses")
        InstanceStatuses getInstanceStatuses();

        @JSONField(name="InstanceStatuses")
        void setInstanceStatuses(final InstanceStatuses instanceStatuses);
    }

    // https://help.aliyun.com/document_detail/25505.html?spm=a2c4g.11186623.6.1204.7c3f649fZpm8hA
    @RpcBuilder
    interface DescribeInstanceStatusBuilder extends PageableBuilder<DescribeInstanceStatusBuilder>  {

        @QueryParam("RegionId")
        DescribeInstanceStatusBuilder regionId(final String regionId);

        @QueryParam("ClusterId")
        DescribeInstanceStatusBuilder clusterId(final String clusterId);

        @QueryParam("ZoneId")
        DescribeInstanceStatusBuilder zoneId(final String zoneId);

        @GET
        @ConstParams({"Action", "DescribeInstanceStatus"})
        Observable<DescribeInstanceStatusResponse> call();
    }

    DescribeInstanceStatusBuilder describeInstanceStatus();

    interface RenewInstanceResponse extends ECSAPIResponse {

    }

    @RpcBuilder
    interface RenewInstanceBuilder {
        // 必选    i-bp67acfmxazb4ph***            需要续费的实例ID。
        @QueryParam("InstanceId")
        RenewInstanceBuilder instanceId(final String instanceId);

        /* 必选
        包年包月续费时长。一旦指定了DedicatedHostId，则取值范围不能超过专有宿主机的订阅时长。取值范围：

        PeriodUnit=Week时，Period取值：1~4
        PeriodUnit=Month时，Period取值：1~12, 24, 36, 48, 60
        */
        @QueryParam("Period")
        RenewInstanceBuilder period(final int period);

        // 可选    Month                          续费时长的时间单位，即参数Period的单位。取值范围： Week / Month（默认）
        @QueryParam("PeriodUnit")
        RenewInstanceBuilder periodUnit(final String periodUnit);

        // 可选    0c593ea1-3bea-11e9-b96b-88e9fe637760   保证请求幂等性。从您的客户端生成一个参数值，确保不同请求间该参数值唯一。ClientToken只支持ASCII字符，且不能超过64个字符。
        @QueryParam("ClientToken")
        RenewInstanceBuilder clientToken(final String clientToken);

        @GET
        @ConstParams({"Action", "RenewInstance"})
        Observable<RenewInstanceResponse> call();
    }

    RenewInstanceBuilder renewInstance();

    interface ReactivateInstancesResponse extends ECSAPIResponse {

    }

    @RpcBuilder
    interface ReactivateInstancesBuilder {
        //  必选   需要重开机的实例 ID。
        @QueryParam("InstanceId")
        ReactivateInstancesBuilder instanceId(final String instanceId);

        @GET
        @ConstParams({"Action", "ReactivateInstances"})
        Observable<ReactivateInstancesResponse> call();
    }

    ReactivateInstancesBuilder reactivateInstances();

    interface RedeployInstanceResponse extends ECSAPIResponse {

    }

    @RpcBuilder
    interface RedeployInstanceBuilder {
        @QueryParam("InstanceIds")
        RedeployInstanceBuilder instanceIds(final String[] instanceIds);

        @QueryParam("RamRoleName")
        RedeployInstanceBuilder ramRoleName(final String ramRoleName);

        @QueryParam("RegionId")
        RedeployInstanceBuilder regionId(final String regionId);

        Observable<RedeployInstanceResponse> call();
    }

    RedeployInstanceBuilder redeployInstance();

    interface DescribeInstanceVncUrlResponse extends ECSAPIResponse {
        @JSONField(name="VncUrl")
        String getVncUrl();

        @JSONField(name="VncUrl")
        void setVncUrl(final String vncUrl);
    }

    @RpcBuilder
    interface DescribeInstanceVncUrlBuilder {
        @QueryParam("InstanceId")
        DescribeInstanceVncUrlBuilder instanceId(final String instanceId);

        @QueryParam("RegionId")
        DescribeInstanceVncUrlBuilder regionId(final String regionId);

        @GET
        @ConstParams({"Action", "DescribeInstanceVncUrl"})
        Observable<DescribeInstanceVncUrlResponse> call();
    }

    DescribeInstanceVncUrlBuilder describeInstanceVncUrl();

    interface DescribeUserDataResponse extends ECSAPIResponse {
        @JSONField(name="RegionId")
        String getRegionId();

        @JSONField(name="RegionId")
        void setRegionId(final String regionId);

        @JSONField(name="InstanceId")
        String getInstanceId();

        @JSONField(name="InstanceId")
        void setInstanceId(final String instanceId);

        @JSONField(name="UserData")
        String getUserData();

        @JSONField(name="UserData")
        void setUserData(final String userData);
    }

    @RpcBuilder
    interface DescribeUserDataBuilder {
        //  必选   i-instanceid1           要查询的实例 ID。
        @QueryParam("InstanceId")
        DescribeUserDataBuilder instanceId(final String instanceId);

        //  必选   cn-hangzhou         实例所属的地域ID。
        @QueryParam("RegionId")
        DescribeUserDataBuilder regionId(final String regionId);

        @GET
        @ConstParams({"Action", "DescribeUserData"})
        Observable<DescribeUserDataResponse> call();
    }

    DescribeUserDataBuilder describeUserData();

    interface InstanceRenewAttribute  {
        @JSONField(name="InstanceId")
        String getInstanceId();

        @JSONField(name="InstanceId")
        void setInstanceId(final String instanceId);

        @JSONField(name="AutoRenewEnabled")
        boolean getAutoRenewEnabled();

        @JSONField(name="AutoRenewEnabled")
        void setAutoRenewEnabled(final boolean autoRenewEnabled);

        @JSONField(name="Duration")
        int getDuration();

        @JSONField(name="Duration")
        void setDuration(final int duration);

        @JSONField(name="PeriodUnit")
        String getPeriodUnit();

        @JSONField(name="PeriodUnit")
        void setPeriodUnit(final String periodUnit);

        @JSONField(name="RenewalStatus")
        String getRenewalStatus();

        @JSONField(name="RenewalStatus")
        void setRenewalStatus(final String renewalStatus);
    }

    interface InstanceRenewAttributes  {
        @JSONField(name="InstanceRenewAttribute")
        InstanceRenewAttribute[] getInstanceRenewAttributes();

        @JSONField(name="InstanceRenewAttribute")
        void setInstanceRenewAttributes(final InstanceRenewAttribute[] instanceRenewAttributes);
    }

    interface DescribeInstanceAutoRenewAttributeResponse extends ECSAPIResponse, Pageable {
        @JSONField(name="InstanceRenewAttributes")
        InstanceRenewAttributes getInstanceRenewAttributes();

        @JSONField(name="InstanceRenewAttributes")
        void setInstanceRenewAttributes(final InstanceRenewAttributes instanceRenewAttributes);
    }

    @RpcBuilder
    interface DescribeInstanceAutoRenewAttributeBuilder extends PageableBuilder<DescribeInstanceAutoRenewAttributeBuilder> {
        //  必选   cn-hangzhou  实例所属的地域ID。
        @QueryParam("RegionId")
        DescribeInstanceAutoRenewAttributeBuilder regionId(final String regionId);

        //  可选   i-bp18x3z4hc7bixhxh***,i-bp1g6zv0ce8oghu7k***    实例ID。支持最多100台包年包月实例批量查询，多个实例ID以半角逗号分隔。
        @QueryParam("InstanceId")
        DescribeInstanceAutoRenewAttributeBuilder instanceId(final String instanceId);

        //  可选   AutoRenewal  实例的自动续费状态。取值范围：
//        AutoRenewal：设置为自动续费。
//        Normal：取消自动续费。
//        NotRenewal：不再续费，系统不再发送到期提醒，只在到期前第三天发送不续费提醒。不再续费的ECS实例可以通过ModifyInstanceAutoRenewAttribute更改成待续费（Noramal）后，再自行续费或设置为自动续费。
        @QueryParam("RenewalStatus")
        DescribeInstanceAutoRenewAttributeBuilder renewalStatus(final String renewalStatus);

        @GET
        @ConstParams({"Action", "DescribeInstanceAutoRenewAttribute"})
        Observable<DescribeInstanceAutoRenewAttributeResponse> call();
    }

    DescribeInstanceAutoRenewAttributeBuilder describeInstanceAutoRenewAttribute();

    interface InstanceRamRoleSets {
        @JSONField(name="InstanceRamRoleSet")
        InstanceRamRoleSet[] getInstanceRamRoleSets();

        @JSONField(name="InstanceRamRoleSet")
        void setInstanceRamRoleSets(final InstanceRamRoleSet[] instanceRamRoleSets);
    }

    interface DescribeInstanceRamRoleResponse extends ECSAPIResponse, Pageable {
        @JSONField(name="RegionId")
        String getRegionId();

        @JSONField(name="RegionId")
        void setRegionId(final String regionId);

        @JSONField(name="InstanceRamRoleSets")
        InstanceRamRoleSets getInstanceRamRoleSets();

        @JSONField(name="InstanceRamRoleSets")
        void setInstanceRamRoleSets(final InstanceRamRoleSets instanceRamRoleSets);
    }

    @RpcBuilder
    interface DescribeInstanceRamRoleBuilder extends PageableBuilder<DescribeInstanceRamRoleBuilder> {
        //  可选   ["i-bp67acfmxazb4ph***", "i-bp67acfmxazb4pi***", "bp67acfmxazb4pj***"…]  指定查询的实例ID的集合。最多支持一次查询100台实例。InstanceIds与RamRoleName参数必须至少填写一个。
        @QueryParam("InstanceIds")
        DescribeInstanceRamRoleBuilder instanceIds(final String instanceIds);

        //  可选   EcsServiceRole-EcsDocGuideTest  查询赋予了某一实例RAM角色的所有ECS实例。
        @QueryParam("RamRoleName")
        DescribeInstanceRamRoleBuilder ramRoleName(final String ramRoleName);

        //  必选   cn-hangzhou 实例RAM角色所在的地域。
        @QueryParam("RegionId")
        DescribeInstanceRamRoleBuilder regionId(final String regionId);

        @GET
        @ConstParams({"Action", "DescribeInstanceRamRole"})
        Observable<DescribeInstanceRamRoleResponse> call();
    }

    DescribeInstanceRamRoleBuilder describeInstanceRamRole();

    interface DescribeInstanceTypeFamiliesResponse extends ECSAPIResponse {
    }

    @RpcBuilder
    interface DescribeInstanceTypeFamiliesBuilder {
        @QueryParam("InstanceIds")
        DescribeInstanceTypeFamiliesBuilder instanceIds(final String[] instanceIds);

        @QueryParam("RamRoleName")
        DescribeInstanceTypeFamiliesBuilder ramRoleName(final String ramRoleName);

        @QueryParam("RegionId")
        DescribeInstanceTypeFamiliesBuilder regionId(final String regionId);

        Observable<DescribeInstanceTypeFamiliesResponse> call();
    }

    DescribeInstanceTypeFamiliesBuilder describeInstanceTypeFamilies();

    interface DescribeInstanceTypesResponse extends ECSAPIResponse {
    }

    @RpcBuilder
    interface DescribeInstanceTypesBuilder {
        @QueryParam("InstanceIds")
        DescribeInstanceTypesBuilder instanceIds(final String[] instanceIds);

        @QueryParam("RamRoleName")
        DescribeInstanceTypesBuilder ramRoleName(final String ramRoleName);

        @QueryParam("RegionId")
        DescribeInstanceTypesBuilder regionId(final String regionId);

        Observable<DescribeInstanceTypesResponse> call();
    }

    DescribeInstanceTypesBuilder describeInstanceTypes();

    interface ModifyInstanceVpcAttributeResponse extends ECSAPIResponse {
    }

    @RpcBuilder
    interface ModifyInstanceVpcAttributeBuilder {
        @QueryParam("InstanceIds")
        ModifyInstanceVpcAttributeBuilder instanceIds(final String[] instanceIds);

        @QueryParam("RamRoleName")
        ModifyInstanceVpcAttributeBuilder ramRoleName(final String ramRoleName);

        @QueryParam("RegionId")
        ModifyInstanceVpcAttributeBuilder regionId(final String regionId);

        Observable<ModifyInstanceVpcAttributeResponse> call();
    }

    ModifyInstanceVpcAttributeBuilder modifyInstanceVpcAttribute();

    interface ModifyInstanceAttributeResponse extends ECSAPIResponse {
    }

    @RpcBuilder
    interface ModifyInstanceAttributeBuilder {
        @QueryParam("InstanceIds")
        ModifyInstanceAttributeBuilder instanceIds(final String[] instanceIds);

        @QueryParam("RamRoleName")
        ModifyInstanceAttributeBuilder ramRoleName(final String ramRoleName);

        @QueryParam("RegionId")
        ModifyInstanceAttributeBuilder regionId(final String regionId);

        Observable<ModifyInstanceAttributeResponse> call();
    }

    ModifyInstanceAttributeBuilder modifyInstanceAttribute();

    interface ModifyInstanceVncPasswdResponse extends ECSAPIResponse {
    }

    @RpcBuilder
    interface ModifyInstanceVncPasswdBuilder {
        @QueryParam("InstanceIds")
        ModifyInstanceVncPasswdBuilder instanceIds(final String[] instanceIds);

        @QueryParam("RamRoleName")
        ModifyInstanceVncPasswdBuilder ramRoleName(final String ramRoleName);

        @QueryParam("RegionId")
        ModifyInstanceVncPasswdBuilder regionId(final String regionId);

        Observable<ModifyInstanceVncPasswdResponse> call();
    }

    ModifyInstanceVncPasswdBuilder modifyInstanceVncPasswd();

    interface ModifyInstanceAutoReleaseTimeResponse extends ECSAPIResponse {
    }

    @RpcBuilder
    interface ModifyInstanceAutoReleaseTimeBuilder {
        @QueryParam("InstanceIds")
        ModifyInstanceAutoReleaseTimeBuilder instanceIds(final String[] instanceIds);

        @QueryParam("RamRoleName")
        ModifyInstanceAutoReleaseTimeBuilder ramRoleName(final String ramRoleName);

        @QueryParam("RegionId")
        ModifyInstanceAutoReleaseTimeBuilder regionId(final String regionId);

        Observable<ModifyInstanceAutoReleaseTimeResponse> call();
    }

    ModifyInstanceAutoReleaseTimeBuilder modifyInstanceAutoReleaseTime();

    interface ModifyInstanceAutoRenewAttributeResponse extends ECSAPIResponse {
    }

    @RpcBuilder
    interface ModifyInstanceAutoRenewAttributeBuilder {
        @QueryParam("InstanceIds")
        ModifyInstanceAutoRenewAttributeBuilder instanceIds(final String[] instanceIds);

        @QueryParam("RamRoleName")
        ModifyInstanceAutoRenewAttributeBuilder ramRoleName(final String ramRoleName);

        @QueryParam("RegionId")
        ModifyInstanceAutoRenewAttributeBuilder regionId(final String regionId);

        Observable<ModifyInstanceAutoRenewAttributeResponse> call();
    }

    ModifyInstanceAutoRenewAttributeBuilder modifyInstanceAutoRenewAttribute();

    interface ModifyInstanceChargeTypeResponse extends ECSAPIResponse {
    }

    @RpcBuilder
    interface ModifyInstanceChargeTypeBuilder {
        @QueryParam("InstanceIds")
        ModifyInstanceChargeTypeBuilder instanceIds(final String[] instanceIds);

        @QueryParam("RamRoleName")
        ModifyInstanceChargeTypeBuilder ramRoleName(final String ramRoleName);

        @QueryParam("RegionId")
        ModifyInstanceChargeTypeBuilder regionId(final String regionId);

        Observable<ModifyInstanceChargeTypeResponse> call();
    }

    ModifyInstanceChargeTypeBuilder modifyInstanceChargeType();

    interface ModifyInstanceSpecResponse extends ECSAPIResponse {
    }

    @RpcBuilder
    interface ModifyInstanceSpecBuilder {
        @QueryParam("InstanceIds")
        ModifyInstanceSpecBuilder instanceIds(final String[] instanceIds);

        @QueryParam("RamRoleName")
        ModifyInstanceSpecBuilder ramRoleName(final String ramRoleName);

        @QueryParam("RegionId")
        ModifyInstanceSpecBuilder regionId(final String regionId);

        Observable<ModifyInstanceSpecResponse> call();
    }

    ModifyInstanceSpecBuilder modifyInstanceSpec();

    interface ModifyPrepayInstanceSpecResponse extends ECSAPIResponse {
    }

    @RpcBuilder
    interface ModifyPrepayInstanceSpecBuilder {
        @QueryParam("InstanceIds")
        ModifyPrepayInstanceSpecBuilder instanceIds(final String[] instanceIds);

        @QueryParam("RamRoleName")
        ModifyPrepayInstanceSpecBuilder ramRoleName(final String ramRoleName);

        @QueryParam("RegionId")
        ModifyPrepayInstanceSpecBuilder regionId(final String regionId);

        Observable<ModifyPrepayInstanceSpecResponse> call();
    }

    ModifyPrepayInstanceSpecBuilder modifyPrepayInstanceSpec();

    interface DescribeInstancesFullStatusResponse extends ECSAPIResponse {
    }

    @RpcBuilder
    interface DescribeInstancesFullStatusBuilder {
        @QueryParam("InstanceIds")
        DescribeInstancesFullStatusBuilder instanceIds(final String[] instanceIds);

        @QueryParam("RamRoleName")
        DescribeInstancesFullStatusBuilder ramRoleName(final String ramRoleName);

        @QueryParam("RegionId")
        DescribeInstancesFullStatusBuilder regionId(final String regionId);

        Observable<DescribeInstancesFullStatusResponse> call();
    }

    DescribeInstancesFullStatusBuilder describeInstancesFullStatus();

    //  TBD: https://help.aliyun.com/document_detail/63962.html?spm=a2c4g.11186623.6.1295.496db95cVFgyMa
    interface DescribeInstanceHistoryEventsResponse extends ECSAPIResponse {
    }

    @RpcBuilder
    interface DescribeInstanceHistoryEventsBuilder {
        @QueryParam("InstanceIds")
        DescribeInstanceHistoryEventsBuilder instanceIds(final String[] instanceIds);

        @QueryParam("RamRoleName")
        DescribeInstanceHistoryEventsBuilder ramRoleName(final String ramRoleName);

        @QueryParam("RegionId")
        DescribeInstanceHistoryEventsBuilder regionId(final String regionId);

        Observable<DescribeInstanceHistoryEventsResponse> call();
    }

    DescribeInstanceHistoryEventsBuilder describeInstanceHistoryEvents();

    //  TBD: https://help.aliyun.com/document_detail/63963.html?spm=a2c4g.11186623.6.1296.1e7f1a4cukcipD
    interface DescribeDisksFullStatusResponse extends ECSAPIResponse {
    }

    @RpcBuilder
    interface DescribeDisksFullStatusBuilder {
        @QueryParam("InstanceIds")
        DescribeDisksFullStatusBuilder instanceIds(final String[] instanceIds);

        @QueryParam("RamRoleName")
        DescribeDisksFullStatusBuilder ramRoleName(final String ramRoleName);

        @QueryParam("RegionId")
        DescribeDisksFullStatusBuilder regionId(final String regionId);

        Observable<DescribeDisksFullStatusResponse> call();
    }

    DescribeDisksFullStatusBuilder describeDisksFullStatus();

    //  TBD: https://help.aliyun.com/document_detail/63963.html?spm=a2c4g.11186623.6.1296.1e7f1a4cukcipD
    interface CancelSimulatedSystemEventsResponse extends ECSAPIResponse {
    }

    @RpcBuilder
    interface CancelSimulatedSystemEventsBuilder {
        @QueryParam("InstanceIds")
        CancelSimulatedSystemEventsBuilder instanceIds(final String[] instanceIds);

        @QueryParam("RamRoleName")
        CancelSimulatedSystemEventsBuilder ramRoleName(final String ramRoleName);

        @QueryParam("RegionId")
        CancelSimulatedSystemEventsBuilder regionId(final String regionId);

        Observable<CancelSimulatedSystemEventsResponse> call();
    }

    CancelSimulatedSystemEventsBuilder cancelSimulatedSystemEvents();

    //  TBD: https://help.aliyun.com/document_detail/88814.html?spm=a2c4g.11186623.6.1302.5e286918lljOHg
    interface CreateSimulatedSystemEventsResponse extends ECSAPIResponse {
    }

    @RpcBuilder
    interface CreateSimulatedSystemEventsBuilder {
        @QueryParam("InstanceIds")
        CreateSimulatedSystemEventsBuilder instanceIds(final String[] instanceIds);

        @QueryParam("RamRoleName")
        CreateSimulatedSystemEventsBuilder ramRoleName(final String ramRoleName);

        @QueryParam("RegionId")
        CreateSimulatedSystemEventsBuilder regionId(final String regionId);

        Observable<CreateSimulatedSystemEventsResponse> call();
    }

    CreateSimulatedSystemEventsBuilder createSimulatedSystemEvents();

    // 运维与监控
    //  TBD: https://help.aliyun.com/document_detail/87546.html?spm=a2c4g.11186623.6.1304.2dbc355fzGeV1i
    interface DescribeEniMonitorDataResponse extends ECSAPIResponse {
    }

    @RpcBuilder
    interface DescribeEniMonitorDataBuilder {
        @QueryParam("InstanceIds")
        DescribeEniMonitorDataBuilder instanceIds(final String[] instanceIds);

        @QueryParam("RamRoleName")
        DescribeEniMonitorDataBuilder ramRoleName(final String ramRoleName);

        @QueryParam("RegionId")
        DescribeEniMonitorDataBuilder regionId(final String regionId);

        Observable<DescribeEniMonitorDataResponse> call();
    }

    DescribeEniMonitorDataBuilder describeEniMonitorData();

    //  TBD: https://help.aliyun.com/document_detail/25614.html?spm=a2c4g.11186623.6.1306.102251f4UVVrfx
    interface DescribeDiskMonitorDataResponse extends ECSAPIResponse {
    }

    @RpcBuilder
    interface DescribeDiskMonitorDataBuilder {
        @QueryParam("InstanceIds")
        DescribeDiskMonitorDataBuilder instanceIds(final String[] instanceIds);

        @QueryParam("RamRoleName")
        DescribeDiskMonitorDataBuilder ramRoleName(final String ramRoleName);

        @QueryParam("RegionId")
        DescribeDiskMonitorDataBuilder regionId(final String regionId);

        Observable<DescribeDiskMonitorDataResponse> call();
    }

    DescribeDiskMonitorDataBuilder describeDiskMonitorData();

    //  TBD: https://help.aliyun.com/document_detail/25612.html?spm=a2c4g.11186623.6.1307.11e21bbcQcss1O
    interface DescribeInstanceMonitorDataResponse extends ECSAPIResponse {
    }

    @RpcBuilder
    interface DescribeInstanceMonitorDataBuilder {
        @QueryParam("InstanceIds")
        DescribeInstanceMonitorDataBuilder instanceIds(final String[] instanceIds);

        @QueryParam("RamRoleName")
        DescribeInstanceMonitorDataBuilder ramRoleName(final String ramRoleName);

        @QueryParam("RegionId")
        DescribeInstanceMonitorDataBuilder regionId(final String regionId);

        Observable<DescribeInstanceMonitorDataResponse> call();
    }

    DescribeInstanceMonitorDataBuilder describeInstanceMonitorData();

    //  TBD: https://help.aliyun.com/document_detail/85921.html?spm=a2c4g.11186623.6.1308.20491e436H33eY
    interface GetInstanceScreenshotResponse extends ECSAPIResponse {
    }

    interface GetInstanceScreenshotBuilder {
        @QueryParam("InstanceIds")
        GetInstanceScreenshotBuilder instanceIds(final String[] instanceIds);

        @QueryParam("RamRoleName")
        GetInstanceScreenshotBuilder ramRoleName(final String ramRoleName);

        @QueryParam("RegionId")
        GetInstanceScreenshotBuilder regionId(final String regionId);

        Observable<GetInstanceScreenshotResponse> call();
    }

    GetInstanceScreenshotBuilder getInstanceScreenshot();

    //  TBD: https://help.aliyun.com/document_detail/85922.html?spm=a2c4g.11186623.6.1310.701959f6mJRm1N
    interface GetInstanceConsoleOutputResponse extends ECSAPIResponse {
    }

    interface GetInstanceConsoleOutputBuilder {
        @QueryParam("InstanceIds")
        GetInstanceConsoleOutputBuilder instanceIds(final String[] instanceIds);

        @QueryParam("RamRoleName")
        GetInstanceConsoleOutputBuilder ramRoleName(final String ramRoleName);

        @QueryParam("RegionId")
        GetInstanceConsoleOutputBuilder regionId(final String regionId);

        Observable<GetInstanceConsoleOutputResponse> call();
    }

    GetInstanceConsoleOutputBuilder getInstanceConsoleOutput();

    //  TBD: https://help.aliyun.com/document_detail/140980.html?spm=a2c4g.11186623.6.1311.11712f88YtgIHU
    interface DescribeSnapshotMonitorDataResponse extends ECSAPIResponse {
    }

    interface DescribeSnapshotMonitorDataBuilder {
        @QueryParam("InstanceIds")
        DescribeSnapshotMonitorDataBuilder instanceIds(final String[] instanceIds);

        @QueryParam("RamRoleName")
        DescribeSnapshotMonitorDataBuilder ramRoleName(final String ramRoleName);

        @QueryParam("RegionId")
        DescribeSnapshotMonitorDataBuilder regionId(final String regionId);

        Observable<DescribeSnapshotMonitorDataResponse> call();
    }

    DescribeSnapshotMonitorDataBuilder describeSnapshotMonitorData();

    //  TBD: https://help.aliyun.com/document_detail/25609.html?spm=a2c4g.11186623.6.1333.785b798dNB8QEy
    interface DescribeRegionsResponse extends ECSAPIResponse {
    }

    interface DescribeRegionsBuilder {
        @QueryParam("InstanceIds")
        DescribeRegionsBuilder instanceIds(final String[] instanceIds);

        @QueryParam("RamRoleName")
        DescribeRegionsBuilder ramRoleName(final String ramRoleName);

        @QueryParam("RegionId")
        DescribeRegionsBuilder regionId(final String regionId);

        Observable<DescribeRegionsResponse> call();
    }

    DescribeRegionsBuilder describeRegions();

    //  TBD: https://help.aliyun.com/document_detail/25610.html?spm=a2c4g.11186623.6.1334.7dc235bd93H3KF
    interface DescribeZonesResponse extends ECSAPIResponse {
    }

    interface DescribeZonesBuilder {
        @QueryParam("InstanceIds")
        DescribeZonesBuilder instanceIds(final String[] instanceIds);

        @QueryParam("RamRoleName")
        DescribeZonesBuilder ramRoleName(final String ramRoleName);

        @QueryParam("RegionId")
        DescribeZonesBuilder regionId(final String regionId);

        Observable<DescribeZonesResponse> call();
    }

    DescribeZonesBuilder describeZones();

    //  TBD: https://help.aliyun.com/document_detail/66187.html?spm=a2c4g.11186623.6.1335.6be3448aCjxWid
    interface DescribeResourcesModificationResponse extends ECSAPIResponse {
    }

    interface DescribeResourcesModificationBuilder {
        @QueryParam("InstanceIds")
        DescribeResourcesModificationBuilder instanceIds(final String[] instanceIds);

        @QueryParam("RamRoleName")
        DescribeResourcesModificationBuilder ramRoleName(final String ramRoleName);

        @QueryParam("RegionId")
        DescribeResourcesModificationBuilder regionId(final String regionId);

        Observable<DescribeResourcesModificationResponse> call();
    }

    DescribeResourcesModificationBuilder describeResourcesModification();

    //  TBD: https://help.aliyun.com/document_detail/66186.html?spm=a2c4g.11186623.6.1336.7bdf6e82n1agNa
    interface DescribeAvailableResourceResponse extends ECSAPIResponse {
    }

    interface DescribeAvailableResourceBuilder {
        @QueryParam("InstanceIds")
        DescribeAvailableResourceBuilder instanceIds(final String[] instanceIds);

        @QueryParam("RamRoleName")
        DescribeAvailableResourceBuilder ramRoleName(final String ramRoleName);

        @QueryParam("RegionId")
        DescribeAvailableResourceBuilder regionId(final String regionId);

        Observable<DescribeAvailableResourceResponse> call();
    }

    DescribeAvailableResourceBuilder describeAvailableResource();

    // SSH 密钥对 相关
    //  TBD: https://help.aliyun.com/document_detail/51771.html?spm=a2c4g.11186623.6.1274.1c2f263aA2hveO
    interface CreateKeyPairResponse extends ECSAPIResponse {
    }

    interface CreateKeyPairBuilder {
        @QueryParam("InstanceIds")
        CreateKeyPairBuilder instanceIds(final String[] instanceIds);

        @QueryParam("RamRoleName")
        CreateKeyPairBuilder ramRoleName(final String ramRoleName);

        @QueryParam("RegionId")
        CreateKeyPairBuilder regionId(final String regionId);

        Observable<CreateKeyPairResponse> call();
    }

    CreateKeyPairBuilder createKeyPair();

    //  TBD: https://help.aliyun.com/document_detail/51774.html?spm=a2c4g.11186623.6.1275.513a2e9eVOSUgn
    interface ImportKeyPairResponse extends ECSAPIResponse {
    }

    interface ImportKeyPairBuilder {
        @QueryParam("InstanceIds")
        ImportKeyPairBuilder instanceIds(final String[] instanceIds);

        @QueryParam("RamRoleName")
        ImportKeyPairBuilder ramRoleName(final String ramRoleName);

        @QueryParam("RegionId")
        ImportKeyPairBuilder regionId(final String regionId);

        Observable<ImportKeyPairResponse> call();
    }

    ImportKeyPairBuilder importKeyPair();

    //  TBD: https://help.aliyun.com/document_detail/51775.html?spm=a2c4g.11186623.6.1282.622f6a31gP2YEb
    interface AttachKeyPairResponse extends ECSAPIResponse {
    }

    interface AttachKeyPairBuilder {
        @QueryParam("InstanceIds")
        AttachKeyPairBuilder instanceIds(final String[] instanceIds);

        @QueryParam("RamRoleName")
        AttachKeyPairBuilder ramRoleName(final String ramRoleName);

        @QueryParam("RegionId")
        AttachKeyPairBuilder regionId(final String regionId);

        Observable<AttachKeyPairResponse> call();
    }

    AttachKeyPairBuilder attachKeyPair();

    //  TBD: https://help.aliyun.com/document_detail/51776.html?spm=a2c4g.11186623.6.1283.61ba1f7bCJDkKl
    interface DetachKeyPairResponse extends ECSAPIResponse {
    }

    interface DetachKeyPairBuilder {
        @QueryParam("InstanceIds")
        DetachKeyPairBuilder instanceIds(final String[] instanceIds);

        @QueryParam("RamRoleName")
        DetachKeyPairBuilder ramRoleName(final String ramRoleName);

        @QueryParam("RegionId")
        DetachKeyPairBuilder regionId(final String regionId);

        Observable<DetachKeyPairResponse> call();
    }

    DetachKeyPairBuilder detachKeyPair();

    //  TBD: https://help.aliyun.com/document_detail/51772.html?spm=a2c4g.11186623.6.1284.7ac41f7be3WAzU
    interface DeleteKeyPairsResponse extends ECSAPIResponse {
    }

    interface DeleteKeyPairsBuilder {
        @QueryParam("InstanceIds")
        DeleteKeyPairsBuilder instanceIds(final String[] instanceIds);

        @QueryParam("RamRoleName")
        DeleteKeyPairsBuilder ramRoleName(final String ramRoleName);

        @QueryParam("RegionId")
        DeleteKeyPairsBuilder regionId(final String regionId);

        Observable<DeleteKeyPairsResponse> call();
    }

    DeleteKeyPairsBuilder deleteKeyPairs();

    //  TBD: https://help.aliyun.com/document_detail/51773.html?spm=a2c4g.11186623.6.1285.4c972427dKRksp
    interface DescribeKeyPairsResponse extends ECSAPIResponse {
    }

    interface DescribeKeyPairsBuilder {
        @QueryParam("InstanceIds")
        DescribeKeyPairsBuilder instanceIds(final String[] instanceIds);

        @QueryParam("RamRoleName")
        DescribeKeyPairsBuilder ramRoleName(final String ramRoleName);

        @QueryParam("RegionId")
        DescribeKeyPairsBuilder regionId(final String regionId);

        Observable<DescribeKeyPairsResponse> call();
    }

    DescribeKeyPairsBuilder describeKeyPairs();

    // 网络 相关
    //  TBD: https://help.aliyun.com/document_detail/25544.html?spm=a2c4g.11186623.6.1286.513d41ddMaHI1A
    interface AllocatePublicIpAddressResponse extends ECSAPIResponse {
    }

    interface AllocatePublicIpAddressBuilder {
        @QueryParam("InstanceIds")
        AllocatePublicIpAddressBuilder instanceIds(final String[] instanceIds);

        @QueryParam("RamRoleName")
        AllocatePublicIpAddressBuilder ramRoleName(final String ramRoleName);

        @QueryParam("RegionId")
        AllocatePublicIpAddressBuilder regionId(final String regionId);

        Observable<AllocatePublicIpAddressResponse> call();
    }

    AllocatePublicIpAddressBuilder allocatePublicIpAddress();

    //  TBD: https://help.aliyun.com/document_detail/60738.html?spm=a2c4g.11186623.6.1287.c0692fa8twN2Kr
    interface ConvertNatPublicIpToEipResponse extends ECSAPIResponse {
    }

    interface ConvertNatPublicIpToEipBuilder {
        @QueryParam("InstanceIds")
        ConvertNatPublicIpToEipBuilder instanceIds(final String[] instanceIds);

        @QueryParam("RamRoleName")
        ConvertNatPublicIpToEipBuilder ramRoleName(final String ramRoleName);

        @QueryParam("RegionId")
        ConvertNatPublicIpToEipBuilder regionId(final String regionId);

        Observable<ConvertNatPublicIpToEipResponse> call();
    }

    ConvertNatPublicIpToEipBuilder convertNatPublicIpToEip();

    //  TBD: https://help.aliyun.com/document_detail/59021.html?spm=a2c4g.11186623.6.1288.79f523f5JWyZou
    interface AttachClassicLinkVpcResponse extends ECSAPIResponse {
    }

    interface AttachClassicLinkVpcBuilder {
        @QueryParam("InstanceIds")
        AttachClassicLinkVpcBuilder instanceIds(final String[] instanceIds);

        @QueryParam("RamRoleName")
        AttachClassicLinkVpcBuilder ramRoleName(final String ramRoleName);

        @QueryParam("RegionId")
        AttachClassicLinkVpcBuilder regionId(final String regionId);

        Observable<AttachClassicLinkVpcResponse> call();
    }

    AttachClassicLinkVpcBuilder attachClassicLinkVpc();

    //  TBD: https://help.aliyun.com/document_detail/59028.html?spm=a2c4g.11186623.6.1289.4275660f7GMRMC
    interface DetachClassicLinkVpcResponse extends ECSAPIResponse {
    }

    interface DetachClassicLinkVpcBuilder {
        @QueryParam("InstanceIds")
        DetachClassicLinkVpcBuilder instanceIds(final String[] instanceIds);

        @QueryParam("RamRoleName")
        DetachClassicLinkVpcBuilder ramRoleName(final String ramRoleName);

        @QueryParam("RegionId")
        DetachClassicLinkVpcBuilder regionId(final String regionId);

        Observable<DetachClassicLinkVpcResponse> call();
    }

    DetachClassicLinkVpcBuilder detachClassicLinkVpc();

    //  TBD: https://help.aliyun.com/document_detail/66182.html?spm=a2c4g.11186623.6.1290.7dd9660fT6ZvRU
    interface DescribeBandwidthLimitationResponse extends ECSAPIResponse {
    }

    interface DescribeBandwidthLimitationBuilder {
        @QueryParam("InstanceIds")
        DescribeBandwidthLimitationBuilder instanceIds(final String[] instanceIds);

        @QueryParam("RamRoleName")
        DescribeBandwidthLimitationBuilder ramRoleName(final String ramRoleName);

        @QueryParam("RegionId")
        DescribeBandwidthLimitationBuilder regionId(final String regionId);

        Observable<DescribeBandwidthLimitationResponse> call();
    }

    DescribeBandwidthLimitationBuilder describeBandwidthLimitation();

    //  TBD: https://help.aliyun.com/document_detail/59018.html?spm=a2c4g.11186623.6.1291.709a75acnjHVjZ
    interface DescribeClassicLinkInstancesResponse extends ECSAPIResponse {
    }

    interface DescribeClassicLinkInstancesBuilder {
        @QueryParam("InstanceIds")
        DescribeClassicLinkInstancesBuilder instanceIds(final String[] instanceIds);

        @QueryParam("RamRoleName")
        DescribeClassicLinkInstancesBuilder ramRoleName(final String ramRoleName);

        @QueryParam("RegionId")
        DescribeClassicLinkInstancesBuilder regionId(final String regionId);

        Observable<DescribeClassicLinkInstancesResponse> call();
    }

    DescribeClassicLinkInstancesBuilder describeClassicLinkInstances();

    //  TBD: https://help.aliyun.com/document_detail/25545.html?spm=a2c4g.11186623.6.1294.a58e4358wzaZKU
    interface ModifyInstanceNetworkSpecResponse extends ECSAPIResponse {
    }

    interface ModifyInstanceNetworkSpecBuilder {
        @QueryParam("InstanceIds")
        ModifyInstanceNetworkSpecBuilder instanceIds(final String[] instanceIds);

        @QueryParam("RamRoleName")
        ModifyInstanceNetworkSpecBuilder ramRoleName(final String ramRoleName);

        @QueryParam("RegionId")
        ModifyInstanceNetworkSpecBuilder regionId(final String regionId);

        Observable<ModifyInstanceNetworkSpecResponse> call();
    }

    ModifyInstanceNetworkSpecBuilder modifyInstanceNetworkSpec();

    // 安全组  相关
    //  TBD: https://help.aliyun.com/document_detail/25553.html?spm=a2c4g.11186623.6.1259.7f561f5bHCAXBJ
    interface CreateSecurityGroupResponse extends ECSAPIResponse {
    }

    interface CreateSecurityGroupBuilder {
        @QueryParam("InstanceIds")
        CreateSecurityGroupBuilder instanceIds(final String[] instanceIds);

        @QueryParam("RamRoleName")
        CreateSecurityGroupBuilder ramRoleName(final String ramRoleName);

        @QueryParam("RegionId")
        CreateSecurityGroupBuilder regionId(final String regionId);

        Observable<CreateSecurityGroupResponse> call();
    }

    CreateSecurityGroupBuilder createSecurityGroup();

    //  TBD: https://help.aliyun.com/document_detail/25554.html?spm=a2c4g.11186623.6.1260.5c6827aftAJ8nn
    interface AuthorizeSecurityGroupResponse extends ECSAPIResponse {
    }

    interface AuthorizeSecurityGroupBuilder {
        @QueryParam("InstanceIds")
        AuthorizeSecurityGroupBuilder instanceIds(final String[] instanceIds);

        @QueryParam("RamRoleName")
        AuthorizeSecurityGroupBuilder ramRoleName(final String ramRoleName);

        @QueryParam("RegionId")
        AuthorizeSecurityGroupBuilder regionId(final String regionId);

        Observable<AuthorizeSecurityGroupResponse> call();
    }

    AuthorizeSecurityGroupBuilder authorizeSecurityGroup();

    //  TBD: https://help.aliyun.com/document_detail/25554.html?spm=a2c4g.11186623.6.1260.5c6827aftAJ8nn
    interface AuthorizeSecurityGroupEgressResponse extends ECSAPIResponse {
    }

    interface AuthorizeSecurityGroupEgressBuilder {
        @QueryParam("InstanceIds")
        AuthorizeSecurityGroupEgressBuilder instanceIds(final String[] instanceIds);

        @QueryParam("RamRoleName")
        AuthorizeSecurityGroupEgressBuilder ramRoleName(final String ramRoleName);

        @QueryParam("RegionId")
        AuthorizeSecurityGroupEgressBuilder regionId(final String regionId);

        Observable<AuthorizeSecurityGroupEgressResponse> call();
    }

    AuthorizeSecurityGroupEgressBuilder authorizeSecurityGroupEgress();

    //  TBD: https://help.aliyun.com/document_detail/25557.html?spm=a2c4g.11186623.6.1261.59602586H5sR9H
    interface RevokeSecurityGroupResponse extends ECSAPIResponse {
    }

    interface RevokeSecurityGroupBuilder {
        @QueryParam("InstanceIds")
        RevokeSecurityGroupBuilder instanceIds(final String[] instanceIds);

        @QueryParam("RamRoleName")
        RevokeSecurityGroupBuilder ramRoleName(final String ramRoleName);

        @QueryParam("RegionId")
        RevokeSecurityGroupBuilder regionId(final String regionId);

        Observable<RevokeSecurityGroupResponse> call();
    }

    RevokeSecurityGroupBuilder revokeSecurityGroup();

    //  TBD: https://help.aliyun.com/document_detail/25561.html?spm=a2c4g.11186623.6.1262.68a92048pBwW9K
    interface RevokeSecurityGroupEgressResponse extends ECSAPIResponse {
    }

    interface RevokeSecurityGroupEgressBuilder {
        @QueryParam("InstanceIds")
        RevokeSecurityGroupEgressBuilder instanceIds(final String[] instanceIds);

        @QueryParam("RamRoleName")
        RevokeSecurityGroupEgressBuilder ramRoleName(final String ramRoleName);

        @QueryParam("RegionId")
        RevokeSecurityGroupEgressBuilder regionId(final String regionId);

        Observable<RevokeSecurityGroupEgressResponse> call();
    }

    RevokeSecurityGroupEgressBuilder revokeSecurityGroupEgress();

    //  TBD: https://help.aliyun.com/document_detail/25508.html?spm=a2c4g.11186623.6.1267.550746fbV4TI1v
    interface JoinSecurityGroupResponse extends ECSAPIResponse {
    }

    interface JoinSecurityGroupBuilder {
        @QueryParam("InstanceIds")
        JoinSecurityGroupBuilder instanceIds(final String[] instanceIds);

        @QueryParam("RamRoleName")
        JoinSecurityGroupBuilder ramRoleName(final String ramRoleName);

        @QueryParam("RegionId")
        JoinSecurityGroupBuilder regionId(final String regionId);

        Observable<JoinSecurityGroupResponse> call();
    }

    JoinSecurityGroupBuilder joinSecurityGroup();

    //  TBD: https://help.aliyun.com/document_detail/25509.html?spm=a2c4g.11186623.6.1268.249f3474eU4KG7
    interface LeaveSecurityGroupResponse extends ECSAPIResponse {
    }

    interface LeaveSecurityGroupBuilder {
        @QueryParam("InstanceIds")
        LeaveSecurityGroupBuilder instanceIds(final String[] instanceIds);

        @QueryParam("RamRoleName")
        LeaveSecurityGroupBuilder ramRoleName(final String ramRoleName);

        @QueryParam("RegionId")
        LeaveSecurityGroupBuilder regionId(final String regionId);

        Observable<LeaveSecurityGroupResponse> call();
    }

    LeaveSecurityGroupBuilder leaveSecurityGroup();

    //  TBD: https://help.aliyun.com/document_detail/25558.html?spm=a2c4g.11186623.6.1269.1bc75f008Q12MF
    interface DeleteSecurityGroupResponse extends ECSAPIResponse {
    }

    interface DeleteSecurityGroupBuilder {
        @QueryParam("InstanceIds")
        DeleteSecurityGroupBuilder instanceIds(final String[] instanceIds);

        @QueryParam("RamRoleName")
        DeleteSecurityGroupBuilder ramRoleName(final String ramRoleName);

        @QueryParam("RegionId")
        DeleteSecurityGroupBuilder regionId(final String regionId);

        Observable<DeleteSecurityGroupResponse> call();
    }

    DeleteSecurityGroupBuilder deleteSecurityGroup();

    //  TBD: https://help.aliyun.com/document_detail/25555.html?spm=a2c4g.11186623.6.1270.1da827a5xPdIKf
    interface DescribeSecurityGroupAttributeResponse extends ECSAPIResponse {
    }

    interface DescribeSecurityGroupAttributeBuilder {
        @QueryParam("InstanceIds")
        DescribeSecurityGroupAttributeBuilder instanceIds(final String[] instanceIds);

        @QueryParam("RamRoleName")
        DescribeSecurityGroupAttributeBuilder ramRoleName(final String ramRoleName);

        @QueryParam("RegionId")
        DescribeSecurityGroupAttributeBuilder regionId(final String regionId);

        Observable<DescribeSecurityGroupAttributeResponse> call();
    }

    DescribeSecurityGroupAttributeBuilder describeSecurityGroupAttribute();

    //  TBD: https://help.aliyun.com/document_detail/25556.html?spm=a2c4g.11186623.6.1271.645e6b965arPc0
    interface DescribeSecurityGroupsResponse extends ECSAPIResponse {
    }

    interface DescribeSecurityGroupsBuilder {
        @QueryParam("InstanceIds")
        DescribeSecurityGroupsBuilder instanceIds(final String[] instanceIds);

        @QueryParam("RamRoleName")
        DescribeSecurityGroupsBuilder ramRoleName(final String ramRoleName);

        @QueryParam("RegionId")
        DescribeSecurityGroupsBuilder regionId(final String regionId);

        Observable<DescribeSecurityGroupsResponse> call();
    }

    DescribeSecurityGroupsBuilder describeSecurityGroups();

    //  TBD: https://help.aliyun.com/document_detail/57320.html?spm=a2c4g.11186623.6.1276.7607480cX0bJvj
    interface DescribeSecurityGroupReferencesResponse extends ECSAPIResponse {
    }

    interface DescribeSecurityGroupReferencesBuilder {
        @QueryParam("InstanceIds")
        DescribeSecurityGroupReferencesBuilder instanceIds(final String[] instanceIds);

        @QueryParam("RamRoleName")
        DescribeSecurityGroupReferencesBuilder ramRoleName(final String ramRoleName);

        @QueryParam("RegionId")
        DescribeSecurityGroupReferencesBuilder regionId(final String regionId);

        Observable<DescribeSecurityGroupReferencesResponse> call();
    }

    DescribeSecurityGroupReferencesBuilder describeSecurityGroupReferences();

    //  TBD: https://help.aliyun.com/document_detail/25559.html?spm=a2c4g.11186623.6.1277.2885461e11hACy
    interface ModifySecurityGroupAttributeResponse extends ECSAPIResponse {
    }

    interface ModifySecurityGroupAttributeBuilder {
        @QueryParam("InstanceIds")
        ModifySecurityGroupAttributeBuilder instanceIds(final String[] instanceIds);

        @QueryParam("RamRoleName")
        ModifySecurityGroupAttributeBuilder ramRoleName(final String ramRoleName);

        @QueryParam("RegionId")
        ModifySecurityGroupAttributeBuilder regionId(final String regionId);

        Observable<ModifySecurityGroupAttributeResponse> call();
    }

    ModifySecurityGroupAttributeBuilder modifySecurityGroupAttribute();

    //  TBD: https://help.aliyun.com/document_detail/100568.html?spm=a2c4g.11186623.6.1281.293d4bf8NSSyef
    interface ModifySecurityGroupEgressRuleResponse extends ECSAPIResponse {
    }

    interface ModifySecurityGroupEgressRuleBuilder {
        @QueryParam("InstanceIds")
        ModifySecurityGroupEgressRuleBuilder instanceIds(final String[] instanceIds);

        @QueryParam("RamRoleName")
        ModifySecurityGroupEgressRuleBuilder ramRoleName(final String ramRoleName);

        @QueryParam("RegionId")
        ModifySecurityGroupEgressRuleBuilder regionId(final String regionId);

        Observable<ModifySecurityGroupEgressRuleResponse> call();
    }

    ModifySecurityGroupEgressRuleBuilder modifySecurityGroupEgressRule();

    //  TBD: https://help.aliyun.com/document_detail/57315.html?spm=a2c4g.11186623.6.1283.62b931a3B4UXGe
    interface ModifySecurityGroupPolicyResponse extends ECSAPIResponse {
    }

    interface ModifySecurityGroupPolicyBuilder {
        @QueryParam("InstanceIds")
        ModifySecurityGroupPolicyBuilder instanceIds(final String[] instanceIds);

        @QueryParam("RamRoleName")
        ModifySecurityGroupPolicyBuilder ramRoleName(final String ramRoleName);

        @QueryParam("RegionId")
        ModifySecurityGroupPolicyBuilder regionId(final String regionId);

        Observable<ModifySecurityGroupPolicyResponse> call();
    }

    ModifySecurityGroupPolicyBuilder modifySecurityGroupPolicy();

    //  TBD: https://help.aliyun.com/document_detail/53516.html?spm=a2c4g.11186623.6.1284.2a427431pKZSkC
    interface ModifySecurityGroupRuleResponse extends ECSAPIResponse {
    }

    interface ModifySecurityGroupRuleBuilder {
        @QueryParam("InstanceIds")
        ModifySecurityGroupRuleBuilder instanceIds(final String[] instanceIds);

        @QueryParam("RamRoleName")
        ModifySecurityGroupRuleBuilder ramRoleName(final String ramRoleName);

        @QueryParam("RegionId")
        ModifySecurityGroupRuleBuilder regionId(final String regionId);

        Observable<ModifySecurityGroupRuleResponse> call();
    }

    ModifySecurityGroupRuleBuilder modifySecurityGroupRule();

    //  镜像  相关
    //  TBD: https://help.aliyun.com/document_detail/25535.html?spm=a2c4g.11186623.6.1244.2a1796fc3mrFHx
    interface CreateImageResponse extends ECSAPIResponse {
    }

    interface CreateImageBuilder {
        @QueryParam("InstanceIds")
        CreateImageBuilder instanceIds(final String[] instanceIds);

        @QueryParam("RamRoleName")
        CreateImageBuilder ramRoleName(final String ramRoleName);

        @QueryParam("RegionId")
        CreateImageBuilder regionId(final String regionId);

        Observable<CreateImageResponse> call();
    }

    CreateImageBuilder createImage();

    //  TBD: https://help.aliyun.com/document_detail/25542.html?spm=a2c4g.11186623.6.1244.51a218c9NjzNn7
    interface ImportImageResponse extends ECSAPIResponse {
    }

    interface ImportImageBuilder {
        @QueryParam("InstanceIds")
        ImportImageBuilder instanceIds(final String[] instanceIds);

        @QueryParam("RamRoleName")
        ImportImageBuilder ramRoleName(final String ramRoleName);

        @QueryParam("RegionId")
        ImportImageBuilder regionId(final String regionId);

        Observable<ImportImageResponse> call();
    }

    ImportImageBuilder importImage();

    //  TBD: https://help.aliyun.com/document_detail/25538.html?spm=a2c4g.11186623.6.1246.7d6045bae8eEGf
    interface CopyImageResponse extends ECSAPIResponse {
    }

    interface CopyImageBuilder {
        @QueryParam("InstanceIds")
        CopyImageBuilder instanceIds(final String[] instanceIds);

        @QueryParam("RamRoleName")
        CopyImageBuilder ramRoleName(final String ramRoleName);

        @QueryParam("RegionId")
        CopyImageBuilder regionId(final String regionId);

        Observable<CopyImageResponse> call();
    }

    CopyImageBuilder copyImage();

    //  TBD: https://help.aliyun.com/document_detail/25539.html?spm=a2c4g.11186623.6.1248.13d44921TTk9JH
    interface CancelCopyImageResponse extends ECSAPIResponse {
    }

    interface CancelCopyImageBuilder {
        @QueryParam("InstanceIds")
        CancelCopyImageBuilder instanceIds(final String[] instanceIds);

        @QueryParam("RamRoleName")
        CancelCopyImageBuilder ramRoleName(final String ramRoleName);

        @QueryParam("RegionId")
        CancelCopyImageBuilder regionId(final String regionId);

        Observable<CancelCopyImageResponse> call();
    }

    CancelCopyImageBuilder cancelCopyImage();

    //  TBD: https://help.aliyun.com/document_detail/58216.html?spm=a2c4g.11186623.6.1249.495f33a9UVhFaq
    interface ExportImageResponse extends ECSAPIResponse {
    }

    interface ExportImageBuilder {
        @QueryParam("InstanceIds")
        ExportImageBuilder instanceIds(final String[] instanceIds);

        @QueryParam("RamRoleName")
        ExportImageBuilder ramRoleName(final String ramRoleName);

        @QueryParam("RegionId")
        ExportImageBuilder regionId(final String regionId);

        Observable<ExportImageResponse> call();
    }

    ExportImageBuilder exportImage();

    //  TBD: https://help.aliyun.com/document_detail/25534.html?spm=a2c4g.11186623.6.1252.1fd345ba3e0f5f
    interface DescribeImagesResponse extends ECSAPIResponse {
    }

    interface DescribeImagesBuilder {
        @QueryParam("InstanceIds")
        DescribeImagesBuilder instanceIds(final String[] instanceIds);

        @QueryParam("RamRoleName")
        DescribeImagesBuilder ramRoleName(final String ramRoleName);

        @QueryParam("RegionId")
        DescribeImagesBuilder regionId(final String regionId);

        Observable<DescribeImagesResponse> call();
    }

    DescribeImagesBuilder describeImages();

    //  TBD: https://help.aliyun.com/document_detail/25537.html?spm=a2c4g.11186623.6.1253.55c674ddK0RWt8
    interface DeleteImageResponse extends ECSAPIResponse {
    }

    interface DeleteImageBuilder {
        @QueryParam("InstanceIds")
        DeleteImageBuilder instanceIds(final String[] instanceIds);

        @QueryParam("RamRoleName")
        DeleteImageBuilder ramRoleName(final String ramRoleName);

        @QueryParam("RegionId")
        DeleteImageBuilder regionId(final String regionId);

        Observable<DeleteImageResponse> call();
    }

    DeleteImageBuilder deleteImage();

    //  TBD: https://help.aliyun.com/document_detail/25541.html?spm=a2c4g.11186623.6.1254.756c21f7BOfxMg
    interface DescribeImageSharePermissionResponse extends ECSAPIResponse {
    }

    interface DescribeImageSharePermissionBuilder {
        @QueryParam("InstanceIds")
        DescribeImageSharePermissionBuilder instanceIds(final String[] instanceIds);

        @QueryParam("RamRoleName")
        DescribeImageSharePermissionBuilder ramRoleName(final String ramRoleName);

        @QueryParam("RegionId")
        DescribeImageSharePermissionBuilder regionId(final String regionId);

        Observable<DescribeImageSharePermissionResponse> call();
    }

    DescribeImageSharePermissionBuilder describeImageSharePermission();

    //  TBD: https://help.aliyun.com/document_detail/25536.html?spm=a2c4g.11186623.6.1255.5d0178e3zL3WG3
    interface ModifyImageAttributeResponse extends ECSAPIResponse {
    }

    interface ModifyImageAttributeBuilder {
        @QueryParam("InstanceIds")
        ModifyImageAttributeBuilder instanceIds(final String[] instanceIds);

        @QueryParam("RamRoleName")
        ModifyImageAttributeBuilder ramRoleName(final String ramRoleName);

        @QueryParam("RegionId")
        ModifyImageAttributeBuilder regionId(final String regionId);

        Observable<ModifyImageAttributeResponse> call();
    }

    ModifyImageAttributeBuilder modifyImageAttribute();

    //  TBD: https://help.aliyun.com/document_detail/25540.html?spm=a2c4g.11186623.6.1256.43e126a2A1SFmx
    interface ModifyImageSharePermissionResponse extends ECSAPIResponse {
    }

    interface ModifyImageSharePermissionBuilder {
        @QueryParam("InstanceIds")
        ModifyImageSharePermissionBuilder instanceIds(final String[] instanceIds);

        @QueryParam("RamRoleName")
        ModifyImageSharePermissionBuilder ramRoleName(final String ramRoleName);

        @QueryParam("RegionId")
        ModifyImageSharePermissionBuilder regionId(final String regionId);

        Observable<ModifyImageSharePermissionResponse> call();
    }

    ModifyImageSharePermissionBuilder modifyImageSharePermission();

    //  TBD: https://help.aliyun.com/document_detail/95504.html?spm=a2c4g.11186623.6.1257.7d1372feZtmu4J
    interface DescribeImageSupportInstanceTypesResponse extends ECSAPIResponse {
    }

    interface DescribeImageSupportInstanceTypesBuilder {
        @QueryParam("InstanceIds")
        DescribeImageSupportInstanceTypesBuilder instanceIds(final String[] instanceIds);

        @QueryParam("RamRoleName")
        DescribeImageSupportInstanceTypesBuilder ramRoleName(final String ramRoleName);

        @QueryParam("RegionId")
        DescribeImageSupportInstanceTypesBuilder regionId(final String regionId);

        Observable<DescribeImageSupportInstanceTypesResponse> call();
    }

    DescribeImageSupportInstanceTypesBuilder describeImageSupportInstanceTypes();

    //   快照  相关
    //  TBD: https://help.aliyun.com/document_detail/25524.html?spm=a2c4g.11186623.6.1260.34125980ffTomq
    interface CreateSnapshotResponse extends ECSAPIResponse {
    }

    interface CreateSnapshotBuilder {
        @QueryParam("InstanceIds")
        CreateSnapshotBuilder instanceIds(final String[] instanceIds);

        @QueryParam("RamRoleName")
        CreateSnapshotBuilder ramRoleName(final String ramRoleName);

        @QueryParam("RegionId")
        CreateSnapshotBuilder regionId(final String regionId);

        Observable<CreateSnapshotResponse> call();
    }

    CreateSnapshotBuilder createSnapshot();

    //  TBD: https://help.aliyun.com/document_detail/25525.html?spm=a2c4g.11186623.6.1261.3bce2c8elgVuIA
    interface DeleteSnapshotResponse extends ECSAPIResponse {
    }

    interface DeleteSnapshotBuilder {
        @QueryParam("InstanceIds")
        DeleteSnapshotBuilder instanceIds(final String[] instanceIds);

        @QueryParam("RamRoleName")
        DeleteSnapshotBuilder ramRoleName(final String ramRoleName);

        @QueryParam("RegionId")
        DeleteSnapshotBuilder regionId(final String regionId);

        Observable<DeleteSnapshotResponse> call();
    }

    DeleteSnapshotBuilder deleteSnapshot();

    //  TBD: https://help.aliyun.com/document_detail/25526.html?spm=a2c4g.11186623.6.1262.45cb2cf0G3QUys
    interface DescribeSnapshotsResponse extends ECSAPIResponse {
    }

    interface DescribeSnapshotsBuilder {
        @QueryParam("InstanceIds")
        DescribeSnapshotsBuilder instanceIds(final String[] instanceIds);

        @QueryParam("RamRoleName")
        DescribeSnapshotsBuilder ramRoleName(final String ramRoleName);

        @QueryParam("RegionId")
        DescribeSnapshotsBuilder regionId(final String regionId);

        Observable<DescribeSnapshotsResponse> call();
    }

    DescribeSnapshotsBuilder describeSnapshots();

    //  TBD: https://help.aliyun.com/document_detail/95551.html?spm=a2c4g.11186623.6.1263.1bca433eEEndIB
    interface DescribeSnapshotsUsageResponse extends ECSAPIResponse {
    }

    interface DescribeSnapshotsUsageBuilder {
        @QueryParam("InstanceIds")
        DescribeSnapshotsUsageBuilder instanceIds(final String[] instanceIds);

        @QueryParam("RamRoleName")
        DescribeSnapshotsUsageBuilder ramRoleName(final String ramRoleName);

        @QueryParam("RegionId")
        DescribeSnapshotsUsageBuilder regionId(final String regionId);

        Observable<DescribeSnapshotsUsageResponse> call();
    }

    DescribeSnapshotsUsageBuilder describeSnapshotsUsage();

    //  TBD: https://help.aliyun.com/document_detail/95578.html?spm=a2c4g.11186623.6.1264.4062522fIDJqAQ
    interface DescribeSnapshotPackageResponse extends ECSAPIResponse {
    }

    interface DescribeSnapshotPackageBuilder {
        @QueryParam("InstanceIds")
        DescribeSnapshotPackageBuilder instanceIds(final String[] instanceIds);

        @QueryParam("RamRoleName")
        DescribeSnapshotPackageBuilder ramRoleName(final String ramRoleName);

        @QueryParam("RegionId")
        DescribeSnapshotPackageBuilder regionId(final String regionId);

        Observable<DescribeSnapshotPackageResponse> call();
    }

    DescribeSnapshotPackageBuilder describeSnapshotPackage();

    //  TBD: https://help.aliyun.com/document_detail/55837.html?spm=a2c4g.11186623.6.1265.7be57e22pefIke
    interface DescribeSnapshotLinksResponse extends ECSAPIResponse {
    }

    interface DescribeSnapshotLinksBuilder {
        @QueryParam("InstanceIds")
        DescribeSnapshotLinksBuilder instanceIds(final String[] instanceIds);

        @QueryParam("RamRoleName")
        DescribeSnapshotLinksBuilder ramRoleName(final String ramRoleName);

        @QueryParam("RegionId")
        DescribeSnapshotLinksBuilder regionId(final String regionId);

        Observable<DescribeSnapshotLinksResponse> call();
    }

    DescribeSnapshotLinksBuilder describeSnapshotLinks();

    //  TBD: https://help.aliyun.com/document_detail/127734.html?spm=a2c4g.11186623.6.1266.76b0d74fSiZQnY
    interface ModifySnapshotAttributeResponse extends ECSAPIResponse {
    }

    interface ModifySnapshotAttributeBuilder {
        @QueryParam("InstanceIds")
        ModifySnapshotAttributeBuilder instanceIds(final String[] instanceIds);

        @QueryParam("RamRoleName")
        ModifySnapshotAttributeBuilder ramRoleName(final String ramRoleName);

        @QueryParam("RegionId")
        ModifySnapshotAttributeBuilder regionId(final String regionId);

        Observable<ModifySnapshotAttributeResponse> call();
    }

    ModifySnapshotAttributeBuilder modifySnapshotAttribute();

    //  TBD: https://help.aliyun.com/document_detail/25527.html?spm=a2c4g.11186623.6.1267.778e9fa0Vyl9xe
    interface CreateAutoSnapshotPolicyResponse extends ECSAPIResponse {
    }

    interface CreateAutoSnapshotPolicyBuilder {
        @QueryParam("InstanceIds")
        CreateAutoSnapshotPolicyBuilder instanceIds(final String[] instanceIds);

        @QueryParam("RamRoleName")
        CreateAutoSnapshotPolicyBuilder ramRoleName(final String ramRoleName);

        @QueryParam("RegionId")
        CreateAutoSnapshotPolicyBuilder regionId(final String regionId);

        Observable<CreateAutoSnapshotPolicyResponse> call();
    }

    CreateAutoSnapshotPolicyBuilder createAutoSnapshotPolicy();

    //  TBD: https://help.aliyun.com/document_detail/25531.html?spm=a2c4g.11186623.6.1268.61ee6236U1IcVM
    interface ApplyAutoSnapshotPolicyResponse extends ECSAPIResponse {
    }

    interface ApplyAutoSnapshotPolicyBuilder {
        @QueryParam("InstanceIds")
        ApplyAutoSnapshotPolicyBuilder instanceIds(final String[] instanceIds);

        @QueryParam("RamRoleName")
        ApplyAutoSnapshotPolicyBuilder ramRoleName(final String ramRoleName);

        @QueryParam("RegionId")
        ApplyAutoSnapshotPolicyBuilder regionId(final String regionId);

        Observable<ApplyAutoSnapshotPolicyResponse> call();
    }

    ApplyAutoSnapshotPolicyBuilder applyAutoSnapshotPolicy();

    //  TBD: https://help.aliyun.com/document_detail/25532.html?spm=a2c4g.11186623.6.1269.27351af2AQ8JeQ
    interface CancelAutoSnapshotPolicyResponse extends ECSAPIResponse {
    }

    interface CancelAutoSnapshotPolicyBuilder {
        @QueryParam("InstanceIds")
        CancelAutoSnapshotPolicyBuilder instanceIds(final String[] instanceIds);

        @QueryParam("RamRoleName")
        CancelAutoSnapshotPolicyBuilder ramRoleName(final String ramRoleName);

        @QueryParam("RegionId")
        CancelAutoSnapshotPolicyBuilder regionId(final String regionId);

        Observable<CancelAutoSnapshotPolicyResponse> call();
    }

    CancelAutoSnapshotPolicyBuilder cancelAutoSnapshotPolicy();

    //  TBD: https://help.aliyun.com/document_detail/25528.html?spm=a2c4g.11186623.6.1271.33827da96i5nA5
    interface DeleteAutoSnapshotPolicyResponse extends ECSAPIResponse {
    }

    interface DeleteAutoSnapshotPolicyBuilder {
        @QueryParam("InstanceIds")
        DeleteAutoSnapshotPolicyBuilder instanceIds(final String[] instanceIds);

        @QueryParam("RamRoleName")
        DeleteAutoSnapshotPolicyBuilder ramRoleName(final String ramRoleName);

        @QueryParam("RegionId")
        DeleteAutoSnapshotPolicyBuilder regionId(final String regionId);

        Observable<DeleteAutoSnapshotPolicyResponse> call();
    }

    DeleteAutoSnapshotPolicyBuilder deleteAutoSnapshotPolicy();

    //  TBD: https://help.aliyun.com/document_detail/25530.html?spm=a2c4g.11186623.6.1277.2d4c6238FywcJH
    interface DescribeAutoSnapshotPolicyEXResponse extends ECSAPIResponse {
    }

    interface DescribeAutoSnapshotPolicyEXBuilder {
        @QueryParam("InstanceIds")
        DescribeAutoSnapshotPolicyEXBuilder instanceIds(final String[] instanceIds);

        @QueryParam("RamRoleName")
        DescribeAutoSnapshotPolicyEXBuilder ramRoleName(final String ramRoleName);

        @QueryParam("RegionId")
        DescribeAutoSnapshotPolicyEXBuilder regionId(final String regionId);

        Observable<DescribeAutoSnapshotPolicyEXResponse> call();
    }

    DescribeAutoSnapshotPolicyEXBuilder describeAutoSnapshotPolicyEX();

    //  TBD: https://help.aliyun.com/document_detail/25530.html?spm=a2c4g.11186623.6.1277.2d4c6238FywcJH
    interface ModifyAutoSnapshotPolicyExResponse extends ECSAPIResponse {
    }

    interface ModifyAutoSnapshotPolicyExBuilder {
        @QueryParam("InstanceIds")
        ModifyAutoSnapshotPolicyExBuilder instanceIds(final String[] instanceIds);

        @QueryParam("RamRoleName")
        ModifyAutoSnapshotPolicyExBuilder ramRoleName(final String ramRoleName);

        @QueryParam("RegionId")
        ModifyAutoSnapshotPolicyExBuilder regionId(final String regionId);

        Observable<ModifyAutoSnapshotPolicyExResponse> call();
    }

    ModifyAutoSnapshotPolicyExBuilder modifyAutoSnapshotPolicyEx();

    //   标签  相关
    //  TBD: https://help.aliyun.com/document_detail/110425.html?spm=a2c4g.11186623.6.1358.488635bdamSbBO
    interface ListTagResourcesResponse extends ECSAPIResponse {
    }

    interface ListTagResourcesBuilder {
        @QueryParam("InstanceIds")
        ListTagResourcesBuilder instanceIds(final String[] instanceIds);

        @QueryParam("RamRoleName")
        ListTagResourcesBuilder ramRoleName(final String ramRoleName);

        @QueryParam("RegionId")
        ListTagResourcesBuilder regionId(final String regionId);

        Observable<ListTagResourcesResponse> call();
    }

    ListTagResourcesBuilder listTagResources();

    //  TBD: https://help.aliyun.com/document_detail/110424.html?spm=a2c4g.11186623.6.1359.1de02661GZftkL
    interface TagResourcesResponse extends ECSAPIResponse {
    }

    interface TagResourcesBuilder {
        @QueryParam("InstanceIds")
        TagResourcesBuilder instanceIds(final String[] instanceIds);

        @QueryParam("RamRoleName")
        TagResourcesBuilder ramRoleName(final String ramRoleName);

        @QueryParam("RegionId")
        TagResourcesBuilder regionId(final String regionId);

        Observable<TagResourcesResponse> call();
    }

    TagResourcesBuilder tagResources();

    //  TBD: https://help.aliyun.com/document_detail/110426.html?spm=a2c4g.11186623.6.1361.1d574ae9OfdcPJ
    interface UntagResourcesResponse extends ECSAPIResponse {
    }

    interface UntagResourcesBuilder {
        @QueryParam("InstanceIds")
        UntagResourcesBuilder instanceIds(final String[] instanceIds);

        @QueryParam("RamRoleName")
        UntagResourcesBuilder ramRoleName(final String ramRoleName);

        @QueryParam("RegionId")
        UntagResourcesBuilder regionId(final String regionId);

        Observable<UntagResourcesResponse> call();
    }

    UntagResourcesBuilder untagResources();

    //   其他接口  相关
    //  TBD: https://help.aliyun.com/document_detail/25624.html?spm=a2c4g.11186623.6.1368.2a3635bdxZ4dUa
    interface CancelTaskResponse extends ECSAPIResponse {
    }

    interface CancelTaskBuilder {
        @QueryParam("InstanceIds")
        CancelTaskBuilder instanceIds(final String[] instanceIds);

        @QueryParam("RamRoleName")
        CancelTaskBuilder ramRoleName(final String ramRoleName);

        @QueryParam("RegionId")
        CancelTaskBuilder regionId(final String regionId);

        Observable<CancelTaskResponse> call();
    }

    CancelTaskBuilder cancelTask();

    //  TBD: https://help.aliyun.com/document_detail/25622.html?spm=a2c4g.11186623.6.1369.5092798dljvEaa
    interface DescribeTasksResponse extends ECSAPIResponse {
    }

    interface DescribeTasksBuilder {
        @QueryParam("InstanceIds")
        DescribeTasksBuilder instanceIds(final String[] instanceIds);

        @QueryParam("RamRoleName")
        DescribeTasksBuilder ramRoleName(final String ramRoleName);

        @QueryParam("RegionId")
        DescribeTasksBuilder regionId(final String regionId);

        Observable<DescribeTasksResponse> call();
    }

    DescribeTasksBuilder describeTasks();

    //  TBD: https://help.aliyun.com/document_detail/25623.html?spm=a2c4g.11186623.6.1370.29ba36f432NkAJ
    interface DescribeTaskAttributeResponse extends ECSAPIResponse {
    }

    interface DescribeTaskAttributeBuilder {
        @QueryParam("InstanceIds")
        DescribeTaskAttributeBuilder instanceIds(final String[] instanceIds);

        @QueryParam("RamRoleName")
        DescribeTaskAttributeBuilder ramRoleName(final String ramRoleName);

        @QueryParam("RegionId")
        DescribeTaskAttributeBuilder regionId(final String regionId);

        Observable<DescribeTaskAttributeResponse> call();
    }

    DescribeTaskAttributeBuilder describeTaskAttribute();

    //  TBD: https://help.aliyun.com/document_detail/73772.html?spm=a2c4g.11186623.6.1371.709c7a75eBme4B
    interface DescribeAccountAttributesResponse extends ECSAPIResponse {
    }

    interface DescribeAccountAttributesBuilder {
        @QueryParam("InstanceIds")
        DescribeAccountAttributesBuilder instanceIds(final String[] instanceIds);

        @QueryParam("RamRoleName")
        DescribeAccountAttributesBuilder ramRoleName(final String ramRoleName);

        @QueryParam("RegionId")
        DescribeAccountAttributesBuilder regionId(final String regionId);

        Observable<DescribeAccountAttributesResponse> call();
    }

    DescribeAccountAttributesBuilder describeAccountAttributes();

    //  TBD: https://help.aliyun.com/document_detail/107829.html?spm=a2c4g.11186623.6.1372.4a895e37VRbOZi
    interface DescribePriceResponse extends ECSAPIResponse {
    }

    interface DescribePriceBuilder {
        @QueryParam("InstanceIds")
        DescribePriceBuilder instanceIds(final String[] instanceIds);

        @QueryParam("RamRoleName")
        DescribePriceBuilder ramRoleName(final String ramRoleName);

        @QueryParam("RegionId")
        DescribePriceBuilder regionId(final String regionId);

        Observable<DescribePriceResponse> call();
    }

    DescribePriceBuilder describePrice();

    //  TBD: https://help.aliyun.com/document_detail/92260.html?spm=a2c4g.11186623.6.1373.72cf263anSGODT
    interface JoinResourceGroupResponse extends ECSAPIResponse {
    }

    interface JoinResourceGroupBuilder {
        @QueryParam("InstanceIds")
        JoinResourceGroupBuilder instanceIds(final String[] instanceIds);

        @QueryParam("RamRoleName")
        JoinResourceGroupBuilder ramRoleName(final String ramRoleName);

        @QueryParam("RegionId")
        JoinResourceGroupBuilder regionId(final String regionId);

        Observable<JoinResourceGroupResponse> call();
    }

    JoinResourceGroupBuilder joinResourceGroup();

    //   云助手  相关
    //  TBD: https://help.aliyun.com/document_detail/64844.html?spm=a2c4g.11186623.6.1343.7a9f6d8eLIV8qe
    interface CreateCommandResponse extends ECSAPIResponse {
    }

    interface CreateCommandBuilder {
        @QueryParam("InstanceIds")
        CreateCommandBuilder instanceIds(final String[] instanceIds);

        @QueryParam("RamRoleName")
        CreateCommandBuilder ramRoleName(final String ramRoleName);

        @QueryParam("RegionId")
        CreateCommandBuilder regionId(final String regionId);

        Observable<CreateCommandResponse> call();
    }

    CreateCommandBuilder createCommand();

    //  TBD: https://help.aliyun.com/document_detail/85916.html?spm=a2c4g.11186623.6.1344.1276472eUDg498
    interface InstallCloudAssistantResponse extends ECSAPIResponse {
    }

    interface InstallCloudAssistantBuilder {
        @QueryParam("InstanceIds")
        InstallCloudAssistantBuilder instanceIds(final String[] instanceIds);

        @QueryParam("RamRoleName")
        InstallCloudAssistantBuilder ramRoleName(final String ramRoleName);

        @QueryParam("RegionId")
        InstallCloudAssistantBuilder regionId(final String regionId);

        Observable<InstallCloudAssistantResponse> call();
    }

    InstallCloudAssistantBuilder installCloudAssistant();

    //  TBD: https://help.aliyun.com/document_detail/64841.html?spm=a2c4g.11186623.6.1346.3cb2773fXyA2wP
    interface InvokeCommandResponse extends ECSAPIResponse {
    }

    interface InvokeCommandBuilder {
        @QueryParam("InstanceIds")
        InvokeCommandBuilder instanceIds(final String[] instanceIds);

        @QueryParam("RamRoleName")
        InvokeCommandBuilder ramRoleName(final String ramRoleName);

        @QueryParam("RegionId")
        InvokeCommandBuilder regionId(final String regionId);

        Observable<InvokeCommandResponse> call();
    }

    InvokeCommandBuilder invokeCommand();

    //  TBD: https://help.aliyun.com/document_detail/64838.html?spm=a2c4g.11186623.6.1349.51561926IyfD8o
    interface StopInvocationResponse extends ECSAPIResponse {
    }

    interface StopInvocationBuilder {
        @QueryParam("InstanceIds")
        StopInvocationBuilder instanceIds(final String[] instanceIds);

        @QueryParam("RamRoleName")
        StopInvocationBuilder ramRoleName(final String ramRoleName);

        @QueryParam("RegionId")
        StopInvocationBuilder regionId(final String regionId);

        Observable<StopInvocationResponse> call();
    }

    StopInvocationBuilder stopInvocation();

    //  TBD: https://help.aliyun.com/document_detail/64842.html?spm=a2c4g.11186623.6.1350.133cb4b0Zdhimm
    interface DeleteCommandResponse extends ECSAPIResponse {
    }

    interface DeleteCommandBuilder {
        @QueryParam("InstanceIds")
        DeleteCommandBuilder instanceIds(final String[] instanceIds);

        @QueryParam("RamRoleName")
        DeleteCommandBuilder ramRoleName(final String ramRoleName);

        @QueryParam("RegionId")
        DeleteCommandBuilder regionId(final String regionId);

        Observable<DeleteCommandResponse> call();
    }

    DeleteCommandBuilder deleteCommand();

    //  TBD: https://help.aliyun.com/document_detail/87346.html?spm=a2c4g.11186623.6.1351.103c47d4xMvPxL
    interface DescribeCloudAssistantStatusResponse extends ECSAPIResponse {
    }

    interface DescribeCloudAssistantStatusBuilder {
        @QueryParam("InstanceIds")
        DescribeCloudAssistantStatusBuilder instanceIds(final String[] instanceIds);

        @QueryParam("RamRoleName")
        DescribeCloudAssistantStatusBuilder ramRoleName(final String ramRoleName);

        @QueryParam("RegionId")
        DescribeCloudAssistantStatusBuilder regionId(final String regionId);

        Observable<DescribeCloudAssistantStatusResponse> call();
    }

    DescribeCloudAssistantStatusBuilder describeCloudAssistantStatus();

    //  TBD: https://help.aliyun.com/document_detail/64843.html?spm=a2c4g.11186623.6.1352.18ac3f24b3cVxc
    interface DescribeCommandsResponse extends ECSAPIResponse {
    }

    interface DescribeCommandsBuilder {
        @QueryParam("InstanceIds")
        DescribeCommandsBuilder instanceIds(final String[] instanceIds);

        @QueryParam("RamRoleName")
        DescribeCommandsBuilder ramRoleName(final String ramRoleName);

        @QueryParam("RegionId")
        DescribeCommandsBuilder regionId(final String regionId);

        Observable<DescribeCommandsResponse> call();
    }

    DescribeCommandsBuilder describeCommands();

    //  TBD: https://help.aliyun.com/document_detail/64840.html?spm=a2c4g.11186623.6.1345.35513a7b1zmzIp
    interface DescribeInvocationsResponse extends ECSAPIResponse {
    }

    interface DescribeInvocationsBuilder {
        @QueryParam("InstanceIds")
        DescribeInvocationsBuilder instanceIds(final String[] instanceIds);

        @QueryParam("RamRoleName")
        DescribeInvocationsBuilder ramRoleName(final String ramRoleName);

        @QueryParam("RegionId")
        DescribeInvocationsBuilder regionId(final String regionId);

        Observable<DescribeInvocationsResponse> call();
    }

    DescribeInvocationsBuilder describeInvocations();

    //  TBD: https://help.aliyun.com/document_detail/64845.html?spm=a2c4g.11186623.6.1347.2e9c158domawVd
    interface DescribeInvocationResultsResponse extends ECSAPIResponse {
    }

    interface DescribeInvocationResultsBuilder {
        @QueryParam("InstanceIds")
        DescribeInvocationResultsBuilder instanceIds(final String[] instanceIds);

        @QueryParam("RamRoleName")
        DescribeInvocationResultsBuilder ramRoleName(final String ramRoleName);

        @QueryParam("RegionId")
        DescribeInvocationResultsBuilder regionId(final String regionId);

        Observable<DescribeInvocationResultsResponse> call();
    }

    DescribeInvocationResultsBuilder describeInvocationResults();

    //  TBD: https://help.aliyun.com/document_detail/141751.html?spm=a2c4g.11186623.6.1354.5bc37a72saFcg1
    interface RunCommandResponse extends ECSAPIResponse {
    }

    interface RunCommandBuilder {
        @QueryParam("InstanceIds")
        RunCommandBuilder instanceIds(final String[] instanceIds);

        @QueryParam("RamRoleName")
        RunCommandBuilder ramRoleName(final String ramRoleName);

        @QueryParam("RegionId")
        RunCommandBuilder regionId(final String regionId);

        Observable<RunCommandResponse> call();
    }

    RunCommandBuilder runCommand();

    //  弹性网卡  相关
    //  TBD: https://help.aliyun.com/document_detail/58504.html?spm=a2c4g.11186623.6.1327.51aa86e9El1PHv
    interface CreateNetworkInterfaceResponse extends ECSAPIResponse {
    }

    interface CreateNetworkInterfaceBuilder {
        @QueryParam("InstanceIds")
        CreateNetworkInterfaceBuilder instanceIds(final String[] instanceIds);

        @QueryParam("RamRoleName")
        CreateNetworkInterfaceBuilder ramRoleName(final String ramRoleName);

        @QueryParam("RegionId")
        CreateNetworkInterfaceBuilder regionId(final String regionId);

        Observable<CreateNetworkInterfaceResponse> call();
    }

    CreateNetworkInterfaceBuilder createNetworkInterface();

    //  TBD: https://help.aliyun.com/document_detail/58515.html?spm=a2c4g.11186623.6.1329.2a067a72ZhM51R
    interface AttachNetworkInterfaceResponse extends ECSAPIResponse {
    }

    interface AttachNetworkInterfaceBuilder {
        @QueryParam("InstanceIds")
        AttachNetworkInterfaceBuilder instanceIds(final String[] instanceIds);

        @QueryParam("RamRoleName")
        AttachNetworkInterfaceBuilder ramRoleName(final String ramRoleName);

        @QueryParam("RegionId")
        AttachNetworkInterfaceBuilder regionId(final String regionId);

        Observable<AttachNetworkInterfaceResponse> call();
    }

    AttachNetworkInterfaceBuilder attachNetworkInterface();

    //  TBD: https://help.aliyun.com/document_detail/85917.html?spm=a2c4g.11186623.6.1330.dd634f39HKBh0v
    interface AssignPrivateIpAddressesResponse extends ECSAPIResponse {
    }

    interface AssignPrivateIpAddressesBuilder {
        @QueryParam("InstanceIds")
        AssignPrivateIpAddressesBuilder instanceIds(final String[] instanceIds);

        @QueryParam("RamRoleName")
        AssignPrivateIpAddressesBuilder ramRoleName(final String ramRoleName);

        @QueryParam("RegionId")
        AssignPrivateIpAddressesBuilder regionId(final String regionId);

        Observable<AssignPrivateIpAddressesResponse> call();
    }

    AssignPrivateIpAddressesBuilder assignPrivateIpAddresses();

    //  TBD: https://help.aliyun.com/document_detail/85919.html?spm=a2c4g.11186623.6.1331.6bf94965QWRawg
    interface UnassignPrivateIpAddressesResponse extends ECSAPIResponse {
    }

    interface UnassignPrivateIpAddressesBuilder {
        @QueryParam("InstanceIds")
        UnassignPrivateIpAddressesBuilder instanceIds(final String[] instanceIds);

        @QueryParam("RamRoleName")
        UnassignPrivateIpAddressesBuilder ramRoleName(final String ramRoleName);

        @QueryParam("RegionId")
        UnassignPrivateIpAddressesBuilder regionId(final String regionId);

        Observable<UnassignPrivateIpAddressesResponse> call();
    }

    UnassignPrivateIpAddressesBuilder unassignPrivateIpAddresses();

    //  TBD: https://help.aliyun.com/document_detail/58514.html?spm=a2c4g.11186623.6.1332.50a81790DO2d8g
    interface DetachNetworkInterfaceResponse extends ECSAPIResponse {
    }

    interface DetachNetworkInterfaceBuilder {
        @QueryParam("InstanceIds")
        DetachNetworkInterfaceBuilder instanceIds(final String[] instanceIds);

        @QueryParam("RamRoleName")
        DetachNetworkInterfaceBuilder ramRoleName(final String ramRoleName);

        @QueryParam("RegionId")
        DetachNetworkInterfaceBuilder regionId(final String regionId);

        Observable<DetachNetworkInterfaceResponse> call();
    }

    DetachNetworkInterfaceBuilder detachNetworkInterface();

    //  TBD: https://help.aliyun.com/document_detail/58516.html?spm=a2c4g.11186623.6.1333.3a904f39QATDWq
    interface DeleteNetworkInterfaceResponse extends ECSAPIResponse {
    }

    interface DeleteNetworkInterfaceBuilder {
        @QueryParam("InstanceIds")
        DeleteNetworkInterfaceBuilder instanceIds(final String[] instanceIds);

        @QueryParam("RamRoleName")
        DeleteNetworkInterfaceBuilder ramRoleName(final String ramRoleName);

        @QueryParam("RegionId")
        DeleteNetworkInterfaceBuilder regionId(final String regionId);

        Observable<DeleteNetworkInterfaceResponse> call();
    }

    DeleteNetworkInterfaceBuilder deleteNetworkInterface();

    //  TBD: https://help.aliyun.com/document_detail/58512.html?spm=a2c4g.11186623.6.1334.ea547a6dhDqTYn
    interface DescribeNetworkInterfacesResponse extends ECSAPIResponse {
    }

    interface DescribeNetworkInterfacesBuilder {
        @QueryParam("InstanceIds")
        DescribeNetworkInterfacesBuilder instanceIds(final String[] instanceIds);

        @QueryParam("RamRoleName")
        DescribeNetworkInterfacesBuilder ramRoleName(final String ramRoleName);

        @QueryParam("RegionId")
        DescribeNetworkInterfacesBuilder regionId(final String regionId);

        Observable<DescribeNetworkInterfacesResponse> call();
    }

    DescribeNetworkInterfacesBuilder describeNetworkInterfaces();

    //  TBD: https://help.aliyun.com/document_detail/58513.html?spm=a2c4g.11186623.6.1335.2c3360e0bTySBa
    interface ModifyNetworkInterfaceAttributeResponse extends ECSAPIResponse {
    }

    interface ModifyNetworkInterfaceAttributeBuilder {
        @QueryParam("InstanceIds")
        ModifyNetworkInterfaceAttributeBuilder instanceIds(final String[] instanceIds);

        @QueryParam("RamRoleName")
        ModifyNetworkInterfaceAttributeBuilder ramRoleName(final String ramRoleName);

        @QueryParam("RegionId")
        ModifyNetworkInterfaceAttributeBuilder regionId(final String regionId);

        Observable<ModifyNetworkInterfaceAttributeResponse> call();
    }

    ModifyNetworkInterfaceAttributeBuilder modifyNetworkInterfaceAttribute();

    //  TBD: https://help.aliyun.com/document_detail/98610.html?spm=a2c4g.11186623.6.1336.416033215s81Yn
    interface AssignIpv6AddressesResponse extends ECSAPIResponse {
    }

    interface AssignIpv6AddressesBuilder {
        @QueryParam("InstanceIds")
        AssignIpv6AddressesBuilder instanceIds(final String[] instanceIds);

        @QueryParam("RamRoleName")
        AssignIpv6AddressesBuilder ramRoleName(final String ramRoleName);

        @QueryParam("RegionId")
        AssignIpv6AddressesBuilder regionId(final String regionId);

        Observable<AssignIpv6AddressesResponse> call();
    }

    AssignIpv6AddressesBuilder assignIpv6Addresses();

    //  TBD: https://help.aliyun.com/document_detail/98611.html?spm=a2c4g.11186623.6.1337.2e0278ecncWwUb
    interface UnassignIpv6AddressesResponse extends ECSAPIResponse {
    }

    interface UnassignIpv6AddressesBuilder {
        @QueryParam("InstanceIds")
        UnassignIpv6AddressesBuilder instanceIds(final String[] instanceIds);

        @QueryParam("RamRoleName")
        UnassignIpv6AddressesBuilder ramRoleName(final String ramRoleName);

        @QueryParam("RegionId")
        UnassignIpv6AddressesBuilder regionId(final String regionId);

        Observable<UnassignIpv6AddressesResponse> call();
    }

    UnassignIpv6AddressesBuilder unassignIpv6Addresses();
}
