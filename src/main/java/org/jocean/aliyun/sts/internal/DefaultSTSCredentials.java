package org.jocean.aliyun.sts.internal;

import java.text.DateFormat;
import java.text.ParseException;
import java.util.Date;

import org.jocean.aliyun.sts.STSCredentials;
import org.springframework.beans.factory.annotation.Value;

public class DefaultSTSCredentials implements STSCredentials {

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
    public Date getExpiration() {
        try {
            return DateFormat.getDateTimeInstance().parse(_expiration);
        } catch (final ParseException e) {
            e.printStackTrace();
            return null;
        }
    }

    @Override
    public Date getLastUpdated() {
        try {
            return DateFormat.getDateTimeInstance().parse(_lastUpdated);
        } catch (final ParseException e) {
            e.printStackTrace();
            return null;
        }
    }

    @Value("${instance_id}")
    String _instanceId;

    @Value("${accesskey_id(}")
    String _accessKeyId;

    @Value("${accesskey_secret}")
    String _accessKeySecret;

    @Value("${security_token")
    String _securityToken;

    @Value("${expiration}")
    String _expiration;

    @Value("${lastupdated}")
    String _lastUpdated;
}
