package org.jocean.aliyun.ecs;

import org.jocean.http.RpcRunner;

import com.alibaba.fastjson.annotation.JSONField;

import rx.Observable.Transformer;

// https://help.aliyun.com/document_detail/102988.html?spm=a2c4g.11186623.6.1069.118a79e0WI5Er2

public interface EcsAPI {
    public interface DescribeInstancesResponse {
        @JSONField(name = "Code")
        public String getCode();

        @JSONField(name = "Code")
        public void setCode(final String code);
    }

    public Transformer<RpcRunner, DescribeInstancesResponse> describeInstances(final String regionId, final String instanceName);
}
