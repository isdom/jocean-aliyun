package org.jocean.aliyun.oss.internal;

import org.jocean.aliyun.oss.OssAPI.Bucketable;
import org.jocean.aliyun.oss.OssAPI.Endpointable;
import org.jocean.aliyun.oss.OssBucket;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;

class DefaultOssBucket implements OssBucket {
    private static final Logger LOG = LoggerFactory.getLogger(DefaultOssBucket.class);

    @Override
    public <BUILDER> BUILDER apply(final Endpointable<BUILDER> endpointable) {
        LOG.debug("apply endpoint({}) to {}", _ossEndpoint, endpointable);
        return endpointable.endpoint(_ossEndpoint);
    }

    @Override
    public <BUILDER> BUILDER apply(final Bucketable<BUILDER> bucketable) {
        LOG.debug("apply endpoint({}) & bucket({}) to {}", _ossEndpoint, _ossBucket, bucketable);
        bucketable.endpoint(_ossEndpoint);
        return bucketable.bucket(_ossBucket);
    }

    @Override
    public String toString() {
        final StringBuilder builder = new StringBuilder();
        builder.append("DefaultOssBucket [ossEndpoint=").append(_ossEndpoint).append(", ossBucket=")
                .append(_ossBucket).append("]");
        return builder.toString();
    }

    @Value("${oss.endpoint}")
    private String _ossEndpoint;

    @Value("${oss.bucket}")
    private String _ossBucket;
}
