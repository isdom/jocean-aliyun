package org.jocean.aliyun.sign;

import org.jocean.http.RpcRunner;
import org.springframework.beans.factory.annotation.Value;

import rx.functions.Action1;

public class AKSigner implements Action1<RpcRunner> {

    @Override
    public void call(final RpcRunner runner) {
        runner.oninteract(interact -> interact.onsending(SignerV1.signRequest(_ak_id, _ak_secret)));
    }

    @Value("${ak_id}")
    String _ak_id;

    @Value("${ak_secret}")
    String _ak_secret;
}
