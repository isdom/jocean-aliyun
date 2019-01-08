package org.jocean.aliyun;

import java.util.Date;

import org.jocean.http.FullMessage;
import org.jocean.http.MessageBody;
import org.jocean.http.RpcRunner;

import io.netty.handler.codec.http.HttpResponse;
import rx.Observable.Transformer;

public interface BlobRepo {
    interface PutObjectResult {
        public String objectName();
        public String etag();
    }

    public interface PutObjectBuilder {

        //  required
        public PutObjectBuilder objectName(final String objectName);

        //  required
        public PutObjectBuilder content(final MessageBody body);

        public Transformer<RpcRunner, PutObjectResult> build();
    }

    /**
     * put object to OSS's Bucket
     * @return
     */
    public PutObjectBuilder putObject();

    public interface SimplifiedObjectMeta {
        public String getETag();
        public long getSize();
        public Date getLastModified();
    }

    public Transformer<RpcRunner, SimplifiedObjectMeta> getSimplifiedObjectMeta(final String objectName);

    public Transformer<RpcRunner, MessageBody> getObject(final String objname);

    public Transformer<RpcRunner, FullMessage<HttpResponse>> listObjects( String prefix);

    public Transformer<RpcRunner, CopyObjectResult> copyObject(final String sourceKey, final String destinationKey);

    public Transformer<RpcRunner, String> deleteObject(final String key);
}
