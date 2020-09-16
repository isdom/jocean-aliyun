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

    interface ModifyLoadBalancerPayTypeBuilder {
        @QueryParam("RegionId")
        ModifyLoadBalancerPayTypeBuilder regionId(final String regionId);

        @QueryParam("LoadBalancerId")
        ModifyLoadBalancerPayTypeBuilder loadBalancerId(final String loadBalancerId);
        @GET
        @ConstParams({"Action", "ModifyLoadBalancerPayType"})
        @Consumes(MediaType.APPLICATION_JSON)
        Observable<ModifyLoadBalancerPayTypeResponse> call();
    }

    // https://help.aliyun.com/document_detail/59589.html?spm=a2c4g.11186623.6.693.56b5480fRBnAw1
    public ModifyLoadBalancerPayTypeBuilder modifyLoadBalancerPayType();

    interface ModifyLoadBalancerInstanceSpecResponse extends SLBAPIResponse {
    }

    interface ModifyLoadBalancerInstanceSpecBuilder {
        @QueryParam("RegionId")
        ModifyLoadBalancerInstanceSpecBuilder regionId(final String regionId);

        @QueryParam("LoadBalancerId")
        ModifyLoadBalancerInstanceSpecBuilder loadBalancerId(final String loadBalancerId);
        @GET
        @ConstParams({"Action", "ModifyLoadBalancerInstanceSpec"})
        @Consumes(MediaType.APPLICATION_JSON)
        Observable<ModifyLoadBalancerInstanceSpecResponse> call();
    }

    // https://help.aliyun.com/document_detail/53360.html?spm=a2c4g.11186623.6.694.7eee6554xoKouH
    public ModifyLoadBalancerInstanceSpecBuilder modifyLoadBalancerInstanceSpec();

    interface DescribeZonesResponse extends SLBAPIResponse {
    }

    interface DescribeZonesBuilder {
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

    interface DescribeRegionsResponse extends SLBAPIResponse {
    }

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

    interface DescribeLoadBalancersResponse extends SLBAPIResponse {
    }

    interface DescribeLoadBalancersBuilder {
        @QueryParam("RegionId")
        DescribeLoadBalancersBuilder regionId(final String regionId);

        @QueryParam("LoadBalancerId")
        DescribeLoadBalancersBuilder loadBalancerId(final String loadBalancerId);

        @GET
        @ConstParams({"Action", "DescribeLoadBalancers"})
        @Consumes(MediaType.APPLICATION_JSON)
        Observable<DescribeLoadBalancersResponse> call();
    }

    // https://help.aliyun.com/document_detail/27582.html?spm=a2c4g.11186623.6.699.ecfd693cxFMAFY
    public DescribeLoadBalancersBuilder describeLoadBalancers();

    interface SetLoadBalancerNameResponse extends SLBAPIResponse {
    }

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
    }

    interface ModifyLoadBalancerInternetSpecBuilder {
        @QueryParam("RegionId")
        ModifyLoadBalancerInternetSpecBuilder regionId(final String regionId);

        @QueryParam("LoadBalancerId")
        ModifyLoadBalancerInternetSpecBuilder loadBalancerId(final String loadBalancerId);

        @QueryParam("InternetChargeType")
        ModifyLoadBalancerInternetSpecBuilder internetChargeType(final String internetChargeType);

        @GET
        @ConstParams({"Action", "ModifyLoadBalancerInternetSpec"})
        @Consumes(MediaType.APPLICATION_JSON)
        Observable<ModifyLoadBalancerInternetSpecResponse> call();
    }

    // https://help.aliyun.com/document_detail/27578.html?spm=a2c4g.11186623.6.702.256d7df4v1o4jn
    public ModifyLoadBalancerInternetSpecBuilder modifyLoadBalancerInternetSpec();

    interface SetLoadBalancerDeleteProtectionResponse extends SLBAPIResponse {
    }

    interface SetLoadBalancerDeleteProtectionBuilder {
        @QueryParam("RegionId")
        SetLoadBalancerDeleteProtectionBuilder regionId(final String regionId);

        @QueryParam("LoadBalancerId")
        SetLoadBalancerDeleteProtectionBuilder loadBalancerId(final String loadBalancerId);

        @QueryParam("DeleteProtection")
        SetLoadBalancerDeleteProtectionBuilder deleteProtection(final String deleteProtection);

        @GET
        @ConstParams({"Action", "SetLoadBalancerDeleteProtection"})
        @Consumes(MediaType.APPLICATION_JSON)
        Observable<SetLoadBalancerDeleteProtectionResponse> call();
    }

    // https://help.aliyun.com/document_detail/122674.html?spm=a2c4g.11186623.6.703.332b78a7NdfVVh
    public SetLoadBalancerDeleteProtectionBuilder setLoadBalancerDeleteProtection();

    interface CreateLoadBalancerHTTPListenerResponse extends SLBAPIResponse {
    }

    interface CreateLoadBalancerHTTPListenerBuilder {
        @QueryParam("RegionId")
        CreateLoadBalancerHTTPListenerBuilder regionId(final String regionId);

        @QueryParam("LoadBalancerId")
        CreateLoadBalancerHTTPListenerBuilder loadBalancerId(final String loadBalancerId);

        @QueryParam("ListenerPort")
        CreateLoadBalancerHTTPListenerBuilder listenerPort(final int listenerPort);

        @GET
        @ConstParams({"Action", "CreateLoadBalancerHTTPListener"})
        @Consumes(MediaType.APPLICATION_JSON)
        Observable<CreateLoadBalancerHTTPListenerResponse> call();
    }

    // https://help.aliyun.com/document_detail/27592.html?spm=a2c4g.11186623.6.713.6be528d3HFwHR0
    public CreateLoadBalancerHTTPListenerBuilder createLoadBalancerHTTPListener();

    interface DescribeLoadBalancerHTTPListenerAttributeResponse extends SLBAPIResponse {
    }

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

    interface SetLoadBalancerHTTPListenerAttributeBuilder {
        @QueryParam("RegionId")
        SetLoadBalancerHTTPListenerAttributeBuilder regionId(final String regionId);

        @QueryParam("LoadBalancerId")
        SetLoadBalancerHTTPListenerAttributeBuilder loadBalancerId(final String loadBalancerId);

        @QueryParam("ListenerPort")
        SetLoadBalancerHTTPListenerAttributeBuilder listenerPort(final int listenerPort);

        @GET
        @ConstParams({"Action", "SetLoadBalancerHTTPListenerAttribute"})
        @Consumes(MediaType.APPLICATION_JSON)
        Observable<SetLoadBalancerHTTPListenerAttributeResponse> call();
    }

    // https://help.aliyun.com/document_detail/27602.html?spm=a2c4g.11186623.6.715.61341ed0U0iKiY
    public SetLoadBalancerHTTPListenerAttributeBuilder setLoadBalancerHTTPListenerAttribute();

    interface CreateLoadBalancerHTTPSListenerResponse extends SLBAPIResponse {
    }

    interface CreateLoadBalancerHTTPSListenerBuilder {
        @QueryParam("RegionId")
        CreateLoadBalancerHTTPSListenerBuilder regionId(final String regionId);

        @QueryParam("LoadBalancerId")
        CreateLoadBalancerHTTPSListenerBuilder loadBalancerId(final String loadBalancerId);

        @QueryParam("ListenerPort")
        CreateLoadBalancerHTTPSListenerBuilder listenerPort(final int listenerPort);

        @GET
        @ConstParams({"Action", "CreateLoadBalancerHTTPSListener"})
        @Consumes(MediaType.APPLICATION_JSON)
        Observable<CreateLoadBalancerHTTPSListenerResponse> call();
    }

    // https://help.aliyun.com/document_detail/27593.html?spm=a2c4g.11186623.6.717.7c5ef5dbRQLorb
    public CreateLoadBalancerHTTPSListenerBuilder createLoadBalancerHTTPSListener();

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
