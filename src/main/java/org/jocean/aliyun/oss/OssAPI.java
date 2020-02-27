package org.jocean.aliyun.oss;

import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;

import org.jocean.http.FullMessage;
import org.jocean.http.Interact;
import org.jocean.http.MessageBody;

import io.netty.handler.codec.http.HttpResponse;
import rx.Observable;
import rx.Observable.Transformer;

public interface OssAPI {
    interface PutObjectBuilder {

        @PathParam("object")
        PutObjectBuilder object(final String object);

        @PathParam("bucket")
        PutObjectBuilder bucket(final String bucket);

        @PathParam("endpoint")
        PutObjectBuilder endpoint(final String endpoint);

        PutObjectBuilder body(final Observable<MessageBody> body);

        @PUT
        @Path("http://{bucket}.{endpoint}/{object}")
        Transformer<Interact, FullMessage<HttpResponse>> call();
    }

    public PutObjectBuilder putObject();
}
