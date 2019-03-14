package org.jocean.aliyun.ccs;

import javax.crypto.Mac;

import org.jocean.http.MessageBody;
import org.jocean.http.RpcRunner;

import rx.Observable;
import rx.Observable.Transformer;

public interface CCSChatAPI {
    public Mac digestInstance();

    public Transformer<RpcRunner, String> uploadFile(
            final String tntInstId, final String scene, final long timestamp,
            final String fileType, final String fileName,
            final Observable<? extends MessageBody> file,
            final Mac digestInstance);
}
