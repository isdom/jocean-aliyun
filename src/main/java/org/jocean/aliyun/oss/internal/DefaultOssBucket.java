package org.jocean.aliyun.oss.internal;

import org.jocean.aliyun.oss.OssAPI.Bucketable;
import org.jocean.aliyun.oss.OssAPI.Endpointable;
import org.jocean.aliyun.oss.OssBucket;
import org.springframework.beans.factory.annotation.Value;

class DefaultOssBucket implements OssBucket {

    @SuppressWarnings("unchecked")
    @Override
    public <BUILDER> BUILDER apply(final Endpointable<BUILDER> endpointable) {
        endpointable.endpoint(_ossEndpoint);

        return (BUILDER) endpointable;
    }

    @SuppressWarnings("unchecked")
    @Override
    public <BUILDER> BUILDER apply(final Bucketable<BUILDER> bucketable) {
        bucketable.endpoint(_ossEndpoint);
        bucketable.bucket(_ossBucket);
        return (BUILDER) bucketable;
    }

    @Value("${oss.endpoint}")
    private String _ossEndpoint;

    @Value("${oss.bucket}")
    private String _ossBucket;
}
