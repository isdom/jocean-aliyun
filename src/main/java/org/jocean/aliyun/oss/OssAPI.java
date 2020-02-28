package org.jocean.aliyun.oss;

import java.util.ArrayList;
import java.util.List;

import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.HEAD;
import javax.ws.rs.HeaderParam;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;

import org.jocean.aliyun.BlobRepo.OSSObjectSummary;
import org.jocean.http.FullMessage;
import org.jocean.http.Interact;
import org.jocean.http.MessageBody;

import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlElementWrapper;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlProperty;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlRootElement;

import io.netty.handler.codec.http.HttpResponse;
import rx.Observable;
import rx.Observable.Transformer;

public interface OssAPI {
    interface PutObjectBuilder {

        @PathParam("object")
        PutObjectBuilder object(final String object);

        @PathParam("bucket")
        PutObjectBuilder bucket(final String bucket);

        @PathParam("endpoint")
        PutObjectBuilder endpoint(final String endpoint);

        PutObjectBuilder body(final Observable<MessageBody> body);

        @PUT
        @Path("http://{bucket}.{endpoint}/{object}")
        Transformer<Interact, FullMessage<HttpResponse>> call();
    }

    public PutObjectBuilder putObject();

    interface GetObjectBuilder {

        @PathParam("object")
        GetObjectBuilder object(final String object);

        @PathParam("bucket")
        GetObjectBuilder bucket(final String bucket);

        @PathParam("endpoint")
        GetObjectBuilder endpoint(final String endpoint);

        @GET
        @Path("http://{bucket}.{endpoint}/{object}")
        Transformer<Interact, FullMessage<HttpResponse>> call();
    }

    public GetObjectBuilder getObject();

    @Consumes({"application/xml","text/xml"})
    @JacksonXmlRootElement(localName="ListBucketResult")
    public static class ObjectListing {

        @Override
        public String toString() {
            final int maxLen = 10;
            final StringBuilder builder = new StringBuilder();
            builder.append("ObjectListing [objectSummaries=")
                    .append(objectSummaries != null
                            ? objectSummaries.subList(0, Math.min(objectSummaries.size(), maxLen)) : null)
                    .append(", commonPrefixes=")
                    .append(commonPrefixes != null ? commonPrefixes.subList(0, Math.min(commonPrefixes.size(), maxLen))
                            : null)
                    .append(", bucketName=").append(bucketName).append(", nextMarker=").append(nextMarker)
                    .append(", isTruncated=").append(isTruncated).append(", prefix=").append(prefix).append(", marker=")
                    .append(marker).append(", maxKeys=").append(maxKeys).append(", delimiter=").append(delimiter)
                    .append(", encodingType=").append(encodingType).append("]");
            return builder.toString();
        }

        /**
         * A list of summary information describing the objects stored in the bucket
         */
        private final List<OSSObjectSummary> objectSummaries = new ArrayList<OSSObjectSummary>();

        private final List<String> commonPrefixes = new ArrayList<String>();

        private String bucketName;

        private String nextMarker;

        private boolean isTruncated;

        private String prefix;

        private String marker;

        private int maxKeys;

        private String delimiter;

        private String encodingType;

        public List<OSSObjectSummary> getObjectSummaries() {
            return objectSummaries;
        }

        public void addObjectSummary(final OSSObjectSummary objectSummary) {
            this.objectSummaries.add(objectSummary);
        }

        @JacksonXmlProperty(localName="Contents")
        @JacksonXmlElementWrapper(useWrapping = false)
        public void setObjectSummaries(final List<OSSObjectSummary> objectSummaries) {
            this.objectSummaries.clear();
            if (objectSummaries != null && !objectSummaries.isEmpty()) {
                this.objectSummaries.addAll(objectSummaries);
            }
        }

        public void clearObjectSummaries() {
            this.objectSummaries.clear();
        }

        public List<String> getCommonPrefixes() {
            return commonPrefixes;
        }

        public void addCommonPrefix(final String commonPrefix) {
            this.commonPrefixes.add(commonPrefix);
        }

        public void setCommonPrefixes(final List<String> commonPrefixes) {
            this.commonPrefixes.clear();
            if (commonPrefixes != null && !commonPrefixes.isEmpty()) {
                this.commonPrefixes.addAll(commonPrefixes);
            }
        }

        public void clearCommonPrefixes() {
            this.commonPrefixes.clear();
        }

        public String getNextMarker() {
            return nextMarker;
        }

        @JacksonXmlProperty(localName="NextMarker")
        public void setNextMarker(final String nextMarker) {
            this.nextMarker = nextMarker;
        }

        public String getBucketName() {
            return bucketName;
        }

        @JacksonXmlProperty(localName="Name")
        public void setBucketName(final String bucketName) {
            this.bucketName = bucketName;
        }

        public String getPrefix() {
            return prefix;
        }

        @JacksonXmlProperty(localName="Prefix")
        public void setPrefix(final String prefix) {
            this.prefix = prefix;
        }

        public String getMarker() {
            return marker;
        }

        @JacksonXmlProperty(localName="Marker")
        public void setMarker(final String marker) {
            this.marker = marker;
        }

        public int getMaxKeys() {
            return maxKeys;
        }

        @JacksonXmlProperty(localName="MaxKeys")
        public void setMaxKeys(final int maxKeys) {
            this.maxKeys = maxKeys;
        }

        public String getDelimiter() {
            return delimiter;
        }

        @JacksonXmlProperty(localName="Delimiter")
        public void setDelimiter(final String delimiter) {
            this.delimiter = delimiter;
        }

        public String getEncodingType() {
            return encodingType;
        }

        @JacksonXmlProperty(localName="EncodingType")
        public void setEncodingType(final String encodingType) {
            this.encodingType = encodingType;
        }

        public boolean isTruncated() {
            return isTruncated;
        }

        @JacksonXmlProperty(localName="IsTruncated")
        public void setTruncated(final boolean isTruncated) {
            this.isTruncated = isTruncated;
        }
    }

    interface ListObjectsBuilder {

        @QueryParam("prefix")
        ListObjectsBuilder prefix(final String prefix);

        @PathParam("bucket")
        ListObjectsBuilder bucket(final String bucket);

        @PathParam("endpoint")
        ListObjectsBuilder endpoint(final String endpoint);

        @GET
        @Path("http://{bucket}.{endpoint}/")
        @Consumes(MediaType.APPLICATION_XML)
        Transformer<Interact, ObjectListing> call();
    }

    public ListObjectsBuilder listObjects();

    interface GetSimplifiedObjectMetaBuilder {

        @PathParam("object")
        GetSimplifiedObjectMetaBuilder object(final String object);

        @PathParam("bucket")
        GetSimplifiedObjectMetaBuilder bucket(final String bucket);

        @PathParam("endpoint")
        GetSimplifiedObjectMetaBuilder endpoint(final String endpoint);

        @HEAD
        @Path("http://{bucket}.{endpoint}/{object}?objectMeta")
        Transformer<Interact, FullMessage<HttpResponse>> call();
    }

    public GetSimplifiedObjectMetaBuilder getSimplifiedObjectMeta();

    /* REF: https://help.aliyun.com/document_detail/31979.html?spm=a2c4g.11186623.6.926.p75n2Q
     * API:
    PUT /DestObjectName HTTP/1.1
    Host: DestBucketName.oss-cn-hangzhou.aliyuncs.com
    Date: GMT Date
    Authorization: SignatureValue
    x-oss-copy-source: /SourceBucketName/SourceObjectName
    */
    interface CopyObjectBuilder {

        @PathParam("destObject")
        CopyObjectBuilder destObject(final String destObject);

        @PathParam("bucket")
        CopyObjectBuilder bucket(final String bucket);

        @PathParam("endpoint")
        CopyObjectBuilder endpoint(final String endpoint);

        // /<bucket>/<sourceObject>
        @HeaderParam("x-oss-copy-source")
        CopyObjectBuilder source(final String sourcePath);

        @PUT
        @Path("http://{bucket}.{endpoint}/{destObject}")
        Transformer<Interact, FullMessage<HttpResponse>> call();
    }

    public CopyObjectBuilder copyObject();

    interface DeleteObjectBuilder {

        @PathParam("object")
        DeleteObjectBuilder object(final String object);

        @PathParam("bucket")
        DeleteObjectBuilder bucket(final String bucket);

        @PathParam("endpoint")
        DeleteObjectBuilder endpoint(final String endpoint);

        @DELETE
        @Path("http://{bucket}.{endpoint}/{object}")
        Transformer<Interact, FullMessage<HttpResponse>> call();
    }

    public DeleteObjectBuilder deleteObject();

    interface PutSymlinkBuilder {

        @PathParam("symlinkObject")
        PutSymlinkBuilder symlinkObject(final String symlinkObject);

        @PathParam("bucket")
        PutSymlinkBuilder bucket(final String bucket);

        @PathParam("endpoint")
        PutSymlinkBuilder endpoint(final String endpoint);

        @HeaderParam("x-oss-symlink-target")
        PutSymlinkBuilder targetObject(final String targetObject);

        @PUT
        @Path("http://{bucket}.{endpoint}/{symlinkObject}?symlink")
        Transformer<Interact, FullMessage<HttpResponse>> call();
    }

    public PutSymlinkBuilder putSymlink();
}
