package org.jocean.aliyun.nls;

import org.jocean.http.MessageBody;
import org.jocean.http.RpcRunner;

import com.alibaba.fastjson.annotation.JSONField;

import rx.Observable.Transformer;

public interface NlsAPI {
    public interface NlsResponse {
        @JSONField(name="NlsRequestId")
        public String getNlsRequestId();

        @JSONField(name="NlsRequestId")
        public void seNlsRequestId(final String nlsRequestId);

        @JSONField(name="RequestId")
        public String getRequestId();

        @JSONField(name="RequestId")
        public void setRequestId(final String requestId);

        @JSONField(name="ErrMsg")
        public String getErrMsg();

        @JSONField(name="ErrMsg")
        public void setErrMsg(final String errMsg);
    }

    public interface NlsToken {
        @JSONField(name="ExpireTime")
        public long getExpireTime();

        @JSONField(name="ExpireTime")
        public void seExpireTime(final long expireTime);

        @JSONField(name="Id")
        public String getId();

        @JSONField(name="Id")
        public void setId(final String id);

        @JSONField(name="UserId")
        public String getUserId();

        @JSONField(name="UserId")
        public void setUserId(final String userId);
    }

    public interface CreateTokenResponse extends NlsResponse {
        @JSONField(name="Token")
        public NlsToken getNlsToken();

        @JSONField(name="Token")
        public void setNlsToken(final NlsToken token);
    }

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

    interface CreateTokenBuilder {
        Transformer<RpcRunner, CreateTokenResponse> call();
    }

    public CreateTokenBuilder createToken();
    public Transformer<RpcRunner, AsrResponse> streamAsrV1(final MessageBody content,
            final String format,
            final int sample_rate);
}
