package org.jocean.aliyun.ivision;

import org.jocean.http.RpcRunner;

import com.aliyuncs.ivision.model.v20190308.DeleteIterationResponse;
import com.aliyuncs.ivision.model.v20190308.DeletePredictDatasResponse;
import com.aliyuncs.ivision.model.v20190308.DescribeIterationsResponse;
import com.aliyuncs.ivision.model.v20190308.DescribePredictDatasResponse;
import com.aliyuncs.ivision.model.v20190308.PredictImageResponse;

import rx.Observable.Transformer;

public interface IvisionAPI {

    public Transformer<RpcRunner, DescribeIterationsResponse> describeIterations(final String projectId);

    public Transformer<RpcRunner, DeleteIterationResponse> deleteIteration(final String projectId, final String iterationId);

    public Transformer<RpcRunner, PredictImageResponse> predictImage(final String projectId, final String iterationId, final String imgurl);

    public Transformer<RpcRunner, DescribePredictDatasResponse> describePredictDatas(final String projectId, final String iterationId);

    public Transformer<RpcRunner, DeletePredictDatasResponse> deletePredictDatas(final String projectId, final String dataIds);

}
