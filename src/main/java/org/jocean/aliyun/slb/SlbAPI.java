package org.jocean.aliyun.slb;

import com.alibaba.fastjson.annotation.JSONField;

public interface SlbAPI {

    interface SLBAPIResponse {
        @JSONField(name="RequestId")
        String getRequestId();

        @JSONField(name="RequestId")
        void setRequestId(final String requestId);

        @JSONField(name="Code")
        String getCode();

        @JSONField(name="Code")
        void setCode(final String code);

        @JSONField(name="Message")
        String getMessage();

        @JSONField(name="Message")
        void setMessage(final String message);
    }
}
