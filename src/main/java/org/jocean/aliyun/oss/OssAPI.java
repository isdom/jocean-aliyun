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

    interface Objectable<BUILDER> extends Bucketable<BUILDER> {
        @PathParam("object")
        BUILDER object(final String object);
    }

    interface StoreOperation<BUILDER> {
        // 指定该Object被下载时网页的缓存行为，详细描述参考RFC2616。
        // 默认值：无
        @HeaderParam("Cache-Control")
        BUILDER cacheControl(final String cacheControl);

        // 指定该Object被下载时的名称，详细描述参考RFC2616。
        // 默认值：无
        @HeaderParam("Content-Disposition")
        BUILDER contentDisposition(final String contentDisposition);

        // 指定该Object被下载时的内容编码格式，详细描述参考RFC2616。
        // 默认值：无
        @HeaderParam("Content-Encoding")
        BUILDER contentEncoding(final String contentEncoding);

        // 过期时间，详细描述参考照RFC2616。
        // 默认值：无
        @HeaderParam("Expires")
        BUILDER expires(final String expires);

        // 指定PutObject操作时是否覆盖同名Object。
        // 不指定x-oss-forbid-overwrite时，默认覆盖同名Object。
        // 指定x-oss-forbid-overwrite为true时，表示禁止覆盖同名Object；
        // 指定x-oss-forbid-overwrite为false时，表示允许覆盖同名Object。
        @HeaderParam("x-oss-forbid-overwrite")
        BUILDER forbidOverwrite(final Boolean isOverwrite);

        // 指定OSS创建Object时的服务器端加密编码算法。
        // 取值：AES256 或 KMS（您需要购买KMS套件，才可以使用 KMS 加密算法，否则会报 KmsServiceNotEnabled 错误码）
        //
        // 指定此参数后，在响应头中会返回此参数，OSS会对上传的Object进行加密编码存储。
        //  当下载该Object时，响应头中会包含x-oss-server-side-encryption，且该值会被设置成该Object的加密算法。
        @HeaderParam("x-oss-server-side-encryption")
        BUILDER serverSideEncryption(final String encryption);

        // KMS托管的用户主密钥。
        // 该参数在x-oss-server-side-encryption为KMS时有效。
        @HeaderParam("x-oss-server-side-encryption-key-id")
        BUILDER serverSideEncryptionKeyid(final String encryptionKeyid);

        // 指定Object的存储类型。
        // 对于任意存储类型的Bucket，若上传Object时指定此参数，则此次上传的Object将存储为指定的类型。
        // 例如，在IA类型的Bucket中上传Object时，若指定x-oss-storage-class为Standard，则该Object直接存储为Standard。
        // 取值：Standard、IA、Archive
        // 支持的接口：PutObject、InitMultipartUpload、AppendObject、 PutObjectSymlink、CopyObject。
        @HeaderParam("x-oss-storage-class")
        BUILDER storageClass(final String storageClass);

        // 指定Object的标签，可同时设置多个标签，例如： TagA=A&TagB=B。
        // 说明 Key和Value需要先进行URL编码，如果某项没有"="，则看作Value为空字符串。
        @HeaderParam("x-oss-tagging")
        BUILDER tagging(final String tagging);
    }

    // https://help.aliyun.com/document_detail/31978.html?spm=a2c4g.11186623.6.1596.4fb211a06jVZO2
    interface PutObjectBuilder extends Objectable<PutObjectBuilder>, StoreOperation<PutObjectBuilder> {

        // 用于检查消息内容是否与发送时一致。Content-MD5是一串由MD5算法生成的值。上传了Content-MD5请求头后，OSS会计算消息体的Content-MD5并检查一致性。
        //  有关Content-MD5的计算方法，详情请参见Content-MD5的计算方法。
        // 默认值：无
        @HeaderParam("Content-MD5")
        PutObjectBuilder contentMD5(final String contentMD5);

        // 指定OSS创建Object时的访问权限。
        // 合法值：public-read，private，public-read-write
        @HeaderParam("x-oss-object-acl")
        PutObjectBuilder objectAcl(final String objectAcl);

        // 使用PutObject接口时，如果配置以x-oss-meta-*为前缀的参数，则该参数视为元数据，例如x-oss-meta-location。
        // 一个Object可以有多个类似的参数，但所有的元数据总大小不能超过8KB。
        // 元数据支持短横线（-）、数字、英文字母（a-z），英文字符的大写字母会被转成小写字母，不支持下划线（_）在内的其他字符
        // TODO
//        @HeaderParam("x-oss-meta-*")
//        PutObjectBuilder meta(final String meta);

        PutObjectBuilder body(final Observable<MessageBody> body);

        @PUT
        @Path("http://{bucket}.{endpoint}/{object}")
        Transformer<Interact, FullMessage<HttpResponse>> call();
    }

    public PutObjectBuilder putObject();

    interface GetObjectBuilder extends Objectable<GetObjectBuilder> {

        //说明 OSS不支持多Range参数，即不支持指定多个范围。ByteRange指请求资源的范围，单位为Byte（字节），ByteRange有效区间在0至object size - 1的范围内。
        // 具体示例如下：
        // Range: bytes=0-499表示第0-499字节范围的内容。
        //        Range: bytes=500-999表示第500-999字节范围的内容。
        //        Range: bytes=-500表示最后500字节的内容。
        //        Range: bytes=500-表示从第500字节开始到文件结束部分的内容。
        //        Range: bytes=0-表示从第一个字节到最后一个字节，即完整的文件内容。

        // 指定文件传输的范围。
        // 默认值：无
        // 如果指定的范围符合规范，返回消息中会包含整个Object的大小和此次返回的范围。
        // 例如：Content-Range: bytes 0-9/44，表示整个Object大小为44，此次返回的范围为0-9。
        // 如果指定的范围不符合范围规范，则传送整个Object，并且不在结果中提及Content-Range。
        @HeaderParam("Range")
        GetObjectBuilder range(final String range);

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
    interface GetObjectMetaBuilder extends Objectable<GetObjectMetaBuilder> {

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

    interface DeleteObjectBuilder extends Objectable<DeleteObjectBuilder> {

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

    @Consumes({"application/xml","text/xml"})
    @JacksonXmlRootElement(localName="InitiateMultipartUploadResult")
    public static class InitiateMultipartUploadResult {

        private String bucket;

        private String key;

        private String uploadId;

//        private String encodingType;

        public String getKey() {
            return key;
        }

        @JacksonXmlProperty(localName="Key")
        public void setKey(final String key) {
            this.key = key;
        }

        public String getBucket() {
            return this.bucket;
        }

        @JacksonXmlProperty(localName="Bucket")
        public void setBucket(final String bucket) {
            this.bucket = bucket;
        }

        public String getUploadId() {
            return uploadId;
        }

        @JacksonXmlProperty(localName="UploadId")
        public void setUploadId(final String uploadId) {
            this.uploadId = uploadId;
        }
//        public String getEncodingType() {
//            return encodingType;
//        }
//
//        @JacksonXmlProperty(localName="EncodingType")
//        public void setEncodingType(final String encodingType) {
//            this.encodingType = encodingType;
//        }
    }

    // https://help.aliyun.com/document_detail/31991.html?spm=a2c4g.11186623.3.3.552a79det2Ev6r
    // 关于MultipartUpload的操作
    interface InitiateMultipartUploadBuilder extends
        Objectable<InitiateMultipartUploadBuilder>, StoreOperation<InitiateMultipartUploadBuilder> {

        @POST
        @Path("http://{bucket}.{endpoint}/{object}?uploads")
        @Consumes(MediaType.APPLICATION_XML)
        Transformer<Interact, InitiateMultipartUploadResult> call();
    }

    public InitiateMultipartUploadBuilder initiateMultipartUpload();

    // https://help.aliyun.com/document_detail/31993.html?spm=a2c4g.11186623.6.1625.405a79deMFs3vX
    interface UploadPartBuilder extends Objectable<UploadPartBuilder> {

        //调用该接口上传Part数据前，必须先调用InitiateMultipartUpload接口来获取一个OSS服务器颁发的Upload ID。Upload ID用于唯一标识上传的part属于哪个Object。
        // 每一个上传的Part都有一个标识它的号码（part number，范围是1-10000），单个Part大小范围100KB-5GB。
        // MultipartUpload要求除最后一个Part以外，其他的Part大小都要大于100KB。
        // 因不确定是否为最后一个Part，UploadPart接口并不会立即校验上传Part的大小，只有当CompleteMultipartUpload的时候才会校验。
        // 如果你用同一个part number上传了新的数据，那么OSS上已有的这个号码的Part数据将被覆盖。
        // OSS会将服务器端收到Part数据的MD5值放在ETag头内返回给用户。
        // 若调用InitiateMultipartUpload接口时，指定了x-oss-server-side-encryption请求头，则会对上传的Part进行加密编码，
        // 并在UploadPart响应头中返回x-oss-server-side-encryption头，其值表明该Part的服务器端加密算法，详情请参考InitiateMultipartUpload。

        @QueryParam("partNumber")
        UploadPartBuilder partNumber(final int partNumber);

        @QueryParam("uploadId")
        UploadPartBuilder uploadId(final String uploadId);

        UploadPartBuilder body(final Observable<MessageBody> body);

        @PUT
        @Path("http://{bucket}.{endpoint}/{object}")
        Transformer<Interact, FullMessage<HttpResponse>> call();
    }

    public UploadPartBuilder uploadPart();
}
