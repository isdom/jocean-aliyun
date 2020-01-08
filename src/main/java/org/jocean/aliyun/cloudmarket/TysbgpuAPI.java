package org.jocean.aliyun.cloudmarket;

import javax.ws.rs.QueryParam;

import org.jocean.http.RpcRunner;

import rx.Observable.Transformer;

// https://market.aliyun.com/products/57124001/cmapi020020.html?spm=5176.2020520132.101.2.1e6f7218ZrujbF#sku=yuncode1402000000
// 印刷文字识别-通用文字识别/OCR文字识别
public interface TysbgpuAPI {
    interface PredictOcrGeneralResponse {
    }

    interface PredictOcrGeneralBuilder {
        @QueryParam("InstanceIds")
        PredictOcrGeneralBuilder instanceIds(final String[] instanceIds);

        @QueryParam("RamRoleName")
        PredictOcrGeneralBuilder ramRoleName(final String ramRoleName);

        @QueryParam("RegionId")
        PredictOcrGeneralBuilder regionId(final String regionId);

        Transformer<RpcRunner, PredictOcrGeneralResponse> call();
    }

    PredictOcrGeneralBuilder predictOcrGeneral();
}
