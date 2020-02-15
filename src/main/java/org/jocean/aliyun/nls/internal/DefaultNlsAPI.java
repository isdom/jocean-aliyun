package org.jocean.aliyun.nls.internal;

import org.jocean.aliyun.nls.NlsAPI;
import org.jocean.http.ContentUtil;
import org.jocean.http.MessageBody;
import org.jocean.http.RpcRunner;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;

import io.netty.handler.codec.http.HttpHeaderNames;
import io.netty.handler.codec.http.HttpHeaderValues;
import io.netty.handler.codec.http.HttpMethod;
import io.netty.handler.codec.http.HttpRequest;
import io.netty.handler.codec.http.HttpUtil;
import rx.Observable;
import rx.Observable.Transformer;

public class DefaultNlsAPI implements NlsAPI {

    private static final Logger LOG = LoggerFactory.getLogger(DefaultNlsAPI.class);

    @Override
    public Transformer<RpcRunner, AsrResponse> streamAsrV1(
            final String token,
            final MessageBody content,
            final String format,
            final int sample_rate) {
        return runners -> runners.flatMap(runner -> runner.name("nls.streamAsrV1").execute(
                interact -> {
                    interact = interact.method(HttpMethod.POST)
                        .uri("http://nls-gateway.cn-shanghai.aliyuncs.com")
                        .path("/stream/v1/asr")
                        .paramAsQuery("appkey", _appkey);

                    if (null != format) {
                        interact = interact.paramAsQuery("format", format);
                    }

                    if (sample_rate > 0) {
                        interact = interact.paramAsQuery("sample_rate", Integer.toString(sample_rate));
                    }

                    return interact.onrequest( obj -> {
                        if (obj instanceof HttpRequest) {
                            final HttpRequest req = (HttpRequest)obj;
                            req.headers().set("X-NLS-Token", token);
                            req.headers().set(HttpHeaderNames.CONTENT_TYPE, HttpHeaderValues.APPLICATION_OCTET_STREAM);
                            HttpUtil.setContentLength(req, content.contentLength());
                        }
                    })
                    .body(Observable.just(content))
                    .responseAs(ContentUtil.ASJSON, AsrResponse.class);
                }
            ));
    }

    @Value("${appkey}")
    String _appkey;
}
