package org.jocean.aliyun;

import rx.Observable;

public interface BlobRepo {
    public Observable<String> putBlob(
            final String key,
            final String contentType,
            final byte[] content);
    
    public Observable<byte[]> getBlob(final String key);
}
