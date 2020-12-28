package org.jocean.aliyun.sign;

import org.jocean.http.Interact;
import org.jocean.rpc.annotation.RpcResource;

import rx.Observable.Transformer;

public interface AliyunSignable<BUILDER> {
    @RpcResource("signer")
    BUILDER signer(final Transformer<Interact, Interact> signer);
}
