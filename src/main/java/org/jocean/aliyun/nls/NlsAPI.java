package org.jocean.aliyun.nls;

import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.QueryParam;

import org.jocean.http.Interact;
import org.jocean.http.MessageBody;
import org.jocean.rpc.annotation.ResponseType;

import com.alibaba.fastjson.annotation.JSONField;

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

    interface StreamAsrV1Builder {
        @QueryParam("format")
        StreamAsrV1Builder format(final String format);

        @QueryParam("sample_rate")
        StreamAsrV1Builder sampleRate(final Integer sample_rate);

        StreamAsrV1Builder body(final MessageBody body);

        @POST
        @Path("http://nls-gateway.cn-shanghai.aliyuncs.com/stream/v1/asr")
        @ResponseType(AsrResponse.class)
        Transformer<Interact, AsrResponse> call();
    }

    public StreamAsrV1Builder streamAsrV1();

    public Transformer<Interact, AsrResponse> streamAsrV1(
            final String token,
            final MessageBody content,
            final String format,
            final int sample_rate);
}
