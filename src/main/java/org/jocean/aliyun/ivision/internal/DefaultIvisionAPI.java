package org.jocean.aliyun.ivision.internal;

import org.jocean.aliyun.ivision.IvisionAPI;
import org.jocean.aliyun.sign.SignerV1;
import org.jocean.http.ContentUtil;
import org.jocean.http.RpcRunner;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;

import com.aliyuncs.DefaultAcsClient;
import com.aliyuncs.http.FormatType;
import com.aliyuncs.ivision.model.v20190308.CreateProjectRequest;
import com.aliyuncs.ivision.model.v20190308.CreateProjectResponse;
import com.aliyuncs.ivision.model.v20190308.DeleteIterationRequest;
import com.aliyuncs.ivision.model.v20190308.DeleteIterationResponse;
import com.aliyuncs.ivision.model.v20190308.DeletePredictDatasRequest;
import com.aliyuncs.ivision.model.v20190308.DeletePredictDatasResponse;
import com.aliyuncs.ivision.model.v20190308.DescribeIterationsRequest;
import com.aliyuncs.ivision.model.v20190308.DescribeIterationsResponse;
import com.aliyuncs.profile.DefaultProfile;

import io.netty.handler.codec.http.HttpMethod;
import rx.Observable;
import rx.Observable.Transformer;

public class DefaultIvisionAPI implements IvisionAPI {

    private static final Logger LOG = LoggerFactory.getLogger(DefaultIvisionAPI.class);

    @Override
    public Transformer<RpcRunner, CreateProjectResponse> createProject(final String name, final String description, final String type) {

        return runners -> runners.flatMap(runner -> {
            final DefaultAcsClient client = new DefaultAcsClient(DefaultProfile.getProfile(_region, _ak_id, _ak_secret));
            final CreateProjectRequest request = new CreateProjectRequest();
            request.setAcceptFormat(FormatType.JSON);
            request.setName(name);
            request.setDescription(description);
            request.setProType(type);

            // If an error occurs, a ClientException or ServerException may be thrown.
            try {
                final CreateProjectResponse response = client.getAcsResponse(request);
                return Observable.just(response);
            } catch (final Exception e) {
                return Observable.error(e);
            }
        });
    }

    @Override
    public Transformer<RpcRunner, DescribeIterationsResponse> describeIterations(final String projectId) {

        return runners -> runners.flatMap(runner -> {
            final DefaultAcsClient client = new DefaultAcsClient(DefaultProfile.getProfile(_region, _ak_id, _ak_secret));
            final DescribeIterationsRequest request = new DescribeIterationsRequest();
            request.setAcceptFormat(FormatType.JSON);
            request.setProjectId(projectId);

            // If an error occurs, a ClientException or ServerException may be thrown.
            try {
                final DescribeIterationsResponse response = client.getAcsResponse(request);
                return Observable.just(response);
            } catch (final Exception e) {
                return Observable.error(e);
            }
        });
    }

    @Override
    public Transformer<RpcRunner, DeleteIterationResponse> deleteIteration(final String projectId, final String iterationId) {
        return runners -> runners.flatMap(runner -> {
            final DefaultAcsClient client = new DefaultAcsClient(DefaultProfile.getProfile(_region, _ak_id, _ak_secret));
            final DeleteIterationRequest request = new DeleteIterationRequest();

            request.setAcceptFormat(FormatType.JSON);
            request.setProjectId(projectId);
            request.setIterationId(iterationId);

            // If an error occurs, a ClientException or ServerException may be thrown.
            try {
                final DeleteIterationResponse response = client.getAcsResponse(request);
                return Observable.just(response);
            } catch (final Exception e) {
                return Observable.error(e);
            }
        });
    }

    @Override
    public Transformer<RpcRunner, PredictImageResponse> predictImage(final String projectId, final String iterationId, final String imgurl) {
        return runners -> runners.flatMap(runner ->
            runner.name("ivision.predictImage").execute(interact -> interact.method(HttpMethod.GET)
                .uri("http://ivision.cn-beijing.aliyuncs.com")
                .path("/")
                .paramAsQuery("Action", "PredictImage")
                .paramAsQuery("Version", "2019-03-08")
                .paramAsQuery("ProjectId", projectId)
                .paramAsQuery("IterationId", iterationId)
                .paramAsQuery("DataUrls", imgurl)
                .onrequest(SignerV1.signRequest(_region, _ak_id, _ak_secret))
                .responseAs(ContentUtil.ASJSON, PredictImageResponse.class)
            ));
    }

    @Override
    public Transformer<RpcRunner, DescribePredictDatasResponse> describePredictDatas(final String projectId, final String iterationId, final String dataIds) {
        return runners -> runners.flatMap(runner ->
            runner.name("ivision.describePredictDatas").execute(interact -> interact.method(HttpMethod.GET)
                    .uri("http://ivision.cn-beijing.aliyuncs.com")
                    .path("/")
                    .paramAsQuery("Action", "DescribePredictDatas")
                    .paramAsQuery("Version", "2019-03-08")
                    .paramAsQuery("ProjectId", projectId)
                    .paramAsQuery("IterationId", iterationId)
                    .paramAsQuery("DataIds", dataIds)
                    .onrequest(SignerV1.signRequest(_region, _ak_id, _ak_secret))
                    .responseAs(ContentUtil.ASJSON, DescribePredictDatasResponse.class)
            ));
    }

    @Override
    public Transformer<RpcRunner, DeletePredictDatasResponse> deletePredictDatas(final String projectId, final String dataIds) {
        return runners -> runners.flatMap(runner -> {
            final DefaultAcsClient client = new DefaultAcsClient(DefaultProfile.getProfile(_region, _ak_id, _ak_secret));

            final DeletePredictDatasRequest request = new DeletePredictDatasRequest();
            request.setAcceptFormat(FormatType.JSON);

            request.setProjectId(projectId);
            request.setDataIds(dataIds);

            // If an error occurs, a ClientException or ServerException may be thrown.
            try {
                final DeletePredictDatasResponse response = client.getAcsResponse(request);
                return Observable.just(response);
            } catch (final Exception e) {
                return Observable.error(e);
            }
        });
    }

    @Value("${regionid}")
    String _region;

    @Value("${ak_id}")
    String _ak_id;

    @Value("${ak_secret}")
    String _ak_secret;
}
