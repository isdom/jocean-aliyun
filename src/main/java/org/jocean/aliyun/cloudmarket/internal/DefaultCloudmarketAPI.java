package org.jocean.aliyun.cloudmarket.internal;

import org.jocean.aliyun.cloudmarket.CloudmarketAPI;
import org.jocean.http.ContentUtil;
import org.jocean.http.RpcRunner;
import org.springframework.beans.factory.annotation.Value;

import com.alibaba.fastjson.annotation.JSONField;

import io.netty.handler.codec.http.HttpMethod;
import io.netty.handler.codec.http.HttpRequest;
import rx.Observable.Transformer;

public class DefaultCloudmarketAPI implements CloudmarketAPI {

    static class OcrGeneralReq {
        @JSONField(name = "image")
        public String getImage() {
            return this._imageUrl;
        }

        @JSONField(name = "image")
        public void setImage(final String url) {
            this._imageUrl = url;
        }

        private String _imageUrl;
    }

    @Override
    public Transformer<RpcRunner, OcrGeneralResponse> predictOcrGeneral(final String imageurl) {
        return runners -> runners.flatMap(runner -> runner.name("ocr.general").execute(interact -> {
            final OcrGeneralReq req = new OcrGeneralReq();
            req.setImage(imageurl);

            return interact.method(HttpMethod.POST).uri("http://tysbgpu.market.alicloudapi.com")
                    .path("/api/predict/ocr_general")
                    .onrequest( obj -> {
                        if (obj instanceof HttpRequest) {
                            ((HttpRequest)obj).headers().set("Authorization", "APPCODE "+ _appCode);
                        }
                    })
                    .body(req, ContentUtil.TOJSON)
                    .responseAs(ContentUtil.ASJSON, OcrGeneralResponse.class);
        }));
    }

    @Value("${cloudmarket.appcode}")
    String _appCode;
}
