package org.jocean.aliyun.oss;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.HEAD;
import javax.ws.rs.HeaderParam;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;

import org.jocean.http.FullMessage;
import org.jocean.http.Interact;
import org.jocean.http.MessageBody;

import com.aliyun.oss.model.Bucket;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlElementWrapper;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlProperty;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlRootElement;

import io.netty.handler.codec.http.HttpResponse;
import rx.Observable;
import rx.Observable.Transformer;

public interface OssAPI {
    interface Endpointable<BUILDER> {
        @PathParam("endpoint")
        BUILDER endpoint(final String endpoint);
    }

    interface Bucketable<BUILDER> extends Endpointable<BUILDER> {
        @PathParam("bucket")
        BUILDER bucket(final String bucket);
    }

    interface PutObjectBuilder extends Bucketable<PutObjectBuilder> {

        @PathParam("object")
        PutObjectBuilder object(final String object);

        PutObjectBuilder body(final Observable<MessageBody> body);

        @PUT
        @Path("http://{bucket}.{endpoint}/{object}")
        Transformer<Interact, FullMessage<HttpResponse>> call();
    }

    public PutObjectBuilder putObject();

    interface GetObjectBuilder extends Bucketable<GetObjectBuilder> {

        @PathParam("object")
        GetObjectBuilder object(final String object);

        @GET
        @Path("http://{bucket}.{endpoint}/{object}")
        Transformer<Interact, FullMessage<HttpResponse>> call();
    }

    public GetObjectBuilder getObject();

    public static class Owner {

        /**
         * Constructor.
         */
        public Owner() {
        }

        /**
         * Constructor.
         *
         * @param id
         *            Owner Id.
         * @param displayName
         *            Owner display name.
         */
        public Owner(final String id, final String displayName) {
            this.id = id;
            this.displayName = displayName;
        }

        /**
         * Serialization the owner information into string, including name and id.
         */
        @Override
        public String toString() {
            return "Owner [name=" + getDisplayName() + ",id=" + getId() + "]";
        }

        /**
         * Gets the owner Id.
         *
         * @return Owner Id.
         */
        public String getId() {
            return id;
        }

        /**
         * Sets the owner Id.
         *
         * @param id
         *            Owner Id.
         */
        @JacksonXmlProperty(localName="ID")
        public void setId(final String id) {
            this.id = id;
        }

        /**
         * Gets the owner's display name
         *
         * @return Owner's display name.
         */
        public String getDisplayName() {
            return displayName;
        }

        /**
         * Sets the owner's display name.
         *
         * @param name
         *            Owner's display name.
         */
        @JacksonXmlProperty(localName="DisplayName")
        public void setDisplayName(final String name) {
            this.displayName = name;
        }

        /**
         * Checks if the current object equals the specified one. Override the
         * object.Equals() method. Both Id and name must be same to return true for
         * this method.
         */
        @Override
        public boolean equals(final Object obj) {
            if (!(obj instanceof Owner)) {
                return false;
            }

            final Owner otherOwner = (Owner) obj;

            String otherOwnerId = otherOwner.getId();
            String otherOwnerName = otherOwner.getDisplayName();
            String thisOwnerId = this.getId();
            String thisOwnerName = this.getDisplayName();

            if (otherOwnerId == null)
                otherOwnerId = "";
            if (otherOwnerName == null)
                otherOwnerName = "";
            if (thisOwnerId == null)
                thisOwnerId = "";
            if (thisOwnerName == null)
                thisOwnerName = "";

            return (otherOwnerId.equals(thisOwnerId) && otherOwnerName.equals(thisOwnerName));
        }

        /**
         * Gets the hash code. It uses the owner's Id's hashCode.
         */
        @Override
        public int hashCode() {
            if (id != null) {
                return id.hashCode();
            } else {
                return 0;
            }
        }

        private String displayName;
        private String id;
    }

    public static class OSSObjectSummary {
        @Override
        public String toString() {
            final StringBuilder builder = new StringBuilder();
            builder.append("OSSObjectSummary [bucketName=").append(bucketName).append(", key=").append(key)
                    .append(", eTag=").append(eTag).append(", type=").append(type).append(", size=").append(size)
                    .append(", lastModified=").append(lastModified).append(", storageClass=").append(storageClass)
                    .append(", owner=").append(owner).append("]");
            return builder.toString();
        }

        /**
         * Constructor.
         */
        public OSSObjectSummary() {
        }

        /**
         * Gets the {@link Bucket} name.
         *
         * @return The bucket name.
         */
        public String getBucketName() {
            return bucketName;
        }

        /**
         * Sets the {@link Bucket} name.
         *
         * @param bucketName
         *            The {@link Bucket} name.
         */
        public void setBucketName(final String bucketName) {
            this.bucketName = bucketName;
        }

        /**
         * Gets the object key.
         *
         * @return Object key.
         */
        public String getKey() {
            return key;
        }

        /**
         * Sets the object key.
         *
         * @param key
         *            Object key.
         */
        @JacksonXmlProperty(localName="Key")
        public void setKey(final String key) {
            this.key = key;
        }

        /**
         * Gets the object ETag. ETag is a 128bit MD5 signature about the object in
         * hex.
         *
         * @return ETag value.
         */
        public String getETag() {
            return eTag;
        }

        /**
         * Sets the object ETag.
         *
         * @param eTag
         *            ETag value.
         */
        @JacksonXmlProperty(localName="ETag")
        public void setETag(final String eTag) {
            this.eTag = eTag;
        }

        /**
         * Gets the object Type
         *
         * @return Object type.
         */
        public String getType() {
            return type;
        }

        /**
         * Sets the object Type.
         *
         * @param type
         *            Object type.
         */
        @JacksonXmlProperty(localName="Type")
        public void setType(final String type) {
            this.type = type;
        }

        /**
         * Gets the object Size
         *
         * @return Object size.
         */
        public long getSize() {
            return size;
        }

        /**
         * Sets the object size.
         *
         * @param size
         *            Object size.
         */
        @JacksonXmlProperty(localName="Size")
        public void setSize(final long size) {
            this.size = size;
        }

        /**
         * Gets the last modified time of the object.
         *
         * @return The last modified time.
         */
        public Date getLastModified() {
            return lastModified;
        }

        /**
         * Sets the last modified time.
         *
         * @param lastModified
         *            Last modified time.
         */
        @JacksonXmlProperty(localName="LastModified")
        public void setLastModified(final Date lastModified) {
            this.lastModified = lastModified;
        }

        /**
         * Gets the owner of the object.
         *
         * @return Object owner.
         */
        public Owner getOwner() {
            return owner;
        }

        /**
         * Sets the owner of the object.
         *
         * @param owner
         *            Object owner.
         */
        @JacksonXmlProperty(localName="Owner")
        public void setOwner(final Owner owner) {
            this.owner = owner;
        }

        /**
         * Gets the storage class of the object.
         *
         * @return Object storage class.
         */
        public String getStorageClass() {
            return storageClass;
        }

        /**
         * Sets the storage class of the object.
         *
         * @param storageClass
         *            Object storage class.
         */
        @JacksonXmlProperty(localName="StorageClass")
        public void setStorageClass(final String storageClass) {
            this.storageClass = storageClass;
        }

        /** The name of the bucket in which this object is stored */
        private String bucketName;

        /** The key under which this object is stored */
        private String key;

        private String eTag;

        private String type;

        private long size;

        private Date lastModified;

        private String storageClass;

        private Owner owner;
    }

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

    //  https://help.aliyun.com/document_detail/31965.html?spm=a2c4g.11186623.6.1570.68afb81eXwEqgq
    interface ListObjectsBuilder extends Bucketable<ListObjectsBuilder> {

        @QueryParam("prefix")
        ListObjectsBuilder prefix(final String prefix);

        @QueryParam("marker")
        ListObjectsBuilder marker(final String marker);

        @QueryParam("max-keys")
        ListObjectsBuilder maxKeys(final String maxKeys);

        @QueryParam("delimiter")
        ListObjectsBuilder delimiter(final String delimiter);

        @QueryParam("encoding-type")
        ListObjectsBuilder encodingType(final String encodingType);

        @GET
        @Path("http://{bucket}.{endpoint}/")
        @Consumes(MediaType.APPLICATION_XML)
        Transformer<Interact, ObjectListing> call();
    }

    public ListObjectsBuilder listObjects();

    // https://help.aliyun.com/document_detail/31985.html?spm=a2c4g.11186623.6.1603.15ec810cYy37lP
    interface GetObjectMetaBuilder extends Bucketable<GetObjectMetaBuilder> {

        @PathParam("object")
        GetObjectMetaBuilder object(final String object);

        @HEAD
        @Path("http://{bucket}.{endpoint}/{object}?objectMeta")
        Transformer<Interact, FullMessage<HttpResponse>> call();
    }

    public GetObjectMetaBuilder getObjectMeta();

    /* REF: https://help.aliyun.com/document_detail/31979.html?spm=a2c4g.11186623.6.926.p75n2Q
     * API:
    PUT /DestObjectName HTTP/1.1
    Host: DestBucketName.oss-cn-hangzhou.aliyuncs.com
    Date: GMT Date
    Authorization: SignatureValue
    x-oss-copy-source: /SourceBucketName/SourceObjectName
    */
    interface CopyObjectBuilder extends Bucketable<CopyObjectBuilder> {

        @PathParam("destObject")
        CopyObjectBuilder destObject(final String destObject);

        // /<bucket>/<sourceObject>
        @HeaderParam("x-oss-copy-source")
        CopyObjectBuilder source(final String sourcePath);

        @PUT
        @Path("http://{bucket}.{endpoint}/{destObject}")
        Transformer<Interact, FullMessage<HttpResponse>> call();
    }

    public CopyObjectBuilder copyObject();

    interface DeleteObjectBuilder extends Bucketable<DeleteObjectBuilder> {

        @PathParam("object")
        DeleteObjectBuilder object(final String object);

        @DELETE
        @Path("http://{bucket}.{endpoint}/{object}")
        Transformer<Interact, FullMessage<HttpResponse>> call();
    }

    public DeleteObjectBuilder deleteObject();

    interface PutSymlinkBuilder extends Bucketable<PutSymlinkBuilder> {

        @PathParam("symlinkObject")
        PutSymlinkBuilder symlinkObject(final String symlinkObject);

        @HeaderParam("x-oss-symlink-target")
        PutSymlinkBuilder targetObject(final String targetObject);

        @PUT
        @Path("http://{bucket}.{endpoint}/{symlinkObject}?symlink")
        Transformer<Interact, FullMessage<HttpResponse>> call();
    }

    public PutSymlinkBuilder putSymlink();

    // https://help.aliyun.com/document_detail/45146.html?spm=a2c4g.11186623.6.1609.a8eeb81ed5SIWi
    interface GetSymlinkBuilder extends Bucketable<GetSymlinkBuilder> {

        @PathParam("symlinkObject")
        GetSymlinkBuilder symlinkObject(final String symlinkObject);

        @GET
        @Path("http://{bucket}.{endpoint}/{symlinkObject}?symlink")
        Transformer<Interact, FullMessage<HttpResponse>> call();
    }

    public GetSymlinkBuilder getSymlink();

    // https://help.aliyun.com/document_detail/31991.html?spm=a2c4g.11186623.3.3.552a79det2Ev6r
    // 关于MultipartUpload的操作
    interface InitiateMultipartUploadBuilder {
        @PathParam("object")
        InitiateMultipartUploadBuilder object(final String object);

        @POST
        @Path("http://{bucket}.{endpoint}/{object}?uploads")
        Transformer<Interact, FullMessage<HttpResponse>> call();
    }

}
