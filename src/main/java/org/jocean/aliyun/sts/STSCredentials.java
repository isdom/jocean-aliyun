/**
 *
 */
package org.jocean.aliyun.sts;

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
    public /*Date*/String getExpiration();

    //  UTC 通用标准时
    public /*Date*/String getLastUpdated();
}
