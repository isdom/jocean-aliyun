package org.jocean.aliyun.oss;


import javax.ws.rs.Consumes;

import org.apache.commons.lang3.builder.ToStringBuilder;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.dataformat.xml.XmlMapper;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlProperty;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlRootElement;

/**
 * OSS的错误响应格式
 *
 * 当用户访问OSS出错时，OSS会返回给用户一个合适的3xx，4xx或者5xx的HTTP状态码；以及一个application/xml格式的消息体。
 * <?xml version="1.0" ?>
 * <Error xmlns=”http://doc.oss-cn-hangzhou.aliyuncs.com”>
 *     <Code>
 *         AccessDenied
 *     </Code>
 *     <Message>
 *         Query-string authentication requires the Signature, Expires and OSSAccessKeyId parameters
 *     </Message>
 *     <RequestId>
 *         1D842BC5425544BB
 *     </RequestId>
 *     <HostId>
 *        oss-cn-hangzhou.aliyuncs.com
 *     </HostId>
 * </Error>
 */
@Consumes({"application/xml","text/xml"})
@JacksonXmlRootElement(localName="Error")
public class OssError {

    //  Code：OSS返回给用户的错误码。
    private String _code;

    //  Message：OSS给出的详细错误信息。
    private String _message;

    //  RequestId：用于唯一标识该次请求的UUID；当你无法解决问题时，可以凭这个RequestId来请求OSS开发工程师的帮助。
    private String _requestId;

    //  HostId：用于标识访问的OSS集群，与用户请求时使用的Host一致。
    private String _hostId; //消息发送时间  the number of seconds since January 1, 1970, 00:00:00 GMT

    //  指定的 Object
    private String _key;

    @JacksonXmlProperty(localName="Code")
    public String getCode() {
        return this._code;
    }

    @JacksonXmlProperty(localName="Code")
    public void setCode(final String code) {
        this._code = code;
    }

    @JacksonXmlProperty(localName="Message")
    public String getMessage() {
        return this._message;
    }

    @JacksonXmlProperty(localName="Message")
    public void setMessage(final String message) {
        this._message = message;
    }

    @JacksonXmlProperty(localName="RequestId")
    public void setRequestId(final String requestId) {
        this._requestId = requestId;
    }

    @JacksonXmlProperty(localName="RequestId")
    public String getRequestId() {
        return this._requestId;
    }

    @JacksonXmlProperty(localName="HostId")
    public void setHostId(final String hostId) {
        this._hostId = hostId;
    }

    @JacksonXmlProperty(localName="HostId")
    public String getHostId() {
        return this._hostId;
    }

    // GetObject REF: https://help.aliyun.com/document_detail/31980.html?spm=a2c4g.11186623.6.1657.58a16711j0zxaO
    @JacksonXmlProperty(localName="Key")
    public void setKey(final String key) {
        this._key = key;
    }

    @JacksonXmlProperty(localName="Key")
    public String getKey() {
        return this._key;
    }

    @Override
    public String toString() {
        return ToStringBuilder.reflectionToString(this);
    }

    public static void main(final String[] args) throws Exception {
        final String xmlsrc =
                "<?xml version=\"1.0\" ?>"
                + "<Error xmlns=\"http://doc.oss-cn-hangzhou.aliyuncs.com\">"
                + "   <Code>"
                + "       AccessDenied"
                + "   </Code>"
                + "   <Message>"
                + "      Query-string authentication requires the Signature, Expires and OSSAccessKeyId parameters"
                + "   </Message>"
                + "   <RequestId>"
                + "       1D842BC5425544BB"
                + "   </RequestId>"
                + "   <HostId>"
                + "       oss-cn-hangzhou.aliyuncs.com"
                + "    </HostId>"
                + "</Error>"
                ;
        final ObjectMapper mapper = new XmlMapper();

        final OssError ossError = mapper.readValue(xmlsrc, OssError.class);
        System.out.println("error:" + ossError);
        System.out.println("as Xml:" + mapper.writeValueAsString(ossError) );

    }
}
