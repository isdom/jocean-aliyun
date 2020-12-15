package org.jocean.aliyun.oss.internal;

import org.jocean.aliyun.oss.OssAPI.Bucketable;
import org.jocean.aliyun.oss.OssAPI.Endpointable;
import org.jocean.aliyun.oss.OssBucket;
import org.springframework.beans.factory.annotation.Value;

class DefaultOssBucket implements OssBucket {
    @Override
    public <BUILDER> BUILDER apply(final Endpointable<BUILDER> endpointable) {
        return endpointable.endpoint(_ossEndpoint);
    }

    @Override
    public <BUILDER> BUILDER apply(final Bucketable<BUILDER> bucketable) {
        bucketable.endpoint(_ossEndpoint);
        return bucketable.bucket(_ossBucket);
    }

    @Value("${oss.endpoint}")
    private String _ossEndpoint;

    @Value("${oss.bucket}")
    private String _ossBucket;
}
