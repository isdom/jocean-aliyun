package org.jocean.aliyun.ecs.internal;

import org.jocean.aliyun.ecs.MetadataAPI;
import org.jocean.http.ContentUtil;
import org.jocean.http.RpcRunner;

import io.netty.handler.codec.http.HttpMethod;
import rx.Observable;
import rx.Observable.Transformer;

public class DefaultMetadataAPI implements MetadataAPI {

    private static final String METADATA_URI = "http://100.100.100.200";
    private static final String METADATA_PATH_BASE = "/latest/meta-data/";

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

    //      /hostname
    @Override
    public Transformer<RpcRunner, String> getHostname() {
        return runners -> runners.flatMap(runner -> runner.name("aliyun.ecs.getHostname").execute(interact -> {
            try {
                return interact.method(HttpMethod.GET).uri(METADATA_URI).path(METADATA_PATH_BASE + "hostname")
                        .responseAs(ContentUtil.ASTEXT, String.class);
            } catch (final Exception e) {
                return Observable.error(e);
            }
        }));
    }

    @Override
    public Transformer<RpcRunner, String> getRegionId() {
        return runners -> runners.flatMap(runner -> runner.name("aliyun.ecs.getRegionId").execute(interact -> {
            try {
                return interact.method(HttpMethod.GET).uri(METADATA_URI).path(METADATA_PATH_BASE + "region-id")
                        .responseAs(ContentUtil.ASTEXT, String.class);
            } catch (final Exception e) {
                return Observable.error(e);
            }
        }));
    }

    @Override
    public Transformer<RpcRunner, String> getInstanceId() {
        return runners -> runners.flatMap(runner -> runner.name("aliyun.ecs.getInstanceId").execute(interact -> {
            try {
                return interact.method(HttpMethod.GET).uri(METADATA_URI).path(METADATA_PATH_BASE + "instance-id")
                        .responseAs(ContentUtil.ASTEXT, String.class);
            } catch (final Exception e) {
                return Observable.error(e);
            }
        }));
    }

    //  /private-ipv4
    @Override
    public Transformer<RpcRunner, String> getPrivateIpv4() {
        return runners -> runners.flatMap(runner -> runner.name("aliyun.ecs.getPrivateIpv4").execute(interact -> {
            try {
                return interact.method(HttpMethod.GET).uri(METADATA_URI).path(METADATA_PATH_BASE + "private-ipv4")
                        .responseAs(ContentUtil.ASTEXT, String.class);
            } catch (final Exception e) {
                return Observable.error(e);
            }
        }));
    }

    //  访问 http://100.100.100.200/latest/meta-data/ram/security-credentials/EcsRamRoleTest 获取 STS 临时凭证。
    //      路径最后一部分是 RAM 角色名称，您应替换为自己的创建的 RAM 角色名称。
    @Override
    public Transformer<RpcRunner, STSTokenResponse> getSTSToken(final String roleName) {
        return runners -> runners.flatMap(runner -> runner.name("aliyun.ecs.getSTSToken").execute(interact -> {
            try {
                return interact.method(HttpMethod.GET).uri(METADATA_URI).path(METADATA_PATH_BASE + "ram/security-credentials/" + roleName)
                        .responseAs(ContentUtil.ASJSON, STSTokenResponse.class);
            } catch (final Exception e) {
                return Observable.error(e);
            }
        }));
    }
}
