package org.jocean.aliyun.ccs;

import javax.crypto.Mac;

import org.jocean.http.MessageBody;
import org.jocean.http.RpcRunner;

import com.alibaba.fastjson.annotation.JSONField;

import rx.Observable;
import rx.Observable.Transformer;

public interface CCSChatAPI {

    public Mac digestInstance();

    public interface CCSResponse {
        @JSONField(name="code")
        public Long getCode();

        @JSONField(name="code")
        public void setCode(final Long code);

        @JSONField(name="msg")
        public String getMsg();

        @JSONField(name="msg")
        public void setMsg(final String msg);
    }


    public Transformer<RpcRunner, MessageBody> fetchFile(
            final String tntInstId, final String scene, final long timestamp, String fileKey);

    public interface UploadFileResponse extends CCSResponse {
        @JSONField(name="type")
        public String getType();

        @JSONField(name="type")
        public void setType(final String type);

        @JSONField(name="fileKey")
        public String getFileKey();

        @JSONField(name="fileKey")
        public void setFileKey(final String filekey);

        @JSONField(name="timestamp")
        public long getTimestamp();

        @JSONField(name="timestamp")
        public void setTimestamp(final long timestamp);
    }

    public Transformer<RpcRunner, UploadFileResponse> uploadFile(
            final String tntInstId, final String scene, final long timestamp,
            final String fileType, final String fileName,
            final Observable<? extends MessageBody> file,
            final Mac digestInstance);
}
