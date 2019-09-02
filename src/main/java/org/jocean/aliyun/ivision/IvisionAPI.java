package org.jocean.aliyun.ivision;

import javax.ws.rs.QueryParam;

import org.jocean.aliyun.ecs.EcsAPI.RebootInstanceBuilder;
import org.jocean.http.RpcRunner;

import com.alibaba.fastjson.annotation.JSONField;
import com.aliyuncs.ivision.model.v20190308.CreateProjectResponse;
import com.aliyuncs.ivision.model.v20190308.DeleteIterationResponse;
import com.aliyuncs.ivision.model.v20190308.DeletePredictDatasResponse;
import com.aliyuncs.ivision.model.v20190308.DescribeIterationsResponse;

import rx.Observable.Transformer;

public interface IvisionAPI {
    public interface IvisionResponse {
        @JSONField(name="Code")
        public String getCode();

        @JSONField(name="Code")
        public void seCode(final String Code);

        @JSONField(name="RequestId")
        public String getRequestId();

        @JSONField(name="RequestId")
        public void setRequestId(final String requestId);

        @JSONField(name="PageSize")
        public Integer getPageSize();

        @JSONField(name="PageSize")
        public void sePageSize(final Integer pageSize);

        @JSONField(name="TotalNum")
        public Integer getTotalNum();

        @JSONField(name="TotalNum")
        public void seTotalNum(final Integer number);

        @JSONField(name="CurrentPage")
        public Integer getCurrentPage();

        @JSONField(name="CurrentPage")
        public void seCurrentPage(final Integer currentPage);
    }

    public interface Region {
        @JSONField(name="Left")
        public String getLeft();

        @JSONField(name="Left")
        public void setLeft(final String left);

        @JSONField(name="Top")
        public String getTop();

        @JSONField(name="Top")
        public void setTop(final String top);

        @JSONField(name="Width")
        public String getWidth();

        @JSONField(name="Width")
        public void setWidth(final String width);

        @JSONField(name="Height")
        public String getHeight();

        @JSONField(name="Height")
        public void setHeight(final String height);
    }

    public interface PredictionResult {
        @JSONField(name="Probability")
        public double getProbability();

        @JSONField(name="Probability")
        public void setProbability(final double probability);

        @JSONField(name="TagId")
        public String getTagId();

        @JSONField(name="TagId")
        public void setTagId(final String tagId);

        @JSONField(name="TagName")
        public String getTagName();

        @JSONField(name="TagName")
        public void setTagName(final String tagName);

        @JSONField(name="RegionType")
        public String getRegionType();

        @JSONField(name="RegionType")
        public void setRegionType(final String regionType);

        @JSONField(name="Region")
        public Region getRegion();

        @JSONField(name="Region")
        public void setRegion(final Region region);
    }

    public interface PredictionResults {
        @JSONField(name="PredictionResult")
        public PredictionResult[] getPredictionResult();

        @JSONField(name="PredictionResult")
        public void setPredictionResult(final PredictionResult[] results);
    }

    public interface PredictData {
        @JSONField(name="Status")
        public String getStatus();

        @JSONField(name="Status")
        public void setStatus(final String status);

        @JSONField(name="DataUrl")
        public String getDataUrl();

        @JSONField(name="DataUrl")
        public void setDataUrl(final String dataUrl);

        @JSONField(name="PredictionResults")
        public PredictionResults getPredictionResults();

        @JSONField(name="PredictionResults")
        public void setPredictionResults(final PredictionResults results);

        @JSONField(name="DataId")
        public String getDataId();

        @JSONField(name="DataId")
        public void setDataId(final String dataId);

        @JSONField(name="DataName")
        public String getDataName();

        @JSONField(name="DataName")
        public void setDataName(final String dataName);

        @JSONField(name="IterationId")
        public String getIterationId();

        @JSONField(name="IterationId")
        public void setIterationId(final String iterationId);

        @JSONField(name="ProjectId")
        public String getProjectId();

        @JSONField(name="ProjectId")
        public void setProjectId(final String projectId);
    }

    public interface PredictDatas {
        @JSONField(name="PredictData")
        public PredictData[] getPredictData();

        @JSONField(name="PredictData")
        public void setPredictData(final PredictData[] datas);
    }

    public Transformer<RpcRunner, CreateProjectResponse> createProject(final String name, final String description, final String type);

    public Transformer<RpcRunner, DescribeIterationsResponse> describeIterations(final String projectId);

    public Transformer<RpcRunner, DeleteIterationResponse> deleteIteration(final String projectId, final String iterationId);

/*
    |00000170| 2e 76 32 30 31 30 31 32 30 35 29 0d 0a 0d 0a 7b |.v20101205)....{|
    |00000180| 22 50 72 65 64 69 63 74 44 61 74 61 73 22 3a 7b |"PredictDatas":{|
    |00000190| 22 50 72 65 64 69 63 74 44 61 74 61 22 3a 5b 7b |"PredictData":[{|
    |000001a0| 22 53 74 61 74 75 73 22 3a 22 50 72 65 64 69 63 |"Status":"Predic|
    |000001b0| 74 69 6e 67 22 2c 22 44 61 74 61 55 72 6c 22 3a |ting","DataUrl":|
    |000001c0| 22 68 74 74 70 73 3a 2f 2f 67 64 74 2d 64 65 70 |"https://gdt-dep|
    |000001d0| 6f 74 2e 6f 73 73 2d 63 6e 2d 62 65 69 6a 69 6e |ot.oss-cn-beijin|
    |000001e0| 67 2e 61 6c 69 79 75 6e 63 73 2e 63 6f 6d 2f 75 |g.aliyuncs.com/u|
    |000001f0| 73 65 72 2f 65 61 73 79 61 72 2f 70 72 65 64 69 |ser/easyar/predi|
    |00000200| 63 74 2f 33 31 66 64 61 62 31 38 33 36 62 32 64 |ct/31fdab1836b2d|
    |00000210| 32 33 30 39 63 32 64 37 30 37 36 33 62 61 62 62 |2309c2d70763babb|
    |00000220| 62 63 32 22 2c 22 44 61 74 61 49 64 22 3a 22 61 |bc2","DataId":"a|
    |00000230| 38 30 37 38 64 37 31 64 33 36 33 34 36 39 63 39 |8078d71d363469c9|
    |00000240| 33 62 32 34 37 61 64 34 34 64 34 65 32 39 39 22 |3b247ad44d4e299"|
    |00000250| 2c 22 49 74 65 72 61 74 69 6f 6e 49 64 22 3a 22 |,"IterationId":"|
    |00000260| 66 62 34 63 37 61 64 34 31 64 65 35 34 33 64 66 |fb4c7ad41de543df|
    |00000270| 39 31 64 64 64 33 31 62 37 63 34 36 63 33 66 37 |91ddd31b7c46c3f7|
    |00000280| 22 2c 22 44 61 74 61 4e 61 6d 65 22 3a 22 33 31 |","DataName":"31|
    |00000290| 66 64 61 62 31 38 33 36 62 32 64 32 33 30 39 63 |fdab1836b2d2309c|
    |000002a0| 32 64 37 30 37 36 33 62 61 62 62 62 63 32 22 2c |2d70763babbbc2",|
    |000002b0| 22 50 72 6f 6a 65 63 74 49 64 22 3a 22 30 61 34 |"ProjectId":"0a4|
    |000002c0| 39 39 33 34 39 66 38 39 35 34 30 34 31 61 36 30 |99349f8954041a60|
    |000002d0| 61 30 61 35 36 62 30 30 37 33 39 62 32 22 7d 5d |a0a56b00739b2"}]|
    |000002e0| 7d 2c 22 52 65 71 75 65 73 74 49 64 22 3a 22 43 |},"RequestId":"C|
    |000002f0| 34 31 33 37 42 39 41 2d 34 32 39 38 2d 34 43 41 |4137B9A-4298-4CA|
    |00000300| 31 2d 41 37 38 33 2d 44 38 34 39 35 42 42 34 31 |1-A783-D8495BB41|
    |00000310| 33 41 42 22 7d                                  |3AB"}           |
*/
    public interface PredictImageResponse extends IvisionResponse {
        @JSONField(name="PredictDatas")
        public PredictDatas getPredictDatas();

        @JSONField(name="PredictDatas")
        public void setPredictDatas(final PredictDatas datas);
    }

    interface PredictImageBuilder {
        @QueryParam("ProjectId")
        PredictImageBuilder projectId(final String projectId);

        @QueryParam("IterationId")
        PredictImageBuilder iterationId(final String iterationId);

        @QueryParam("DataUrls")
        RebootInstanceBuilder imgurls(final String imgurls);

        Transformer<RpcRunner, PredictImageResponse> call();
    }

    public PredictImageBuilder predictImage();

    public interface DescribePredictDatasResponse extends IvisionResponse {
        @JSONField(name="PredictDatas")
        public PredictDatas getPredictDatas();

        @JSONField(name="PredictDatas")
        public void setPredictDatas(final PredictDatas datas);
    }

    interface DescribePredictDatasBuilder {
        @QueryParam("ProjectId")
        DescribePredictDatasBuilder projectId(final String projectId);

        @QueryParam("IterationId")
        DescribePredictDatasBuilder iterationId(final String iterationId);

        @QueryParam("DataIds")
        DescribePredictDatasBuilder DataIds(final String dataIds);

        Transformer<RpcRunner, DescribePredictDatasResponse> call();
    }

    public DescribePredictDatasBuilder describePredictDatas();

    public Transformer<RpcRunner, DeletePredictDatasResponse> deletePredictDatas(final String projectId, final String dataIds);

}
