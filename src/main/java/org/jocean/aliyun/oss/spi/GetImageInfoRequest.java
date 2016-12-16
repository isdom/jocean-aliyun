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
public class GetImageInfoRequest {
    
    @Override
    public String toString() {
        final StringBuilder builder = new StringBuilder();
        builder.append("GetImageInfoRequest [pathToImage=").append(_pathToImage)
                .append(", xOssProcess=").append(_xOssProcess).append("]");
        return builder.toString();
    }

    public void setPathToImage(final String pathToImage) {
        this._pathToImage = pathToImage;
    }

    @PathParam("path_to_image")
    private String _pathToImage;

    @QueryParam("x-oss-process")
    private final String _xOssProcess = "image/info";
    
}
