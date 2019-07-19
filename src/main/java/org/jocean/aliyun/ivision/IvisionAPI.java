package org.jocean.aliyun.ivision;

import org.jocean.http.RpcRunner;

import com.aliyuncs.ivision.model.v20190308.DescribeIterationsResponse;
import com.aliyuncs.ivision.model.v20190308.PredictImageResponse;

import rx.Observable.Transformer;

public interface IvisionAPI {

    public Transformer<RpcRunner, DescribeIterationsResponse> describeIterations(final String projectId);

    public Transformer<RpcRunner, PredictImageResponse> predictImage(final String projectId, final String iterationId, final String imgurl);
}
