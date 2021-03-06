package org.jocean.aliyun.nls;

import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.QueryParam;

import org.jocean.http.Interact;
import org.jocean.http.MessageBody;
import org.jocean.rpc.annotation.RpcBuilder;
import org.jocean.rpc.annotation.RpcResource;

import com.alibaba.fastjson.annotation.JSONField;

import rx.Observable;
import rx.Observable.Transformer;

public interface NlsAPI {

    public interface AsrResponse {
        @JSONField(name="status")
        public int getStatus();

        @JSONField(name="status")
        public void setStatus(final int status);

        @JSONField(name="message")
        public String getMessage();

        @JSONField(name="message")
        public void setMessage(final String message);

        @JSONField(name="task_id")
        public String getTaskId();

        @JSONField(name="task_id")
        public void setTaskId(final String task_id);

        @JSONField(name="result")
        public String getResult();

        @JSONField(name="result")
        public void setResult(final String result);
    }

    @RpcBuilder
    interface StreamAsrV1Builder {
        @RpcResource("nlstoken")
        StreamAsrV1Builder nlstoken(final Transformer<Interact, Interact> nlstoken);

        @RpcResource("appkey")
        StreamAsrV1Builder appkey(final Transformer<Interact, Interact> appkey);

        @QueryParam("format")
        StreamAsrV1Builder format(final String format);

        @QueryParam("sample_rate")
        StreamAsrV1Builder sampleRate(final Integer sample_rate);

        StreamAsrV1Builder body(final Observable<MessageBody> body);

        @POST
        @Path("http://nls-gateway.cn-shanghai.aliyuncs.com/stream/v1/asr")
        Observable<AsrResponse> call();
    }

    // https://help.aliyun.com/document_detail/92131.html?spm=a2c4g.11186623.6.573.68fc7142HRaq2y
    public StreamAsrV1Builder streamAsrV1();
}
