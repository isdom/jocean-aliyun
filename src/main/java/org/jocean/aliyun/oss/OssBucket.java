/**
 *
 */
package org.jocean.aliyun.oss;

import org.jocean.aliyun.oss.OssAPI.Bucketable;

/**
 * @author isdom
 *
 */
public interface OssBucket extends OssEndpoint {
    <BUILDER> BUILDER apply(final Bucketable<BUILDER> bucketable);

}
