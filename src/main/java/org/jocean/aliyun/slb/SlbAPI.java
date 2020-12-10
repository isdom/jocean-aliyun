package org.jocean.aliyun.slb;

import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;

import org.jocean.rpc.annotation.ConstParams;
import org.jocean.rpc.annotation.RpcBuilder;

import com.alibaba.fastjson.annotation.JSONField;

import rx.Observable;

// https://help.aliyun.com/document_detail/27570.html?spm=a2c4g.11186623.6.698.4a1f4fadlRqxeD
@Path("http://slb.aliyuncs.com/")
@ConstParams({"Version", "2014-05-15"})
public interface SlbAPI {

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

    interface SLBAPIResponse {
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

    interface CreateLoadBalancerResponse extends SLBAPIResponse {
        @JSONField(name="LoadBalancerId")
        String getLoadBalancerId();

        @JSONField(name="LoadBalancerId")
        void setLoadBalancerId(final String loadBalancerId);

        @JSONField(name="ResourceGroupId")
        String getResourceGroupId();

        @JSONField(name="ResourceGroupId")
        void setResourceGroupId(final String resourceGroupId);

        @JSONField(name="Address")
        String getAddress();

        @JSONField(name="Address")
        void setAddress(final String address);

        @JSONField(name="LoadBalancerName")
        String getLoadBalancerName();

        @JSONField(name="LoadBalancerName")
        void setLoadBalancerName(final String loadBalancerName);

        @JSONField(name="VpcId")
        String getVpcId();

        @JSONField(name="VpcId")
        void setVpcId(final String vpcId);

        @JSONField(name="VSwitchId")
        String getVSwitchId();

        @JSONField(name="VSwitchId")
        void setVSwitchId(final String vSwitchId);

        @JSONField(name="NetworkType")
        String getNetworkType();

        @JSONField(name="NetworkType")
        void setNetworkType(final String networkType);

        @JSONField(name="OrderId")
        Long getOrderId();

        @JSONField(name="OrderId")
        void setOrderId(final Long orderId);

        @JSONField(name="AddressIPVersion")
        String getAddressIPVersion();

        @JSONField(name="AddressIPVersion")
        void setAddressIPVersion(final String addressIPVersion);
    }

    interface ClientTokenable<T> {
        @QueryParam("ClientToken")
        T clientToken(final String clientToken);
    }

    // https://help.aliyun.com/document_detail/27577.html?spm=a2c4g.11186623.6.704.63de1771E9lN47
    @RpcBuilder
    interface CreateLoadBalancerBuilder extends ClientTokenable<CreateLoadBalancerBuilder> {
        /**
         *
         * @param regionId
         * @return CreateLoadBalancerBuilder
         *  必选，样例：cn-hangzhou
         *  负载均衡实例的地域。
            您可以通过调用DescribeRegions接口查询地域ID。
         */
        @QueryParam("RegionId")
        CreateLoadBalancerBuilder regionId(final String regionId);

        /**
         *
         * @param addressType
         * @return CreateLoadBalancerBuilder
         *  非必选，样例：internet
         *  负载均衡实例的网络类型。取值：
            internet：创建公网负载均衡实例后，系统会分配一个公网IP地址，可以转发公网请求。
            intranet：创建内网负载均衡实例后，系统会分配一个内网IP地址，仅可转发内网请求。
         */
        @QueryParam("AddressType")
        CreateLoadBalancerBuilder addressType(final String addressType);

        /**
         *
         * @param internetChargeType
         * @return CreateLoadBalancerBuilder
         *  非必选，样例：paybytraffic
         *  公网类型实例的付费方式。取值：
            paybybandwidth：按带宽计费。
            paybytraffic：按流量计费（默认值）。
         */
        @QueryParam("InternetChargeType")
        CreateLoadBalancerBuilder internetChargeType(final String internetChargeType);

        /**
         *
         * @param bandwidth
         * @return CreateLoadBalancerBuilder
         *  非必选，样例：10
         *  监听的带宽峰值。
            取值：-1 | 1-5120。
            -1：对于按流量计费的公网负载均衡实例，可以将带宽峰值设置为-1，
                即不限制带宽峰值。
            1-5120(Mbps)： 对于按带宽计费的公网负载均衡实例，可以设置
                每个监听的带宽峰值，但所有监听的带宽峰值之和不能超过实例
                的带宽峰值。详情参见共享实例带宽。
         */
        @QueryParam("Bandwidth")
        CreateLoadBalancerBuilder bandwidth(final Integer bandwidth);

        /**
         *
         * @param loadBalancerName
         * @return CreateLoadBalancerBuilder
         *  非必选，样例：abc
         *  负载均衡实例的名称。
            长度为1-80个英文或中文字符，必须以大小字母或中文开头，可包含数字，点号（.），下划线（_）和短横线（-）。
            不指定该参数时，默认由系统分配一个实例名称。
         */
        @QueryParam("LoadBalancerName")
        CreateLoadBalancerBuilder loadBalancerName(final String loadBalancerName);

        /**
         *
         * @param vpcId
         * @return CreateLoadBalancerBuilder
         *  非必选，样例：vpc-bp1aevy8sofi8mh1*****
         *  负载均衡实例的所属的VPC ID
         */
        @QueryParam("VpcId")
        CreateLoadBalancerBuilder vpcId(final String vpcId);

        /**
         *
         * @param vSwitchId
         * @return CreateLoadBalancerBuilder
         *  非必选，样例：vsw-bp12mw1f8k3jgy*****
         *  专有网络实例的所属交换机ID。
            创建专有网络类型的负载均衡实例，必须指定该参数。如果指定了该参数，AddessType参数的值会默认被设置为intranet。
         */
        @QueryParam("VSwitchId")
        CreateLoadBalancerBuilder VSwitchId(final String vSwitchId);

        /**
         *
         * @param masterZoneId
         * @return CreateLoadBalancerBuilder
         *  非必选，样例：cn-hangzhou-b
         *  负载均衡实例的主可用区ID。
            您可以通过调用DescribeZone接口可查到相应地域下的主备可用区信息。
         */
        @QueryParam("MasterZoneId")
        CreateLoadBalancerBuilder masterZoneId(final String masterZoneId);

        /**
         *
         * @param slaveZoneId
         * @return CreateLoadBalancerBuilder
         *  非必选，样例：cn-hangzhou-d
         *  负载均衡实例的备可用区ID。
            您可以通过调用DescribeZone接口可查到相应地域下的主备可用区信息。
         */
        @QueryParam("SlaveZoneId")
        CreateLoadBalancerBuilder slaveZoneId(final String slaveZoneId);

        /**
         *
         * @param loadBalancerSpec
         * @return CreateLoadBalancerBuilder
         *  非必选，样例：slb.s2.small
         *  负载均衡实例的规格。取值：
            slb.s1.small
            slb.s2.small
            slb.s2.medium
            slb.s3.small
            slb.s3.medium
            slb.s3.large
            每个地域支持的规格不同。
            目前支持性能保障型实例的地域有：华北 1（青岛）、华北 2（北京）、华东 1（杭州）、华东 2（上海）、华南 1（深圳）、华北 3（张家口）、
              华北 5 （呼和浩特）、亚太东南 1（新加坡）、英国（伦敦）、欧洲中部 1（法兰克福）、亚太东南 2（悉尼）、亚太东南 3（吉隆坡）、
              中东东部 1（迪拜）、亚太东南 5（雅加达）、美西 1（硅谷）、亚太南部 1（孟买）、亚太东北 1（东京）、香港和美东 1（弗吉尼亚）。
              关于每种规格的说明，参见性能保障型实例。
            说明 若不指定规格，则创建性能共享型实例。
         */
        @QueryParam("LoadBalancerSpec")
        CreateLoadBalancerBuilder loadBalancerSpec(final String loadBalancerSpec);

        /**
         *
         * @param resourceGroupId
         * @return CreateLoadBalancerBuilder
         *  非必选，样例：rg-atstuj3rtopt****
         *  企业资源组ID。
         */
        @QueryParam("ResourceGroupId")
        CreateLoadBalancerBuilder resourceGroupId(final String resourceGroupId);

        /**
         *
         * @param payType
         * @return CreateLoadBalancerBuilder
         *  非必选，样例：PayOnDemand
         *  实例的计费类型，取值：
            PayOnDemand：按量付费。
            PrePay：包年包月。
            如果该参数设置为PrePay，即表示创建包年包月实例，则Duration参数必选。
         */
        @QueryParam("PayType")
        CreateLoadBalancerBuilder payType(final String payType);

        /**
         *
         * @param pricingCycle
         * @return CreateLoadBalancerBuilder
         *  非必选，样例：month
         *  预付费公网实例的计费周期，取值：month|year
            说明
            仅适用于中国站。
            仅对包年包月实例有效，即PayType的参数值为PrePay时有效。
         */
        @QueryParam("PricingCycle")
        CreateLoadBalancerBuilder pricingCycle(final String pricingCycle);

        /**
         *
         * @param duration
         * @return CreateLoadBalancerBuilder
         *  非必选，样例：1
         *  预付费公网实例的购买时长，取值：
            如果PricingCycle为month，取值为1~9。
            如果PricingCycle为year，取值为1~3。
            说明
            该参数仅适用于中国站。
            仅对包年包月实例有效，即PayType的参数值为PrePay时有效。
         */
        @QueryParam("Duration")
        CreateLoadBalancerBuilder duration(final Integer duration);

        /**
         *
         * @param autoPay
         * @return CreateLoadBalancerBuilder
         *  非必选，样例：true
         *  是否是自动支付预付费公网实例的账单。
            取值：true|false（默认）。
            说明
            该参数仅适用于中国站。
            仅对包年包月实例有效，即PayType的参数值为PrePay时有效。
         */
        @QueryParam("AutoPay")
        CreateLoadBalancerBuilder autoPay(final Boolean autoPay);

        /**
         *
         * @param addressIPVersion
         * @return CreateLoadBalancerBuilder
         *  非必选，样例：ipv4
         *  负载均衡实例的IP版本，可以设置为ipv4或者ipv6。
         */
        @QueryParam("AddressIPVersion")
        CreateLoadBalancerBuilder addressIPVersion(final String addressIPVersion);

        /**
         *
         * @param address
         * @return CreateLoadBalancerBuilder
         *  非必选，样例：192.168.0.**
         *  指定负载均衡实例的私网IP地址，该地址必须包含在交换机的目标网段下。
         */
        @QueryParam("Address")
        CreateLoadBalancerBuilder address(final String address);

        /**
         *
         * @param deleteProtection
         * @return CreateLoadBalancerBuilder
         *  非必选，样例：on
         *  是否开启实例删除保护。
         */
        @QueryParam("DeleteProtection")
        CreateLoadBalancerBuilder deleteProtection(final String deleteProtection);

        @GET
        @ConstParams({"Action", "CreateLoadBalancer"})
        @Consumes(MediaType.APPLICATION_JSON)
        Observable<CreateLoadBalancerResponse> call();
    }

    public CreateLoadBalancerBuilder createLoadBalancer();

    interface DeleteLoadBalancerResponse extends SLBAPIResponse {
    }

    @RpcBuilder
    interface DeleteLoadBalancerBuilder {
        @QueryParam("RegionId")
        DeleteLoadBalancerBuilder regionId(final String regionId);

        @QueryParam("LoadBalancerId")
        DeleteLoadBalancerBuilder loadBalancerId(final String loadBalancerId);
        @GET
        @ConstParams({"Action", "DeleteLoadBalancer"})
        @Consumes(MediaType.APPLICATION_JSON)
        Observable<DeleteLoadBalancerResponse> call();
    }

    // https://help.aliyun.com/document_detail/27579.html?spm=a2c4g.11186623.6.692.61024800KmMSxH
    public DeleteLoadBalancerBuilder deleteLoadBalancer();

    interface ModifyLoadBalancerPayTypeResponse extends SLBAPIResponse {
    }

    @RpcBuilder
    interface ModifyLoadBalancerPayTypeBuilder {
        @QueryParam("RegionId")
        ModifyLoadBalancerPayTypeBuilder regionId(final String regionId);

        @QueryParam("LoadBalancerId")
        ModifyLoadBalancerPayTypeBuilder loadBalancerId(final String loadBalancerId);

        /**
         *
        实例的计费类型，取值：

        PayOnDemand：按量计费。
        PrePay：包年包月。
        按量计费转为包年包月计费，该参数取值只能为PrePay，且该实例之前的计费类型必须为PayOnDemand。
         */
        @QueryParam("PayType")
        ModifyLoadBalancerPayTypeBuilder payType(final String payType);

        /**
         *
        计费周期。

        取值：year|month 。
         */
        @QueryParam("PricingCycle")
        ModifyLoadBalancerPayTypeBuilder pricingCycle(final String pricingCycle);

        /**
        *
        * @param duration
        * @return ModifyLoadBalancerPayTypeBuilder
        *  非必选，样例：1
        *  预付费公网实例的购买时长，取值：
           如果PricingCycle为month，取值为1~9。
           如果PricingCycle为year，取值为1~3。
           说明
           仅对包年包月实例有效，即PayType的参数值为PrePay时有效。
        */
       @QueryParam("Duration")
       ModifyLoadBalancerPayTypeBuilder duration(final Integer duration);

       /**
        *
        * @param autoPay
        * @return ModifyLoadBalancerPayTypeBuilder
        *  非必选，样例：true
        *  是否是自动支付预付费公网实例的账单。
           取值：true|false（默认）。
           说明
           该参数仅适用于中国站。
           仅对包年包月实例有效，即PayType的参数值为PrePay时有效。
        */
       @QueryParam("AutoPay")
       ModifyLoadBalancerPayTypeBuilder autoPay(final Boolean autoPay);

        @GET
        @ConstParams({"Action", "ModifyLoadBalancerPayType"})
        @Consumes(MediaType.APPLICATION_JSON)
        Observable<ModifyLoadBalancerPayTypeResponse> call();
    }

    // https://help.aliyun.com/document_detail/59589.html?spm=a2c4g.11186623.6.693.56b5480fRBnAw1
    public ModifyLoadBalancerPayTypeBuilder modifyLoadBalancerPayType();

    interface ModifyLoadBalancerInstanceSpecResponse extends SLBAPIResponse {
        /**
         *
        预付费实例的订单ID。
         */
        @JSONField(name="OrderId")
        Long getOrderId();

        @JSONField(name="OrderId")
        void setOrderId(final Long orderId);
    }

    @RpcBuilder
    interface ModifyLoadBalancerInstanceSpecBuilder {
        @QueryParam("RegionId")
        ModifyLoadBalancerInstanceSpecBuilder regionId(final String regionId);

        @QueryParam("LoadBalancerId")
        ModifyLoadBalancerInstanceSpecBuilder loadBalancerId(final String loadBalancerId);

        /**
         *
        负载均衡实例的规格。取值：

        slb.s1.small
        slb.s2.small
        slb.s2.medium
        slb.s3.small
        slb.s3.medium
        slb.s3.large
        每个地域支持的规格不同。关于每种规格的说明，参见性能保障型实例。
        */
        @QueryParam("LoadBalancerSpec")
        ModifyLoadBalancerInstanceSpecBuilder loadBalancerSpec(final String loadBalancerSpec);

        /**
        *
        * @param autoPay
        * @return ModifyLoadBalancerInstanceSpecBuilder
        *  非必选，样例：true
        *  是否自动付费。

        取值为true则自动支付订单。
        取值为false则需要在订单中心中进行支付。
        */
       @QueryParam("AutoPay")
       ModifyLoadBalancerInstanceSpecBuilder autoPay(final Boolean autoPay);

        @GET
        @ConstParams({"Action", "ModifyLoadBalancerInstanceSpec"})
        @Consumes(MediaType.APPLICATION_JSON)
        Observable<ModifyLoadBalancerInstanceSpecResponse> call();
    }

    // https://help.aliyun.com/document_detail/53360.html?spm=a2c4g.11186623.6.694.7eee6554xoKouH
    /**
     * 修改负载均衡的实例规格。
     * @return
     */
    public ModifyLoadBalancerInstanceSpecBuilder modifyLoadBalancerInstanceSpec();

    interface SlaveZone {
        @JSONField(name="ZoneId")
        String getZoneId();

        @JSONField(name="ZoneId")
        void setZoneId(final String zoneId);

        @JSONField(name="LocalName")
        String getLocalName();

        @JSONField(name="LocalName")
        void setLocalName(final String localName);
    }

    interface SlaveZones {
        @JSONField(name="SlaveZone")
        SlaveZone[] getSlaveZone();

        @JSONField(name="SlaveZone")
        void setSlaveZone(final SlaveZone[] slaveZone);
    }

    interface Zone {
        @JSONField(name="SlaveZones")
        SlaveZones getSlaveZones();

        @JSONField(name="SlaveZones")
        void setSlaveZones(final SlaveZones slaveZones);

        @JSONField(name="ZoneId")
        String getZoneId();

        @JSONField(name="ZoneId")
        void setZoneId(final String zoneId);

        @JSONField(name="LocalName")
        String getLocalName();

        @JSONField(name="LocalName")
        void setLocalName(final String localName);
    }

    interface Zones {
        @JSONField(name="Zone")
        Zone[] getZone();

        @JSONField(name="Zone")
        void setZone(final Zone[] zone);
    }

    interface DescribeZonesResponse extends SLBAPIResponse {
        @JSONField(name="Zones")
        Zones getZones();

        @JSONField(name="Zones")
        void setZones(final Zones zones);
    }

    @RpcBuilder
    interface DescribeZonesBuilder {
        /**
         * cn-hangzhou   所属地域ID。
         */
        @QueryParam("RegionId")
        DescribeZonesBuilder regionId(final String regionId);

        @QueryParam("OwnerAccount")
        DescribeZonesBuilder ownerAccount(final String ownerAccount);

        @GET
        @ConstParams({"Action", "DescribeZones"})
        @Consumes(MediaType.APPLICATION_JSON)
        Observable<DescribeZonesResponse> call();
    }

    // https://help.aliyun.com/document_detail/27585.html?spm=a2c4g.11186623.6.695.2e921b0eLXm2aa
    public DescribeZonesBuilder describeZones();

    interface Region {
        @JSONField(name="RegionId")
        String getRegionId();

        @JSONField(name="RegionId")
        void setRegionId(final String regionId);

        @JSONField(name="RegionEndpoint")
        String getRegionEndpoint();

        @JSONField(name="RegionEndpoint")
        void setRegionEndpoint(final String regionEndpoint);

        @JSONField(name="LocalName")
        String getLocalName();

        @JSONField(name="LocalName")
        void setLocalName(final String localName);
    }

    interface Regions {
        @JSONField(name="Region")
        Region[] getRegion();

        @JSONField(name="Region")
        void setRegion(final Region[] region);
    }

    interface DescribeRegionsResponse extends SLBAPIResponse {
        @JSONField(name="Regions")
        Regions getRegions();

        @JSONField(name="Regions")
        void setRegions(final Regions regions);
    }

    @RpcBuilder
    interface DescribeRegionsBuilder {
        @QueryParam("RegionId")
        DescribeRegionsBuilder regionId(final String regionId);

        @QueryParam("AcceptLanguage")
        DescribeRegionsBuilder acceptLanguage(final String acceptLanguage);

        @GET
        @ConstParams({"Action", "DescribeRegions"})
        @Consumes(MediaType.APPLICATION_JSON)
        Observable<DescribeRegionsResponse> call();
    }

    // https://help.aliyun.com/document_detail/27584.html?spm=a2c4g.11186623.6.696.373f50f5BL3ayX
    public DescribeRegionsBuilder describeRegions();

    interface LoadBalancer {
        /**
         * 负载均衡实例ID。
         */
        @JSONField(name="LoadBalancerId")
        String getLoadBalancerId();

        @JSONField(name="LoadBalancerId")
        void setLoadBalancerId(final String loadBalancerId);

        /**
         * 负载均衡实例的名称。
         */
        @JSONField(name="LoadBalancerName")
        String getLoadBalancerName();

        @JSONField(name="LoadBalancerName")
        void setLoadBalancerName(final String loadBalancerName);

        /**
         * 负载均衡实例状态：

            inactive: 此状态的实例监听不会再转发流量。
            active: 实例创建后，默认状态为active。
            locked: 实例已经被锁定。
         */
        @JSONField(name="LoadBalancerStatus")
        String getLoadBalancerStatus();

        @JSONField(name="LoadBalancerStatus")
        void setLoadBalancerStatus(final String loadBalancerStatus);

        /**
         * 负载均衡实例服务地址。
         */
        @JSONField(name="Address")
        String getAddress();

        @JSONField(name="Address")
        void setAddress(final String address);

        /**
         * 负载均衡实例的网络类型。
         */
        @JSONField(name="AddressType")
        String getAddressType();

        @JSONField(name="AddressType")
        void setAddressType(final String addressType);

        /**
         * 负载均衡实例的地域ID。
         */
        @JSONField(name="RegionId")
        String getRegionId();

        @JSONField(name="RegionId")
        void setRegionId(final String regionId);

        /**
         * 负载均衡实例的地域名称。
         */
        @JSONField(name="RegionIdAlias")
        String getRegionIdAlias();

        @JSONField(name="RegionIdAlias")
        void setRegionIdAlias(final String regionIdAlias);

        /**
         * 私网负载均衡实例的交换机ID。
         */
        @JSONField(name="VSwitchId")
        String getVSwitchId();

        @JSONField(name="VSwitchId")
        void setVSwitchId(final String vSwitchId);

        /**
         * 私网负载均衡实例的专有网络ID。
         */
        @JSONField(name="VpcId")
        String getVpcId();

        @JSONField(name="VpcId")
        void setVpcId(final String vpcId);

        /**
         * 私网负载均衡实例的网络类型，取值：

            vpc：专有网络实例。
            classic：经典网络实例。
         */
        @JSONField(name="NetworkType")
        String getNetworkType();

        @JSONField(name="NetworkType")
        void setNetworkType(final String networkType);

        /**
         * 实例的主可用区ID。
         */
        @JSONField(name="MasterZoneId")
        String getMasterZoneId();

        @JSONField(name="MasterZoneId")
        void setMasterZoneId(final String masterZoneId);

        /**
         * 实例的备可用区ID。
         */
        @JSONField(name="SlaveZoneId")
        String getSlaveZoneId();

        @JSONField(name="SlaveZoneId")
        void setSlaveZoneId(final String slaveZoneId);

        /**
         * 公网实例的计费方式。取值：

            3：paybybandwidth，按带宽计费。
            4：paybytraffic，按流量计费（默认值）。
            说明 当PayType参数的值为PrePay时，只支持按带宽计费。
         */
        @JSONField(name="InternetChargeType")
        String getInternetChargeType();

        @JSONField(name="InternetChargeType")
        void setInternetChargeType(final String internetChargeType);

        /**
         * 负载均衡实例创建时间。
         */
        @JSONField(name="CreateTime")
        String getCreateTime();

        @JSONField(name="CreateTime")
        void setCreateTime(final String createTime);

        /**
         * 负载均衡实例创建时间戳。
         */
        @JSONField(name="CreateTimeStamp")
        Long getCreateTimeStamp();

        @JSONField(name="CreateTimeStamp")
        void setCreateTimeStamp(final Long createTimeStamp);

        /**
         * 负载均衡实例付费类型，取值PayOnDemand或者PrePay。
         */
        @JSONField(name="PayType")
        String getPayType();

        @JSONField(name="PayType")
        void setPayType(final String payType);

        /**
         * 企业资源组ID。
         */
        @JSONField(name="ResourceGroupId")
        String getResourceGroupId();

        @JSONField(name="ResourceGroupId")
        void setResourceGroupId(final String resourceGroupId);

        /**
         * IP版本，可以设置为ipv4或者ipv6。
         */
        @JSONField(name="AddressIPVersion")
        String getAddressIPVersion();

        @JSONField(name="AddressIPVersion")
        void setAddressIPVersion(final String addressIPVersion);
    }

    interface LoadBalancers {
        @JSONField(name="LoadBalancer")
        LoadBalancer[] getLoadBalancer();

        @JSONField(name="LoadBalancer")
        void setLoadBalancer(final LoadBalancer[] loadBalancer);
    }

    interface DescribeLoadBalancersResponse extends SLBAPIResponse, Pageable {
        @JSONField(name="LoadBalancers")
        LoadBalancers getLoadBalancers();

        @JSONField(name="LoadBalancers")
        void setLoadBalancers(final LoadBalancers loadBalancers);
    }

    @RpcBuilder
    interface DescribeLoadBalancersBuilder {
        @QueryParam("RegionId")
        DescribeLoadBalancersBuilder regionId(final String regionId);

        @QueryParam("ServerId")
        DescribeLoadBalancersBuilder serverId(final String serverId);

        @QueryParam("AddressIPVersion")
        DescribeLoadBalancersBuilder addressIPVersion(final String addressIPVersion);

        /**
         * 负载均衡实例状态：
            inactive: 此状态的实例监听不会再转发流量。
            active: 实例创建后，默认状态为active。
            locked: 实例已经被锁定。
         */
        @QueryParam("LoadBalancerStatus")
        DescribeLoadBalancersBuilder loadBalancerStatus(final String loadBalancerStatus);

        @QueryParam("LoadBalancerId")
        DescribeLoadBalancersBuilder loadBalancerId(final String loadBalancerId);

        @QueryParam("LoadBalancerName")
        DescribeLoadBalancersBuilder LoadBalancerName(final String loadBalancerName);

        @QueryParam("ServerIntranetAddress")
        DescribeLoadBalancersBuilder serverIntranetAddress(final String serverIntranetAddress);

        /**
         * 负载均衡实例的网络类型。

            取值：intranet或internet。

            internet：创建公网负载均衡实例后，系统会分配一个公网IP地址，可以转发公网请求。
            intranet：创建内网负载均衡实例后，系统会分配一个内网IP地址，仅可转发内网请求。
         */
        @QueryParam("AddressType")
        DescribeLoadBalancersBuilder addressType(final String addressType);

        @QueryParam("InternetChargeType")
        DescribeLoadBalancersBuilder internetChargeType(final String internetChargeType);

        @QueryParam("VpcId")
        DescribeLoadBalancersBuilder vpcId(final String vpcId);

        @QueryParam("VSwitchId")
        DescribeLoadBalancersBuilder vSwitchId(final String vSwitchId);

        @QueryParam("NetworkType")
        DescribeLoadBalancersBuilder networkType(final String networkType);

        @QueryParam("Address")
        DescribeLoadBalancersBuilder address(final String address);

        @QueryParam("MasterZoneId")
        DescribeLoadBalancersBuilder masterZoneId(final String masterZoneId);

        @QueryParam("SlaveZoneId")
        DescribeLoadBalancersBuilder slaveZoneId(final String slaveZoneId);

        @QueryParam("Tags")
        DescribeLoadBalancersBuilder tags(final String tags);

        @QueryParam("PayType")
        DescribeLoadBalancersBuilder payType(final String payType);

        @QueryParam("ResourceGroupId")
        DescribeLoadBalancersBuilder resourceGroupId(final String resourceGroupId);

        @QueryParam("PageNumber")
        DescribeLoadBalancersBuilder pageNumber(final Integer pageNumber);

        @QueryParam("PageSize")
        DescribeLoadBalancersBuilder pageSize(final Integer pageSize);

        @GET
        @ConstParams({"Action", "DescribeLoadBalancers"})
        @Consumes(MediaType.APPLICATION_JSON)
        Observable<DescribeLoadBalancersResponse> call();
    }

    // https://help.aliyun.com/document_detail/27582.html?spm=a2c4g.11186623.6.699.ecfd693cxFMAFY
    public DescribeLoadBalancersBuilder describeLoadBalancers();

    interface SetLoadBalancerNameResponse extends SLBAPIResponse {
    }

    @RpcBuilder
    interface SetLoadBalancerNameBuilder {
        @QueryParam("RegionId")
        SetLoadBalancerNameBuilder regionId(final String regionId);

        @QueryParam("LoadBalancerId")
        SetLoadBalancerNameBuilder loadBalancerId(final String loadBalancerId);

        @QueryParam("LoadBalancerName")
        SetLoadBalancerNameBuilder loadBalancerName(final String loadBalancerName);

        @GET
        @ConstParams({"Action", "SetLoadBalancerName"})
        @Consumes(MediaType.APPLICATION_JSON)
        Observable<SetLoadBalancerNameResponse> call();
    }

    // https://help.aliyun.com/document_detail/27581.html?spm=a2c4g.11186623.6.700.d02d673cWfXpEP
    public SetLoadBalancerNameBuilder setLoadBalancerName();

    interface SetLoadBalancerStatusResponse extends SLBAPIResponse {
    }

    @RpcBuilder
    interface SetLoadBalancerStatusBuilder {
        @QueryParam("RegionId")
        SetLoadBalancerStatusBuilder regionId(final String regionId);

        @QueryParam("LoadBalancerId")
        SetLoadBalancerStatusBuilder loadBalancerId(final String loadBalancerId);

        @QueryParam("LoadBalancerStatus")
        SetLoadBalancerStatusBuilder loadBalancerStatus(final String loadBalancerStatus);

        @GET
        @ConstParams({"Action", "SetLoadBalancerStatus"})
        @Consumes(MediaType.APPLICATION_JSON)
        Observable<SetLoadBalancerStatusResponse> call();
    }

    // https://help.aliyun.com/document_detail/27580.html?spm=a2c4g.11186623.6.701.18436d67SWaQRd
    public SetLoadBalancerStatusBuilder setLoadBalancerStatus();

    interface ModifyLoadBalancerInternetSpecResponse extends SLBAPIResponse {
        @JSONField(name="OrderId")
        Long getOrderId();

        @JSONField(name="OrderId")
        void setOrderId(final Long orderId);
    }

    @RpcBuilder
    interface ModifyLoadBalancerInternetSpecBuilder {
        @QueryParam("RegionId")
        ModifyLoadBalancerInternetSpecBuilder regionId(final String regionId);

        @QueryParam("LoadBalancerId")
        ModifyLoadBalancerInternetSpecBuilder loadBalancerId(final String loadBalancerId);

        @QueryParam("InternetChargeType")
        ModifyLoadBalancerInternetSpecBuilder internetChargeType(final String internetChargeType);

        @QueryParam("Bandwidth")
        ModifyLoadBalancerInternetSpecBuilder bandwidth(final Integer bandwidth);

        @QueryParam("AutoPay")
        ModifyLoadBalancerInternetSpecBuilder autoPay(final Boolean autoPay);

        @GET
        @ConstParams({"Action", "ModifyLoadBalancerInternetSpec"})
        @Consumes(MediaType.APPLICATION_JSON)
        Observable<ModifyLoadBalancerInternetSpecResponse> call();
    }

    // https://help.aliyun.com/document_detail/27578.html?spm=a2c4g.11186623.6.702.256d7df4v1o4jn
    public ModifyLoadBalancerInternetSpecBuilder modifyLoadBalancerInternetSpec();

    interface SetLoadBalancerDeleteProtectionResponse extends SLBAPIResponse {
    }

    @RpcBuilder
    interface SetLoadBalancerDeleteProtectionBuilder {
        @QueryParam("RegionId")
        SetLoadBalancerDeleteProtectionBuilder regionId(final String regionId);

        @QueryParam("LoadBalancerId")
        SetLoadBalancerDeleteProtectionBuilder loadBalancerId(final String loadBalancerId);

        @QueryParam("DeleteProtection")
        SetLoadBalancerDeleteProtectionBuilder deleteProtection(final String deleteProtection);

        @QueryParam("OwnerAccount")
        SetLoadBalancerDeleteProtectionBuilder ownerAccount(final String ownerAccount);

        @GET
        @ConstParams({"Action", "SetLoadBalancerDeleteProtection"})
        @Consumes(MediaType.APPLICATION_JSON)
        Observable<SetLoadBalancerDeleteProtectionResponse> call();
    }

    // https://help.aliyun.com/document_detail/122674.html?spm=a2c4g.11186623.6.703.332b78a7NdfVVh
    public SetLoadBalancerDeleteProtectionBuilder setLoadBalancerDeleteProtection();

    interface CreateLoadBalancerHTTPListenerResponse extends SLBAPIResponse {
    }

    @RpcBuilder
    interface CreateLoadBalancerHTTPListenerBuilder {
        @QueryParam("RegionId")
        CreateLoadBalancerHTTPListenerBuilder regionId(final String regionId);

        @QueryParam("LoadBalancerId")
        CreateLoadBalancerHTTPListenerBuilder loadBalancerId(final String loadBalancerId);

        /**
         * 是否开启健康检查。
            取值：on | off。
         */
        @QueryParam("HealthCheck")
        CreateLoadBalancerHTTPListenerBuilder healthCheck(final String healthCheck);

        @QueryParam("ListenerPort")
        CreateLoadBalancerHTTPListenerBuilder listenerPort(final int listenerPort);

        /**
         * 是否开启会话保持。
            取值：on | off。
         */
        @QueryParam("StickySession")
        CreateLoadBalancerHTTPListenerBuilder stickySession(final String stickySession);

        /**
         * cookie的处理方式。取值：

            insert：植入Cookie。
            客户端第一次访问时，负载均衡会在返回请求中植入Cookie（即在HTTP/HTTPS响应报文中插入SERVERID），下次客户端携带此Cookie访问，负载均衡服务会将请求定向转发给之前记录到的后端服务器上。

            server：重写Cookie。
            负载均衡发现用户自定义了Cookie，将会对原来的Cookie进行重写，下次客户端携带新的Cookie访问，负载均衡服务会将请求定向转发给之前记录到的后端服务器。
            说明 当StickySession的值为on时，必须指定该参数。
         */
        @QueryParam("StickySessionType")
        CreateLoadBalancerHTTPListenerBuilder stickySessionType(final String stickySessionType);

        /**
         * 监听的带宽峰值，取值：
            -1：不限制带宽峰值。
            1~5120：监听的带宽峰值，所有监听的带宽峰值之和不能超过实例的带宽峰值。
         */
        @QueryParam("Bandwidth")
        CreateLoadBalancerHTTPListenerBuilder bandwidth(final int bandwidth);

        /**
         * 负载均衡实例后端使用的端口。
            取值：1~65535。
            说明 如果不使用服务器组（不指定VServerGroupId参数），则该参数必选。
         */
        @QueryParam("BackendServerPort")
        CreateLoadBalancerHTTPListenerBuilder backendServerPort(final int backendServerPort);

        /**
         * 是否开启通过X-Forwarded-For头字段获取来访者真实 IP。
            取值为on。
         */
        @QueryParam("XForwardedFor")
        CreateLoadBalancerHTTPListenerBuilder xForwardedFor(final String xForwardedFor);

        /**
         * 调度算法。取值：
            wrr（默认值）：权重值越高的后端服务器，被轮询到的次数（概率）也越高。
            wlc：除了根据每台后端服务器设定的权重值来进行轮询，同时还考虑后端服务器的实际负载（即连接数）。当权重值相同时，当前连接数越小的后端服务器被轮询到的次数（概率）也越高。
            rr：按照访问顺序依次将外部请求依序分发到后端服务器。
         */
        @QueryParam("Scheduler")
        CreateLoadBalancerHTTPListenerBuilder scheduler(final String scheduler);

        /**
         * Cookie超时时间。
            取值：1~86400（秒）。
            说明 当StickySession为on且StickySessionType为insert时，该参数必选。
         */
        @QueryParam("CookieTimeout")
        CreateLoadBalancerHTTPListenerBuilder cookieTimeout(final int cookieTimeout);

        /**
         * 服务器上配置的Cookie。
            长度为1~200个字符，只能包含ASCII英文字母和数字字符，不能包含逗号、分号或空格，也不能以$开头。
            说明 当StickySession为on且StickySessionType为server时，该参数必选。
         */
        @QueryParam("Cookie")
        CreateLoadBalancerHTTPListenerBuilder cookie(final String cookie);

        /**
         * 用于健康检查的域名，取值：
            $_ip： 后端服务器的私网IP。当指定了IP或该参数未指定时，负载均衡会使用各后端服务器的私网IP当做健康检查使用的域名。
            domain：域名长度为1-80字符，只能包含字母、数字、点号（.）和连字符（-）。
            说明 在HealthCheck值为on时才会有效。
         */
        @QueryParam("HealthCheckDomain")
        CreateLoadBalancerHTTPListenerBuilder healthCheckDomain(final String healthCheckDomain);

        /**
         * 用于健康检查的URI。

            长度限制为1~80，只能使用字母、数字和-/.%?#&amp;这些字符。 URL不能只为/，但必须以/开头。
            说明 在HealthCheck值为on时才会有效。
         */
        @QueryParam("HealthCheckURI")
        CreateLoadBalancerHTTPListenerBuilder healthCheckURI(final String healthCheckURI);

        /**
         * 健康检查连续成功多少次后，将后端服务器的健康检查状态由fail判定为success。

            取值：2~10。
            说明 在HealthCheck值为on时才会有效。
         */
        @QueryParam("HealthyThreshold")
        CreateLoadBalancerHTTPListenerBuilder healthyThreshold(final int healthyThreshold);

        /**
         * 健康检查连续失败多少次后，将后端服务器的健康检查状态由success判定为fail。

            取值：2~10。
            说明 在HealthCheck值为on时才会有效。
         */
        @QueryParam("UnhealthyThreshold")
        CreateLoadBalancerHTTPListenerBuilder unhealthyThreshold(final int unhealthyThreshold);

        /**
         * 接收来自运行状况检查的响应需要等待的时间。如果后端ECS在指定的时间内没有正确响应，则判定为健康检查失败。在HealthCheck值为on时才会有效。

            取值：1~300（秒）。
            说明 如果HealthCHeckTimeout的值小于HealthCheckInterval的值，则HealthCHeckTimeout无效，超时时间为HealthCheckInterval的值。
         */
        @QueryParam("HealthCheckTimeout")
        CreateLoadBalancerHTTPListenerBuilder healthCheckTimeout(final int healthCheckTimeout);

        /**
         * 健康检查的后端服务器的端口。

            取值： 1~65535。
            说明 在HealthCheck值为on时才会有效。
         */
        @QueryParam("HealthCheckConnectPort")
        CreateLoadBalancerHTTPListenerBuilder healthCheckConnectPort(final int healthCheckConnectPort);

        /**
         * 健康检查的时间间隔。

            取值： 1~50（秒）。
            说明 在HealthCheck值为on时才会有效。
         */
        @QueryParam("HealthCheckInterval")
        CreateLoadBalancerHTTPListenerBuilder healthCheckInterval(final int healthCheckInterval);

        /**
         * 健康检查正常的HTTP状态码，多个状态码用逗号分隔。

            默认值为http_2xx。
            取值：http_2xx | http_3xx | http_4xx | http_5xx。
            说明 在HealthCheck值为on时才会有效。
         */
        @QueryParam("HealthCheckHttpCode")
        CreateLoadBalancerHTTPListenerBuilder healthCheckHttpCode(final String healthCheckHttpCode);

        /**
         * 虚拟服务器组ID。
         */
        @QueryParam("VServerGroupId")
        CreateLoadBalancerHTTPListenerBuilder vServerGroupId(final String vServerGroupId);

        /**
         * 是否通过SLB-IP头字段获取客户端请求的真实IP。

            取值：on | off（默认值）。
         */
        @QueryParam("XForwardedFor_SLBIP")
        CreateLoadBalancerHTTPListenerBuilder xForwardedFor_SLBIP(final String xForwardedFor_SLBIP);

        /**
         * 是否通过SLB-ID头字段获取负载均衡实例ID。

            取值：on | off（默认值）。
         */
        @QueryParam("XForwardedFor_SLBID")
        CreateLoadBalancerHTTPListenerBuilder xForwardedFor_SLBID(final String xForwardedFor_SLBID);

        /**
         * 是否通过X-Forwarded-Proto头字段获取负载均衡实例的监听协议。

            取值：on | off（默认值）。
         */
        @QueryParam("XForwardedFor_proto")
        CreateLoadBalancerHTTPListenerBuilder xForwardedFor_proto(final String xForwardedFor_proto);

        /**
         * 是否开启Gzip压缩，对特定文件类型进行压缩。默认值为on。

            取值：on | off。
         */
        @QueryParam("Gzip")
        CreateLoadBalancerHTTPListenerBuilder gzip(final String gzip);

        /**
         * 监听绑定的访问策略组ID。

            当AclStatus参数的值为on时，该参数必选。
         */
        @QueryParam("AclId")
        CreateLoadBalancerHTTPListenerBuilder aclId(final String aclId);

        /**
         * 访问控制类型：

            white： 仅转发来自所选访问控制策略组中设置的IP地址或地址段的请求，白名单适用于应用只允许特定IP访问的场景。
            设置白名单存在一定业务风险。
            一旦设置白名单，就只有白名单中的IP可以访问负载均衡监听。
            如果开启了白名单访问，但访问策略组中没有添加任何IP，则负载均衡监听不会转发请求。

            black： 来自所选访问控制策略组中设置的IP地址或地址段的所有请求都不会转发，黑名单适用于应用只限制某些特定IP访问的场景。
            如果开启了黑名单访问，但访问策略组中没有添加任何IP，则负载均衡监听会转发全部请求。
            当AclStatus参数的值为on时，该参数有效。
         */
        @QueryParam("AclType")
        CreateLoadBalancerHTTPListenerBuilder aclType(final String aclType);

        /**
         * 是否开启访问控制功能。

            取值：on | off（默认值）。
         */
        @QueryParam("AclStatus")
        CreateLoadBalancerHTTPListenerBuilder aclStatus(final String aclStatus);

        /**
         * 设置监听的描述信息。

            长度限制为1-80个字符，允许包含字母、数字、短横线（-）、正斜杠（/）、点号（.）和下划线（_）等字符。支持中文描述。
         */
        @QueryParam("Description")
        CreateLoadBalancerHTTPListenerBuilder description(final String description);

        /**
         * 是否开启HTTP至HTTPS的转发。

            取值：on | off。
         */
        @QueryParam("ListenerForward")
        CreateLoadBalancerHTTPListenerBuilder listenerForward(final String listenerForward);

        /**
         * HTTP至HTTPS的监听转发端口。
         */
        @QueryParam("ForwardPort")
        CreateLoadBalancerHTTPListenerBuilder forwardPort(final int forwardPort);

        /**
         * 指定连接空闲超时时间，取值范围为1~60秒，默认值为15秒。

            在超时时间内一直没有访问请求，负载均衡会暂时中断当前连接，直到一下次请求来临时重新建立新的连接。
         */
        @QueryParam("IdleTimeout")
        CreateLoadBalancerHTTPListenerBuilder idleTimeout(final int idleTimeout);

        /**
         * 指定请求超时时间，取值范围为1~180秒，默认值为60秒。

            在超时时间内后端服务器一直没有响应，负载均衡将放弃等待，给客户端返回 HTTP 504 错误码。
         */
        @QueryParam("RequestTimeout")
        CreateLoadBalancerHTTPListenerBuilder requestTimeout(final int requestTimeout);

        @GET
        @ConstParams({"Action", "CreateLoadBalancerHTTPListener"})
        @Consumes(MediaType.APPLICATION_JSON)
        Observable<CreateLoadBalancerHTTPListenerResponse> call();
    }

    // https://help.aliyun.com/document_detail/27592.html?spm=a2c4g.11186623.6.713.6be528d3HFwHR0
    public CreateLoadBalancerHTTPListenerBuilder createLoadBalancerHTTPListener();

    interface Rule {
        @JSONField(name="RuleId")
        String getRuleId();

        @JSONField(name="RuleId")
        void setRuleId(final String ruleId);

        @JSONField(name="RuleName")
        String getRuleName();

        @JSONField(name="RuleName")
        void setRuleName(final String ruleName);

        @JSONField(name="Domain")
        String getDomain();

        @JSONField(name="Domain")
        void setDomain(final String domain);

        @JSONField(name="Url")
        String getUrl();

        @JSONField(name="Url")
        void setUrl(final String url);

        @JSONField(name="VServerGroupId")
        String getVServerGroupId();

        @JSONField(name="VServerGroupId")
        void setVServerGroupId(final String vServerGroupId);
    }

    interface Rules {
        @JSONField(name="Rule")
        Rule[] getRule();

        @JSONField(name="Rule")
        void setRule(final Rule[] rule);
    }

    interface DescribeLoadBalancerHTTPListenerAttributeResponse extends SLBAPIResponse {
        @JSONField(name="ListenerPort")
        Integer getListenerPort();

        @JSONField(name="ListenerPort")
        void setListenerPort(final Integer listenerPort);

        @JSONField(name="BackendServerPort")
        Integer getBackendServerPort();

        @JSONField(name="BackendServerPort")
        void setBackendServerPort(final Integer backendServerPort);

        @JSONField(name="Bandwidth")
        Integer getBandwidth();

        @JSONField(name="Bandwidth")
        void setBandwidth(final Integer bandwidth);

        @JSONField(name="Status")
        String getStatus();

        @JSONField(name="Status")
        void setStatus(final String status);

        @JSONField(name="SecurityStatus")
        String getSecurityStatus();

        @JSONField(name="SecurityStatus")
        void setSecurityStatus(final String securityStatus);

        @JSONField(name="XForwardedFor")
        String getXForwardedFor();

        @JSONField(name="XForwardedFor")
        void setXForwardedFor(final String xForwardedFor);

        @JSONField(name="Scheduler")
        String getScheduler();

        @JSONField(name="Scheduler")
        void setScheduler(final String scheduler);

        @JSONField(name="StickySession")
        String getStickySession();

        @JSONField(name="StickySession")
        void setStickySession(final String stickySession);

        @JSONField(name="StickySessionType")
        String getStickySessionType();

        @JSONField(name="StickySessionType")
        void setStickySessionType(final String stickySessionType);

        @JSONField(name="CookieTimeout")
        Integer getCookieTimeout();

        @JSONField(name="CookieTimeout")
        void setCookieTimeout(final Integer cookieTimeout);

        @JSONField(name="Cookie")
        String getCookie();

        @JSONField(name="Cookie")
        void setCookie(final String cookie);

        @JSONField(name="HealthCheck")
        String getHealthCheck();

        @JSONField(name="HealthCheck")
        void setHealthCheck(final String healthCheck);

        @JSONField(name="HealthCheckDomain")
        String getHealthCheckDomain();

        @JSONField(name="HealthCheckDomain")
        void setHealthCheckDomain(final String healthCheckDomain);

        @JSONField(name="HealthCheckURI")
        String getHealthCheckURI();

        @JSONField(name="HealthCheckURI")
        void setHealthCheckURI(final String healthCheckURI);

        @JSONField(name="HealthyThreshold")
        Integer getHealthyThreshold();

        @JSONField(name="HealthyThreshold")
        void setHealthyThreshold(final Integer healthyThreshold);

        @JSONField(name="UnhealthyThreshold")
        Integer getUnhealthyThreshold();

        @JSONField(name="UnhealthyThreshold")
        void setUnhealthyThreshold(final Integer unhealthyThreshold);

        @JSONField(name="HealthCheckTimeout")
        Integer getHealthCheckTimeout();

        @JSONField(name="HealthCheckTimeout")
        void setHealthCheckTimeout(final Integer healthCheckTimeout);

        @JSONField(name="HealthCheckInterval")
        Integer getHealthCheckInterval();

        @JSONField(name="HealthCheckInterval")
        void setHealthCheckInterval(final Integer healthCheckInterval);

        @JSONField(name="HealthCheckConnectPort")
        Integer getHealthCheckConnectPort();

        @JSONField(name="HealthCheckConnectPort")
        void setHealthCheckConnectPort(final Integer healthCheckConnectPort);

        @JSONField(name="HealthCheckHttpCode")
        String getHealthCheckHttpCode();

        @JSONField(name="HealthCheckHttpCode")
        void setHealthCheckHttpCode(final String healthCheckHttpCode);

        @JSONField(name="VServerGroupId")
        String getVServerGroupId();

        @JSONField(name="VServerGroupId")
        void setVServerGroupId(final String vServerGroupId);

        @JSONField(name="Gzip")
        String getGzip();

        @JSONField(name="Gzip")
        void setGzip(final String gzip);

        @JSONField(name="XForwardedFor_SLBIP")
        String getXForwardedFor_SLBIP();

        @JSONField(name="XForwardedFor_SLBIP")
        void setXForwardedFor_SLBIP(final String xForwardedFor_SLBIP);

        @JSONField(name="XForwardedFor_SLBID")
        String getXForwardedFor_SLBID();

        @JSONField(name="XForwardedFor_SLBID")
        void setXForwardedFor_SLBID(final String xForwardedFor_SLBID);

        @JSONField(name="XForwardedFor_proto")
        String getXForwardedFor_proto();

        @JSONField(name="XForwardedFor_proto")
        void setXForwardedFor_proto(final String xForwardedFor_proto);

        @JSONField(name="AclId")
        String getAclId();

        @JSONField(name="AclId")
        void setAclId(final String aclId);

        @JSONField(name="AclType")
        String getAclType();

        @JSONField(name="AclType")
        void setAclType(final String aclType);

        @JSONField(name="AclStatus")
        String getAclStatus();

        @JSONField(name="AclStatus")
        void setAclStatus(final String aclStatus);

        @JSONField(name="ListenerForward")
        String getListenerForward();

        @JSONField(name="ListenerForward")
        void setListenerForward(final String listenerForward);

        @JSONField(name="ForwardPort")
        Integer getForwardPort();

        @JSONField(name="ForwardPort")
        void setForwardPort(final Integer forwardPort);

        @JSONField(name="RequestTimeout")
        Integer getRequestTimeout();

        @JSONField(name="RequestTimeout")
        void setRequestTimeout(final Integer requestTimeout);

        @JSONField(name="IdleTimeout")
        Integer getIdleTimeout();

        @JSONField(name="IdleTimeout")
        void setIdleTimeout(final Integer idleTimeout);

        @JSONField(name="Description")
        String getDescription();

        @JSONField(name="Description")
        void setDescription(final String description);

        @JSONField(name="Rules")
        Rules getRules();

        @JSONField(name="Rules")
        void setRules(final Rules rules);
    }

    @RpcBuilder
    interface DescribeLoadBalancerHTTPListenerAttributeBuilder {
        @QueryParam("RegionId")
        DescribeLoadBalancerHTTPListenerAttributeBuilder regionId(final String regionId);

        @QueryParam("LoadBalancerId")
        DescribeLoadBalancerHTTPListenerAttributeBuilder loadBalancerId(final String loadBalancerId);

        @QueryParam("ListenerPort")
        DescribeLoadBalancerHTTPListenerAttributeBuilder listenerPort(final int listenerPort);

        @GET
        @ConstParams({"Action", "DescribeLoadBalancerHTTPListenerAttribute"})
        @Consumes(MediaType.APPLICATION_JSON)
        Observable<DescribeLoadBalancerHTTPListenerAttributeResponse> call();
    }

    // https://help.aliyun.com/document_detail/27606.html?spm=a2c4g.11186623.6.714.612318ca5glapE
    public DescribeLoadBalancerHTTPListenerAttributeBuilder describeLoadBalancerHTTPListenerAttribute();

    interface SetLoadBalancerHTTPListenerAttributeResponse extends SLBAPIResponse {
    }

    @RpcBuilder
    interface SetLoadBalancerHTTPListenerAttributeBuilder {
        /**
         * 是否必选:
         * 是
         * 示例值:
         * cn-hangzhou
         * 描述:
         * 负载均衡实例的地域。
            您可以从地域和可用区列表或通过调用DescribeRegions接口查询地域ID。
         */
        @QueryParam("RegionId")
        SetLoadBalancerHTTPListenerAttributeBuilder regionId(final String regionId);

        /**
         * 是否必选:
         * 是
         * 示例值:
         * lb-bp1qjwo61pqz3ah*****
         * 描述:
         * 负载均衡实例的ID。
         */
        @QueryParam("LoadBalancerId")
        SetLoadBalancerHTTPListenerAttributeBuilder loadBalancerId(final String loadBalancerId);

        /**
         * 是否开启健康检查。
            取值：on | off。
         */
        @QueryParam("HealthCheck")
        SetLoadBalancerHTTPListenerAttributeBuilder healthCheck(final String healthCheck);

        /**
         * 描述:
         * 负载均衡实例前端使用的端口。
            取值：1-65535。
         * @param listenerPort
         * @return
         */
        @QueryParam("ListenerPort")
        SetLoadBalancerHTTPListenerAttributeBuilder listenerPort(final int listenerPort);

        /**
         * 是否开启会话保持。
            取值：on | off。
         */
        @QueryParam("StickySession")
        SetLoadBalancerHTTPListenerAttributeBuilder stickySession(final String stickySession);

        /**
         * cookie的处理方式。取值：

            insert：植入Cookie。
            客户端第一次访问时，负载均衡会在返回请求中植入Cookie（即在HTTP/HTTPS响应报文中插入SERVERID），下次客户端携带此Cookie访问，负载均衡服务会将请求定向转发给之前记录到的后端服务器上。

            server：重写Cookie。
            负载均衡发现用户自定义了Cookie，将会对原来的Cookie进行重写，下次客户端携带新的Cookie访问，负载均衡服务会将请求定向转发给之前记录到的后端服务器。
            说明 当StickySession的值为on时，必须指定该参数。
         */
        @QueryParam("StickySessionType")
        SetLoadBalancerHTTPListenerAttributeBuilder stickySessionType(final String stickySessionType);

        /**
         * 监听的带宽峰值，取值：
            -1：不限制带宽峰值。
            1~5120：监听的带宽峰值，所有监听的带宽峰值之和不能超过实例的带宽峰值。
         */
        @QueryParam("Bandwidth")
        SetLoadBalancerHTTPListenerAttributeBuilder bandwidth(final int bandwidth);

        /**
         * 负载均衡实例后端使用的端口。
            取值：1~65535。
            说明 如果不使用服务器组（不指定VServerGroupId参数），则该参数必选。
         */
//        @QueryParam("BackendServerPort")
//        SetLoadBalancerHTTPListenerAttributeBuilder backendServerPort(final int backendServerPort);

        /**
         * 是否开启通过X-Forwarded-For头字段获取来访者真实 IP。
            取值为on。
         */
        @QueryParam("XForwardedFor")
        SetLoadBalancerHTTPListenerAttributeBuilder xForwardedFor(final String xForwardedFor);

        /**
         * 调度算法。取值：
            wrr（默认值）：权重值越高的后端服务器，被轮询到的次数（概率）也越高。
            wlc：除了根据每台后端服务器设定的权重值来进行轮询，同时还考虑后端服务器的实际负载（即连接数）。当权重值相同时，当前连接数越小的后端服务器被轮询到的次数（概率）也越高。
            rr：按照访问顺序依次将外部请求依序分发到后端服务器。
         */
        @QueryParam("Scheduler")
        SetLoadBalancerHTTPListenerAttributeBuilder scheduler(final String scheduler);

        /**
         * Cookie超时时间。
            取值：1~86400（秒）。
            说明 当StickySession为on且StickySessionType为insert时，该参数必选。
         */
        @QueryParam("CookieTimeout")
        SetLoadBalancerHTTPListenerAttributeBuilder cookieTimeout(final int cookieTimeout);

        /**
         * 服务器上配置的Cookie。
            长度为1~200个字符，只能包含ASCII英文字母和数字字符，不能包含逗号、分号或空格，也不能以$开头。
            说明 当StickySession为on且StickySessionType为server时，该参数必选。
         */
        @QueryParam("Cookie")
        SetLoadBalancerHTTPListenerAttributeBuilder cookie(final String cookie);

        /**
         * 用于健康检查的域名，取值：
            $_ip： 后端服务器的私网IP。当指定了IP或该参数未指定时，负载均衡会使用各后端服务器的私网IP当做健康检查使用的域名。
            domain：域名长度为1-80字符，只能包含字母、数字、点号（.）和连字符（-）。
            说明 在HealthCheck值为on时才会有效。
         */
        @QueryParam("HealthCheckDomain")
        SetLoadBalancerHTTPListenerAttributeBuilder healthCheckDomain(final String healthCheckDomain);

        /**
         * 用于健康检查的URI。

            长度限制为1~80，只能使用字母、数字和-/.%?#&amp;这些字符。 URL不能只为/，但必须以/开头。
            说明 在HealthCheck值为on时才会有效。
         */
        @QueryParam("HealthCheckURI")
        SetLoadBalancerHTTPListenerAttributeBuilder healthCheckURI(final String healthCheckURI);

        /**
         * 健康检查连续成功多少次后，将后端服务器的健康检查状态由fail判定为success。

            取值：2~10。
            说明 在HealthCheck值为on时才会有效。
         */
        @QueryParam("HealthyThreshold")
        SetLoadBalancerHTTPListenerAttributeBuilder healthyThreshold(final int healthyThreshold);

        /**
         * 健康检查连续失败多少次后，将后端服务器的健康检查状态由success判定为fail。

            取值：2~10。
            说明 在HealthCheck值为on时才会有效。
         */
        @QueryParam("UnhealthyThreshold")
        SetLoadBalancerHTTPListenerAttributeBuilder unhealthyThreshold(final int unhealthyThreshold);

        /**
         * 接收来自运行状况检查的响应需要等待的时间。如果后端ECS在指定的时间内没有正确响应，则判定为健康检查失败。在HealthCheck值为on时才会有效。

            取值：1~300（秒）。
            说明 如果HealthCHeckTimeout的值小于HealthCheckInterval的值，则HealthCHeckTimeout无效，超时时间为HealthCheckInterval的值。
         */
        @QueryParam("HealthCheckTimeout")
        SetLoadBalancerHTTPListenerAttributeBuilder healthCheckTimeout(final int healthCheckTimeout);

        /**
         * 健康检查的后端服务器的端口。

            取值： 1~65535。
            说明 在HealthCheck值为on时才会有效。
         */
        @QueryParam("HealthCheckConnectPort")
        SetLoadBalancerHTTPListenerAttributeBuilder healthCheckConnectPort(final int healthCheckConnectPort);

        /**
         * 健康检查的时间间隔。

            取值： 1~50（秒）。
            说明 在HealthCheck值为on时才会有效。
         */
        @QueryParam("HealthCheckInterval")
        SetLoadBalancerHTTPListenerAttributeBuilder healthCheckInterval(final int healthCheckInterval);

        /**
         * 健康检查正常的HTTP状态码，多个状态码用逗号分隔。

            默认值为http_2xx。
            取值：http_2xx | http_3xx | http_4xx | http_5xx。
            说明 在HealthCheck值为on时才会有效。
         */
        @QueryParam("HealthCheckHttpCode")
        SetLoadBalancerHTTPListenerAttributeBuilder healthCheckHttpCode(final String healthCheckHttpCode);

        /**
         * 是否使用虚拟服务器组。
            取值：on|off。
         */
        @QueryParam("VServerGroup")
        SetLoadBalancerHTTPListenerAttributeBuilder vServerGroup(final String vServerGroup);

        /**
         * 虚拟服务器组ID。
         */
        @QueryParam("VServerGroupId")
        SetLoadBalancerHTTPListenerAttributeBuilder vServerGroupId(final String vServerGroupId);

        /**
         * 是否通过SLB-IP头字段获取客户端请求的真实IP。

            取值：on | off（默认值）。
         */
        @QueryParam("XForwardedFor_SLBIP")
        SetLoadBalancerHTTPListenerAttributeBuilder xForwardedFor_SLBIP(final String xForwardedFor_SLBIP);

        /**
         * 是否通过SLB-ID头字段获取负载均衡实例ID。

            取值：on | off（默认值）。
         */
        @QueryParam("XForwardedFor_SLBID")
        SetLoadBalancerHTTPListenerAttributeBuilder xForwardedFor_SLBID(final String xForwardedFor_SLBID);

        /**
         * 是否通过X-Forwarded-Proto头字段获取负载均衡实例的监听协议。

            取值：on | off（默认值）。
         */
        @QueryParam("XForwardedFor_proto")
        SetLoadBalancerHTTPListenerAttributeBuilder xForwardedFor_proto(final String xForwardedFor_proto);

        /**
         * 是否开启Gzip压缩，对特定文件类型进行压缩。默认值为on。

            取值：on | off。
         */
        @QueryParam("Gzip")
        SetLoadBalancerHTTPListenerAttributeBuilder gzip(final String gzip);

        /**
         * 监听绑定的访问策略组ID。

            当AclStatus参数的值为on时，该参数必选。
         */
        @QueryParam("AclId")
        SetLoadBalancerHTTPListenerAttributeBuilder aclId(final String aclId);

        /**
         * 访问控制类型：

            white： 仅转发来自所选访问控制策略组中设置的IP地址或地址段的请求，白名单适用于应用只允许特定IP访问的场景。
            设置白名单存在一定业务风险。
            一旦设置白名单，就只有白名单中的IP可以访问负载均衡监听。
            如果开启了白名单访问，但访问策略组中没有添加任何IP，则负载均衡监听不会转发请求。

            black： 来自所选访问控制策略组中设置的IP地址或地址段的所有请求都不会转发，黑名单适用于应用只限制某些特定IP访问的场景。
            如果开启了黑名单访问，但访问策略组中没有添加任何IP，则负载均衡监听会转发全部请求。
            当AclStatus参数的值为on时，该参数有效。
         */
        @QueryParam("AclType")
        SetLoadBalancerHTTPListenerAttributeBuilder aclType(final String aclType);

        /**
         * 是否开启访问控制功能。

            取值：on | off（默认值）。
         */
        @QueryParam("AclStatus")
        SetLoadBalancerHTTPListenerAttributeBuilder aclStatus(final String aclStatus);

        /**
         * 设置监听的描述信息。

            长度限制为1-80个字符，允许包含字母、数字、短横线（-）、正斜杠（/）、点号（.）和下划线（_）等字符。支持中文描述。
         */
        @QueryParam("Description")
        SetLoadBalancerHTTPListenerAttributeBuilder description(final String description);

        /**
         * 是否开启HTTP至HTTPS的转发。

            取值：on | off。
         */
        @QueryParam("ListenerForward")
        SetLoadBalancerHTTPListenerAttributeBuilder listenerForward(final String listenerForward);

        /**
         * HTTP至HTTPS的监听转发端口。
         */
        @QueryParam("ForwardPort")
        SetLoadBalancerHTTPListenerAttributeBuilder forwardPort(final int forwardPort);

        /**
         * 指定连接空闲超时时间，取值范围为1~60秒，默认值为15秒。

            在超时时间内一直没有访问请求，负载均衡会暂时中断当前连接，直到一下次请求来临时重新建立新的连接。
         */
        @QueryParam("IdleTimeout")
        SetLoadBalancerHTTPListenerAttributeBuilder idleTimeout(final int idleTimeout);

        /**
         * 指定请求超时时间，取值范围为1~180秒，默认值为60秒。

            在超时时间内后端服务器一直没有响应，负载均衡将放弃等待，给客户端返回 HTTP 504 错误码。
         */
        @QueryParam("RequestTimeout")
        SetLoadBalancerHTTPListenerAttributeBuilder requestTimeout(final int requestTimeout);

        @GET
        @ConstParams({"Action", "SetLoadBalancerHTTPListenerAttribute"})
        @Consumes(MediaType.APPLICATION_JSON)
        Observable<SetLoadBalancerHTTPListenerAttributeResponse> call();
    }

    // https://help.aliyun.com/document_detail/27602.html?spm=a2c4g.11186623.6.715.61341ed0U0iKiY
    public SetLoadBalancerHTTPListenerAttributeBuilder setLoadBalancerHTTPListenerAttribute();

    interface CreateLoadBalancerHTTPSListenerResponse extends SLBAPIResponse {
    }

    @RpcBuilder
    interface CreateLoadBalancerHTTPSListenerBuilder {
        @QueryParam("RegionId")
        CreateLoadBalancerHTTPSListenerBuilder regionId(final String regionId);

        @QueryParam("LoadBalancerId")
        CreateLoadBalancerHTTPSListenerBuilder loadBalancerId(final String loadBalancerId);

        /**
         *
        负载均衡实例前端使用的端口。
        取值：1-65535。
         */
        @QueryParam("ListenerPort")
        CreateLoadBalancerHTTPSListenerBuilder listenerPort(final int listenerPort);

        /**
         *
        监听的带宽峰值。
        取值：-1 | 1-5120。
        -1：对于按流量计费的公网负载均衡实例，可以将带宽峰值设置为-1，即不限制带宽峰值。
        1-5120(Mbps)： 对于按带宽计费的公网负载均衡实例，可以设置每个监听的带宽峰值，但所有监听的带宽峰值之和不能超过实例的带宽峰值。
         */
        @QueryParam("Bandwidth")
        CreateLoadBalancerHTTPSListenerBuilder bandwidth(final int bandwidth);

        /**
         *
         * 服务器证书的ID。
         */
        @QueryParam("ServerCertificateId")
        CreateLoadBalancerHTTPSListenerBuilder serverCertificateId(final String serverCertificateId);

        /**
         *
        是否开启会话保持。
        取值：on | off。
         */
        @QueryParam("StickySession")
        CreateLoadBalancerHTTPSListenerBuilder stickySession(final String stickySession);

        /**
         *
        是否开启健康检查。
        取值：on | off。
         */
        @QueryParam("HealthCheck")
        CreateLoadBalancerHTTPSListenerBuilder healthCheck(final String healthCheck);

        /**
        *
        负载均衡实例后端使用的端口，取值：1-65535。
        如果不使用服务器组（不指定VServerGroupId），则该参数必选。
        */
        @QueryParam("BackendServerPort")
        CreateLoadBalancerHTTPSListenerBuilder backendServerPort(final int backendServerPort);

        /**
        *
        是否通过X-Forwarded-For获取客户端请求的真实IP。
        取值为on。
        */
        @QueryParam("XForwardedFor")
        CreateLoadBalancerHTTPSListenerBuilder xForwardedFor(final String xForwardedFor);

        /**
        *
        调度算法。取值：
        wrr（默认值）：权重值越高的后端服务器，被轮询到的次数（概率）也越高。
        wlc：除了根据每台后端服务器设定的权重值来进行轮询，同时还考虑后端服务器的实际负载（即连接数）。当权重值相同时，当前连接数越小的后端服务器被轮询到的次数（概率）也越高。
        rr：按照访问顺序依次将外部请求依序分发到后端服务器。
        */
        @QueryParam("Scheduler")
        CreateLoadBalancerHTTPSListenerBuilder scheduler(final String scheduler);

        /**
        *
        Cookie的处理方式。当StickySession的值为on时，必须指定该参数。
        取值：insert | server。
        insert：植入Cookie。
        客户端第一次访问时，负载均衡会在返回请求中植入Cookie（即在HTTP/HTTPS响应报文中插入SERVERID），下次客户端携带此Cookie访问，负载均衡服务会将请求定向转发给之前记录到的后端服务器上。
        server：重写Cookie。
        负载均衡发现用户自定义了Cookie，将会对原来的Cookie进行重写，下次客户端携带新的Cookie访问，负载均衡服务会将请求定向转发给之前记录到的后端服务器。
        */
        @QueryParam("StickySessionType")
        CreateLoadBalancerHTTPSListenerBuilder stickySessionType(final String stickySessionType);

        /**
        *
        Cookie超时时间。
        取值：1-86400（秒）。
        说明 当StickySession为on且StickySessionType为insert时，该参数必选。
        */
        @QueryParam("CookieTimeout")
        CreateLoadBalancerHTTPSListenerBuilder cookieTimeout(final int cookieTimeout);

        /**
        *
        服务器上配置的Cookie。
        长度为1-200，只能包含ASCII英文字母和数字字符，不能包含逗号、分号或空格，也不能以$开头。
        说明 当StickySession为on且StickySessionType为server时，该参数必选。
        */
        @QueryParam("Cookie")
        CreateLoadBalancerHTTPSListenerBuilder cookie(final String cookie);

        /**
        *
        用于健康检查的域名。取值：
        $_ip： 后端服务器的私网IP。当指定了IP或该参数未指定时，负载均衡会使用各后端服务器的私网IP当做健康检查使用的域名。
        domain：域名长度为1~80，只能包含字母、数字、点号（.）和连字符（-）。
        说明 在HealthCheck值为on时才会有效。
        */
        @QueryParam("HealthCheckDomain")
        CreateLoadBalancerHTTPSListenerBuilder healthCheckDomain(final String healthCheckDomain);

        /**
        *
        用于健康检查的URI。
        长度限制为1~80，只能使用字母、数字和-/.%?#&这些字符。 URL不能只为/，但必须以/开头。
        说明 在HealthCheck值为on时才会有效。
        */
        @QueryParam("HealthCheckURI")
        CreateLoadBalancerHTTPSListenerBuilder healthCheckURI(final String healthCheckURI);

        /**
        *
        健康检查连续成功多少次后，将后端服务器的健康检查状态由fail判定为success。
        取值：2-10。
        说明 在HealthCheck值为on时才会有效。
        */
        @QueryParam("HealthyThreshold")
        CreateLoadBalancerHTTPSListenerBuilder healthyThreshold(final int healthyThreshold);

        /**
        *
        健康检查连续失败多少次后，将后端服务器的健康检查状态由success判定为fail。
        取值：2-10。
        说明 在HealthCheck值为on时才会有效。
        */
        @QueryParam("UnhealthyThreshold")
        CreateLoadBalancerHTTPSListenerBuilder unhealthyThreshold(final int unhealthyThreshold);

        /**
        *
        接收来自运行状况检查的响应需要等待的时间。如果后端ECS在指定的时间内没有正确响应，则判定为健康检查失败。在HealthCheck值为on时才会有效。
        取值：1-300（秒）。
        说明
        如果HealthCHeckTimeout的值小于HealthCheckInterval的值，则HealthCHeckTimeout无效，超时时间为HealthCheckInterval的值。
        在HealthCheck值为on时才会有效。
        */
        @QueryParam("HealthCheckTimeout")
        CreateLoadBalancerHTTPSListenerBuilder healthCheckTimeout(final int healthCheckTimeout);

        /**
        *
        健康检查使用的端口。
        取值：1-65535。
        说明 当HealthCheck值为on时才会有效。
        */
        @QueryParam("HealthCheckConnectPort")
        CreateLoadBalancerHTTPSListenerBuilder healthCheckConnectPort(final int healthCheckConnectPort);

        /**
        *
        健康检查的时间间隔。
        取值：1-50（秒）。
        说明 在HealthCheck值为on时才会有效。
        */
        @QueryParam("HealthCheckInterval")
        CreateLoadBalancerHTTPSListenerBuilder healthCheckInterval(final int healthCheckInterval);

        /**
        *
        健康检查正常的HTTP状态码，多个状态码用逗号（,）分割。默认值为http_2xx。
        取值：http_2xx | http_3xx | http_4xx | http_5xx。
        说明 在HealthCheck值为on时才会有效。
        */
        @QueryParam("HealthCheckHttpCode")
        CreateLoadBalancerHTTPSListenerBuilder healthCheckHttpCode(final String healthCheckHttpCode);

        /**
        *
        服务器组ID。
        */
        @QueryParam("VServerGroupId")
        CreateLoadBalancerHTTPSListenerBuilder vServerGroupId(final String vServerGroupId);

        /**
        *
        CA证书ID。
        若既上传CA证书又上传服务器证书，即采用双向认证。
        若用户只上传服务器证书，即为单向认证。
        */
        @QueryParam("CACertificateId")
        CreateLoadBalancerHTTPSListenerBuilder cACertificateId(final String cACertificateId);

        /**
        *
        是否通过SLB-IP头字段获取来访者真实IP。
        取值：on |off （默认值）。
        */
        @QueryParam("XForwardedFor_SLBIP")
        CreateLoadBalancerHTTPSListenerBuilder xForwardedFor_SLBIP(final String xForwardedFor_SLBIP);

        /**
        *
        是否通过SLB-ID头字段获取SLB实例ID。
        取值：on | off（默认值）。
        */
        @QueryParam("XForwardedFor_SLBID")
        CreateLoadBalancerHTTPSListenerBuilder xForwardedFor_SLBID(final String xForwardedFor_SLBID);

        /**
        *
        是否通过X-Forwarded-Proto头字段获取SLB的监听协议。
        取值：on | off（默认值）。
        */
        @QueryParam("XForwardedFor_proto")
        CreateLoadBalancerHTTPSListenerBuilder xForwardedFor_proto(final String xForwardedFor_proto);

        /**
        *
        是否开启Gzip压缩，对特定文件类型进行压缩。
        取值：on（默认值）| off。
        */
        @QueryParam("Gzip")
        CreateLoadBalancerHTTPSListenerBuilder gzip(final String gzip);

        /**
        *
        监听绑定的访问策略组ID。
        说明 当AclStatus参数的值为on时，该参数必选。
        */
        @QueryParam("AclId")
        CreateLoadBalancerHTTPSListenerBuilder aclId(final String aclId);

        /**
        *
        访问控制类型：
        white： 仅转发来自所选访问控制策略组中设置的IP地址或地址段的请求，白名单适用于应用只允许特定IP访问的场景。
        设置白名单存在一定业务风险。
        一旦设置白名单，就只有白名单中的IP可以访问负载均衡监听。
        如果开启了白名单访问，但访问策略组中没有添加任何IP，则负载均衡监听不会转发请求。
        black： 来自所选访问控制策略组中设置的IP地址或地址段的所有请求都不会转发，黑名单适用于应用只限制某些特定IP访问的场景。
        如果开启了黑名单访问，但访问策略组中没有添加任何IP，则负载均衡监听会转发全部请求。
        说明 当AclStatus参数的值为on时，该参数有效。
        */
        @QueryParam("AclType")
        CreateLoadBalancerHTTPSListenerBuilder aclType(final String aclType);

        /**
        *
        是否开启访问控制功能。
        取值：on | off（默认值）。
        */
        @QueryParam("AclStatus")
        CreateLoadBalancerHTTPSListenerBuilder aclStatus(final String aclType);

        /**
        *
        设置监听的描述信息。
        长度限制为1-80个字符，允许包含字母、数字、“-”、“/”、“.”和“_”等字符。支持中文描述。
        */
        @QueryParam("Description")
        CreateLoadBalancerHTTPSListenerBuilder description(final String description);

        /**
        *
        指定连接空闲超时时间，取值范围为1-60秒，默认值为15秒。
        在超时时间内一直没有访问请求，负载均衡会暂时中断当前连接，直到一下次请求来临时重新建立新的连接。
        */
        @QueryParam("IdleTimeout")
        CreateLoadBalancerHTTPSListenerBuilder idleTimeout(final int idleTimeout);

        /**
        *
        指定请求超时时间，取值范围为1-180秒，默认值为60秒。
        在超时时间内后端服务器一直没有响应，负载均衡将放弃等待，给客户端返回HTTP 504错误码。
        */
        @QueryParam("RequestTimeout")
        CreateLoadBalancerHTTPSListenerBuilder requestTimeout(final int requestTimeout);

        /**
        *
        是否开启HTTP/2特性。
        取值：on（默认值）|off。
        */
        @QueryParam("EnableHttp2")
        CreateLoadBalancerHTTPSListenerBuilder enableHttp2(final String enableHttp2);

        /**
        *
        只有性能保障型实例才可以指定TLSCipherPolicy参数，每种policy定义了一种安全策略，安全策略包含HTTPS可选的TLS协议版本和配套的加密算法套件。
        目前支持以下四种安全策略，详细区别请参见TLS安全策略差异说明，请根据实际情况选择对应的policy。
        tls_cipher_policy_1_0：
        支持TLS版本： TLSv1.0、TLSv1.1和TLSv1.2。
        支持加密算法套件：ECDHE-RSA-AES128-GCM-SHA256、ECDHE-RSA-AES256-GCM-SHA384、ECDHE-RSA-AES128-SHA256、ECDHE-RSA-AES256-SHA384、AES128-GCM-SHA256、AES256-GCM-SHA384、AES128-SHA256、AES256-SHA256、ECDHE-RSA-AES128-SHA、ECDHE-RSA-AES256-SHA、AES128-SHA、AES256-SHA和DES-CBC3-SHA。
        tls_cipher_policy_1_1：
        支持TLS版本： TLSv1.1和TLSv1.2。
        支持加密算法套件：ECDHE-RSA-AES128-GCM-SHA256、ECDHE-RSA-AES256-GCM-SHA384、ECDHE-RSA-AES128-SHA256、ECDHE-RSA-AES256-SHA384、AES128-GCM-SHA256、AES256-GCM-SHA384、AES128-SHA256、AES256-SHA256、ECDHE-RSA-AES128-SHA、ECDHE-RSA-AES256-SHA、AES128-SHA、AES256-SHA和DES-CBC3-SHA。
        tls_cipher_policy_1_2
        支持TLS版本：TLSv1.2。
        支持加密算法套件：ECDHE-RSA-AES128-GCM-SHA256、ECDHE-RSA-AES256-GCM-SHA384、ECDHE-RSA-AES128-SHA256、ECDHE-RSA-AES256-SHA384、AES128-GCM-SHA256、AES256-GCM-SHA384、AES128-SHA256、AES256-SHA256、ECDHE-RSA-AES128-SHA、ECDHE-RSA-AES256-SHA、AES128-SHA、AES256-SHA和DES-CBC3-SHA。
        tls_cipher_policy_1_2_strict
        支持TLS版本：TLSv1.2。
        支持加密算法套件：ECDHE-RSA-AES128-GCM-SHA256、ECDHE-RSA-AES256-GCM-SHA384、ECDHE-RSA-AES128-SHA256、ECDHE-RSA-AES256-SHA384、ECDHE-RSA-AES128-SHA和ECDHE-RSA-AES256-SHA。
        */
        @QueryParam("TLSCipherPolicy")
        CreateLoadBalancerHTTPSListenerBuilder tLSCipherPolicy(final String tLSCipherPolicy);

        @GET
        @ConstParams({"Action", "CreateLoadBalancerHTTPSListener"})
        @Consumes(MediaType.APPLICATION_JSON)
        Observable<CreateLoadBalancerHTTPSListenerResponse> call();
    }

    // https://help.aliyun.com/document_detail/27593.html?spm=a2c4g.11186623.6.717.7c5ef5dbRQLorb
    public CreateLoadBalancerHTTPSListenerBuilder createLoadBalancerHTTPSListener();

    interface DescribeLoadBalancerHTTPSListenerAttributeResponse extends SLBAPIResponse {
    }

    @RpcBuilder
    interface DescribeLoadBalancerHTTPSListenerAttributeBuilder {
        @QueryParam("RegionId")
        DescribeLoadBalancerHTTPSListenerAttributeBuilder regionId(final String regionId);

        @QueryParam("LoadBalancerId")
        DescribeLoadBalancerHTTPSListenerAttributeBuilder loadBalancerId(final String loadBalancerId);

        @QueryParam("ListenerPort")
        DescribeLoadBalancerHTTPSListenerAttributeBuilder listenerPort(final int listenerPort);

        @GET
        @ConstParams({"Action", "DescribeLoadBalancerHTTPSListenerAttribute"})
        @Consumes(MediaType.APPLICATION_JSON)
        Observable<DescribeLoadBalancerHTTPSListenerAttributeResponse> call();
    }

    // https://help.aliyun.com/document_detail/27607.html?spm=a2c4g.11186623.6.718.25792ed7OJ4Vdz
    public DescribeLoadBalancerHTTPSListenerAttributeBuilder describeLoadBalancerHTTPSListenerAttribute();

    interface SetLoadBalancerHTTPSListenerAttributeResponse extends SLBAPIResponse {
    }

    interface SetLoadBalancerHTTPSListenerAttributeBuilder {
        @QueryParam("RegionId")
        SetLoadBalancerHTTPSListenerAttributeBuilder regionId(final String regionId);

        @QueryParam("LoadBalancerId")
        SetLoadBalancerHTTPSListenerAttributeBuilder loadBalancerId(final String loadBalancerId);

        @QueryParam("ListenerPort")
        SetLoadBalancerHTTPSListenerAttributeBuilder listenerPort(final int listenerPort);

        @GET
        @ConstParams({"Action", "SetLoadBalancerHTTPSListenerAttribute"})
        @Consumes(MediaType.APPLICATION_JSON)
        Observable<SetLoadBalancerHTTPSListenerAttributeResponse> call();
    }

    // https://help.aliyun.com/document_detail/27603.html?spm=a2c4g.11186623.6.719.4e4938ddw9rgTY
    public SetLoadBalancerHTTPSListenerAttributeBuilder setLoadBalancerHTTPSListenerAttribute();

    interface StartLoadBalancerListenerResponse extends SLBAPIResponse {
    }

    @RpcBuilder
    interface StartLoadBalancerListenerBuilder {
        @QueryParam("RegionId")
        StartLoadBalancerListenerBuilder regionId(final String regionId);

        @QueryParam("LoadBalancerId")
        StartLoadBalancerListenerBuilder loadBalancerId(final String loadBalancerId);

        @QueryParam("ListenerPort")
        StartLoadBalancerListenerBuilder listenerPort(final int listenerPort);

        @GET
        @ConstParams({"Action", "StartLoadBalancerListener"})
        @Consumes(MediaType.APPLICATION_JSON)
        Observable<StartLoadBalancerListenerResponse> call();
    }

    // https://help.aliyun.com/document_detail/27597.html?spm=a2c4g.11186623.6.721.306f43562zFIPm
    public StartLoadBalancerListenerBuilder startLoadBalancerListener();

    interface StopLoadBalancerListenerResponse extends SLBAPIResponse {
    }

    @RpcBuilder
    interface StopLoadBalancerListenerBuilder {
        @QueryParam("RegionId")
        StopLoadBalancerListenerBuilder regionId(final String regionId);

        @QueryParam("LoadBalancerId")
        StopLoadBalancerListenerBuilder loadBalancerId(final String loadBalancerId);

        @QueryParam("ListenerPort")
        StopLoadBalancerListenerBuilder listenerPort(final int listenerPort);

        @GET
        @ConstParams({"Action", "StopLoadBalancerListener"})
        @Consumes(MediaType.APPLICATION_JSON)
        Observable<StopLoadBalancerListenerResponse> call();
    }

    // https://help.aliyun.com/document_detail/27598.html?spm=a2c4g.11186623.6.722.154a53bdYREouI
    public StopLoadBalancerListenerBuilder stopLoadBalancerListener();

    interface DeleteLoadBalancerListenerResponse extends SLBAPIResponse {
    }

    @RpcBuilder
    interface DeleteLoadBalancerListenerBuilder {
        @QueryParam("RegionId")
        DeleteLoadBalancerListenerBuilder regionId(final String regionId);

        @QueryParam("LoadBalancerId")
        DeleteLoadBalancerListenerBuilder loadBalancerId(final String loadBalancerId);

        @QueryParam("ListenerPort")
        DeleteLoadBalancerListenerBuilder listenerPort(final int listenerPort);

        @GET
        @ConstParams({"Action", "DeleteLoadBalancerListener"})
        @Consumes(MediaType.APPLICATION_JSON)
        Observable<DeleteLoadBalancerListenerResponse> call();
    }

    // https://help.aliyun.com/document_detail/27596.html?spm=a2c4g.11186623.6.723.125c1a84OmUino
    public DeleteLoadBalancerListenerBuilder deleteLoadBalancerListener();

    interface AddBackendServersResponse extends SLBAPIResponse {
    }

    @RpcBuilder
    interface AddBackendServersBuilder {
        @QueryParam("RegionId")
        AddBackendServersBuilder regionId(final String regionId);

        @QueryParam("LoadBalancerId")
        AddBackendServersBuilder loadBalancerId(final String loadBalancerId);

        @QueryParam("ListenerPort")
        AddBackendServersBuilder listenerPort(final int listenerPort);

        @GET
        @ConstParams({"Action", "AddBackendServers"})
        @Consumes(MediaType.APPLICATION_JSON)
        Observable<AddBackendServersResponse> call();
    }

    // https://help.aliyun.com/document_detail/27632.html?spm=a2c4g.11186623.6.725.58424d63syzT0P
    public AddBackendServersBuilder addBackendServers();

    interface RemoveBackendServersResponse extends SLBAPIResponse {
    }

    @RpcBuilder
    interface RemoveBackendServersBuilder {
        @QueryParam("RegionId")
        RemoveBackendServersBuilder regionId(final String regionId);

        @QueryParam("LoadBalancerId")
        RemoveBackendServersBuilder loadBalancerId(final String loadBalancerId);

        @QueryParam("ListenerPort")
        RemoveBackendServersBuilder listenerPort(final int listenerPort);

        @GET
        @ConstParams({"Action", "RemoveBackendServers"})
        @Consumes(MediaType.APPLICATION_JSON)
        Observable<RemoveBackendServersResponse> call();
    }

    // https://help.aliyun.com/document_detail/27633.html?spm=a2c4g.11186623.6.726.489269736wkqxr
    public RemoveBackendServersBuilder removeBackendServers();

    interface SetBackendServersResponse extends SLBAPIResponse {
    }

    @RpcBuilder
    interface SetBackendServersBuilder {
        @QueryParam("RegionId")
        SetBackendServersBuilder regionId(final String regionId);

        @QueryParam("LoadBalancerId")
        SetBackendServersBuilder loadBalancerId(final String loadBalancerId);

        @QueryParam("ListenerPort")
        SetBackendServersBuilder listenerPort(final int listenerPort);

        @GET
        @ConstParams({"Action", "SetBackendServers"})
        @Consumes(MediaType.APPLICATION_JSON)
        Observable<SetBackendServersResponse> call();
    }

    // https://help.aliyun.com/document_detail/27634.html?spm=a2c4g.11186623.6.727.46d718b4oUXgvA
    public SetBackendServersBuilder setBackendServers();

    interface DescribeHealthStatusResponse extends SLBAPIResponse {
    }

    @RpcBuilder
    interface DescribeHealthStatusBuilder {
        @QueryParam("RegionId")
        DescribeHealthStatusBuilder regionId(final String regionId);

        @QueryParam("LoadBalancerId")
        DescribeHealthStatusBuilder loadBalancerId(final String loadBalancerId);

        @QueryParam("ListenerPort")
        DescribeHealthStatusBuilder listenerPort(final int listenerPort);

        @GET
        @ConstParams({"Action", "DescribeHealthStatus"})
        @Consumes(MediaType.APPLICATION_JSON)
        Observable<DescribeHealthStatusResponse> call();
    }

    // https://help.aliyun.com/document_detail/27635.html?spm=a2c4g.11186623.6.728.5cb76961s6NMty
    public DescribeHealthStatusBuilder describeHealthStatus();

    interface CreateVServerGroupResponse extends SLBAPIResponse {
    }

    @RpcBuilder
    interface CreateVServerGroupBuilder {
        @QueryParam("RegionId")
        CreateVServerGroupBuilder regionId(final String regionId);

        @QueryParam("LoadBalancerId")
        CreateVServerGroupBuilder loadBalancerId(final String loadBalancerId);

        @QueryParam("ListenerPort")
        CreateVServerGroupBuilder listenerPort(final int listenerPort);

        @GET
        @ConstParams({"Action", "CreateVServerGroup"})
        @Consumes(MediaType.APPLICATION_JSON)
        Observable<CreateVServerGroupResponse> call();
    }

    // https://help.aliyun.com/document_detail/35215.html?spm=a2c4g.11186623.6.730.279a4143OhabhX
    public CreateVServerGroupBuilder createVServerGroup();

    interface UploadServerCertificateResponse extends SLBAPIResponse {
    }

    @RpcBuilder
    interface UploadServerCertificateBuilder {
        @QueryParam("RegionId")
        UploadServerCertificateBuilder regionId(final String regionId);

        @QueryParam("LoadBalancerId")
        UploadServerCertificateBuilder loadBalancerId(final String loadBalancerId);

        @QueryParam("ListenerPort")
        UploadServerCertificateBuilder listenerPort(final int listenerPort);

        @GET
        @ConstParams({"Action", "UploadServerCertificate"})
        @Consumes(MediaType.APPLICATION_JSON)
        Observable<UploadServerCertificateResponse> call();
    }

    // https://help.aliyun.com/document_detail/34181.html?spm=a2c4g.11186623.6.744.3ad91b42pK13EL
    public UploadServerCertificateBuilder uploadServerCertificate();

    interface DeleteServerCertificateResponse extends SLBAPIResponse {
    }

    @RpcBuilder
    interface DeleteServerCertificateBuilder {
        @QueryParam("RegionId")
        DeleteServerCertificateBuilder regionId(final String regionId);

        @QueryParam("LoadBalancerId")
        DeleteServerCertificateBuilder loadBalancerId(final String loadBalancerId);

        @QueryParam("ListenerPort")
        DeleteServerCertificateBuilder listenerPort(final int listenerPort);

        @GET
        @ConstParams({"Action", "DeleteServerCertificate"})
        @Consumes(MediaType.APPLICATION_JSON)
        Observable<DeleteServerCertificateResponse> call();
    }

    // https://help.aliyun.com/document_detail/34182.html?spm=a2c4g.11186623.6.745.29d91d728CSNu7
    public DeleteServerCertificateBuilder deleteServerCertificate();

    interface DescribeServerCertificatesResponse extends SLBAPIResponse {
    }

    @RpcBuilder
    interface DescribeServerCertificatesBuilder {
        @QueryParam("RegionId")
        DescribeServerCertificatesBuilder regionId(final String regionId);

        @QueryParam("LoadBalancerId")
        DescribeServerCertificatesBuilder loadBalancerId(final String loadBalancerId);

        @QueryParam("ListenerPort")
        DescribeServerCertificatesBuilder listenerPort(final int listenerPort);

        @GET
        @ConstParams({"Action", "DescribeServerCertificates"})
        @Consumes(MediaType.APPLICATION_JSON)
        Observable<DescribeServerCertificatesResponse> call();
    }

    // https://help.aliyun.com/document_detail/34183.html?spm=a2c4g.11186623.6.746.41121c44IZfrVh
    public DescribeServerCertificatesBuilder describeServerCertificates();

    interface SetServerCertificateNameResponse extends SLBAPIResponse {
    }

    @RpcBuilder
    interface SetServerCertificateNameBuilder {
        @QueryParam("RegionId")
        SetServerCertificateNameBuilder regionId(final String regionId);

        @QueryParam("LoadBalancerId")
        SetServerCertificateNameBuilder loadBalancerId(final String loadBalancerId);

        @QueryParam("ListenerPort")
        SetServerCertificateNameBuilder listenerPort(final int listenerPort);

        @GET
        @ConstParams({"Action", "SetServerCertificateName"})
        @Consumes(MediaType.APPLICATION_JSON)
        Observable<SetServerCertificateNameResponse> call();
    }

    // https://help.aliyun.com/document_detail/34184.html?spm=a2c4g.11186623.6.747.6c824e35y4HBUk
    public SetServerCertificateNameBuilder setServerCertificateName();

    interface UploadCACertificateResponse extends SLBAPIResponse {
    }

    @RpcBuilder
    interface UploadCACertificateBuilder {
        @QueryParam("RegionId")
        UploadCACertificateBuilder regionId(final String regionId);

        @QueryParam("LoadBalancerId")
        UploadCACertificateBuilder loadBalancerId(final String loadBalancerId);

        @QueryParam("ListenerPort")
        UploadCACertificateBuilder listenerPort(final int listenerPort);

        @GET
        @ConstParams({"Action", "UploadCACertificate"})
        @Consumes(MediaType.APPLICATION_JSON)
        Observable<UploadCACertificateResponse> call();
    }

    // https://help.aliyun.com/document_detail/34935.html?spm=a2c4g.11186623.6.748.237b34ffZfZNOL
    public UploadCACertificateBuilder uploadCACertificate();

    interface DeleteCACertificateResponse extends SLBAPIResponse {
    }

    @RpcBuilder
    interface DeleteCACertificateBuilder {
        @QueryParam("RegionId")
        DeleteCACertificateBuilder regionId(final String regionId);

        @QueryParam("LoadBalancerId")
        DeleteCACertificateBuilder loadBalancerId(final String loadBalancerId);

        @QueryParam("ListenerPort")
        DeleteCACertificateBuilder listenerPort(final int listenerPort);

        @GET
        @ConstParams({"Action", "DeleteCACertificate"})
        @Consumes(MediaType.APPLICATION_JSON)
        Observable<DeleteCACertificateResponse> call();
    }

    // https://help.aliyun.com/document_detail/34937.html?spm=a2c4g.11186623.6.749.684f2b5cmjx1OT
    public DeleteCACertificateBuilder deleteCACertificate();

    interface DescribeCACertificatesResponse extends SLBAPIResponse {
    }

    @RpcBuilder
    interface DescribeCACertificatesBuilder {
        @QueryParam("RegionId")
        DescribeCACertificatesBuilder regionId(final String regionId);

        @QueryParam("LoadBalancerId")
        DescribeCACertificatesBuilder loadBalancerId(final String loadBalancerId);

        @QueryParam("ListenerPort")
        DescribeCACertificatesBuilder listenerPort(final int listenerPort);

        @GET
        @ConstParams({"Action", "DescribeCACertificates"})
        @Consumes(MediaType.APPLICATION_JSON)
        Observable<DescribeCACertificatesResponse> call();
    }

    // https://help.aliyun.com/document_detail/34938.html?spm=a2c4g.11186623.6.750.3fe014687zmHpv
    public DescribeCACertificatesBuilder describeCACertificates();

    interface SetCACertificateNameResponse extends SLBAPIResponse {
    }

    @RpcBuilder
    interface SetCACertificateNameBuilder {
        @QueryParam("RegionId")
        SetCACertificateNameBuilder regionId(final String regionId);

        @QueryParam("LoadBalancerId")
        SetCACertificateNameBuilder loadBalancerId(final String loadBalancerId);

        @QueryParam("ListenerPort")
        SetCACertificateNameBuilder listenerPort(final int listenerPort);

        @GET
        @ConstParams({"Action", "SetCACertificateName"})
        @Consumes(MediaType.APPLICATION_JSON)
        Observable<SetCACertificateNameResponse> call();
    }

    // https://help.aliyun.com/document_detail/34936.html?spm=a2c4g.11186623.6.751.13e86180FiIzbI
    public SetCACertificateNameBuilder setCACertificateName();

    interface CreateDomainExtensionResponse extends SLBAPIResponse {
    }

    @RpcBuilder
    interface CreateDomainExtensionBuilder {
        @QueryParam("RegionId")
        CreateDomainExtensionBuilder regionId(final String regionId);

        @QueryParam("LoadBalancerId")
        CreateDomainExtensionBuilder loadBalancerId(final String loadBalancerId);

        @QueryParam("ListenerPort")
        CreateDomainExtensionBuilder listenerPort(final int listenerPort);

        @GET
        @ConstParams({"Action", "CreateDomainExtension"})
        @Consumes(MediaType.APPLICATION_JSON)
        Observable<CreateDomainExtensionResponse> call();
    }

    // https://help.aliyun.com/document_detail/85912.html?spm=a2c4g.11186623.6.753.5da71933xB2QVh
    public CreateDomainExtensionBuilder createDomainExtension();

    interface DeleteDomainExtensionResponse extends SLBAPIResponse {
    }

    @RpcBuilder
    interface DeleteDomainExtensionBuilder {
        @QueryParam("RegionId")
        DeleteDomainExtensionBuilder regionId(final String regionId);

        @QueryParam("LoadBalancerId")
        DeleteDomainExtensionBuilder loadBalancerId(final String loadBalancerId);

        @QueryParam("ListenerPort")
        DeleteDomainExtensionBuilder listenerPort(final int listenerPort);

        @GET
        @ConstParams({"Action", "DeleteDomainExtension"})
        @Consumes(MediaType.APPLICATION_JSON)
        Observable<DeleteDomainExtensionResponse> call();
    }

    // https://help.aliyun.com/document_detail/85915.html?spm=a2c4g.11186623.6.754.76eb6b245g8u9z
    public DeleteDomainExtensionBuilder deleteDomainExtension();

    interface DescribeDomainExtensionsResponse extends SLBAPIResponse {
    }

    @RpcBuilder
    interface DescribeDomainExtensionsBuilder {
        @QueryParam("RegionId")
        DescribeDomainExtensionsBuilder regionId(final String regionId);

        @QueryParam("LoadBalancerId")
        DescribeDomainExtensionsBuilder loadBalancerId(final String loadBalancerId);

        @QueryParam("ListenerPort")
        DescribeDomainExtensionsBuilder listenerPort(final int listenerPort);

        @GET
        @ConstParams({"Action", "DescribeDomainExtensions"})
        @Consumes(MediaType.APPLICATION_JSON)
        Observable<DescribeDomainExtensionsResponse> call();
    }

    // https://help.aliyun.com/document_detail/85914.html?spm=a2c4g.11186623.6.755.5e546b19v352Q2
    public DescribeDomainExtensionsBuilder describeDomainExtensions();

    interface SetDomainExtensionAttributeResponse extends SLBAPIResponse {
    }

    @RpcBuilder
    interface SetDomainExtensionAttributeBuilder {
        @QueryParam("RegionId")
        SetDomainExtensionAttributeBuilder regionId(final String regionId);

        @QueryParam("LoadBalancerId")
        SetDomainExtensionAttributeBuilder loadBalancerId(final String loadBalancerId);

        @QueryParam("ListenerPort")
        SetDomainExtensionAttributeBuilder listenerPort(final int listenerPort);

        @GET
        @ConstParams({"Action", "SetDomainExtensionAttribute"})
        @Consumes(MediaType.APPLICATION_JSON)
        Observable<SetDomainExtensionAttributeResponse> call();
    }

    // https://help.aliyun.com/document_detail/85913.html?spm=a2c4g.11186623.6.756.11631410UUENyy
    public SetDomainExtensionAttributeBuilder setDomainExtensionAttribute();

    interface DescribeAvailableResourceResponse extends SLBAPIResponse {
    }

    @RpcBuilder
    interface DescribeAvailableResourceBuilder {
        @QueryParam("RegionId")
        DescribeAvailableResourceBuilder regionId(final String regionId);

        @QueryParam("LoadBalancerId")
        DescribeAvailableResourceBuilder loadBalancerId(final String loadBalancerId);

        @QueryParam("ListenerPort")
        DescribeAvailableResourceBuilder listenerPort(final int listenerPort);

        @GET
        @ConstParams({"Action", "DescribeAvailableResource"})
        @Consumes(MediaType.APPLICATION_JSON)
        Observable<DescribeAvailableResourceResponse> call();
    }

    // https://help.aliyun.com/document_detail/117645.html?spm=a2c4g.11186623.6.758.12ab4442TEgt55
    public DescribeAvailableResourceBuilder describeAvailableResource();

    interface ListTagResourcesResponse extends SLBAPIResponse {
    }

    @RpcBuilder
    interface ListTagResourcesBuilder {
        @QueryParam("RegionId")
        ListTagResourcesBuilder regionId(final String regionId);

        @QueryParam("LoadBalancerId")
        ListTagResourcesBuilder loadBalancerId(final String loadBalancerId);

        @QueryParam("ListenerPort")
        ListTagResourcesBuilder listenerPort(final int listenerPort);

        @GET
        @ConstParams({"Action", "ListTagResources"})
        @Consumes(MediaType.APPLICATION_JSON)
        Observable<ListTagResourcesResponse> call();
    }

    // https://help.aliyun.com/document_detail/144394.html?spm=a2c4g.11186623.6.760.12905c8cbgT0g0
    public ListTagResourcesBuilder listTagResources();

    interface TagResourcesResponse extends SLBAPIResponse {
    }

    @RpcBuilder
    interface TagResourcesBuilder {
        @QueryParam("RegionId")
        TagResourcesBuilder regionId(final String regionId);

        @QueryParam("LoadBalancerId")
        TagResourcesBuilder loadBalancerId(final String loadBalancerId);

        @QueryParam("ListenerPort")
        TagResourcesBuilder listenerPort(final int listenerPort);

        @GET
        @ConstParams({"Action", "TagResources"})
        @Consumes(MediaType.APPLICATION_JSON)
        Observable<TagResourcesResponse> call();
    }

    // https://help.aliyun.com/document_detail/144396.html?spm=a2c4g.11186623.6.761.4e1534afcuELZB
    public TagResourcesBuilder tagResources();

    interface UntagResourcesResponse extends SLBAPIResponse {
    }

    @RpcBuilder
    interface UntagResourcesBuilder {
        @QueryParam("RegionId")
        UntagResourcesBuilder regionId(final String regionId);

        @QueryParam("LoadBalancerId")
        UntagResourcesBuilder loadBalancerId(final String loadBalancerId);

        @QueryParam("ListenerPort")
        UntagResourcesBuilder listenerPort(final int listenerPort);

        @GET
        @ConstParams({"Action", "UntagResources"})
        @Consumes(MediaType.APPLICATION_JSON)
        Observable<UntagResourcesResponse> call();
    }

    // https://help.aliyun.com/document_detail/144395.html?spm=a2c4g.11186623.6.762.67e974efUN5sqJ
    public UntagResourcesBuilder untagResources();

    interface CreateRulesResponse extends SLBAPIResponse {
    }

    @RpcBuilder
    interface CreateRulesBuilder {
        @QueryParam("RegionId")
        CreateRulesBuilder regionId(final String regionId);

        @QueryParam("LoadBalancerId")
        CreateRulesBuilder loadBalancerId(final String loadBalancerId);

        @QueryParam("ListenerPort")
        CreateRulesBuilder listenerPort(final int listenerPort);

        @GET
        @ConstParams({"Action", "CreateRules"})
        @Consumes(MediaType.APPLICATION_JSON)
        Observable<CreateRulesResponse> call();
    }

    // https://help.aliyun.com/document_detail/35226.html?spm=a2c4g.11186623.6.768.113e784a9v5pWM
    public CreateRulesBuilder createRules();

    interface DeleteRulesResponse extends SLBAPIResponse {
    }

    @RpcBuilder
    interface DeleteRulesBuilder {
        @QueryParam("RegionId")
        DeleteRulesBuilder regionId(final String regionId);

        @QueryParam("LoadBalancerId")
        DeleteRulesBuilder loadBalancerId(final String loadBalancerId);

        @QueryParam("ListenerPort")
        DeleteRulesBuilder listenerPort(final int listenerPort);

        @GET
        @ConstParams({"Action", "DeleteRules"})
        @Consumes(MediaType.APPLICATION_JSON)
        Observable<DeleteRulesResponse> call();
    }

    // https://help.aliyun.com/document_detail/35227.html?spm=a2c4g.11186623.6.769.2f1d205dZd0lWP
    public DeleteRulesBuilder deleteRules();

    interface SetRuleResponse extends SLBAPIResponse {
    }

    @RpcBuilder
    interface SetRuleBuilder {
        @QueryParam("RegionId")
        SetRuleBuilder regionId(final String regionId);

        @QueryParam("LoadBalancerId")
        SetRuleBuilder loadBalancerId(final String loadBalancerId);

        @QueryParam("ListenerPort")
        SetRuleBuilder listenerPort(final int listenerPort);

        @GET
        @ConstParams({"Action", "SetRule"})
        @Consumes(MediaType.APPLICATION_JSON)
        Observable<SetRuleResponse> call();
    }

    // https://help.aliyun.com/document_detail/35228.html?spm=a2c4g.11186623.6.770.31fc204e32OsTQ
    public SetRuleBuilder setRule();

    interface DescribeRuleAttributeResponse extends SLBAPIResponse {
    }

    @RpcBuilder
    interface DescribeRuleAttributeBuilder {
        @QueryParam("RegionId")
        DescribeRuleAttributeBuilder regionId(final String regionId);

        @QueryParam("LoadBalancerId")
        DescribeRuleAttributeBuilder loadBalancerId(final String loadBalancerId);

        @QueryParam("ListenerPort")
        DescribeRuleAttributeBuilder listenerPort(final int listenerPort);

        @GET
        @ConstParams({"Action", "DescribeRuleAttribute"})
        @Consumes(MediaType.APPLICATION_JSON)
        Observable<DescribeRuleAttributeResponse> call();
    }

    // https://help.aliyun.com/document_detail/35229.html?spm=a2c4g.11186623.6.771.2be8468aLnYEa3
    public DescribeRuleAttributeBuilder describeRuleAttribute();

    interface DescribeRulesResponse extends SLBAPIResponse {
    }

    @RpcBuilder
    interface DescribeRulesBuilder {
        @QueryParam("RegionId")
        DescribeRulesBuilder regionId(final String regionId);

        @QueryParam("LoadBalancerId")
        DescribeRulesBuilder loadBalancerId(final String loadBalancerId);

        @QueryParam("ListenerPort")
        DescribeRulesBuilder listenerPort(final int listenerPort);

        @GET
        @ConstParams({"Action", "DescribeRules"})
        @Consumes(MediaType.APPLICATION_JSON)
        Observable<DescribeRulesResponse> call();
    }

    // https://help.aliyun.com/document_detail/35230.html?spm=a2c4g.11186623.6.772.1e3e4b5fdf0deS
    public DescribeRulesBuilder describeRules();

    interface CreateAccessControlListResponse extends SLBAPIResponse {
    }

    @RpcBuilder
    interface CreateAccessControlListBuilder {
        @QueryParam("RegionId")
        CreateAccessControlListBuilder regionId(final String regionId);

        @QueryParam("LoadBalancerId")
        CreateAccessControlListBuilder loadBalancerId(final String loadBalancerId);

        @QueryParam("ListenerPort")
        CreateAccessControlListBuilder listenerPort(final int listenerPort);

        @GET
        @ConstParams({"Action", "CreateAccessControlList"})
        @Consumes(MediaType.APPLICATION_JSON)
        Observable<CreateAccessControlListResponse> call();
    }

    // https://help.aliyun.com/document_detail/70015.html?spm=a2c4g.11186623.6.774.122d23e2nsDgFA
    public CreateAccessControlListBuilder createAccessControlList();

    interface DeleteAccessControlListResponse extends SLBAPIResponse {
    }

    @RpcBuilder
    interface DeleteAccessControlListBuilder {
        @QueryParam("RegionId")
        DeleteAccessControlListBuilder regionId(final String regionId);

        @QueryParam("LoadBalancerId")
        DeleteAccessControlListBuilder loadBalancerId(final String loadBalancerId);

        @QueryParam("ListenerPort")
        DeleteAccessControlListBuilder listenerPort(final int listenerPort);

        @GET
        @ConstParams({"Action", "DeleteAccessControlList"})
        @Consumes(MediaType.APPLICATION_JSON)
        Observable<DeleteAccessControlListResponse> call();
    }

    // https://help.aliyun.com/document_detail/70056.html?spm=a2c4g.11186623.6.774.69e2438ffAjDvb
    public DeleteAccessControlListBuilder deleteAccessControlList();

    interface DescribeAccessControlListsResponse extends SLBAPIResponse {
    }

    @RpcBuilder
    interface DescribeAccessControlListsBuilder {
        @QueryParam("RegionId")
        DescribeAccessControlListsBuilder regionId(final String regionId);

        @QueryParam("LoadBalancerId")
        DescribeAccessControlListsBuilder loadBalancerId(final String loadBalancerId);

        @QueryParam("ListenerPort")
        DescribeAccessControlListsBuilder listenerPort(final int listenerPort);

        @GET
        @ConstParams({"Action", "DescribeAccessControlLists"})
        @Consumes(MediaType.APPLICATION_JSON)
        Observable<DescribeAccessControlListsResponse> call();
    }

    // https://help.aliyun.com/document_detail/70052.html?spm=a2c4g.11186623.6.775.753d439fWS2SEm
    public DescribeAccessControlListsBuilder describeAccessControlLists();

    interface DescribeAccessControlListAttributeResponse extends SLBAPIResponse {
    }

    @RpcBuilder
    interface DescribeAccessControlListAttributeBuilder {
        @QueryParam("RegionId")
        DescribeAccessControlListAttributeBuilder regionId(final String regionId);

        @QueryParam("LoadBalancerId")
        DescribeAccessControlListAttributeBuilder loadBalancerId(final String loadBalancerId);

        @QueryParam("ListenerPort")
        DescribeAccessControlListAttributeBuilder listenerPort(final int listenerPort);

        @GET
        @ConstParams({"Action", "DescribeAccessControlListAttribute"})
        @Consumes(MediaType.APPLICATION_JSON)
        Observable<DescribeAccessControlListAttributeResponse> call();
    }

    // https://help.aliyun.com/document_detail/70051.html?spm=a2c4g.11186623.6.776.775a6f176hV8oM
    public DescribeAccessControlListAttributeBuilder describeAccessControlListAttribute();

    interface SetAccessControlListAttributeResponse extends SLBAPIResponse {
    }

    @RpcBuilder
    interface SetAccessControlListAttributeBuilder {
        @QueryParam("RegionId")
        SetAccessControlListAttributeBuilder regionId(final String regionId);

        @QueryParam("LoadBalancerId")
        SetAccessControlListAttributeBuilder loadBalancerId(final String loadBalancerId);

        @QueryParam("ListenerPort")
        SetAccessControlListAttributeBuilder listenerPort(final int listenerPort);

        @GET
        @ConstParams({"Action", "SetAccessControlListAttribute"})
        @Consumes(MediaType.APPLICATION_JSON)
        Observable<SetAccessControlListAttributeResponse> call();
    }

    // https://help.aliyun.com/document_detail/70022.html?spm=a2c4g.11186623.6.777.5d3b5b05JwNo9u
    public SetAccessControlListAttributeBuilder setAccessControlListAttribute();

    interface AddAccessControlListEntryResponse extends SLBAPIResponse {
    }

    @RpcBuilder
    interface AddAccessControlListEntryBuilder {
        @QueryParam("RegionId")
        AddAccessControlListEntryBuilder regionId(final String regionId);

        @QueryParam("LoadBalancerId")
        AddAccessControlListEntryBuilder loadBalancerId(final String loadBalancerId);

        @QueryParam("ListenerPort")
        AddAccessControlListEntryBuilder listenerPort(final int listenerPort);

        @GET
        @ConstParams({"Action", "AddAccessControlListEntry"})
        @Consumes(MediaType.APPLICATION_JSON)
        Observable<AddAccessControlListEntryResponse> call();
    }

    // https://help.aliyun.com/document_detail/70023.html?spm=a2c4g.11186623.6.778.a4634f939y4Qwk
    public AddAccessControlListEntryBuilder addAccessControlListEntry();

    interface RemoveAccessControlListEntryResponse extends SLBAPIResponse {
    }

    @RpcBuilder
    interface RemoveAccessControlListEntryBuilder {
        @QueryParam("RegionId")
        RemoveAccessControlListEntryBuilder regionId(final String regionId);

        @QueryParam("LoadBalancerId")
        RemoveAccessControlListEntryBuilder loadBalancerId(final String loadBalancerId);

        @QueryParam("ListenerPort")
        RemoveAccessControlListEntryBuilder listenerPort(final int listenerPort);

        @GET
        @ConstParams({"Action", "RemoveAccessControlListEntry"})
        @Consumes(MediaType.APPLICATION_JSON)
        Observable<RemoveAccessControlListEntryResponse> call();
    }

    // https://help.aliyun.com/document_detail/70055.html?spm=a2c4g.11186623.6.779.62e55618ESZYmk
    public RemoveAccessControlListEntryBuilder removeAccessControlListEntry();

    interface DescribeLoadBalancerTCPListenerAttributeResponse extends SLBAPIResponse {
    }

    @RpcBuilder
    interface DescribeLoadBalancerTCPListenerAttributeBuilder {
        @QueryParam("RegionId")
        DescribeLoadBalancerTCPListenerAttributeBuilder regionId(final String regionId);

        @QueryParam("LoadBalancerId")
        DescribeLoadBalancerTCPListenerAttributeBuilder loadBalancerId(final String loadBalancerId);

        @QueryParam("ListenerPort")
        DescribeLoadBalancerTCPListenerAttributeBuilder listenerPort(final int listenerPort);

        @GET
        @ConstParams({"Action", "DescribeLoadBalancerTCPListenerAttribute"})
        @Consumes(MediaType.APPLICATION_JSON)
        Observable<DescribeLoadBalancerTCPListenerAttributeResponse> call();
    }

    // https://help.aliyun.com/document_detail/27608.html?spm=a2c4g.11186623.6.712.159a3d751pSnnC
    public DescribeLoadBalancerTCPListenerAttributeBuilder describeLoadBalancerTCPListenerAttribute();

    interface CreateLoadBalancerTCPListenerResponse extends SLBAPIResponse {
    }

    @RpcBuilder
    interface CreateLoadBalancerTCPListenerBuilder {
        @QueryParam("RegionId")
        CreateLoadBalancerTCPListenerBuilder regionId(final String regionId);

        @QueryParam("LoadBalancerId")
        CreateLoadBalancerTCPListenerBuilder loadBalancerId(final String loadBalancerId);

        @QueryParam("ListenerPort")
        CreateLoadBalancerTCPListenerBuilder listenerPort(final int listenerPort);

        @GET
        @ConstParams({"Action", "CreateLoadBalancerTCPListener"})
        @Consumes(MediaType.APPLICATION_JSON)
        Observable<CreateLoadBalancerTCPListenerResponse> call();
    }

    // https://help.aliyun.com/document_detail/27594.html?spm=a2c4g.11186623.6.713.2d8d15c5hZZa59
    public CreateLoadBalancerTCPListenerBuilder createLoadBalancerTCPListener();

    interface SetLoadBalancerTCPListenerAttributeResponse extends SLBAPIResponse {
    }

    @RpcBuilder
    interface SetLoadBalancerTCPListenerAttributeBuilder {
        @QueryParam("RegionId")
        SetLoadBalancerTCPListenerAttributeBuilder regionId(final String regionId);

        @QueryParam("LoadBalancerId")
        SetLoadBalancerTCPListenerAttributeBuilder loadBalancerId(final String loadBalancerId);

        @QueryParam("ListenerPort")
        SetLoadBalancerTCPListenerAttributeBuilder listenerPort(final int listenerPort);

        @GET
        @ConstParams({"Action", "SetLoadBalancerTCPListenerAttribute"})
        @Consumes(MediaType.APPLICATION_JSON)
        Observable<SetLoadBalancerTCPListenerAttributeResponse> call();
    }

    // https://help.aliyun.com/document_detail/27604.html?spm=a2c4g.11186623.6.715.f52a1e63RM0rXd
    public SetLoadBalancerTCPListenerAttributeBuilder setLoadBalancerTCPListenerAttribute();

    interface CreateLoadBalancerUDPListenerResponse extends SLBAPIResponse {
    }

    @RpcBuilder
    interface CreateLoadBalancerUDPListenerBuilder {
        @QueryParam("RegionId")
        CreateLoadBalancerUDPListenerBuilder regionId(final String regionId);

        @QueryParam("LoadBalancerId")
        CreateLoadBalancerUDPListenerBuilder loadBalancerId(final String loadBalancerId);

        @QueryParam("ListenerPort")
        CreateLoadBalancerUDPListenerBuilder listenerPort(final int listenerPort);

        @GET
        @ConstParams({"Action", "CreateLoadBalancerUDPListener"})
        @Consumes(MediaType.APPLICATION_JSON)
        Observable<CreateLoadBalancerUDPListenerResponse> call();
    }

    // https://help.aliyun.com/document_detail/27595.html?spm=a2c4g.11186623.6.709.247a44ed3RBpEs
    public CreateLoadBalancerUDPListenerBuilder createLoadBalancerUDPListener();

    interface SetLoadBalancerUDPListenerAttributeResponse extends SLBAPIResponse {
    }

    @RpcBuilder
    interface SetLoadBalancerUDPListenerAttributeBuilder {
        @QueryParam("RegionId")
        SetLoadBalancerUDPListenerAttributeBuilder regionId(final String regionId);

        @QueryParam("LoadBalancerId")
        SetLoadBalancerUDPListenerAttributeBuilder loadBalancerId(final String loadBalancerId);

        @QueryParam("ListenerPort")
        SetLoadBalancerUDPListenerAttributeBuilder listenerPort(final int listenerPort);

        @GET
        @ConstParams({"Action", "SetLoadBalancerUDPListenerAttribute"})
        @Consumes(MediaType.APPLICATION_JSON)
        Observable<SetLoadBalancerUDPListenerAttributeResponse> call();
    }

    // https://help.aliyun.com/document_detail/27605.html?spm=a2c4g.11186623.6.710.565c27f2sxHpaB
    public SetLoadBalancerUDPListenerAttributeBuilder setLoadBalancerUDPListenerAttribute();

    interface DescribeLoadBalancerUDPListenerAttributeResponse extends SLBAPIResponse {
    }

    @RpcBuilder
    interface DescribeLoadBalancerUDPListenerAttributeBuilder {
        @QueryParam("RegionId")
        DescribeLoadBalancerUDPListenerAttributeBuilder regionId(final String regionId);

        @QueryParam("LoadBalancerId")
        DescribeLoadBalancerUDPListenerAttributeBuilder loadBalancerId(final String loadBalancerId);

        @QueryParam("ListenerPort")
        DescribeLoadBalancerUDPListenerAttributeBuilder listenerPort(final int listenerPort);

        @GET
        @ConstParams({"Action", "DescribeLoadBalancerUDPListenerAttribute"})
        @Consumes(MediaType.APPLICATION_JSON)
        Observable<DescribeLoadBalancerUDPListenerAttributeResponse> call();
    }

    // https://help.aliyun.com/document_detail/27609.html?spm=a2c4g.11186623.6.711.468b5ee6tEGWL8
    public DescribeLoadBalancerUDPListenerAttributeBuilder describeLoadBalancerUDPListenerAttribute();

    interface CreateMasterSlaveServerGroupResponse extends SLBAPIResponse {
    }

    @RpcBuilder
    interface CreateMasterSlaveServerGroupBuilder {
        @QueryParam("RegionId")
        CreateMasterSlaveServerGroupBuilder regionId(final String regionId);

        @QueryParam("LoadBalancerId")
        CreateMasterSlaveServerGroupBuilder loadBalancerId(final String loadBalancerId);

        @QueryParam("ListenerPort")
        CreateMasterSlaveServerGroupBuilder listenerPort(final int listenerPort);

        @GET
        @ConstParams({"Action", "CreateMasterSlaveServerGroup"})
        @Consumes(MediaType.APPLICATION_JSON)
        Observable<CreateMasterSlaveServerGroupResponse> call();
    }

    // https://help.aliyun.com/document_detail/50506.html?spm=a2c4g.11186623.6.743.6aaf6b24YeyUer
    public CreateMasterSlaveServerGroupBuilder createMasterSlaveServerGroup();

    interface DeleteMasterSlaveServerGroupResponse extends SLBAPIResponse {
    }

    @RpcBuilder
    interface DeleteMasterSlaveServerGroupBuilder {
        @QueryParam("RegionId")
        DeleteMasterSlaveServerGroupBuilder regionId(final String regionId);

        @QueryParam("LoadBalancerId")
        DeleteMasterSlaveServerGroupBuilder loadBalancerId(final String loadBalancerId);

        @QueryParam("ListenerPort")
        DeleteMasterSlaveServerGroupBuilder listenerPort(final int listenerPort);

        @GET
        @ConstParams({"Action", "DeleteMasterSlaveServerGroup"})
        @Consumes(MediaType.APPLICATION_JSON)
        Observable<DeleteMasterSlaveServerGroupResponse> call();
    }

    // https://help.aliyun.com/document_detail/50507.html?spm=a2c4g.11186623.6.744.73a661c2KC8VZZ
    public DeleteMasterSlaveServerGroupBuilder deleteMasterSlaveServerGroup();

    interface DescribeMasterSlaveServerGroupAttributeResponse extends SLBAPIResponse {
    }

    @RpcBuilder
    interface DescribeMasterSlaveServerGroupAttributeBuilder {
        @QueryParam("RegionId")
        DescribeMasterSlaveServerGroupAttributeBuilder regionId(final String regionId);

        @QueryParam("LoadBalancerId")
        DescribeMasterSlaveServerGroupAttributeBuilder loadBalancerId(final String loadBalancerId);

        @QueryParam("ListenerPort")
        DescribeMasterSlaveServerGroupAttributeBuilder listenerPort(final int listenerPort);

        @GET
        @ConstParams({"Action", "DescribeMasterSlaveServerGroupAttribute"})
        @Consumes(MediaType.APPLICATION_JSON)
        Observable<DescribeMasterSlaveServerGroupAttributeResponse> call();
    }

    // https://help.aliyun.com/document_detail/50509.html?spm=a2c4g.11186623.6.745.484861d5nfKb2G
    public DescribeMasterSlaveServerGroupAttributeBuilder describeMasterSlaveServerGroupAttribute();

    interface DescribeMasterSlaveServerGroupsResponse extends SLBAPIResponse {
    }

    @RpcBuilder
    interface DescribeMasterSlaveServerGroupsBuilder {
        @QueryParam("RegionId")
        DescribeMasterSlaveServerGroupsBuilder regionId(final String regionId);

        @QueryParam("LoadBalancerId")
        DescribeMasterSlaveServerGroupsBuilder loadBalancerId(final String loadBalancerId);

        @QueryParam("ListenerPort")
        DescribeMasterSlaveServerGroupsBuilder listenerPort(final int listenerPort);

        @GET
        @ConstParams({"Action", "DescribeMasterSlaveServerGroups"})
        @Consumes(MediaType.APPLICATION_JSON)
        Observable<DescribeMasterSlaveServerGroupsResponse> call();
    }

    // https://help.aliyun.com/document_detail/50508.html?spm=a2c4g.11186623.6.746.1a9f18780aph8Y
    public DescribeMasterSlaveServerGroupsBuilder describeMasterSlaveServerGroups();

    // https://help.aliyun.com/document_detail/35215.html?spm=a2c4g.11186623.6.742.22274267oYul4v

    // TODO : CreateVServerGroup: 调用CreateVServerGroup向指定的后端服务器组中添加后端服务器。
//  @Produces(MediaType.APPLICATION_JSON)
//  SlbXXXBuilder content(final SlbRequest content);

//    product:Slb
//    action:SetVServerGroupAttribute
//    params:
//    RegionId: "cn-beijing"LoadBalancerId: "lb-2zevw0mj6xjt37a0xyc8l"VServerGroupId: "rsp-2zehmkunz529y"VServerGroupName: "bgw"BackendServers: "[{\"Type\":\"ecs\",\"ServerId\":\"i-2zedtq67d9fm3bvq6r76\",\"Port\":8081,\"Weight\":100}]"

    interface BackendServer {
        @JSONField(name="ServerId")
        String getServerId();

        @JSONField(name="ServerId")
        void setServerId(final String serverId);

        @JSONField(name="Port")
        Integer getPort();

        @JSONField(name="Port")
        void setPort(final Integer port);

        @JSONField(name="Weight")
        Integer getWeight();

        @JSONField(name="Weight")
        void setWeight(final Integer weight);

        @JSONField(name="Description")
        String getDescription();

        @JSONField(name="Description")
        void setDescription(final String description);

        @JSONField(name="Type")
        String getType();

        @JSONField(name="Type")
        void setType(final String type);
    }

    interface BackendServers {
        @JSONField(name="BackendServer")
        BackendServer[] getBackendServer();

        @JSONField(name="BackendServer")
        void setBackendServer(final BackendServer[] servers);
    }

    interface SetVServerGroupAttributeResponse extends SLBAPIResponse {
        @JSONField(name="VServerGroupId")
        String getVServerGroupId();

        @JSONField(name="VServerGroupId")
        void setVServerGroupId(final String vServerGroupId);

        @JSONField(name="VServerGroupName")
        String getVServerGroupName();

        @JSONField(name="VServerGroupName")
        void setVServerGroupName(final String vServerGroupName);

        @JSONField(name="BackendServers")
        BackendServers getBackendServers();

        @JSONField(name="BackendServers")
        void setBackendServers(final BackendServers backendServers);
    }

    // https://help.aliyun.com/document_detail/35217.html?spm=a2c4g.11186623.6.741.6e2c7b92lpw1MB
    @RpcBuilder
    interface SetVServerGroupAttributeBuilder {
        /**
         *
         * @param regionId
         * @return SetVServerGroupAttributeBuilder
         *  必选，样例：cn-hangzhou
         *  负载均衡地域ID。
         */
        @QueryParam("RegionId")
        SetVServerGroupAttributeBuilder regionId(final String regionId);

        /**
         *
         * @param vServerGroupId
         * @return SetVServerGroupAttributeBuilder
         *  必选，样例：rsp-cige6******
         *  后端服务器组ID。
         */
        @QueryParam("VServerGroupId")
        SetVServerGroupAttributeBuilder vServerGroupId(final String vServerGroupId);

        /**
         *
         * @param vServerGroupName
         * @return SetVServerGroupAttributeBuilder
         *  非必选，样例：Group1
         *  虚拟服务器组名称。
         */
        @QueryParam("VServerGroupName")
        SetVServerGroupAttributeBuilder vServerGroupName(final String vServerGroupName);

        /**
         *
         * @param backendServers
         * @return SetVServerGroupAttributeBuilder
         *  非必选，样例：[{ "ServerId": "eni-xxxxxxxxx", "Weight": "100", "Type": "eni", "ServerIp": "192.168.**.**", "Port":"80","Description":"test-112" },{ "ServerId": "eni-xxxxxxxxx", "Weight": "100", "Type": "eni", "ServerIp": "172.166.**.**", "Port":"80","Description":"test-113" }]
         *  虚拟服务器组列表。
            单次调用最多可添加20个后端服务器。
         */
        @QueryParam("BackendServers")
        SetVServerGroupAttributeBuilder backendServers(final String backendServers);

        @GET
        @ConstParams({"Action", "SetVServerGroupAttribute"})
        @Consumes(MediaType.APPLICATION_JSON)
        Observable<SetVServerGroupAttributeResponse> call();
    }

    public SetVServerGroupAttributeBuilder setVServerGroupAttribute();

    interface AddVServerGroupBackendServersResponse extends SLBAPIResponse {

    }

    @RpcBuilder
    interface AddVServerGroupBackendServersBuilder {
        Observable<AddVServerGroupBackendServersResponse> call();
    }

    // https://help.aliyun.com/document_detail/35218.html?spm=a2c4g.11186623.6.731.7b3e5cf8eNcIAh
    public AddVServerGroupBackendServersBuilder addVServerGroupBackendServers();

    interface RemoveVServerGroupBackendServersResponse extends SLBAPIResponse {
        // rsp-cige6j*****  服务器组ID。
        @JSONField(name="VServerGroupId")
        String getVServerGroupId();

        @JSONField(name="VServerGroupId")
        void setVServerGroupId(final String vServerGroupId);

        @JSONField(name="BackendServers")
        BackendServers getBackendServers();

        @JSONField(name="BackendServers")
        void setBackendServers(final BackendServers backendServers);
    }

    @RpcBuilder
    interface RemoveVServerGroupBackendServersBuilder {
        @QueryParam("BackendServers")
        RemoveVServerGroupBackendServersBuilder backendServers(final String backendServers);

        @QueryParam("RegionId")
        RemoveVServerGroupBackendServersBuilder regionId(final String regionId);

        @QueryParam("VServerGroupId")
        RemoveVServerGroupBackendServersBuilder vServerGroupId(final String vServerGroupId);

        @GET
        @ConstParams({"Action", "RemoveVServerGroupBackendServers"})
        @Consumes(MediaType.APPLICATION_JSON)
        Observable<RemoveVServerGroupBackendServersResponse> call();
    }

    // https://help.aliyun.com/document_detail/35219.html?spm=a2c4g.11186623.6.732.23355cf8jUIMah
    public RemoveVServerGroupBackendServersBuilder removeVServerGroupBackendServers();


    interface ModifyVServerGroupBackendServersResponse extends SLBAPIResponse {
    }

    @RpcBuilder
    interface ModifyVServerGroupBackendServersBuilder {
        @QueryParam("RegionId")
        ModifyVServerGroupBackendServersBuilder regionId(final String regionId);

        @QueryParam("VServerGroupId")
        ModifyVServerGroupBackendServersBuilder vServerGroupId(final String vServerGroupId);

        @QueryParam("OldBackendServers")
        ModifyVServerGroupBackendServersBuilder oldBackendServers(final String oldBackendServers);

        @QueryParam("NewBackendServers")
        ModifyVServerGroupBackendServersBuilder newBackendServers(final String newBackendServers);

        @GET
        @ConstParams({"Action", "ModifyVServerGroupBackendServers"})
        @Consumes(MediaType.APPLICATION_JSON)
        Observable<ModifyVServerGroupBackendServersResponse> call();
    }

    // https://help.aliyun.com/document_detail/35220.html?spm=a2c4g.11186623.6.733.3db61acaPWckA6
    public ModifyVServerGroupBackendServersBuilder modifyVServerGroupBackendServers();

    interface DeleteVServerGroupResponse extends SLBAPIResponse {

    }

    @RpcBuilder
    interface DeleteVServerGroupServersBuilder {
        @QueryParam("RegionId")
        DeleteVServerGroupServersBuilder regionId(final String regionId);

        @QueryParam("VServerGroupId")
        DeleteVServerGroupServersBuilder vServerGroupId(final String vServerGroupId);

        @GET
        @ConstParams({"Action", "DeleteVServerGroup"})
        @Consumes(MediaType.APPLICATION_JSON)
        Observable<DeleteVServerGroupResponse> call();
    }

    // https://help.aliyun.com/document_detail/35221.html?spm=a2c4g.11186623.6.734.6e063221MqYOpg
    public DeleteVServerGroupServersBuilder deleteVServerGroup();

    interface DescribeVServerGroupsResponse extends SLBAPIResponse {
    }

    @RpcBuilder
    interface DescribeVServerGroupsBuilder {
        @QueryParam("RegionId")
        DescribeVServerGroupsBuilder regionId(final String regionId);

        @QueryParam("LoadBalancerId")
        DescribeVServerGroupsBuilder loadBalancerId(final String loadBalancerId);

        @GET
        @ConstParams({"Action", "DescribeVServerGroups"})
        @Consumes(MediaType.APPLICATION_JSON)
        Observable<DescribeVServerGroupsResponse> call();
    }

    // https://help.aliyun.com/document_detail/35222.html?spm=a2c4g.11186623.6.735.23473b9bJMIsMa
    public DescribeVServerGroupsBuilder describeVServerGroups();

    interface DescribeVServerGroupAttributeResponse extends SLBAPIResponse {
    }

    @RpcBuilder
    interface DescribeVServerGroupAttributeBuilder {
        @QueryParam("RegionId")
        DescribeVServerGroupAttributeBuilder regionId(final String regionId);

        @QueryParam("VServerGroupId")
        DescribeVServerGroupAttributeBuilder vServerGroupId(final String vServerGroupId);

        @QueryParam("OwnerAccount")
        DescribeVServerGroupAttributeBuilder ownerAccount(final String ownerAccount);

        @GET
        @ConstParams({"Action", "DescribeVServerGroupAttribute"})
        @Consumes(MediaType.APPLICATION_JSON)
        Observable<DescribeVServerGroupAttributeResponse> call();
    }

    // https://help.aliyun.com/document_detail/35224.html?spm=a2c4g.11186623.6.736.424f52abd6XuJh
    public DescribeVServerGroupAttributeBuilder describeVServerGroupAttribute();
}
