package org.jocean.aliyun.ivision.internal;

import org.jocean.aliyun.ivision.IvisionAPI;
import org.jocean.http.RpcRunner;
import org.springframework.beans.factory.annotation.Value;

import com.aliyuncs.DefaultAcsClient;
import com.aliyuncs.http.FormatType;
import com.aliyuncs.ivision.model.v20190308.DeleteIterationRequest;
import com.aliyuncs.ivision.model.v20190308.DeleteIterationResponse;
import com.aliyuncs.ivision.model.v20190308.DeletePredictDatasRequest;
import com.aliyuncs.ivision.model.v20190308.DeletePredictDatasResponse;
import com.aliyuncs.ivision.model.v20190308.DescribeIterationsRequest;
import com.aliyuncs.ivision.model.v20190308.DescribeIterationsResponse;
import com.aliyuncs.ivision.model.v20190308.DescribePredictDatasRequest;
import com.aliyuncs.ivision.model.v20190308.DescribePredictDatasResponse;
import com.aliyuncs.ivision.model.v20190308.PredictImageRequest;
import com.aliyuncs.ivision.model.v20190308.PredictImageResponse;
import com.aliyuncs.profile.DefaultProfile;

import rx.Observable;
import rx.Observable.Transformer;

public class DefaultIvisionAPI implements IvisionAPI {

    @Override
    public Transformer<RpcRunner, DescribeIterationsResponse> describeIterations(final String projectId) {

        return runners -> runners.flatMap(runner -> {
            final DefaultAcsClient client = new DefaultAcsClient(DefaultProfile.getProfile(_region, _ak_id, _ak_secret));
            final DescribeIterationsRequest request = new DescribeIterationsRequest();
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
        return runners -> runners.flatMap(runner -> {
            final DefaultAcsClient client = new DefaultAcsClient(DefaultProfile.getProfile(_region, _ak_id, _ak_secret));

            final PredictImageRequest request = new PredictImageRequest();
            request.setAcceptFormat(FormatType.JSON);

            request.setProjectId(projectId);
            request.setIterationId(iterationId);
            request.setDataUrls(imgurl);

            // If an error occurs, a ClientException or ServerException may be thrown.
            try {
                final PredictImageResponse response = client.getAcsResponse(request);
                return Observable.just(response);
            } catch (final Exception e) {
                return Observable.error(e);
            }
        });
    }

    @Override
    public Transformer<RpcRunner, DescribePredictDatasResponse> describePredictDatas(final String projectId, final String iterationId) {
        return runners -> runners.flatMap(runner -> {
            final DefaultAcsClient client = new DefaultAcsClient(DefaultProfile.getProfile(_region, _ak_id, _ak_secret));

            final DescribePredictDatasRequest request = new DescribePredictDatasRequest();
            request.setAcceptFormat(FormatType.JSON);

            request.setProjectId(projectId);
            request.setIterationId(iterationId);

            // If an error occurs, a ClientException or ServerException may be thrown.
            try {
                final DescribePredictDatasResponse response = client.getAcsResponse(request);
                return Observable.just(response);
            } catch (final Exception e) {
                return Observable.error(e);
            }
        });
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
