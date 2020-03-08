package org.jocean.aliyun.sign;

import org.jocean.http.Interact;
import org.springframework.beans.factory.annotation.Value;

public class AKSigner implements AliyunSigner {

    @Override
    public void call(final Interact interact) {
        interact.onsending(SignerV1.signRequest(_ak_id, _ak_secret));
    }

    @Value("${ak_id}")
    String _ak_id;

    @Value("${ak_secret}")
    String _ak_secret;
}
