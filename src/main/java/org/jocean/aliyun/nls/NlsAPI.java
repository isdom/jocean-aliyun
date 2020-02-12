package org.jocean.aliyun.nls;

import org.jocean.aliyun.nls.NlsmetaAPI.CreateTokenBuilder;
import org.jocean.http.MessageBody;
import org.jocean.http.RpcRunner;

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


    public Transformer<RpcRunner, AsrResponse> streamAsrV1(
            CreateTokenBuilder builder,
            final MessageBody content,
            final String format,
            final int sample_rate);
}
