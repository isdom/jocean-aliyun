package org.jocean.aliyun.ecs;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.QueryParam;

import org.jocean.aliyun.sign.AliyunSignable;
import org.jocean.rpc.annotation.ConstParams;
import org.jocean.rpc.annotation.OnInteract;
import org.jocean.rpc.annotation.RpcBuilder;

import com.alibaba.fastjson.annotation.JSONField;

import rx.Observable;

// https://help.aliyun.com/document_detail/102988.html?spm=a2c4g.11186623.6.1069.118a79e0WI5Er2

@Path("https://ecs.aliyuncs.com/")
@ConstParams({"Version", "2014-05-26"})
public interface EcsAPI {

    interface EcsBuilder<BUILDER> extends AliyunSignable<BUILDER> {
    }

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
    interface DescribeInstancesBuilder extends EcsBuilder<DescribeInstancesBuilder> {
        //  String   是   cn-hangzhou
        //  实例所属的地域ID。您可以调用DescribeRegions查看最新的阿里云地域列表。
        @QueryParam("RegionId")
        DescribeInstancesBuilder regionId(final String regionId);

        @QueryParam("VpcId")
        DescribeInstancesBuilder vpcId(final String vpcId);

        @QueryParam("VSwitchId")
        DescribeInstancesBuilder vSwitchId(final String vSwitchId);

        @QueryParam("ZoneId")
        DescribeInstancesBuilder zoneId(final String zoneId);

        @QueryParam("InstanceNetworkType")
        DescribeInstancesBuilder instanceNetworkType(final String instanceNetworkType);

        /**
         *
        是否必选：否
        示例值：["i-bp67acfmxazb4p****", "i-bp67acfmxazb4p****", … "i-bp67acfmxazb4p****"]
        实例ID。取值可以由多个实例ID组成一个JSON数组，最多支持100个ID，ID之间用半角逗号（,）隔开。
         */
        @QueryParam("InstanceIds")
        DescribeInstancesBuilder instanceIds(final String instanceIds);

        /**
        是否必选：否
        示例值：Test
        实例名称，支持使用通配符*进行模糊搜索。
         */
        @QueryParam("InstanceName")
        DescribeInstancesBuilder instanceName(final String instanceName);

        @GET
        @ConstParams({"Action", "DescribeInstances"})
        @OnInteract("signer")
        Observable<DescribeInstancesResponse> call();
    }

    // https://help.aliyun.com/document_detail/25506.html?spm=a2c4g.11186623.6.1310.4ab27d848m8E7i
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
    interface DescribeSpotPriceHistoryBuilder extends EcsBuilder<DescribeSpotPriceHistoryBuilder> {
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
        @OnInteract("signer")
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
    interface CreateInstanceBuilder  extends EcsBuilder<CreateInstanceBuilder> {
        /**
        是否必选：否
        示例值：123e4567-e89b-12d3-a456-426655440000
        保证请求幂等性。从您的客户端生成一个参数值，确保不同请求间该参数值唯一。ClientToken只支持ASCII字符，且不能超过64个字符。更多详情，请参见如何保证幂等性。
         */
        @QueryParam("ClientToken")
        CreateInstanceBuilder clientToken(final String clientToken);

        /**
        是否必选：否
        示例值：false
        是否只预检此次请求。取值范围：
        true：发送检查请求，不会创建实例。检查项包括是否填写了必需参数、请求格式、业务限制和ECS库存。如果检查不通过，则返回对应错误。如果检查通过，则返回错误码DryRunOperation。
        false（默认）：发送正常请求，通过检查后直接创建实例。
        */
        @QueryParam("DryRun")
        CreateInstanceBuilder dryRun(final boolean dryRun);

        /**
        是否必选：否
        示例值：ubuntu_18_04_64_20G_alibase_20190624.vhd
        镜像文件ID，启动实例时选择的镜像资源。如需使用云市场镜像，您可以在云市场镜像商详情页查看ImageId。当您不通过指定ImageFamily选用镜像族系最新可用自定义镜像时，此参数必选。
        */
        @QueryParam("ImageId")
        CreateInstanceBuilder imageId(final String imageId);

        /**
        镜像族系名称，通过设置该参数来获取当前镜像族系内最新可用自定义镜像来创建实例。
        设置了ImageId，则不能设置此参数。
        未设置ImageId，则可以设置该参数。
        eg: hangzhou-daily-update
        */
        @QueryParam("ImageFamily")
        CreateInstanceBuilder imageFamily(final String imageFamily);

        @QueryParam("InstanceType")
        CreateInstanceBuilder instanceType(final String instanceType);

        @QueryParam("RegionId")
        CreateInstanceBuilder regionId(final String regionId);

        @QueryParam("SecurityGroupId")
        CreateInstanceBuilder securityGroupId(final String securityGroupId);

        /**
        实例的名称。长度为2~128个英文或中文字符。必须以大小字母或中文开头，不能以http://和https://开头。
        可以包含数字、半角冒号（:）、下划线（_）、英文句号（.）或者连字符（-）。如果没有指定该参数，默认值为实例的InstanceId。
        */
        @QueryParam("InstanceName")
        CreateInstanceBuilder instanceName(final String instanceName);

        /**
         *
        是否要自动续费。当参数InstanceChargeType取值PrePaid时才生效。取值范围：
        true：自动续费。
        false（默认）：不自动续费。
         */
        @QueryParam("AutoRenew")
        CreateInstanceBuilder autoRenew(final boolean autoRenew);

        /**
         *
        每次自动续费的时长，当参数AutoRenew取值True时为必填。
        PeriodUnit为Week时，AutoRenewPeriod取值{"1", "2", "3"}。
        PeriodUnit为Month时，AutoRenewPeriod取值{"1", "2", "3", "6", "12"}。
         */
        @QueryParam("AutoRenewPeriod")
        CreateInstanceBuilder autoRenewPeriod(final int autoRenewPeriod);

        /**
         *
        网络计费类型。取值范围：
        PayByBandwidth：按固定带宽计费。
        PayByTraffic（默认）：按使用流量计费。
        说明 按使用流量计费模式下的出入带宽峰值都是带宽上限，不作为业务承诺指标。当出现资源争抢时，带宽峰值可能会受到限制。如果您的业务需要有带宽的保障，请使用按固定带宽计费模式。
         */
        @QueryParam("InternetChargeType")
        CreateInstanceBuilder internetChargeType(final String internetChargeType);

        /**
         *
        公网入带宽最大值，单位为Mbit/s。取值范围：
        当所购出网带宽小于等于10 Mbit/s时：1~10。默认值：10
        当所购出网带宽大于10 Mbit/s时：1~InternetMaxBandwidthOut的取值，默认为InternetMaxBandwidthOut的取值。
         */
        @QueryParam("InternetMaxBandwidthIn")
        CreateInstanceBuilder internetMaxBandwidthIn(final int internetMaxBandwidthIn);

        /**
         *
        公网出带宽最大值，单位为Mbit/s。取值范围：0~100
        默认值：0
         */
        @QueryParam("InternetMaxBandwidthOut")
        CreateInstanceBuilder internetMaxBandwidthOut(final int internetMaxBandwidthOut);

        /**
         *
        是否必选：否
        云服务器的主机名。
        英文句号（.）和短横线（-）不能作为首尾字符，更不能连续使用。
        Windows实例：字符长度为2~15，不支持英文句号（.），不能全是数字。允许大小写英文字母、数字和短横线（-）。
        其他类型实例（Linux等）：字符长度为2~64，支持多个英文句号（.），英文句号之间为一段，每段允许大小写英文字母、数字和短横线（-）。
         */
        @QueryParam("HostName")
        CreateInstanceBuilder hostName(final String hostName);

        /**
         *
        是否必选：否
        实例的密码。长度为8至30个字符，必须同时包含大小写英文字母、数字和特殊符号中的三类字符。特殊符号可以是：

        ()`~!@#$%^&*-_+=|{}[]:;'<>,.?/
        其中，Windows实例不能以斜线号（/）为密码首字符。
        说明 如果传入Password参数，建议您使用HTTPS协议发送请求，避免密码泄露。
         */
        @QueryParam("Password")
        CreateInstanceBuilder password(final String password);

        /**
         *
        是否必选：否
        是否使用镜像预设的密码。使用该参数时，Password参数必须为空，同时您需要确保使用的镜像已经设置了密码。
         */
        @QueryParam("PasswordInherit")
        CreateInstanceBuilder passwordInherit(final boolean passwordInherit);

        /**
         *
        是否必选：否
        部署集ID。
         */
        @QueryParam("DeploymentSetId")
        CreateInstanceBuilder deploymentSetId(final String deploymentSetId);

        /**
         *
        是否必选：否
        示例值：cn-hangzhou-g
        实例所属的可用区ID。更多详情，请参见DescribeZones获取可用区列表。
        默认值：空，表示随机选择。
         */
        @QueryParam("ZoneId")
        CreateInstanceBuilder zoneId(final String zoneId);

        /**
         *
        是否必选：否
        实例所在的集群ID。
        说明 该参数即将被弃用，为提高兼容性，请尽量使用其他参数。
         */
        @QueryParam("ClusterId")
        CreateInstanceBuilder clusterId(final String clusterId);

        /**
         *
        是否必选：否
        示例值：10
        虚拟局域网ID。
         */
        @QueryParam("VlanId")
        CreateInstanceBuilder vlanId(final String vlanId);

        /**
         *
        是否必选：否
        示例值：192.168.**.**
        实例的内网IP。
         */
        @QueryParam("InnerIpAddress")
        CreateInstanceBuilder innerIpAddress(final String innerIpAddress);

        /**
         *
        是否必选：否
        示例值：40
        系统盘大小，单位为GiB。取值范围：20~500
        该参数的取值必须大于或者等于max{20, ImageSize}。
        默认值：max{40, ImageSize}
         */
        @QueryParam("SystemDisk.Size")
        CreateInstanceBuilder systemDiskSize(final int systemDiskSize);

        /**
         *
        是否必选：否
        示例值：cloud_ssd
        系统盘的云盘种类。取值范围：

        cloud_essd：ESSD云盘，您可以通过参数SystemDisk.PerformanceLevel设置云盘的性能等级。
        cloud_efficiency：高效云盘。
        cloud_ssd：SSD云盘。
        cloud：普通云盘。
        已停售的实例规格且非I/O优化实例默认值为cloud，否则默认值为cloud_efficiency。
         */
        @QueryParam("SystemDisk.Category")
        CreateInstanceBuilder systemDiskCategory(final String systemDiskCategory);

        /**
         *
        是否必选：否
        示例值：SystemDiskName
        系统盘名称。长度为2~128个英文或中文字符。必须以大小字母或中文开头，不能以http://和https://开头。可以包含数字、半角冒号（:）、下划线（_）或者连字符（-）。
        默认值：空
         */
        @QueryParam("SystemDisk.DiskName")
        CreateInstanceBuilder systemDiskDiskName(final String systemDiskDiskName);

        /**
         *
        是否必选：否
        示例值：TestDescription
        系统盘描述。长度为2~256个英文或中文字符，不能以http://和https://开头。
        默认值：空
         */
        @QueryParam("SystemDisk.Description")
        CreateInstanceBuilder systemDiskDescription(final String systemDiskDescription);

        /**
         *
        是否必选：否
        示例值：PL1
        创建ESSD云盘作为系统盘使用时，设置云盘的性能等级。取值范围：
        PL0（默认）：单盘最高随机读写IOPS 1万。
        PL1：单盘最高随机读写IOPS 5万。
        PL2：单盘最高随机读写IOPS 10万。
        PL3：单盘最高随机读写IOPS 100万。
        有关如何选择ESSD性能等级，请参见ESSD云盘。
         */
        @QueryParam("SystemDisk.PerformanceLevel")
        CreateInstanceBuilder systemDiskPerformanceLevel(final String systemDiskPerformanceLevel);

        /**
         *
        是否必选：否
        示例值：InstanceTest
        实例的描述。长度为2~256个英文或中文字符，不能以http://和https://开头。
        默认值：空
        */
        @QueryParam("Description")
        CreateInstanceBuilder description(final String description);

        /**
         *
        是否必选：否
        示例值：vsw-bp1s5fnvk4gn2tws0****
        如果是创建VPC类型的实例，需要指定交换机ID。
        */
        @QueryParam("VSwitchId")
        CreateInstanceBuilder vSwitchId(final String vSwitchId);

        /**
        是否必选：否
        示例值：172.16.236.*
        实例私网IP地址。该IP地址必须为交换机（VSwitchId）网段的空闲地址。
        */
        @QueryParam("PrivateIpAddress")
        CreateInstanceBuilder privateIpAddress(final String privateIpAddress);

        /**
        是否必选：否
        示例值：optimized
        是否为I/O优化实例。取值范围：
        none：非I/O优化
        optimized：I/O优化
        已停售的实例规格实例默认值是none。
        其他实例规格默认值是optimized。
        */
        @QueryParam("IoOptimized")
        CreateInstanceBuilder ioOptimized(final String ioOptimized);

        /**
        是否必选：否
        示例值：true
        是否使用阿里云提供的虚拟机系统配置（Windows：NTP、KMS；Linux：NTP、YUM）。
        */
        @QueryParam("UseAdditionalService")
        CreateInstanceBuilder useAdditionalService(final boolean useAdditionalService);

        /**
        是否必选：否
        示例值：PrePaid
        实例的付费方式。取值范围：
        PrePaid：包年包月。选择该类付费方式时，您必须确认自己的账号支持余额支付/信用支付，否则将返回 InvalidPayMethod的错误提示。
        PostPaid（默认）：按量付费。
        */
        @QueryParam("InstanceChargeType")
        CreateInstanceBuilder instanceChargeType(final String instanceChargeType);

        /**
        是否必选：否
        示例值：1
        购买资源的时长，单位由PeriodUnit指定。当参数InstanceChargeType取值为PrePaid时才生效且为必选值。一旦指定了DedicatedHostId，则取值范围不能超过专有宿主机的订阅时长。取值范围：

        PeriodUnit=Week时，Period取值：{“1”, “2”, “3”, “4”}。
        PeriodUnit=Month时，Period取值：{“1”, “2”, “3”, “4”, “5”, “6”, “7”, “8”, “9”, “12”, “24”, “36”, ”48”, ”60”}。
        */
        @QueryParam("Period")
        CreateInstanceBuilder period(final int period);

        /**
        是否必选：否
        示例值：Month
        购买资源的时长。
        取值范围：Week和Month（默认）
        */
        @QueryParam("PeriodUnit")
        CreateInstanceBuilder periodUnit(final String periodUnit);

        /**
        是否必选：否
        示例值：ZWNobyBoZWxsbyBlY3Mh
        实例自定义数据，需要以Base64方式编码，原始数据最多为16KB。
        */
        @QueryParam("UserData")
        CreateInstanceBuilder userData(final String userData);

        /**
        是否必选：否
        示例值：NoSpot
        实例的抢占策略。当参数InstanceChargeType取值为PostPaid时生效。取值范围：
        NoSpot（默认）：正常按量付费实例。
        SpotWithPriceLimit：设置上限价格的抢占式实例。
        SpotAsPriceGo：系统自动出价，跟随当前市场实际价格。
        */
        @QueryParam("SpotStrategy")
        CreateInstanceBuilder spotStrategy(final String spotStrategy);

        /**
        是否必选：否
        示例值：0.98
        设置实例的每小时最高价格。支持最大3位小数，参数SpotStrategy取值为SpotWithPriceLimit时生效。
        */
        @QueryParam("SpotPriceLimit")
        CreateInstanceBuilder spotPriceLimit(final float spotPriceLimit);

        /**
        是否必选：否
        示例值：1
        抢占式实例的保留时长，单位为小时。取值范围：0~6
        保留时长2~6正在邀测中，如需开通请提交工单。
        取值为0，则为无保护期模式。
        默认值：1
        */
        @QueryParam("SpotDuration")
        CreateInstanceBuilder spotDuration(final int spotDuration);

        /**
        是否必选：否
        示例值：Terminate
        抢占实例中断模式。目前仅支持Terminate（默认）直接释放实例。
        */
        @QueryParam("SpotInterruptionBehavior")
        CreateInstanceBuilder spotInterruptionBehavior(final String spotInterruptionBehavior);

        /**
        是否必选：否
        示例值：KeyPairTestName
        密钥对名称。
        Windows实例，忽略该参数。默认为空。即使填写了该参数，仍旧只执行Password的内容。
        Linux实例的密码登录方式会被初始化成禁止。为提高实例安全性，强烈建议您使用密钥对的连接方式。
        */
        @QueryParam("KeyPairName")
        CreateInstanceBuilder keyPairName(final String keyPairName);

        /**
        是否必选：否
        示例值：RAMTestName
        实例RAM角色名称。您可以使用RAM API ListRoles查询您已创建的实例RAM角色。
        */
        @QueryParam("RamRoleName")
        CreateInstanceBuilder ramRoleName(final String ramRoleName);

        /**
        是否必选：否
        示例值：Active
        是否开启安全加固。取值范围：
        Active：启用安全加固，只对系统镜像生效。
        Deactive：不启用安全加固，对所有镜像类型生效。
        */
        @QueryParam("SecurityEnhancementStrategy")
        CreateInstanceBuilder securityEnhancementStrategy(final String securityEnhancementStrategy);

        /**
        是否必选：否
        示例值：rg-bp67acfmxazb4p****
        实例所在的企业资源组ID。
        */
        @QueryParam("ResourceGroupId")
        CreateInstanceBuilder resourceGroupId(final String resourceGroupId);

        /**
        是否必选：否
        示例值：hpc-bp67acfmxazb4p****
        实例所属的HPC集群ID。
        */
        @QueryParam("HpcClusterId")
        CreateInstanceBuilder hpcClusterId(final String hpcClusterId);

        /**
        是否必选：否
        示例值：dh-bp67acfmxazb4p****
        是否在专有宿主机上创建ECS实例。
        您可以通过DescribeDedicatedHosts查询专有宿主机ID列表。
        由于专有宿主机不支持创建抢占式实例，指定DedicatedHostId参数后，会自动忽略请求中的SpotStrategy和SpotPriceLimit设置。
        */
        @QueryParam("DedicatedHostId")
        CreateInstanceBuilder dedicatedHostId(final String dedicatedHostId);

        /**
        是否必选：否
        示例值：Standard
        修改突发性能实例的运行模式。取值范围：
        Standard：标准模式，实例性能请参见什么是突发性能实例下的性能约束模式章节。
        Unlimited：无性能约束模式，实例性能请参见什么是突发性能实例下的无性能约束模式章节。
        默认值：无
        */
        @QueryParam("CreditSpecification")
        CreateInstanceBuilder creditSpecification(final String creditSpecification);

        /**
        是否必选：否
        示例值：false
        实例释放保护属性，指定是否支持通过控制台或API（DeleteInstance）释放实例。
        true：开启实例释放保护。
        false（默认）：关闭实例释放保护。
        说明 该属性仅适用于按量付费实例，且只能限制手动释放操作，对系统释放操作不生效。
        */
        @QueryParam("DeletionProtection")
        CreateInstanceBuilder deletionProtection(final boolean deletionProtection);

        /**
        是否必选：否
        示例值：default
        专有宿主机实例是否与专有宿主机关联。取值范围：
        default：实例不与专有宿主机关联。已开启停机不收费功能的实例，停机后再次启动时，若原专有宿主机可用资源不足，则实例被放置在自动部署资源池的其它专有宿主机上。
        host：实例与专有宿主机关联。已开启停机不收费功能的实例，停机后再次启动时，仍放置在原专有宿主机上。若原专有宿主机可用资源不足，则实例重启失败。
        默认值：default
        */
        @QueryParam("Affinity")
        CreateInstanceBuilder affinity(final String affinity);

        /**
        是否必选：否
        示例值：default
        是否在专有宿主机上创建实例。取值范围：
        default：在非专有宿主机上创建实例。
        host：在专有宿主机上创建实例。若您不指定DedicatedHostId，则由阿里云自动选择专有宿主机部署实例。
        默认值：default
        */
        @QueryParam("Tenancy")
        CreateInstanceBuilder tenancy(final String tenancy);

        /**
        是否必选：否
        示例值：ss-bp1j4i2jdf3owlhe****
        存储集ID。
        */
        @QueryParam("StorageSetId")
        CreateInstanceBuilder storageSetId(final String storageSetId);

        /**
        是否必选：否
        示例值：2
        存储集中的最大分区数量。取值范围：大于等于2
        */
        @QueryParam("StorageSetPartitionNumber")
        CreateInstanceBuilder storageSetPartitionNumber(final int storageSetPartitionNumber);

        /**
        是否必选：否
        示例值：enabled
        是否启用实例元数据的访问通道。取值范围：
        enabled：启用
        disabled：禁用
        默认值：enabled
        说明 有关实例元数据的信息，请参见实例元数据概述。
        */
        @QueryParam("HttpEndpoint")
        CreateInstanceBuilder httpEndpoint(final String httpEndpoint);

        /**
        是否必选：否
        示例值：optional
        访问实例元数据时是否强制使用加固模式（IMDSv2）。取值范围：
        optional：不强制使用。
        required：强制使用。设置该取值后，普通模式无法访问实例元数据。
        默认值：optional

        说明 有关访问实例元数据的模式，请参见实例元数据访问模式。
        */
        @QueryParam("HttpTokens")
        CreateInstanceBuilder httpTokens(final String httpTokens);

        /**
        是否必选：否
        示例值：Open
        实例启动的私有池容量选项。弹性保障服务或容量预定服务在生效后会生成私有池容量，供实例启动时选择。取值范围：

        Open：开放模式。将自动匹配开放类型的私有池容量。如果没有符合条件的私有池容量，则使用公共池资源启动。该模式下无需设置PrivatePoolOptions.Id参数。
        Target：指定模式。使用指定的私有池容量启动实例，如果该私有池容量不可用，则实例会启动失败。该模式下必须指定私有池ID，即PrivatePoolOptions.Id参数为必填项。
        None：不使用模式。实例启动将不使用私有池容量。
        默认值：空
        以下任一场景，实例启动的私有池容量选项只能取值None或不传值。
        创建抢占式实例。
        创建经典网络类型的ECS实例。
        在专有宿主机DDH上创建ECS实例。
        说明 该参数邀测中，详情请提交工单咨询。
        */
        @QueryParam("PrivatePoolOptions.MatchCriteria")
        CreateInstanceBuilder privatePoolOptionsMatchCriteria(final String privatePoolOptionsMatchCriteria);

        /**
        是否必选：否
        示例值：eap-bp67acfmxazb4****
        私有池ID。即弹性保障服务ID或容量预定服务ID。
        说明 该参数邀测中，详情请提交工单咨询。
        */
        @QueryParam("PrivatePoolOptions.Id")
        CreateInstanceBuilder privatePoolOptionsId(final String privatePoolOptionsId);

        @GET
        @ConstParams({"Action", "CreateInstance"})
        @OnInteract("signer")
        Observable<CreateInstanceResponse> call();
    }

    CreateInstanceBuilder createInstance();

    interface StartInstanceResponse extends ECSAPIResponse {
    }

    @RpcBuilder
    interface StartInstanceBuilder extends EcsBuilder<StartInstanceBuilder> {
        @QueryParam("InstanceId")
        StartInstanceBuilder instanceId(final String instanceId);

        @QueryParam("InitLocalDisk")
        StartInstanceBuilder initLocalDisk(final boolean init);

        @QueryParam("OwnerAccount")
        StartInstanceBuilder ownerAccount(final String ownerAccount);

        @GET
        @ConstParams({"Action", "StartInstance"})
        @OnInteract("signer")
        Observable<StartInstanceResponse> call();
    }

    StartInstanceBuilder startInstance();

    interface RebootInstanceResponse extends ECSAPIResponse {
    }

    @RpcBuilder
    interface RebootInstanceBuilder extends EcsBuilder<RebootInstanceBuilder> {
        @QueryParam("InstanceId")
        RebootInstanceBuilder instanceId(final String instanceId);

        @QueryParam("DryRun")
        RebootInstanceBuilder dryRun(final boolean dryRun);

        @QueryParam("ForceStop")
        RebootInstanceBuilder forceStop(final boolean forceStop);

        @GET
        @ConstParams({"Action", "RebootInstance"})
        @OnInteract("signer")
        Observable<RebootInstanceResponse> call();
    }

    RebootInstanceBuilder rebootInstance();

    interface StopInstanceResponse extends ECSAPIResponse {
    }

    @RpcBuilder
    interface StopInstanceBuilder extends EcsBuilder<StopInstanceBuilder> {
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
        @OnInteract("signer")
        Observable<StopInstanceResponse> call();
    }

    StopInstanceBuilder stopInstance();

    interface DeleteInstanceResponse extends ECSAPIResponse {
    }

    @RpcBuilder
    interface DeleteInstanceBuilder extends EcsBuilder<DeleteInstanceBuilder> {
        @QueryParam("InstanceId")
        DeleteInstanceBuilder instanceId(final String instanceId);

        @QueryParam("Force")
        DeleteInstanceBuilder force(final boolean force);

        @QueryParam("TerminateSubscription")
        DeleteInstanceBuilder terminateSubscription(final boolean terminateSubscription);

        @GET
        @ConstParams({"Action", "DeleteInstance"})
        @OnInteract("signer")
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
    interface AttachInstanceRamRoleBuilder extends EcsBuilder<AttachInstanceRamRoleBuilder> {
        /**
        是否必选：是
        示例值：[“i-bp14ss25xca5ex1u****”, “i-bp154z5o1qjalfse****”, “i-bp10ws62o04ubhvi****”…]
        实例ID。取值可以由多个实例ID组成一个JSON数组，最多支持100个ID，ID之间用半角逗号（,）隔开。
         */
        @QueryParam("InstanceIds")
        AttachInstanceRamRoleBuilder instanceIds(final String[] instanceIds);

        //  必选   RamRoleTest    实例RAM角色名称。您可以使用 RAM API ListRoles查询您已创建的实例RAM角色。
        @QueryParam("RamRoleName")
        AttachInstanceRamRoleBuilder ramRoleName(final String ramRoleName);

        //  必选   cn-hangzhou    地域ID。
        @QueryParam("RegionId")
        AttachInstanceRamRoleBuilder regionId(final String regionId);

        /**
        是否必选：否
        示例值：{"Statement": [{"Action": ["*"],"Effect": "Allow","Resource": ["*"]}],"Version":"1"}
        权限策略。长度为1~1024个字符。为一台或多台ECS实例授予实例RAM角色时，可以指定一个额外的权限策略，以进一步限制RAM角色的权限。
        更多信息，请参见权限策略概览。
         */
        @QueryParam("Policy")
        AttachInstanceRamRoleBuilder policy(final String policy);

        @GET
        @ConstParams({"Action", "AttachInstanceRamRole"})
        @OnInteract("signer")
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
    interface DetachInstanceRamRoleBuilder extends EcsBuilder<DetachInstanceRamRoleBuilder> {
        @QueryParam("InstanceIds")
        DetachInstanceRamRoleBuilder instanceIds(final String[] instanceIds);

        @QueryParam("RamRoleName")
        DetachInstanceRamRoleBuilder ramRoleName(final String ramRoleName);

        @QueryParam("RegionId")
        DetachInstanceRamRoleBuilder regionId(final String regionId);

        @GET
        @ConstParams({"Action", "DetachInstanceRamRole"})
        @OnInteract("signer")
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
    interface DescribeInstanceStatusBuilder extends PageableBuilder<DescribeInstanceStatusBuilder>, EcsBuilder<DescribeInstanceStatusBuilder>  {

        @QueryParam("RegionId")
        DescribeInstanceStatusBuilder regionId(final String regionId);

        @QueryParam("ClusterId")
        DescribeInstanceStatusBuilder clusterId(final String clusterId);

        @QueryParam("ZoneId")
        DescribeInstanceStatusBuilder zoneId(final String zoneId);

        @GET
        @ConstParams({"Action", "DescribeInstanceStatus"})
        @OnInteract("signer")
        Observable<DescribeInstanceStatusResponse> call();
    }

    DescribeInstanceStatusBuilder describeInstanceStatus();

    interface RenewInstanceResponse extends ECSAPIResponse {

    }

    @RpcBuilder
    interface RenewInstanceBuilder extends EcsBuilder<RenewInstanceBuilder> {
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
        @OnInteract("signer")
        Observable<RenewInstanceResponse> call();
    }

    RenewInstanceBuilder renewInstance();

    interface ReactivateInstancesResponse extends ECSAPIResponse {

    }

    @RpcBuilder
    interface ReactivateInstancesBuilder extends EcsBuilder<ReactivateInstancesBuilder> {
        //  必选   需要重开机的实例 ID。
        @QueryParam("InstanceId")
        ReactivateInstancesBuilder instanceId(final String instanceId);

        @GET
        @ConstParams({"Action", "ReactivateInstances"})
        @OnInteract("signer")
        Observable<ReactivateInstancesResponse> call();
    }

    ReactivateInstancesBuilder reactivateInstances();

    interface RedeployInstanceResponse extends ECSAPIResponse {

    }

    @RpcBuilder
    interface RedeployInstanceBuilder extends EcsBuilder<RedeployInstanceBuilder> {
        @QueryParam("InstanceIds")
        RedeployInstanceBuilder instanceIds(final String[] instanceIds);

        @QueryParam("RamRoleName")
        RedeployInstanceBuilder ramRoleName(final String ramRoleName);

        @QueryParam("RegionId")
        RedeployInstanceBuilder regionId(final String regionId);

        @GET
        @ConstParams({"Action", "RedeployInstance"})
        @OnInteract("signer")
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
    interface DescribeInstanceVncUrlBuilder extends EcsBuilder<DescribeInstanceVncUrlBuilder> {
        @QueryParam("InstanceId")
        DescribeInstanceVncUrlBuilder instanceId(final String instanceId);

        @QueryParam("RegionId")
        DescribeInstanceVncUrlBuilder regionId(final String regionId);

        @GET
        @ConstParams({"Action", "DescribeInstanceVncUrl"})
        @OnInteract("signer")
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
    interface DescribeUserDataBuilder extends EcsBuilder<DescribeUserDataBuilder> {
        //  必选   i-instanceid1           要查询的实例 ID。
        @QueryParam("InstanceId")
        DescribeUserDataBuilder instanceId(final String instanceId);

        //  必选   cn-hangzhou         实例所属的地域ID。
        @QueryParam("RegionId")
        DescribeUserDataBuilder regionId(final String regionId);

        @GET
        @ConstParams({"Action", "DescribeUserData"})
        @OnInteract("signer")
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
    interface DescribeInstanceAutoRenewAttributeBuilder extends PageableBuilder<DescribeInstanceAutoRenewAttributeBuilder>,
        EcsBuilder<DescribeInstanceAutoRenewAttributeBuilder> {
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
        @OnInteract("signer")
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
    interface DescribeInstanceRamRoleBuilder extends PageableBuilder<DescribeInstanceRamRoleBuilder>,
        EcsBuilder<DescribeInstanceRamRoleBuilder> {
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
        @OnInteract("signer")
        Observable<DescribeInstanceRamRoleResponse> call();
    }

    DescribeInstanceRamRoleBuilder describeInstanceRamRole();

    interface DescribeInstanceTypeFamiliesResponse extends ECSAPIResponse {
    }

    @RpcBuilder
    interface DescribeInstanceTypeFamiliesBuilder extends EcsBuilder<DescribeInstanceTypeFamiliesBuilder> {
        @QueryParam("InstanceIds")
        DescribeInstanceTypeFamiliesBuilder instanceIds(final String[] instanceIds);

        @QueryParam("RamRoleName")
        DescribeInstanceTypeFamiliesBuilder ramRoleName(final String ramRoleName);

        @QueryParam("RegionId")
        DescribeInstanceTypeFamiliesBuilder regionId(final String regionId);

        @GET
        @ConstParams({"Action", "DescribeInstanceTypeFamilies"})
        @OnInteract("signer")
        Observable<DescribeInstanceTypeFamiliesResponse> call();
    }

    DescribeInstanceTypeFamiliesBuilder describeInstanceTypeFamilies();

    interface DescribeInstanceTypesResponse extends ECSAPIResponse {
    }

    @RpcBuilder
    interface DescribeInstanceTypesBuilder extends EcsBuilder<DescribeInstanceTypesBuilder> {
        @QueryParam("InstanceIds")
        DescribeInstanceTypesBuilder instanceIds(final String[] instanceIds);

        @QueryParam("RamRoleName")
        DescribeInstanceTypesBuilder ramRoleName(final String ramRoleName);

        @QueryParam("RegionId")
        DescribeInstanceTypesBuilder regionId(final String regionId);

        @GET
        @ConstParams({"Action", "DescribeInstanceTypes"})
        @OnInteract("signer")
        Observable<DescribeInstanceTypesResponse> call();
    }

    DescribeInstanceTypesBuilder describeInstanceTypes();

    interface ModifyInstanceVpcAttributeResponse extends ECSAPIResponse {
    }

    @RpcBuilder
    interface ModifyInstanceVpcAttributeBuilder extends EcsBuilder<ModifyInstanceVpcAttributeBuilder> {
        @QueryParam("InstanceIds")
        ModifyInstanceVpcAttributeBuilder instanceIds(final String[] instanceIds);

        @QueryParam("RamRoleName")
        ModifyInstanceVpcAttributeBuilder ramRoleName(final String ramRoleName);

        @QueryParam("RegionId")
        ModifyInstanceVpcAttributeBuilder regionId(final String regionId);

        @GET
        @ConstParams({"Action", "ModifyInstanceVpcAttribute"})
        @OnInteract("signer")
        Observable<ModifyInstanceVpcAttributeResponse> call();
    }

    ModifyInstanceVpcAttributeBuilder modifyInstanceVpcAttribute();

    interface ModifyInstanceAttributeResponse extends ECSAPIResponse {
    }

    @RpcBuilder
    interface ModifyInstanceAttributeBuilder extends EcsBuilder<ModifyInstanceAttributeBuilder> {
        @QueryParam("InstanceIds")
        ModifyInstanceAttributeBuilder instanceIds(final String[] instanceIds);

        @QueryParam("RamRoleName")
        ModifyInstanceAttributeBuilder ramRoleName(final String ramRoleName);

        @QueryParam("RegionId")
        ModifyInstanceAttributeBuilder regionId(final String regionId);

        @GET
        @ConstParams({"Action", "ModifyInstanceAttribute"})
        @OnInteract("signer")
        Observable<ModifyInstanceAttributeResponse> call();
    }

    ModifyInstanceAttributeBuilder modifyInstanceAttribute();

    interface ModifyInstanceVncPasswdResponse extends ECSAPIResponse {
    }

    @RpcBuilder
    interface ModifyInstanceVncPasswdBuilder extends EcsBuilder<ModifyInstanceVncPasswdBuilder> {
        @QueryParam("InstanceIds")
        ModifyInstanceVncPasswdBuilder instanceIds(final String[] instanceIds);

        @QueryParam("RamRoleName")
        ModifyInstanceVncPasswdBuilder ramRoleName(final String ramRoleName);

        @QueryParam("RegionId")
        ModifyInstanceVncPasswdBuilder regionId(final String regionId);

        @GET
        @ConstParams({"Action", "ModifyInstanceVncPasswd"})
        @OnInteract("signer")
        Observable<ModifyInstanceVncPasswdResponse> call();
    }

    ModifyInstanceVncPasswdBuilder modifyInstanceVncPasswd();

    interface ModifyInstanceAutoReleaseTimeResponse extends ECSAPIResponse {
    }

    @RpcBuilder
    interface ModifyInstanceAutoReleaseTimeBuilder extends EcsBuilder<ModifyInstanceAutoReleaseTimeBuilder> {
        @QueryParam("InstanceIds")
        ModifyInstanceAutoReleaseTimeBuilder instanceIds(final String[] instanceIds);

        @QueryParam("RamRoleName")
        ModifyInstanceAutoReleaseTimeBuilder ramRoleName(final String ramRoleName);

        @QueryParam("RegionId")
        ModifyInstanceAutoReleaseTimeBuilder regionId(final String regionId);

        @GET
        @ConstParams({"Action", "ModifyInstanceAutoReleaseTime"})
        @OnInteract("signer")
        Observable<ModifyInstanceAutoReleaseTimeResponse> call();
    }

    ModifyInstanceAutoReleaseTimeBuilder modifyInstanceAutoReleaseTime();

    interface ModifyInstanceAutoRenewAttributeResponse extends ECSAPIResponse {
    }

    @RpcBuilder
    interface ModifyInstanceAutoRenewAttributeBuilder extends EcsBuilder<ModifyInstanceAutoRenewAttributeBuilder> {
        @QueryParam("InstanceIds")
        ModifyInstanceAutoRenewAttributeBuilder instanceIds(final String[] instanceIds);

        @QueryParam("RamRoleName")
        ModifyInstanceAutoRenewAttributeBuilder ramRoleName(final String ramRoleName);

        @QueryParam("RegionId")
        ModifyInstanceAutoRenewAttributeBuilder regionId(final String regionId);

        @GET
        @ConstParams({"Action", "ModifyInstanceAutoRenewAttribute"})
        @OnInteract("signer")
        Observable<ModifyInstanceAutoRenewAttributeResponse> call();
    }

    ModifyInstanceAutoRenewAttributeBuilder modifyInstanceAutoRenewAttribute();

    interface ModifyInstanceChargeTypeResponse extends ECSAPIResponse {
    }

    @RpcBuilder
    interface ModifyInstanceChargeTypeBuilder extends EcsBuilder<ModifyInstanceChargeTypeBuilder> {
        @QueryParam("InstanceIds")
        ModifyInstanceChargeTypeBuilder instanceIds(final String[] instanceIds);

        @QueryParam("RamRoleName")
        ModifyInstanceChargeTypeBuilder ramRoleName(final String ramRoleName);

        @QueryParam("RegionId")
        ModifyInstanceChargeTypeBuilder regionId(final String regionId);

        @GET
        @ConstParams({"Action", "ModifyInstanceChargeType"})
        @OnInteract("signer")
        Observable<ModifyInstanceChargeTypeResponse> call();
    }

    ModifyInstanceChargeTypeBuilder modifyInstanceChargeType();

    interface ModifyInstanceSpecResponse extends ECSAPIResponse {
    }

    @RpcBuilder
    interface ModifyInstanceSpecBuilder extends EcsBuilder<ModifyInstanceSpecBuilder> {
        @QueryParam("InstanceIds")
        ModifyInstanceSpecBuilder instanceIds(final String[] instanceIds);

        @QueryParam("RamRoleName")
        ModifyInstanceSpecBuilder ramRoleName(final String ramRoleName);

        @QueryParam("RegionId")
        ModifyInstanceSpecBuilder regionId(final String regionId);

        @GET
        @ConstParams({"Action", "ModifyInstanceSpec"})
        @OnInteract("signer")
        Observable<ModifyInstanceSpecResponse> call();
    }

    ModifyInstanceSpecBuilder modifyInstanceSpec();

    interface ModifyPrepayInstanceSpecResponse extends ECSAPIResponse {
    }

    @RpcBuilder
    interface ModifyPrepayInstanceSpecBuilder extends EcsBuilder<ModifyPrepayInstanceSpecBuilder> {
        @QueryParam("InstanceIds")
        ModifyPrepayInstanceSpecBuilder instanceIds(final String[] instanceIds);

        @QueryParam("RamRoleName")
        ModifyPrepayInstanceSpecBuilder ramRoleName(final String ramRoleName);

        @QueryParam("RegionId")
        ModifyPrepayInstanceSpecBuilder regionId(final String regionId);

        @GET
        @ConstParams({"Action", "ModifyPrepayInstanceSpec"})
        @OnInteract("signer")
        Observable<ModifyPrepayInstanceSpecResponse> call();
    }

    ModifyPrepayInstanceSpecBuilder modifyPrepayInstanceSpec();

    interface DescribeInstancesFullStatusResponse extends ECSAPIResponse {
    }

    @RpcBuilder
    interface DescribeInstancesFullStatusBuilder extends EcsBuilder<DescribeInstancesFullStatusBuilder> {
        @QueryParam("InstanceIds")
        DescribeInstancesFullStatusBuilder instanceIds(final String[] instanceIds);

        @QueryParam("RamRoleName")
        DescribeInstancesFullStatusBuilder ramRoleName(final String ramRoleName);

        @QueryParam("RegionId")
        DescribeInstancesFullStatusBuilder regionId(final String regionId);

        @GET
        @ConstParams({"Action", "DescribeInstancesFullStatus"})
        @OnInteract("signer")
        Observable<DescribeInstancesFullStatusResponse> call();
    }

    DescribeInstancesFullStatusBuilder describeInstancesFullStatus();

    //  TBD: https://help.aliyun.com/document_detail/63962.html?spm=a2c4g.11186623.6.1295.496db95cVFgyMa
    interface DescribeInstanceHistoryEventsResponse extends ECSAPIResponse {
    }

    @RpcBuilder
    interface DescribeInstanceHistoryEventsBuilder extends EcsBuilder<DescribeInstanceHistoryEventsBuilder> {
        @QueryParam("InstanceIds")
        DescribeInstanceHistoryEventsBuilder instanceIds(final String[] instanceIds);

        @QueryParam("RamRoleName")
        DescribeInstanceHistoryEventsBuilder ramRoleName(final String ramRoleName);

        @QueryParam("RegionId")
        DescribeInstanceHistoryEventsBuilder regionId(final String regionId);

        @GET
        @ConstParams({"Action", "DescribeInstanceHistoryEvents"})
        @OnInteract("signer")
        Observable<DescribeInstanceHistoryEventsResponse> call();
    }

    DescribeInstanceHistoryEventsBuilder describeInstanceHistoryEvents();

    //  TBD: https://help.aliyun.com/document_detail/63963.html?spm=a2c4g.11186623.6.1296.1e7f1a4cukcipD
    interface DescribeDisksFullStatusResponse extends ECSAPIResponse {
    }

    @RpcBuilder
    interface DescribeDisksFullStatusBuilder extends EcsBuilder<DescribeDisksFullStatusBuilder> {
        @QueryParam("InstanceIds")
        DescribeDisksFullStatusBuilder instanceIds(final String[] instanceIds);

        @QueryParam("RamRoleName")
        DescribeDisksFullStatusBuilder ramRoleName(final String ramRoleName);

        @QueryParam("RegionId")
        DescribeDisksFullStatusBuilder regionId(final String regionId);

        @GET
        @ConstParams({"Action", "DescribeDisksFullStatus"})
        @OnInteract("signer")
        Observable<DescribeDisksFullStatusResponse> call();
    }

    DescribeDisksFullStatusBuilder describeDisksFullStatus();

    //  TBD: https://help.aliyun.com/document_detail/63963.html?spm=a2c4g.11186623.6.1296.1e7f1a4cukcipD
    interface CancelSimulatedSystemEventsResponse extends ECSAPIResponse {
    }

    @RpcBuilder
    interface CancelSimulatedSystemEventsBuilder extends EcsBuilder<CancelSimulatedSystemEventsBuilder> {
        @QueryParam("InstanceIds")
        CancelSimulatedSystemEventsBuilder instanceIds(final String[] instanceIds);

        @QueryParam("RamRoleName")
        CancelSimulatedSystemEventsBuilder ramRoleName(final String ramRoleName);

        @QueryParam("RegionId")
        CancelSimulatedSystemEventsBuilder regionId(final String regionId);

        @GET
        @ConstParams({"Action", "CancelSimulatedSystemEvents"})
        @OnInteract("signer")
        Observable<CancelSimulatedSystemEventsResponse> call();
    }

    CancelSimulatedSystemEventsBuilder cancelSimulatedSystemEvents();

    //  TBD: https://help.aliyun.com/document_detail/88814.html?spm=a2c4g.11186623.6.1302.5e286918lljOHg
    interface CreateSimulatedSystemEventsResponse extends ECSAPIResponse {
    }

    @RpcBuilder
    interface CreateSimulatedSystemEventsBuilder extends EcsBuilder<CreateSimulatedSystemEventsBuilder> {
        @QueryParam("InstanceIds")
        CreateSimulatedSystemEventsBuilder instanceIds(final String[] instanceIds);

        @QueryParam("RamRoleName")
        CreateSimulatedSystemEventsBuilder ramRoleName(final String ramRoleName);

        @QueryParam("RegionId")
        CreateSimulatedSystemEventsBuilder regionId(final String regionId);

        @GET
        @ConstParams({"Action", "CreateSimulatedSystemEvents"})
        @OnInteract("signer")
        Observable<CreateSimulatedSystemEventsResponse> call();
    }

    CreateSimulatedSystemEventsBuilder createSimulatedSystemEvents();

    // 运维与监控
    //  TBD: https://help.aliyun.com/document_detail/87546.html?spm=a2c4g.11186623.6.1304.2dbc355fzGeV1i
    interface DescribeEniMonitorDataResponse extends ECSAPIResponse {
    }

    @RpcBuilder
    interface DescribeEniMonitorDataBuilder extends EcsBuilder<DescribeEniMonitorDataBuilder> {
        @QueryParam("InstanceIds")
        DescribeEniMonitorDataBuilder instanceIds(final String[] instanceIds);

        @QueryParam("RamRoleName")
        DescribeEniMonitorDataBuilder ramRoleName(final String ramRoleName);

        @QueryParam("RegionId")
        DescribeEniMonitorDataBuilder regionId(final String regionId);

        @GET
        @ConstParams({"Action", "DescribeEniMonitorData"})
        @OnInteract("signer")
        Observable<DescribeEniMonitorDataResponse> call();
    }

    DescribeEniMonitorDataBuilder describeEniMonitorData();

    //  TBD: https://help.aliyun.com/document_detail/25614.html?spm=a2c4g.11186623.6.1306.102251f4UVVrfx
    interface DescribeDiskMonitorDataResponse extends ECSAPIResponse {
    }

    @RpcBuilder
    interface DescribeDiskMonitorDataBuilder extends EcsBuilder<DescribeDiskMonitorDataBuilder> {
        @QueryParam("InstanceIds")
        DescribeDiskMonitorDataBuilder instanceIds(final String[] instanceIds);

        @QueryParam("RamRoleName")
        DescribeDiskMonitorDataBuilder ramRoleName(final String ramRoleName);

        @QueryParam("RegionId")
        DescribeDiskMonitorDataBuilder regionId(final String regionId);

        @GET
        @ConstParams({"Action", "DescribeDiskMonitorData"})
        @OnInteract("signer")
        Observable<DescribeDiskMonitorDataResponse> call();
    }

    DescribeDiskMonitorDataBuilder describeDiskMonitorData();

    //  TBD: https://help.aliyun.com/document_detail/25612.html?spm=a2c4g.11186623.6.1307.11e21bbcQcss1O
    interface DescribeInstanceMonitorDataResponse extends ECSAPIResponse {
    }

    @RpcBuilder
    interface DescribeInstanceMonitorDataBuilder extends EcsBuilder<DescribeInstanceMonitorDataBuilder> {
        @QueryParam("InstanceIds")
        DescribeInstanceMonitorDataBuilder instanceIds(final String[] instanceIds);

        @QueryParam("RamRoleName")
        DescribeInstanceMonitorDataBuilder ramRoleName(final String ramRoleName);

        @QueryParam("RegionId")
        DescribeInstanceMonitorDataBuilder regionId(final String regionId);

        @GET
        @ConstParams({"Action", "DescribeInstanceMonitorData"})
        @OnInteract("signer")
        Observable<DescribeInstanceMonitorDataResponse> call();
    }

    DescribeInstanceMonitorDataBuilder describeInstanceMonitorData();

    //  TBD: https://help.aliyun.com/document_detail/85921.html?spm=a2c4g.11186623.6.1308.20491e436H33eY
    interface GetInstanceScreenshotResponse extends ECSAPIResponse {
    }

    @RpcBuilder
    interface GetInstanceScreenshotBuilder extends EcsBuilder<GetInstanceScreenshotBuilder> {
        @QueryParam("InstanceIds")
        GetInstanceScreenshotBuilder instanceIds(final String[] instanceIds);

        @QueryParam("RamRoleName")
        GetInstanceScreenshotBuilder ramRoleName(final String ramRoleName);

        @QueryParam("RegionId")
        GetInstanceScreenshotBuilder regionId(final String regionId);

        @GET
        @ConstParams({"Action", "GetInstanceScreenshot"})
        @OnInteract("signer")
        Observable<GetInstanceScreenshotResponse> call();
    }

    GetInstanceScreenshotBuilder getInstanceScreenshot();

    //  TBD: https://help.aliyun.com/document_detail/85922.html?spm=a2c4g.11186623.6.1310.701959f6mJRm1N
    interface GetInstanceConsoleOutputResponse extends ECSAPIResponse {
    }

    @RpcBuilder
    interface GetInstanceConsoleOutputBuilder extends EcsBuilder<GetInstanceConsoleOutputBuilder> {
        @QueryParam("InstanceIds")
        GetInstanceConsoleOutputBuilder instanceIds(final String[] instanceIds);

        @QueryParam("RamRoleName")
        GetInstanceConsoleOutputBuilder ramRoleName(final String ramRoleName);

        @QueryParam("RegionId")
        GetInstanceConsoleOutputBuilder regionId(final String regionId);

        @GET
        @ConstParams({"Action", "GetInstanceConsoleOutput"})
        @OnInteract("signer")
        Observable<GetInstanceConsoleOutputResponse> call();
    }

    GetInstanceConsoleOutputBuilder getInstanceConsoleOutput();

    //  TBD: https://help.aliyun.com/document_detail/140980.html?spm=a2c4g.11186623.6.1311.11712f88YtgIHU
    interface DescribeSnapshotMonitorDataResponse extends ECSAPIResponse {
    }

    @RpcBuilder
    interface DescribeSnapshotMonitorDataBuilder extends EcsBuilder<DescribeSnapshotMonitorDataBuilder> {
        @QueryParam("InstanceIds")
        DescribeSnapshotMonitorDataBuilder instanceIds(final String[] instanceIds);

        @QueryParam("RamRoleName")
        DescribeSnapshotMonitorDataBuilder ramRoleName(final String ramRoleName);

        @QueryParam("RegionId")
        DescribeSnapshotMonitorDataBuilder regionId(final String regionId);

        @GET
        @ConstParams({"Action", "DescribeSnapshotMonitorData"})
        @OnInteract("signer")
        Observable<DescribeSnapshotMonitorDataResponse> call();
    }

    DescribeSnapshotMonitorDataBuilder describeSnapshotMonitorData();

    //  TBD: https://help.aliyun.com/document_detail/25609.html?spm=a2c4g.11186623.6.1333.785b798dNB8QEy
    interface DescribeRegionsResponse extends ECSAPIResponse {
    }

    @RpcBuilder
    interface DescribeRegionsBuilder extends EcsBuilder<DescribeRegionsBuilder> {
        @QueryParam("InstanceIds")
        DescribeRegionsBuilder instanceIds(final String[] instanceIds);

        @QueryParam("RamRoleName")
        DescribeRegionsBuilder ramRoleName(final String ramRoleName);

        @QueryParam("RegionId")
        DescribeRegionsBuilder regionId(final String regionId);

        @GET
        @ConstParams({"Action", "DescribeRegions"})
        @OnInteract("signer")
        Observable<DescribeRegionsResponse> call();
    }

    DescribeRegionsBuilder describeRegions();

    //  TBD: https://help.aliyun.com/document_detail/25610.html?spm=a2c4g.11186623.6.1334.7dc235bd93H3KF
    interface DescribeZonesResponse extends ECSAPIResponse {
    }

    @RpcBuilder
    interface DescribeZonesBuilder extends EcsBuilder<DescribeZonesBuilder> {
        @QueryParam("InstanceIds")
        DescribeZonesBuilder instanceIds(final String[] instanceIds);

        @QueryParam("RamRoleName")
        DescribeZonesBuilder ramRoleName(final String ramRoleName);

        @QueryParam("RegionId")
        DescribeZonesBuilder regionId(final String regionId);

        @GET
        @ConstParams({"Action", "DescribeZones"})
        @OnInteract("signer")
        Observable<DescribeZonesResponse> call();
    }

    DescribeZonesBuilder describeZones();

    //  TBD: https://help.aliyun.com/document_detail/66187.html?spm=a2c4g.11186623.6.1335.6be3448aCjxWid
    interface DescribeResourcesModificationResponse extends ECSAPIResponse {
    }

    @RpcBuilder
    interface DescribeResourcesModificationBuilder extends EcsBuilder<DescribeResourcesModificationBuilder> {
        @QueryParam("InstanceIds")
        DescribeResourcesModificationBuilder instanceIds(final String[] instanceIds);

        @QueryParam("RamRoleName")
        DescribeResourcesModificationBuilder ramRoleName(final String ramRoleName);

        @QueryParam("RegionId")
        DescribeResourcesModificationBuilder regionId(final String regionId);

        @GET
        @ConstParams({"Action", "DescribeResourcesModification"})
        @OnInteract("signer")
        Observable<DescribeResourcesModificationResponse> call();
    }

    DescribeResourcesModificationBuilder describeResourcesModification();

    //  TBD: https://help.aliyun.com/document_detail/66186.html?spm=a2c4g.11186623.6.1336.7bdf6e82n1agNa
    interface DescribeAvailableResourceResponse extends ECSAPIResponse {
    }

    @RpcBuilder
    interface DescribeAvailableResourceBuilder extends EcsBuilder<DescribeAvailableResourceBuilder> {
        @QueryParam("InstanceIds")
        DescribeAvailableResourceBuilder instanceIds(final String[] instanceIds);

        @QueryParam("RamRoleName")
        DescribeAvailableResourceBuilder ramRoleName(final String ramRoleName);

        @QueryParam("RegionId")
        DescribeAvailableResourceBuilder regionId(final String regionId);

        @GET
        @ConstParams({"Action", "DescribeAvailableResource"})
        @OnInteract("signer")
        Observable<DescribeAvailableResourceResponse> call();
    }

    DescribeAvailableResourceBuilder describeAvailableResource();

    // SSH 密钥对 相关
    //  TBD: https://help.aliyun.com/document_detail/51771.html?spm=a2c4g.11186623.6.1274.1c2f263aA2hveO
    interface CreateKeyPairResponse extends ECSAPIResponse {
    }

    @RpcBuilder
    interface CreateKeyPairBuilder extends EcsBuilder<CreateKeyPairBuilder> {
        @QueryParam("InstanceIds")
        CreateKeyPairBuilder instanceIds(final String[] instanceIds);

        @QueryParam("RamRoleName")
        CreateKeyPairBuilder ramRoleName(final String ramRoleName);

        @QueryParam("RegionId")
        CreateKeyPairBuilder regionId(final String regionId);

        @GET
        @ConstParams({"Action", "CreateKeyPair"})
        @OnInteract("signer")
        Observable<CreateKeyPairResponse> call();
    }

    CreateKeyPairBuilder createKeyPair();

    //  TBD: https://help.aliyun.com/document_detail/51774.html?spm=a2c4g.11186623.6.1275.513a2e9eVOSUgn
    interface ImportKeyPairResponse extends ECSAPIResponse {
    }

    @RpcBuilder
    interface ImportKeyPairBuilder extends EcsBuilder<ImportKeyPairBuilder> {
        @QueryParam("InstanceIds")
        ImportKeyPairBuilder instanceIds(final String[] instanceIds);

        @QueryParam("RamRoleName")
        ImportKeyPairBuilder ramRoleName(final String ramRoleName);

        @QueryParam("RegionId")
        ImportKeyPairBuilder regionId(final String regionId);

        @GET
        @ConstParams({"Action", "ImportKeyPair"})
        @OnInteract("signer")
        Observable<ImportKeyPairResponse> call();
    }

    ImportKeyPairBuilder importKeyPair();

    //  TBD: https://help.aliyun.com/document_detail/51775.html?spm=a2c4g.11186623.6.1282.622f6a31gP2YEb
    interface AttachKeyPairResponse extends ECSAPIResponse {
    }

    @RpcBuilder
    interface AttachKeyPairBuilder extends EcsBuilder<AttachKeyPairBuilder> {
        @QueryParam("InstanceIds")
        AttachKeyPairBuilder instanceIds(final String[] instanceIds);

        @QueryParam("RamRoleName")
        AttachKeyPairBuilder ramRoleName(final String ramRoleName);

        @QueryParam("RegionId")
        AttachKeyPairBuilder regionId(final String regionId);

        @GET
        @ConstParams({"Action", "AttachKeyPair"})
        @OnInteract("signer")
        Observable<AttachKeyPairResponse> call();
    }

    AttachKeyPairBuilder attachKeyPair();

    //  TBD: https://help.aliyun.com/document_detail/51776.html?spm=a2c4g.11186623.6.1283.61ba1f7bCJDkKl
    interface DetachKeyPairResponse extends ECSAPIResponse {
    }

    @RpcBuilder
    interface DetachKeyPairBuilder extends EcsBuilder<DetachKeyPairBuilder> {
        @QueryParam("InstanceIds")
        DetachKeyPairBuilder instanceIds(final String[] instanceIds);

        @QueryParam("RamRoleName")
        DetachKeyPairBuilder ramRoleName(final String ramRoleName);

        @QueryParam("RegionId")
        DetachKeyPairBuilder regionId(final String regionId);

        @GET
        @ConstParams({"Action", "DetachKeyPair"})
        @OnInteract("signer")
        Observable<DetachKeyPairResponse> call();
    }

    DetachKeyPairBuilder detachKeyPair();

    //  TBD: https://help.aliyun.com/document_detail/51772.html?spm=a2c4g.11186623.6.1284.7ac41f7be3WAzU
    interface DeleteKeyPairsResponse extends ECSAPIResponse {
    }

    @RpcBuilder
    interface DeleteKeyPairsBuilder extends EcsBuilder<DeleteKeyPairsBuilder> {
        @QueryParam("InstanceIds")
        DeleteKeyPairsBuilder instanceIds(final String[] instanceIds);

        @QueryParam("RamRoleName")
        DeleteKeyPairsBuilder ramRoleName(final String ramRoleName);

        @QueryParam("RegionId")
        DeleteKeyPairsBuilder regionId(final String regionId);

        @GET
        @ConstParams({"Action", "DeleteKeyPairs"})
        @OnInteract("signer")
        Observable<DeleteKeyPairsResponse> call();
    }

    DeleteKeyPairsBuilder deleteKeyPairs();

    //  TBD: https://help.aliyun.com/document_detail/51773.html?spm=a2c4g.11186623.6.1285.4c972427dKRksp
    interface DescribeKeyPairsResponse extends ECSAPIResponse {
    }

    @RpcBuilder
    interface DescribeKeyPairsBuilder extends EcsBuilder<DescribeKeyPairsBuilder> {
        @QueryParam("InstanceIds")
        DescribeKeyPairsBuilder instanceIds(final String[] instanceIds);

        @QueryParam("RamRoleName")
        DescribeKeyPairsBuilder ramRoleName(final String ramRoleName);

        @QueryParam("RegionId")
        DescribeKeyPairsBuilder regionId(final String regionId);

        @GET
        @ConstParams({"Action", "DescribeKeyPairs"})
        @OnInteract("signer")
        Observable<DescribeKeyPairsResponse> call();
    }

    DescribeKeyPairsBuilder describeKeyPairs();

    // 网络 相关
    //  TBD: https://help.aliyun.com/document_detail/25544.html?spm=a2c4g.11186623.6.1286.513d41ddMaHI1A
    interface AllocatePublicIpAddressResponse extends ECSAPIResponse {
    }

    @RpcBuilder
    interface AllocatePublicIpAddressBuilder extends EcsBuilder<AllocatePublicIpAddressBuilder> {
        @QueryParam("InstanceIds")
        AllocatePublicIpAddressBuilder instanceIds(final String[] instanceIds);

        @QueryParam("RamRoleName")
        AllocatePublicIpAddressBuilder ramRoleName(final String ramRoleName);

        @QueryParam("RegionId")
        AllocatePublicIpAddressBuilder regionId(final String regionId);

        @GET
        @ConstParams({"Action", "AllocatePublicIpAddress"})
        @OnInteract("signer")
        Observable<AllocatePublicIpAddressResponse> call();
    }

    AllocatePublicIpAddressBuilder allocatePublicIpAddress();

    //  TBD: https://help.aliyun.com/document_detail/60738.html?spm=a2c4g.11186623.6.1287.c0692fa8twN2Kr
    interface ConvertNatPublicIpToEipResponse extends ECSAPIResponse {
    }

    @RpcBuilder
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

    @RpcBuilder
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

    @RpcBuilder
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

    @RpcBuilder
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

    @RpcBuilder
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

    @RpcBuilder
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

    @RpcBuilder
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

    @RpcBuilder
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

    @RpcBuilder
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

    @RpcBuilder
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

    @RpcBuilder
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

    @RpcBuilder
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

    @RpcBuilder
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

    @RpcBuilder
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

    @RpcBuilder
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

    @RpcBuilder
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

    @RpcBuilder
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

    @RpcBuilder
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

    @RpcBuilder
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

    @RpcBuilder
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

    @RpcBuilder
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

    @RpcBuilder
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

    @RpcBuilder
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

    @RpcBuilder
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

    @RpcBuilder
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

    @RpcBuilder
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

    @RpcBuilder
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

    @RpcBuilder
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

    @RpcBuilder
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

    @RpcBuilder
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

    @RpcBuilder
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

    @RpcBuilder
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

    @RpcBuilder
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

    @RpcBuilder
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

    @RpcBuilder
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

    @RpcBuilder
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

    @RpcBuilder
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

    @RpcBuilder
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

    @RpcBuilder
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

    @RpcBuilder
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

    @RpcBuilder
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

    @RpcBuilder
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

    @RpcBuilder
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

    @RpcBuilder
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

    @RpcBuilder
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

    @RpcBuilder
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

    @RpcBuilder
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

    @RpcBuilder
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

    @RpcBuilder
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

    @RpcBuilder
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

    @RpcBuilder
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

    @RpcBuilder
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

    @RpcBuilder
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

    @RpcBuilder
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

    @RpcBuilder
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

    @RpcBuilder
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

    @RpcBuilder
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

    @RpcBuilder
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

    @RpcBuilder
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

    @RpcBuilder
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

    @RpcBuilder
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

    @RpcBuilder
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

    @RpcBuilder
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

    @RpcBuilder
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

    @RpcBuilder
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

    @RpcBuilder
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

    @RpcBuilder
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

    @RpcBuilder
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

    @RpcBuilder
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

    @RpcBuilder
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

    @RpcBuilder
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

    @RpcBuilder
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

    @RpcBuilder
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

    @RpcBuilder
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
