package org.jocean.aliyun.nls;

import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.core.MediaType;

import org.jocean.http.Interact;
import org.jocean.rpc.annotation.ConstParams;
import org.jocean.rpc.annotation.ResponseType;

import com.alibaba.fastjson.annotation.JSONField;

import rx.Observable.Transformer;

public interface NlsmetaAPI {
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

    interface CreateTokenBuilder {
        @GET
        @Path("http://nls-meta.cn-shanghai.aliyuncs.com/")
        @ConstParams({"Action", "CreateToken", "Version", "2019-02-28", "RegionId", "cn-shanghai"})
        @Consumes(MediaType.APPLICATION_JSON)
        @ResponseType(CreateTokenResponse.class)
        Transformer<Interact, CreateTokenResponse> call();
    }

    // https://help.aliyun.com/document_detail/113251.html?spm=a2c4g.11186623.2.16.5bca7229C5kw00
    //  获取Token协议说明
    public CreateTokenBuilder createToken();
}
