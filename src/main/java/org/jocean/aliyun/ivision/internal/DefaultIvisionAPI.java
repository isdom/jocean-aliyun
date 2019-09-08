package org.jocean.aliyun.ivision.internal;

import org.jocean.aliyun.ecs.internal.DefaultEcsAPI;
import org.jocean.aliyun.ivision.IvisionAPI;
import org.jocean.http.ContentUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;

import io.netty.handler.codec.http.HttpMethod;

public class DefaultIvisionAPI implements IvisionAPI {

    private static final Logger LOG = LoggerFactory.getLogger(DefaultIvisionAPI.class);

    @Override
    public CreateProjectBuilder createProject() {
        return DefaultEcsAPI.delegate(CreateProjectBuilder.class,
                "aliyun.ivision.createProject",
                interact -> interact.method(HttpMethod.GET)
                    .uri("http://ivision.cn-beijing.aliyuncs.com")
                    .path("/")
                    .paramAsQuery("RegionId", _region)
                    .paramAsQuery("Action", "CreateProject")
                    .paramAsQuery("Version", "2019-03-08")
                    .responseAs(ContentUtil.ASJSON, CreateProjectResponse.class)
            );
    }

    @Override
    public DescribeIterationsBuilder describeIterations() {
        return DefaultEcsAPI.delegate(DescribeIterationsBuilder.class,
                "aliyun.ivision.describeIterations",
                interact -> interact.method(HttpMethod.GET)
                    .uri("http://ivision.cn-beijing.aliyuncs.com")
                    .path("/")
                    .paramAsQuery("RegionId", _region)
                    .paramAsQuery("Action", "DescribeIterations")
                    .paramAsQuery("Version", "2019-03-08")
                    .responseAs(ContentUtil.ASJSON, DescribeIterationsResponse.class)
            );
    }

    @Override
    public DeleteIterationBuilder deleteIteration() {
        return DefaultEcsAPI.delegate(DeleteIterationBuilder.class,
                "aliyun.ivision.deleteIteration",
                interact -> interact.method(HttpMethod.GET)
                    .uri("http://ivision.cn-beijing.aliyuncs.com")
                    .path("/")
                    .paramAsQuery("RegionId", _region)
                    .paramAsQuery("Action", "DeleteIteration")
                    .paramAsQuery("Version", "2019-03-08")
                    .responseAs(ContentUtil.ASJSON, DeleteIterationResponse.class)
            );
    }

    @Override
    public PredictImageBuilder predictImage() {
        return DefaultEcsAPI.delegate(PredictImageBuilder.class,
                "aliyun.ivision.predictImage",
                interact -> interact.method(HttpMethod.GET)
                    .uri("http://ivision.cn-beijing.aliyuncs.com")
                    .path("/")
                    .paramAsQuery("RegionId", _region)
                    .paramAsQuery("Action", "PredictImage")
                    .paramAsQuery("Version", "2019-03-08")
                    .responseAs(ContentUtil.ASJSON, PredictImageResponse.class)
            );
    }

    @Override
    public DescribePredictDatasBuilder describePredictDatas() {
        return DefaultEcsAPI.delegate(DescribePredictDatasBuilder.class,
                "aliyun.ivision.describePredictDatas",
                interact -> interact.method(HttpMethod.GET)
                    .uri("http://ivision.cn-beijing.aliyuncs.com")
                    .path("/")
                    .paramAsQuery("RegionId", _region)
                    .paramAsQuery("Action", "DescribePredictDatas")
                    .paramAsQuery("Version", "2019-03-08")
                    .responseAs(ContentUtil.ASJSON, DescribePredictDatasResponse.class)
            );
    }

    @Override
    public DeletePredictDatasBuilder deletePredictDatas() {
        return DefaultEcsAPI.delegate(DeletePredictDatasBuilder.class,
                "aliyun.ivision.deletePredictDatas",
                interact -> interact.method(HttpMethod.GET)
                    .uri("http://ivision.cn-beijing.aliyuncs.com")
                    .path("/")
                    .paramAsQuery("RegionId", _region)
                    .paramAsQuery("Action", "DeletePredictDatas")
                    .paramAsQuery("Version", "2019-03-08")
                    .responseAs(ContentUtil.ASJSON, DeletePredictDatasResponse.class)
            );
    }

    @Value("${regionid}")
    String _region;

    @Value("${ak_id}")
    String _ak_id;

    @Value("${ak_secret}")
    String _ak_secret;
}
