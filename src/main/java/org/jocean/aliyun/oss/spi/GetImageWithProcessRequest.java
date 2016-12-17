/**
 * 
 */
package org.jocean.aliyun.oss.spi;

import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.QueryParam;


/**
 * @author isdom
 *
 */
@Path("/{path_to_image}")
public class GetImageWithProcessRequest {
    public enum ProcessAction {
        info,
    }
    
    @Override
    public String toString() {
        final StringBuilder builder = new StringBuilder();
        builder.append("GetImageWithProcessRequest [pathToImage=").append(_pathToImage)
                .append(", xOssProcess=").append(_xOssProcess).append("]");
        return builder.toString();
    }

    public void setPathToImage(final String pathToImage) {
        this._pathToImage = pathToImage;
    }

    public class ProcessBuilder {
        public ProcessBuilder add(final ProcessAction action) {
            return add(action.name(), null);
        }
        
        public ProcessBuilder add(final String action) {
            return add(action, null);
        }
        
        public ProcessBuilder add(final String action, final String param) {
            _xOssProcess += "/" + action;
            if (null != param) {
                _xOssProcess += "," + param;
            }
            return this;
        }
    }
    
    public ProcessBuilder process() {
        return new ProcessBuilder();
    }
    
    @PathParam("path_to_image")
    private String _pathToImage;

    @QueryParam("x-oss-process")
    private String _xOssProcess = "image";
}
