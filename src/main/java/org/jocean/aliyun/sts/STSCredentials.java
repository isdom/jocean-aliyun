/**
 *
 */
package org.jocean.aliyun.sts;

import java.util.Date;

/**
 * @author isdom
 *
 */
public interface STSCredentials {
    public String getInstanceId();

    public String getAccessKeyId();

    public String getAccessKeySecret();

    public String getSecurityToken();

    //  UTC 通用标准时
    public Date getExpiration();

    //  UTC 通用标准时
    public Date getLastUpdated();
}
