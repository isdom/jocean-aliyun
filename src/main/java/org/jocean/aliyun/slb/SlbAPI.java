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

    // https://help.aliyun.com/document_detail/35215.html?spm=a2c4g.11186623.6.742.22274267oYul4v

    // TODO : CreateVServerGroup: 调用CreateVServerGroup向指定的后端服务器组中添加后端服务器。
//  @Produces(MediaType.APPLICATION_JSON)
//  SlbXXXBuilder content(final SlbRequest content);
}
