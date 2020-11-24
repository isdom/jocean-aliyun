package org.jocean.aliyun.ecs;

import java.util.Date;

import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.core.MediaType;

import org.jocean.rpc.annotation.RpcBuilder;

import com.alibaba.fastjson.annotation.JSONField;

import rx.Observable;

// https://help.aliyun.com/knowledge_detail/49122.html#concept-j5w-pj4-xdb
//      实例元数据: 实例元数据包含了ECS实例在阿里云系统中的基本信息，例如实例ID、IP地址、网卡MAC地址和操作系统类型等。您可以使用元数据管理或配置ECS实例。
/*
为Windows实例获取元数据
远程连接实例。关于如何远程连接实例，请参见连接实例概述。
使用PowerShell执行命令Invoke-RestMethod http://100.100.100.200/latest/meta-data/可以获取元数据信息。
在URL中添加具体的元数据名称即可获取具体的元数据（具体参见下文实例元数据列表和动态实例元数据项），例如：
执行命令Invoke-RestMethod http://100.100.100.200/latest/meta-data/instance-id获取实例ID。
执行命令Invoke-RestMethod http://100.100.100.200/latest/meta-data/image-id获取创建实例时所使用的镜像ID。
为Linux实例获取元数据
远程连接实例。关于如何远程连接实例，请参见连接实例概述。
执行命令curl http://100.100.100.200/latest/meta-data/可以访问元数据的根目录。
在URL中添加具体的元数据名称即可获取具体的元数据（具体参见下文实例元数据列表和动态实例元数据项），例如：
执行命令curl http://100.100.100.200/latest/meta-data/instance-id获取实例ID。
执行命令curl http://100.100.100.200/latest/meta-data/image-id获取创建实例时所使用的镜像ID。
*/

public interface MetadataAPI {

    @RpcBuilder
    interface HostnameBuilder {
        @GET
        @Path("http://100.100.100.200/latest/meta-data/hostname")
        @Consumes(MediaType.TEXT_PLAIN)
        Observable<String> call();
    }

    //  获取实例的主机名
    HostnameBuilder hostname();

    @RpcBuilder
    interface RegionIdBuilder {
        @GET
        @Path("http://100.100.100.200/latest/meta-data/region-id")
        @Consumes(MediaType.TEXT_PLAIN)
        Observable<String> call();
    }

    //  获取实例所属地域
    RegionIdBuilder regionId();

    @RpcBuilder
    interface InstanceIdBuilder {
        @GET
        @Path("http://100.100.100.200/latest/meta-data/instance-id")
        @Consumes(MediaType.TEXT_PLAIN)
        Observable<String> call();
    }

    //  获取实例ID
    InstanceIdBuilder instanceId();

    @RpcBuilder
    interface PrivateIpv4Builder {
        @GET
        @Path("http://100.100.100.200/latest/meta-data/private-ipv4")
        @Consumes(MediaType.TEXT_PLAIN)
        Observable<String> call();
    }

    //  获取实例主网卡的私网IPv4地址。
    PrivateIpv4Builder privateIpv4();

    public interface STSTokenResponse {
        @JSONField(name = "Code")
        public String getCode();

        @JSONField(name = "Code")
        public void setCode(final String code);

        @JSONField(name = "AccessKeyId")
        public String getAccessKeyId();

        @JSONField(name = "AccessKeyId")
        public void setAccessKeyId(final String akid);

        @JSONField(name = "AccessKeySecret")
        public String getAccessKeySecret();

        @JSONField(name = "AccessKeySecret")
        public void setAccessKeySecret(final String aksecret);

        @JSONField(name = "SecurityToken")
        public String getSecurityToken();

        @JSONField(name = "SecurityToken")
        public void setSecurityToken(final String stsToken);

        //  UTC 通用标准时
        @JSONField(name = "Expiration")
        public void setExpiration(final Date expiration);

        @JSONField(name = "Expiration")
        public Date getExpiration();

        //  UTC 通用标准时
        @JSONField(name = "LastUpdated")
        public void setLastUpdated(final Date lastUpdated);

        @JSONField(name = "LastUpdated")
        public Date getLastUpdated();
    }

    @RpcBuilder
    interface STSTokenBuilder {
        @PathParam("roleName")
        STSTokenBuilder roleName(final String roleName);

        @GET
        @Path("http://100.100.100.200/latest/meta-data/ram/security-credentials/{roleName}")
        @Consumes(MediaType.APPLICATION_JSON)
        Observable<STSTokenResponse> call();
    }

    //  访问 http://100.100.100.200/latest/meta-data/ram/security-credentials/RoleXXX 获取 STS 临时凭证。
    //      路径最后一部分是 RAM 角色名称，您应替换为自己的创建的 RAM 角色名称。
    //  获取实例RAM角色策略所生成的STS临时凭证
    STSTokenBuilder getSTSToken();
}
