/**
 * 
 */
package org.jocean.aliyun.oss.spi;

import com.alibaba.fastjson.annotation.JSONField;


/**
 * @author isdom
 *
 */
public class GetImageInfoResponse {
    
    public static class Value {
        
        @JSONField(name = "value")
        public String getValue() {
            return _value;
        }

        @JSONField(name = "value")
        public void setValue(final String value) {
            this._value = value;
        }

        private String _value;
    }
    
    @JSONField(name = "FileSize")
    public void setFileSize(final Value fileSize) {
        this._fileSize = fileSize;
    }
    
    @JSONField(name = "Format")
    public void setFormat(final Value format) {
        this._format = format;
    }
    
    @JSONField(name = "ImageWidth")
    public void setImageWidth(final Value imageWidth) {
        this._imageWidth = imageWidth;
    }
    
    @JSONField(name = "ImageHeight")
    public void setImageHeight(final Value imageHeight) {
        this._imageHeight = imageHeight;
    }
    
    public long getFileSize() {
        return null == _fileSize ? 0 : Long.parseLong(_fileSize._value);
    }
    
    public String getFormat() {
        return null == _format ? null : _format._value;
    }
    
    public int getImageWidth() {
        return null == _imageWidth ? 0 : Integer.parseInt(_imageWidth._value);
    }
    
    public int getImageHeight() {
        return null == _imageHeight ? 0 : Integer.parseInt(_imageHeight._value);
    }
    
    private Value _fileSize;
    private Value _format;
    private Value _imageWidth;
    private Value _imageHeight;
}
