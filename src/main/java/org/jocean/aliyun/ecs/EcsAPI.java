package org.jocean.aliyun.ecs;

import org.jocean.http.RpcRunner;

import com.alibaba.fastjson.annotation.JSONField;

import rx.Observable.Transformer;

// https://help.aliyun.com/document_detail/102988.html?spm=a2c4g.11186623.6.1069.118a79e0WI5Er2

public interface EcsAPI {


    public interface OperationLocks {
        @JSONField(name="LockMsg")
        public String getLockMsg();

        @JSONField(name="LockMsg")
        public void setLockMsg(final String lockMsg);

        @JSONField(name="LockReason")
        public String getLockReason();

        @JSONField(name="LockReason")
        public void setLockReason(final String lockReason);
    }

    public interface InstanceAttributesType {
        @JSONField(name="HostName")
        public String getHostName();

        @JSONField(name="HostName")
        public void setHostName(final String hostName);

        @JSONField(name="InstanceId")
        public String getInstanceId();

        @JSONField(name="InstanceId")
        public void setInstanceId(final String instanceId);

        @JSONField(name="InstanceName")
        public String getInstanceName();

        @JSONField(name="InstanceName")
        public void setInstanceName(final String instanceName);

        @JSONField(name="OperationLocks")
        public OperationLocks getOperationLocks();

        @JSONField(name="OperationLocks")
        public void setOperationLocks(final OperationLocks operationLocks);
    }

    public interface DescribeInstancesResponse {
        @JSONField(name="RequestId")
        public String getRequestId();

        @JSONField(name="RequestId")
        public void setRequestId(final String requestId);

        @JSONField(name="Instances")
        public InstanceAttributesType[] getInstances();

        @JSONField(name="Instances")
        public void setInstances(final InstanceAttributesType[] instances);
    }

    public Transformer<RpcRunner, DescribeInstancesResponse> describeInstances(final String regionId, final String instanceName);
}
