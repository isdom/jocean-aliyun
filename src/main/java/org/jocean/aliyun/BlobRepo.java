package org.jocean.aliyun;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.ws.rs.Consumes;

import org.jocean.http.MessageBody;
import org.jocean.http.RpcRunner;

import com.aliyun.oss.model.Bucket;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlElementWrapper;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlProperty;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlRootElement;

import rx.Observable.Transformer;

/**
 * @deprecated use {@link org.jocean.aliyun.oss.OssAPI} instead.
 */
@Deprecated
public interface BlobRepo {
    interface PutObjectResult {
        public String objectName();
        public String etag();
    }

    public interface PutObjectBuilder {

        //  required
        public PutObjectBuilder objectName(final String objectName);

        //  required
        public PutObjectBuilder content(final MessageBody body);

        public Transformer<RpcRunner, PutObjectResult> build();
    }

    /**
     * put object to OSS's Bucket
     * @return
     */
    public PutObjectBuilder putObject();

    public interface SimplifiedObjectMeta {
        public String getETag();
        public long getSize();
        public Date getLastModified();
    }

    public Transformer<RpcRunner, SimplifiedObjectMeta> getSimplifiedObjectMeta(final String objectName);

    public Transformer<RpcRunner, MessageBody> getObject(final String objname);


    public static class Owner {

        private String displayName;
        private String id;

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

    }

    public static class OSSObjectSummary {

        @Override
        public String toString() {
            final StringBuilder builder = new StringBuilder();
            builder.append("OSSObjectSummary [bucketName=").append(bucketName).append(", key=").append(key)
                    .append(", eTag=").append(eTag).append(", size=").append(size).append(", lastModified=")
                    .append(lastModified).append(", storageClass=").append(storageClass).append(", owner=")
                    .append(owner).append("]");
            return builder.toString();
        }

        /** The name of the bucket in which this object is stored */
        private String bucketName;

        /** The key under which this object is stored */
        private String key;

        private String eTag;

        private long size;

        private Date lastModified;

        private String storageClass;

        private Owner owner;

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

    public Transformer<RpcRunner, ObjectListing> listObjects( String prefix);

    public Transformer<RpcRunner, CopyObjectResult> copyObject(final String sourceKey, final String destinationKey);

    public Transformer<RpcRunner, String> deleteObject(final String key);

    public Transformer<RpcRunner, String> putSymlink(final String targetObjectName, final String symlinkObjectName);
}
