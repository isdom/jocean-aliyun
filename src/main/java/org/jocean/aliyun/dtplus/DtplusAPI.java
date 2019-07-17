package org.jocean.aliyun.dtplus;

import org.jocean.http.RpcRunner;

import com.alibaba.fastjson.annotation.JSONField;

import rx.Observable.Transformer;

public interface DtplusAPI {

    static interface ImageTag {
        @JSONField(name="confidence")
        public int getConfidence();

        @JSONField(name="confidence")
        public void setConfidence(final int confidence);

        @JSONField(name="value")
        public String getValue();

        @JSONField(name="value")
        public void setValue(final String value);
    }

    static interface ImageTagResponse {
        @JSONField(name="errno")
        public int getErrno();

        @JSONField(name="errno")
        public void setErrno(final int errno);

        @JSONField(name="err_msg")
        public String getErrmsg();

        @JSONField(name="err_msg")
        public void setErrmsg(final String errmsg);

        @JSONField(name="request_id")
        public String getRequestId();

        @JSONField(name="request_id")
        public void setRequestId(final String requestId);

        @JSONField(name="tags")
        public ImageTag[] getTags();

        @JSONField(name="tags")
        public void setTags(final ImageTag[] tags);
    }

    static class ImageTagRequest {
        @JSONField(name="type")
        public int getType() {
            return _type;
        }

        @JSONField(name="image_url")
        public String getUrl() {
            return _url;
        }

        @JSONField(name="image_url")
        public void setUrl(final String url) {
            this._url = url;
        }

        private final int _type = 0;
        private String _url;
    }

    public Transformer<RpcRunner, ImageTagResponse> imagetag (final String imgurl);
}
