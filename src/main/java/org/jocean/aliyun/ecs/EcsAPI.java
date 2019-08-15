package org.jocean.aliyun.ecs;

import javax.ws.rs.QueryParam;

import org.jocean.http.RpcRunner;

import com.alibaba.fastjson.annotation.JSONField;

import rx.Observable.Transformer;

// https://help.aliyun.com/document_detail/102988.html?spm=a2c4g.11186623.6.1069.118a79e0WI5Er2

public interface EcsAPI {

//    "SecurityGroupIds":{
//    "SecurityGroupId":[
//        "sg-2ze7v8o1cbogte75c8xz"
//        ]
//    },
    public interface SecurityGroupIds {
        @JSONField(name="SecurityGroupId")
        public String[] getSecurityGroupIdArray();

        @JSONField(name="SecurityGroupId")
        public void setSecurityGroupIdArray(final String[] securityGroupIdArray);
    }

    public interface OperationLocks {
        @JSONField(name="LockMsg")
        public String getLockMsg();

        @JSONField(name="LockMsg")
        public void setLockMsg(final String lockMsg);

        @JSONField(name="LockReason")
        public String[] getLockReason();

        @JSONField(name="LockReason")
        public void setLockReason(final String[] lockReason);
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

    public interface NetworkInterface {
        @JSONField(name="MacAddress")
        public String getMacAddress();

        @JSONField(name="MacAddress")
        public void setMacAddress(final String macAddress);

        @JSONField(name="PrimaryIpAddress")
        public String getPrimaryIpAddress();

        @JSONField(name="PrimaryIpAddress")
        public void setPrimaryIpAddress(final String primaryIpAddress);

        @JSONField(name="NetworkInterfaceId")
        public String getNetworkInterfaceId();

        @JSONField(name="NetworkInterfaceId")
        public void setNetworkInterfaceId(final String networkInterfaceId);
    }

    public interface NetworkInterfaces {
        @JSONField(name="NetworkInterface")
        public NetworkInterface[] getNetworkInterfaceArray();

        @JSONField(name="NetworkInterface")
        public void setNetworkInterfaceArray(final NetworkInterface[] networkInterface);
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

    public interface IpAddress {
        @JSONField(name="IpAddress")
        public String[] getIpAddressArray();

        @JSONField(name="IpAddress")
        public void setIpAddressArray(final String[] ipAddressArray);
    }

    public interface VpcAttributes {
        @JSONField(name="NatIpAddress")
        public String getNatIpAddress();

        @JSONField(name="NatIpAddress")
        public void setNatIpAddress(final String natIpAddress);

        @JSONField(name="PrivateIpAddress")
        public IpAddress getPrivateIpAddress();

        @JSONField(name="PrivateIpAddress")
        public void setPrivateIpAddress(final IpAddress privateIpAddress);

        @JSONField(name="VSwitchId")
        public String getVSwitchId();

        @JSONField(name="VSwitchId")
        public void setVSwitchId(final String vSwitchId);

        @JSONField(name="VpcId")
        public String getVpcId();

        @JSONField(name="VpcId")
        public void setVpcId(final String vpcId);
    }

    public interface InstanceAttributesType {

        @JSONField(name="AutoReleaseTime")
        public String getAutoReleaseTime();

        @JSONField(name="AutoReleaseTime")
        public void setAutoReleaseTime(final String autoReleaseTime);

        @JSONField(name="Cpu")
        public int getCpu();

        @JSONField(name="Cpu")
        public void setCpu(final int Cpu);

        @JSONField(name="CreationTime")
        public String getCreationTime();

        @JSONField(name="CreationTime")
        public void setCreationTime(final String creationTime);

        @JSONField(name="CreditSpecification")
        public String getCreditSpecification();

        @JSONField(name="CreditSpecification")
        public void setCreditSpecification(final String creditSpecification);

        @JSONField(name="DeletionProtection")
        public boolean getDeletionProtection();

        @JSONField(name="DeletionProtection")
        public void setDeletionProtection(final boolean deletionProtection);

        @JSONField(name="DeploymentSetId")
        public String getDeploymentSetId();

        @JSONField(name="DeploymentSetId")
        public void setDeploymentSetId(final String deploymentSetId);

        @JSONField(name="Description")
        public String getDescription();

        @JSONField(name="Description")
        public void setDescription(final String description);

        @JSONField(name="DeviceAvailable")
        public boolean getDeviceAvailable();

        @JSONField(name="DeviceAvailable")
        public void setDeviceAvailable(final boolean deviceAvailable);

        @JSONField(name="ExpiredTime")
        public String getExpiredTime();

        @JSONField(name="ExpiredTime")
        public void setExpiredTime(final String expiredTime);

        @JSONField(name="GPUAmount")
        public int getGPUAmount();

        @JSONField(name="GPUAmount")
        public void setGPUAmount(final int gpuAmount);

        @JSONField(name="GPUSpec")
        public String getGPUSpec();

        @JSONField(name="GPUSpec")
        public void setGPUSpec(final String gpuSpec);

        @JSONField(name="HostName")
        public String getHostName();

        @JSONField(name="HostName")
        public void setHostName(final String hostName);

        @JSONField(name="ImageId")
        public String getImageId();

        @JSONField(name="ImageId")
        public void setImageId(final String imageId);

        @JSONField(name="InnerIpAddress")
        public IpAddress getInnerIpAddress();

        @JSONField(name="InnerIpAddress")
        public void setInnerIpAddress(final IpAddress innerIpAddress);

        @JSONField(name="InstanceChargeType")
        public String getInstanceChargeType();

        @JSONField(name="InstanceChargeType")
        public void setInstanceChargeType(final String instanceChargeType);

        @JSONField(name="InstanceId")
        public String getInstanceId();

        @JSONField(name="InstanceId")
        public void setInstanceId(final String instanceId);

        @JSONField(name="InstanceName")
        public String getInstanceName();

        @JSONField(name="InstanceName")
        public void setInstanceName(final String instanceName);

        @JSONField(name="InstanceNetworkType")
        public String getInstanceNetworkType();

        @JSONField(name="InstanceNetworkType")
        public void setInstanceNetworkType(final String instanceNetworkType);

        @JSONField(name="InstanceType")
        public String getInstanceType();

        @JSONField(name="InstanceType")
        public void setInstanceType(final String instanceType);

        @JSONField(name="InstanceTypeFamily")
        public String getInstanceTypeFamily();

        @JSONField(name="InstanceTypeFamily")
        public void setInstanceTypeFamily(final String instanceTypeFamily);

        @JSONField(name="InternetChargeType")
        public String getInternetChargeType();

        @JSONField(name="InternetChargeType")
        public void setInternetChargeType(final String internetChargeType);

        @JSONField(name="IoOptimized")
        public boolean getIoOptimized();

        @JSONField(name="IoOptimized")
        public void setIoOptimized(final boolean ioOptimized);

        @JSONField(name="KeyPairName")
        public String getKeyPairName();

        @JSONField(name="KeyPairName")
        public void setKeyPairName(final String keyPairName);

        @JSONField(name="LocalStorageAmount")
        public Integer getLocalStorageAmount();

        @JSONField(name="LocalStorageAmount")
        public void setLocalStorageAmount(final Integer localStorageAmount);

        @JSONField(name="LocalStorageCapacity")
        public Long getLocalStorageCapacity();

        @JSONField(name="LocalStorageCapacity")
        public void setLocalStorageCapacity(final Long localStorageCapacity);

        @JSONField(name="Memory")
        public int getMemory();

        @JSONField(name="Memory")
        public void setMemory(final int memory);

        @JSONField(name="NetworkInterfaces")
        public NetworkInterfaces getNetworkInterfaces();

        @JSONField(name="NetworkInterfaces")
        public void setNetworkInterfaces(final NetworkInterfaces networkInterfaces);

        @JSONField(name="OSName")
        public String getOSName();

        @JSONField(name="OSName")
        public void setOSName(final String osName);

        @JSONField(name="OSNameEn")
        public String getOSNameEn();

        @JSONField(name="OSNameEn")
        public void setOSNameEn(final String osNameEn);

        @JSONField(name="OSType")
        public String getOSType();

        @JSONField(name="OSType")
        public void setOSType(final String osType);

        @JSONField(name="OperationLocks")
        public OperationLocks getOperationLocks();

        @JSONField(name="OperationLocks")
        public void setOperationLocks(final OperationLocks operationLocks);

        @JSONField(name="PublicIpAddress")
        public IpAddress getPublicIpAddress();

        @JSONField(name="PublicIpAddress")
        public void setPublicIpAddress(final IpAddress publicIpAddress);

        @JSONField(name="Recyclable")
        public boolean getRecyclable();

        @JSONField(name="Recyclable")
        public void setRecyclable(final boolean recyclable);

        @JSONField(name="RegionId")
        public String getRegionId();

        @JSONField(name="RegionId")
        public void setRegionId(final String regionId);

        @JSONField(name="ResourceGroupId")
        public String getResourceGroupId();

        @JSONField(name="ResourceGroupId")
        public void setResourceGroupId(final String resourceGroupId);

        @JSONField(name="SaleCycle")
        public String getSaleCycle();

        @JSONField(name="SaleCycle")
        public void setSaleCycle(final String saleCycle);

        @JSONField(name="SecurityGroupIds")
        public SecurityGroupIds getSecurityGroupIds();

        @JSONField(name="SecurityGroupIds")
        public void setSecurityGroupIds(final SecurityGroupIds securityGroupIds);

        @JSONField(name="SerialNumber")
        public String getSerialNumber();

        @JSONField(name="SerialNumber")
        public void setSerialNumber(final String serialNumber);

        @JSONField(name="SpotPriceLimit")
        public float getSpotPriceLimit();

        @JSONField(name="SpotPriceLimit")
        public void setSpotPriceLimit(final float spotPriceLimit);

        @JSONField(name="SpotStrategy")
        public String getSpotStrategy();

        @JSONField(name="SpotStrategy")
        public void setSpotStrategy(final String spotStrategy);

        @JSONField(name="StartTime")
        public String getStartTime();

        @JSONField(name="StartTime")
        public void setStartTime(final String startTime);

        @JSONField(name="Status")
        public String getStatus();

        @JSONField(name="Status")
        public void setStatus(final String status);

        @JSONField(name="StoppedMode")
        public String getStoppedMode();

        @JSONField(name="StoppedMode")
        public void setStoppedMode(final String stoppedMode);

//        @JSONField(name="Tags")
//        public String getTags();
//
//        @JSONField(name="Tags")
//        public void setTags(final String tags);

        @JSONField(name="VpcAttributes")
        public VpcAttributes getVpcAttributes();

        @JSONField(name="VpcAttributes")
        public void setVpcAttributes(final VpcAttributes vpcAttributes);

        @JSONField(name="ZoneId")
        public String getZoneId();

        @JSONField(name="ZoneId")
        public void setZoneId(final String zoneId);
    }

    public interface InstanceSet {
        @JSONField(name="Instance")
        public InstanceAttributesType[] getInstance();

        @JSONField(name="Instance")
        public void setInstance(final InstanceAttributesType[] instances);
    }

    public interface DescribeInstancesResponse {
        @JSONField(name="RequestId")
        public String getRequestId();

        @JSONField(name="RequestId")
        public void setRequestId(final String requestId);

        @JSONField(name="PageNumber")
        public int getPageNumber();

        @JSONField(name="PageNumber")
        public void setPageNumber(final int pageNumber);

        @JSONField(name="PageSize")
        public int getPageSize();

        @JSONField(name="PageSize")
        public void setPageSize(final int pageSize);

        @JSONField(name="TotalCount")
        public int getTotalCount();

        @JSONField(name="TotalCount")
        public void setTotalCount(final int totalCount);

        @JSONField(name="Instances")
        public InstanceSet getInstances();

        @JSONField(name="Instances")
        public void setInstances(final InstanceSet instanceSet);
    }

    public Transformer<RpcRunner, DescribeInstancesResponse> describeInstances(final String regionId,
            final String vpcId,
            final String InstanceName);

    // DescribeSpotPriceHistory: https://help.aliyun.com/document_detail/60400.html?spm=a2c4g.11186623.6.1099.74662eafdAxS1p

    public interface SpotPriceType {
        @JSONField(name="InstanceType")
        public String getInstanceType();

        @JSONField(name="InstanceType")
        public void setInstanceType(final String instanceType);

        @JSONField(name="IoOptimized")
        public String getIoOptimized();

        @JSONField(name="IoOptimized")
        public void setIoOptimized(final String ioOptimized);

        @JSONField(name="NetworkType")
        public String getNetworkType();

        @JSONField(name="NetworkType")
        public void setNetworkType(final String networkType);

        @JSONField(name="OriginPrice")
        public float getOriginPrice();

        @JSONField(name="OriginPrice")
        public void setOriginPrice(final float originPrice);

        @JSONField(name="SpotPrice")
        public float getSpotPrice();

        @JSONField(name="SpotPrice")
        public void setSpotPrice(final float spotPrice);

        @JSONField(name="Timestamp")
        public String getTimestamp();

        @JSONField(name="Timestamp")
        public void setTimestamp(final String timestamp);

        @JSONField(name="ZoneId")
        public String getZoneId();

        @JSONField(name="ZoneId")
        public void setZoneId(final String zoneId);
    }

    public interface SpotPrices {
        @JSONField(name="SpotPriceType")
        public SpotPriceType[] getSpotPriceTypeArray();

        @JSONField(name="SpotPriceType")
        public void setSpotPriceTypeArray(final SpotPriceType[] spotPriceTypeArray);
    }

    public interface DescribeSpotPriceHistoryResponse {
        @JSONField(name="RequestId")
        public String getRequestId();

        @JSONField(name="RequestId")
        public void setRequestId(final String requestId);

        @JSONField(name="Currency")
        public String getCurrency();

        @JSONField(name="Currency")
        public void setCurrency(final String currency);

        @JSONField(name="NextOffset")
        public Integer getNextOffset();

        @JSONField(name="NextOffset")
        public void setNextOffset(final Integer nextOffset);

        @JSONField(name="SpotPrices")
        public SpotPrices getSpotPrices();

        @JSONField(name="SpotPrices")
        public void setSpotPrices(final SpotPrices spotPrices);
    }

    public interface DescribeSpotPriceHistoryBuilder {
        //  String   是   cn-hangzhou
        //  实例所属的地域ID。您可以调用DescribeRegions查看最新的阿里云地域列表。
        @QueryParam("RegionId")
        public DescribeSpotPriceHistoryBuilder regionId(final String regionId);

        //  String  是   ecs.t1.xsmall
        //  实例规格。
        @QueryParam("InstanceType")
        public DescribeSpotPriceHistoryBuilder instanceType(final String instanceType);

        //  String  是   vpc
        //  抢占式实例网络类型。取值范围：
        //  classic：表示抢占式实例的网络类型为经典网络。
        //  vpc：表示抢占式实例的网络类型为专有网络。
        @QueryParam("NetworkType")
        public DescribeSpotPriceHistoryBuilder networkType(final String networkType);

        public Transformer<RpcRunner, DescribeSpotPriceHistoryResponse> call();
    }

    public DescribeSpotPriceHistoryBuilder describeSpotPriceHistory();

    // CreateInstance: https://help.aliyun.com/document_detail/25499.html?spm=a2c4g.11186623.6.1083.73643ff5dezPxV
    public interface CreateInstanceResponse {
        @JSONField(name="RequestId")
        public String getRequestId();

        @JSONField(name="RequestId")
        public void setRequestId(final String requestId);

        @JSONField(name="InstanceId")
        public String getInstanceId();

        @JSONField(name="InstanceId")
        public void setInstanceId(final String instanceId);
    }

    public interface CreateInstanceBuilder {
        @QueryParam("ClientToken")
        public CreateInstanceBuilder clientToken(final String clientToken);

        @QueryParam("DryRun")
        public CreateInstanceBuilder dryRun(final boolean dryRun);

        @QueryParam("ImageId")
        public CreateInstanceBuilder imageId(final String imageId);

        @QueryParam("InstanceType")
        public CreateInstanceBuilder instanceType(final String instanceType);

        @QueryParam("RegionId")
        public CreateInstanceBuilder regionId(final String regionId);

        @QueryParam("SecurityGroupId")
        public CreateInstanceBuilder securityGroupId(final String securityGroupId);

        @QueryParam("InstanceName")
        public CreateInstanceBuilder instanceName(final String instanceName);

        @QueryParam("AutoRenew")
        public CreateInstanceBuilder autoRenew(final boolean autoRenew);

        @QueryParam("AutoRenewPeriod")
        public CreateInstanceBuilder autoRenewPeriod(final int autoRenewPeriod);

        @QueryParam("InternetChargeType")
        public CreateInstanceBuilder internetChargeType(final boolean internetChargeType);

        @QueryParam("InternetMaxBandwidthIn")
        public CreateInstanceBuilder internetMaxBandwidthIn(final int internetMaxBandwidthIn);

        @QueryParam("InternetMaxBandwidthOut")
        public CreateInstanceBuilder internetMaxBandwidthOut(final int internetMaxBandwidthOut);

        @QueryParam("HostName")
        public CreateInstanceBuilder hostName(final String hostName);

        @QueryParam("Password")
        public CreateInstanceBuilder password(final String password);

        @QueryParam("PasswordInherit")
        public CreateInstanceBuilder passwordInherit(final boolean passwordInherit);

        @QueryParam("DeploymentSetId")
        public CreateInstanceBuilder deploymentSetId(final String deploymentSetId);

        @QueryParam("ZoneId")
        public CreateInstanceBuilder zoneId(final String zoneId);

        @QueryParam("ClusterId")
        public CreateInstanceBuilder clusterId(final String clusterId);

        @QueryParam("VlanId")
        public CreateInstanceBuilder vlanId(final String vlanId);

        @QueryParam("InnerIpAddress")
        public CreateInstanceBuilder innerIpAddress(final String innerIpAddress);

        @QueryParam("SystemDisk.Size")
        public CreateInstanceBuilder systemDiskSize(final int systemDiskSize);

        @QueryParam("SystemDisk.Category")
        public CreateInstanceBuilder systemDiskCategory(final String systemDiskCategory);

        @QueryParam("SystemDisk.DiskName")
        public CreateInstanceBuilder systemDiskDiskName(final String systemDiskDiskName);

        @QueryParam("SystemDisk.Description")
        public CreateInstanceBuilder systemDiskDescription(final String systemDiskDescription);

        @QueryParam("Description")
        public CreateInstanceBuilder description(final String description);

        @QueryParam("VSwitchId")
        public CreateInstanceBuilder vSwitchId(final String vSwitchId);

        @QueryParam("PrivateIpAddress")
        public CreateInstanceBuilder privateIpAddress(final String privateIpAddress);

        @QueryParam("IoOptimized")
        public CreateInstanceBuilder ioOptimized(final String ioOptimized);

        @QueryParam("UseAdditionalService")
        public CreateInstanceBuilder useAdditionalService(final boolean useAdditionalService);

        @QueryParam("InstanceChargeType")
        public CreateInstanceBuilder instanceChargeType(final String instanceChargeType);

        @QueryParam("Period")
        public CreateInstanceBuilder period(final int period);

        @QueryParam("PeriodUnit")
        public CreateInstanceBuilder periodUnit(final String periodUnit);

        @QueryParam("UserData")
        public CreateInstanceBuilder userData(final String userData);

        @QueryParam("SpotStrategy")
        public CreateInstanceBuilder spotStrategy(final String spotStrategy);

        @QueryParam("SpotPriceLimit")
        public CreateInstanceBuilder spotPriceLimit(final float spotPriceLimit);

        @QueryParam("KeyPairName")
        public CreateInstanceBuilder keyPairName(final String keyPairName);

        @QueryParam("SpotInterruptionBehavior")
        public CreateInstanceBuilder spotInterruptionBehavior(final String spotInterruptionBehavior);

        @QueryParam("RamRoleName")
        public CreateInstanceBuilder ramRoleName(final String ramRoleName);

        @QueryParam("SecurityEnhancementStrategy")
        public CreateInstanceBuilder securityEnhancementStrategy(final String securityEnhancementStrategy);

        @QueryParam("ResourceGroupId")
        public CreateInstanceBuilder resourceGroupId(final String resourceGroupId);

        @QueryParam("HpcClusterId")
        public CreateInstanceBuilder hpcClusterId(final String hpcClusterId);

        @QueryParam("DedicatedHostId")
        public CreateInstanceBuilder dedicatedHostId(final String dedicatedHostId);

        @QueryParam("CreditSpecification")
        public CreateInstanceBuilder creditSpecification(final String creditSpecification);

        @QueryParam("DeletionProtection")
        public CreateInstanceBuilder deletionProtection(final boolean deletionProtection);

        @QueryParam("SystemDisk.PerformanceLevel")
        public CreateInstanceBuilder systemDiskPerformanceLevel(final String systemDiskPerformanceLevel);

        @QueryParam("Affinity")
        public CreateInstanceBuilder affinity(final String affinity);

        @QueryParam("Tenancy")
        public CreateInstanceBuilder tenancy(final String tenancy);

        public Transformer<RpcRunner, CreateInstanceResponse> call();
    }

    public CreateInstanceBuilder createInstance();
}
