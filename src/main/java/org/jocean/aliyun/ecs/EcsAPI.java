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

        @JSONField(name="ExpiredTime")
        public String getExpiredTime();

        @JSONField(name="ExpiredTime")
        public void setExpiredTime(final String expiredTime);

        @JSONField(name="HostName")
        public String getHostName();

        @JSONField(name="HostName")
        public void setHostName(final String hostName);

        @JSONField(name="ImageId")
        public String getImageId();

        @JSONField(name="ImageId")
        public void setImageId(final String imageId);

        @JSONField(name="InnerIpAddress")
        public String getInnerIpAddress();

        @JSONField(name="InnerIpAddress")
        public void setInnerIpAddress(final String innerIpAddress);

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

//        SecurityGroupIds        sg-securitygroupid1
//        实例所属安全组集合。
//
//        "SecurityGroupIds":{
//            "SecurityGroupId":[
//                "sg-2ze7v8o1cbogte75c8xz"
//            ]
//        },

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
}
