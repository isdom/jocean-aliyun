/**
 * 
 */
package org.jocean.aliyun.oss.spi;

import java.util.Arrays;

import javax.ws.rs.BeanParam;
import javax.ws.rs.HeaderParam;


/**
 * @author isdom
 *
 */
public class GetImageContentResponse {
    
    @Override
    public String toString() {
        final StringBuilder builder = new StringBuilder();
        builder.append("GetImageContentResponse [contentType=")
                .append(_contentType)
                .append(", contentLength=")
                .append(_contentLength)
                .append(", contentDisposition=")
                .append(_contentDisposition)
                .append(", content=")
                .append(Arrays.toString(_content))
                .append("]");
        return builder.toString();
    }

    public String contentType() {
        return _contentType;
    }

    public byte[] content() {
        return _content;
    }

    public Integer contentLength() {
        return _contentLength;
    }

    public String contentDisposition() {
        return _contentDisposition;
    }
    
    @HeaderParam("Content-Type")
    private String _contentType;

    @HeaderParam("Content-Length")
    private Integer _contentLength;
    
    @HeaderParam("Content-disposition")
    private String _contentDisposition;


    @BeanParam
    private byte[] _content;
    
    //  TODO, add xml parse
}
