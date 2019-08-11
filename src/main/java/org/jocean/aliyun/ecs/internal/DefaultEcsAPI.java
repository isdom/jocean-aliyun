package org.jocean.aliyun.ecs.internal;

import org.jocean.aliyun.ecs.EcsAPI;
import org.jocean.aliyun.sign.SignerV1;
import org.jocean.http.ContentUtil;
import org.jocean.http.RpcRunner;
import org.springframework.beans.factory.annotation.Value;

import io.netty.handler.codec.http.HttpMethod;
import rx.Observable.Transformer;

public class DefaultEcsAPI implements EcsAPI {

    // https://help.aliyun.com/document_detail/102988.html?spm=a2c4g.11186623.6.1069.118a79e0WI5Er2
    @Override
    public Transformer<RpcRunner, DescribeInstancesResponse> describeInstances(final String regionId,
            final String vpcId,
            final String instanceName
            ) {
        return runners -> runners.flatMap(runner -> runner.name("aliyun.ecs.describeInstances").execute(
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

                    return interact.onrequest(SignerV1.signRequest(regionId, _ak_id, _ak_secret))
                            .responseAs(ContentUtil.ASJSON, DescribeInstancesResponse.class);
                }
        ));
    }

    @Value("${ak_id}")
    String _ak_id;

    @Value("${ak_secret}")
    String _ak_secret;
}
