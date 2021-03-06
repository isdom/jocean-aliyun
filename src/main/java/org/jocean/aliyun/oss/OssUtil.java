package org.jocean.aliyun.oss;

import org.jocean.http.FullMessage;
import org.jocean.http.MessageUtil;

import io.netty.handler.codec.http.HttpResponse;
import rx.Observable;
import rx.Observable.Transformer;

public class OssUtil {
    private OssUtil() {
        throw new IllegalStateException("No instances!");
    }

    public static <T> Observable<? extends T> extractAndReturnOSSError(final FullMessage<HttpResponse> resp,final String msg) {
        return resp.body().flatMap(body -> MessageUtil.<OssError>decodeXmlAs(body, OssError.class))
                .flatMap(error -> Observable.error(new RuntimeException(null != msg ? msg + "/" + error.toString() : error.toString())));
    }

    public static Transformer<FullMessage<HttpResponse>, FullMessage<HttpResponse>> checkOssError() {
        return OssAPI.CHECK_OSSERROR;
    }
}
