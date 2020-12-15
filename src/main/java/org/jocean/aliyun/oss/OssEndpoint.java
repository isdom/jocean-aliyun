/**
 *
 */
package org.jocean.aliyun.oss;

import org.jocean.aliyun.oss.OssAPI.Endpointable;

/**
 * @author isdom
 *
 */
public interface OssEndpoint {
    <BUILDER> BUILDER apply(final Endpointable<BUILDER> endpointable);
}
