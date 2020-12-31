/**
 *
 */
package org.jocean.aliyun.sts;

import org.jocean.http.Interact;

import rx.Observable.Transformer;

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

    public Transformer<Interact, Interact> ossSigner();

    public Transformer<Interact, Interact> aliSigner();
}
