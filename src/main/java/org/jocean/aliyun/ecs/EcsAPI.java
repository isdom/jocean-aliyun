package org.jocean.aliyun.ecs;

import javax.ws.rs.QueryParam;

import org.jocean.http.RpcRunner;

import com.alibaba.fastjson.annotation.JSONField;

import rx.Observable.Transformer;

// https://help.aliyun.com/document_detail/102988.html?spm=a2c4g.11186623.6.1069.118a79e0WI5Er2

public interface EcsAPI {

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

    interface DescribeInstancesResponse extends ECSAPIResponse {
        @JSONField(name="PageNumber")
        int getPageNumber();

        @JSONField(name="PageNumber")
        void setPageNumber(final int pageNumber);

        @JSONField(name="PageSize")
        int getPageSize();

        @JSONField(name="PageSize")
        void setPageSize(final int pageSize);

        @JSONField(name="TotalCount")
        int getTotalCount();

        @JSONField(name="TotalCount")
        void setTotalCount(final int totalCount);

        @JSONField(name="Instances")
        InstanceSet getInstances();

        @JSONField(name="Instances")
        void setInstances(final InstanceSet instanceSet);
    }

    Transformer<RpcRunner, DescribeInstancesResponse> describeInstances(final String regionId,
                                                                        final String vpcId,
                                                                        final String InstanceName);

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

        Transformer<RpcRunner, DescribeSpotPriceHistoryResponse> call();
    }

    DescribeSpotPriceHistoryBuilder describeSpotPriceHistory();

    // CreateInstance: https://help.aliyun.com/document_detail/25499.html?spm=a2c4g.11186623.6.1083.73643ff5dezPxV
    interface CreateInstanceResponse extends ECSAPIResponse {
        @JSONField(name="InstanceId")
        String getInstanceId();

        @JSONField(name="InstanceId")
        void setInstanceId(final String instanceId);
    }

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

        Transformer<RpcRunner, CreateInstanceResponse> call();
    }

    CreateInstanceBuilder createInstance();

    interface StartInstanceResponse extends ECSAPIResponse {
    }

    interface StartInstanceBuilder {
        @QueryParam("InstanceId")
        StartInstanceBuilder instanceId(final String instanceId);

        @QueryParam("InitLocalDisk")
        StartInstanceBuilder initLocalDisk(final boolean init);

        @QueryParam("OwnerAccount")
        StartInstanceBuilder ownerAccount(final String ownerAccount);

        Transformer<RpcRunner, StartInstanceResponse> call();
    }

    StartInstanceBuilder startInstance();

    interface RebootInstanceResponse extends ECSAPIResponse {
    }

    interface RebootInstanceBuilder {
        @QueryParam("InstanceId")
        RebootInstanceBuilder instanceId(final String instanceId);

        @QueryParam("DryRun")
        RebootInstanceBuilder dryRun(final boolean dryRun);

        @QueryParam("ForceStop")
        RebootInstanceBuilder forceStop(final boolean forceStop);

        Transformer<RpcRunner, RebootInstanceResponse> call();
    }

    RebootInstanceBuilder rebootInstance();

    interface StopInstanceResponse extends ECSAPIResponse {
    }

    interface StopInstanceBuilder {
        @QueryParam("InstanceId")
        StopInstanceBuilder instanceId(final String instanceId);

        @QueryParam("StoppedMode")
        StopInstanceBuilder stoppedMode(final String stoppedMode);

        @QueryParam("ForceStop")
        StopInstanceBuilder forceStop(final boolean forceStop);

        @QueryParam("ConfirmStop")
        StopInstanceBuilder confirmStop(final boolean confirmStop);

        Transformer<RpcRunner, StopInstanceResponse> call();
    }

    StopInstanceBuilder stopInstance();

    interface DeleteInstanceResponse extends ECSAPIResponse {
    }

    interface DeleteInstanceBuilder {
        @QueryParam("InstanceId")
        DeleteInstanceBuilder instanceId(final String instanceId);

        @QueryParam("Force")
        DeleteInstanceBuilder force(final boolean force);

        @QueryParam("TerminateSubscription")
        DeleteInstanceBuilder terminateSubscription(final boolean terminateSubscription);

        Transformer<RpcRunner, DeleteInstanceResponse> call();
    }

    DeleteInstanceBuilder deleteInstance();

    interface AttachInstanceRamRoleResponse extends ECSAPIResponse {
    }

    interface AttachInstanceRamRoleBuilder {
        @QueryParam("InstanceIds")
        AttachInstanceRamRoleBuilder instanceIds(final String[] instanceIds);

        @QueryParam("RamRoleName")
        AttachInstanceRamRoleBuilder ramRoleName(final String ramRoleName);

        @QueryParam("RegionId")
        AttachInstanceRamRoleBuilder regionId(final String regionId);

        Transformer<RpcRunner, AttachInstanceRamRoleResponse> call();
    }

    AttachInstanceRamRoleBuilder attachInstanceRamRole();
}
