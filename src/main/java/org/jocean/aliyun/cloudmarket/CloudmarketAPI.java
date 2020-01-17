package org.jocean.aliyun.cloudmarket;

import org.jocean.http.RpcRunner;

import com.alibaba.fastjson.annotation.JSONField;

import rx.Observable.Transformer;

public interface CloudmarketAPI {
    interface Rect {
        @JSONField(name="angle")
        double getAngle();

        @JSONField(name="angle")
        void setAngle(final double angle);

        @JSONField(name="left")
        double getLeft();

        @JSONField(name="left")
        void setLeft(final double left);

        @JSONField(name="top")
        double getTop();

        @JSONField(name="top")
        void setTop(final double top);

        @JSONField(name="width")
        double getWidth();

        @JSONField(name="width")
        void setWidth(final double width);

        @JSONField(name="height")
        double getHeight();

        @JSONField(name="height")
        void setHeight(final double height);
    }

    interface Block {
        @JSONField(name="rect")
        Rect getRect();

        @JSONField(name="rect")
        void setRect(final Rect rect);

        @JSONField(name="word")
        String getWord();

        @JSONField(name="word")
        void setWord(final String word);
    }

    interface OcrGeneralResponse {
        @JSONField(name="request_id")
        String getRequestId();

        @JSONField(name="request_id")
        void setRequestId(final String requestId);

        @JSONField(name="success")
        Boolean getSuccess();

        @JSONField(name="success")
        void setSuccess(final Boolean success);

        @JSONField(name="ret")
        Block[] getRet();

        @JSONField(name="ret")
        void setRet(final Block[] blocks);
    }

    // https://market.aliyun.com/products/57124001/cmapi020020.html?spm=5176.2020520132.101.2.1e6f7218ZrujbF#sku=yuncode1402000000
    // 印刷文字识别-通用文字识别/OCR文字识别

    Transformer<RpcRunner, OcrGeneralResponse> predictOcrGeneral(final String imageurl);
}
