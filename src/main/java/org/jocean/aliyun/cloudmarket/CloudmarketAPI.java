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

    interface Point {
        @JSONField(name="x")
        int getX();

        @JSONField(name="x")
        void setX(final int x);

        @JSONField(name="y")
        int getY();

        @JSONField(name="y")
        void setY(final int y);
    }

    interface WordInfo {
        @JSONField(name="pos")
        Point[] getRect();

        @JSONField(name="pos")
        void setRect(final Point[] points);

        @JSONField(name="word")
        String getWord();

        @JSONField(name="word")
        void setWord(final String word);
    }

    interface OcrDocumentResponse {
        @JSONField(name="sid")
        String getSid();

        @JSONField(name="sid")
        void setSid(final String sid);

        @JSONField(name="prism_version")
        String getVersion();

        @JSONField(name="prism_version")
        void setVersion(final String version);

        @JSONField(name="prism_wnum")
        void setWordNum(final int wnum);

        @JSONField(name="prism_wnum")
        int getWordNum();


        @JSONField(name="prism_wordsInfo")
        WordInfo[] getWordsInfo();

        @JSONField(name="prism_wordsInfo")
        void setWordsInfo(final WordInfo[] info);
    }

    // https://market.aliyun.com/products/57124001/cmapi023866.html?spm=5176.2020520132.101.2.57c97218LZKqqU#sku=yuncode1786600000
    public Transformer<RpcRunner, OcrDocumentResponse> predictOcrDocument(final String imageurl);
}
