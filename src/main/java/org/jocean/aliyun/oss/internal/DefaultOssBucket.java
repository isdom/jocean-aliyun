package org.jocean.aliyun.oss.internal;

import org.jocean.aliyun.oss.OssBucket;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;

class DefaultOssBucket implements OssBucket {
    private static final Logger LOG = LoggerFactory.getLogger(DefaultOssBucket.class);

    @Override
    public String endpoint() {
        return _ossEndpoint;
    }

    @Override
    public String bucket() {
        return _ossBucket;
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
