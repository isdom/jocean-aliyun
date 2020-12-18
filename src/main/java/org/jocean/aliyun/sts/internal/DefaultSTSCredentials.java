package org.jocean.aliyun.sts.internal;

import org.jocean.aliyun.sign.Signer4OSS;
import org.jocean.aliyun.sts.STSCredentials;
import org.jocean.http.Interact;
import org.springframework.beans.factory.annotation.Value;

import rx.Observable.Transformer;

public class DefaultSTSCredentials implements STSCredentials {

    @Override
    public Transformer<Interact, Interact> ossSigner() {
        return interacts -> interacts.doOnNext(
                interact -> interact.onsending(Signer4OSS.signRequest(this)));
    }

    @Override
    public String toString() {
        final StringBuilder builder = new StringBuilder();
        builder.append("DefaultSTSCredentials [instanceId=").append(_instanceId).append(", accessKeyId=")
                .append(_accessKeyId).append(", accessKeySecret=").append(_accessKeySecret).append(", securityToken=")
                .append(_securityToken).append(", expiration=").append(_expiration).append(", lastUpdated=")
                .append(_lastUpdated).append("]");
        return builder.toString();
    }

    @Override
    public String getInstanceId() {
        return _instanceId;
    }

    @Override
    public String getAccessKeyId() {
        return _accessKeyId;
    }

    @Override
    public String getAccessKeySecret() {
        return _accessKeySecret;
    }

    @Override
    public String getSecurityToken() {
        return _securityToken;
    }

    @Override
    public /*Date*/String getExpiration() {
        return _expiration;
//        try {
//            return DateFormat.getDateTimeInstance().parse(_expiration);
//        } catch (final ParseException e) {
//            e.printStackTrace();
//            return null;
//        }
    }

    @Override
    public /*Date*/String getLastUpdated() {
        return _lastUpdated;
//        try {
//            return DateFormat.getDateTimeInstance().parse(_lastUpdated);
//        } catch (final ParseException e) {
//            e.printStackTrace();
//            return null;
//        }
    }

    @Value("${sts.instance_id}")
    String _instanceId;

    @Value("${sts.ak_id}")
    String _accessKeyId;

    @Value("${sts.ak_secret}")
    String _accessKeySecret;

    @Value("${sts.token}")
    String _securityToken;

    @Value("${sts.expiration}")
    String _expiration;

    @Value("${sts.lastupdated}")
    String _lastUpdated;
}
