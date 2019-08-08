package org.jocean.aliyun.nls.internal;

import org.jocean.aliyun.nls.NlsAPI;
import org.jocean.aliyun.sign.SignerV1;
import org.jocean.http.ContentUtil;
import org.jocean.http.RpcRunner;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;

import io.netty.handler.codec.http.HttpMethod;
import rx.Observable.Transformer;

public class DefaultNlsAPI implements NlsAPI {

    private static final Logger LOG = LoggerFactory.getLogger(DefaultNlsAPI.class);

    @Override
    public Transformer<RpcRunner, CreateTokenResponse> createToken() {
        return runners -> runners.flatMap(runner ->
        runner.name("nls.createToken").execute(interact -> interact.method(HttpMethod.GET)
            .uri("http://nls-meta.cn-shanghai.aliyuncs.com")
            .path("/")
            .paramAsQuery("Action", "CreateToken")
            .paramAsQuery("Version", "2019-02-28")
            .onrequest(SignerV1.signRequest(_region, _ak_id, _ak_secret))
            .responseAs(ContentUtil.ASJSON, CreateTokenResponse.class)
        ));
    }

    @Value("${regionid}")
    String _region;

    @Value("${ak_id}")
    String _ak_id;

    @Value("${ak_secret}")
    String _ak_secret;
}
