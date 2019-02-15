package org.jocean.aliyun.ecs;

import java.util.Date;

import org.jocean.http.RpcRunner;

import com.alibaba.fastjson.annotation.JSONField;

import rx.Observable.Transformer;

// https://help.aliyun.com/knowledge_detail/49122.html#concept-j5w-pj4-xdb
//      实例元数据: 实例元数据包含了ECS实例在阿里云系统中的基本信息，例如实例ID、IP地址、网卡MAC地址和操作系统类型等。您可以使用元数据管理或配置ECS实例。

public interface MetadataAPI {

    //  获取实例的主机名
    public Transformer<RpcRunner, String> getHostname();

    //  获取实例所属地域
    public Transformer<RpcRunner, String> getRegionId();

    //  获取实例ID
    public Transformer<RpcRunner, String> getInstanceId();

    //  获取实例主网卡的私网IPv4地址。
    public Transformer<RpcRunner, String> getPrivateIpv4();

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

    //  获取实例RAM角色策略所生成的STS临时凭证
    public Transformer<RpcRunner, STSTokenResponse> getSTSToken(final String roleName);
}
