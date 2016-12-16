package org.jocean.aliyun.oss;

import rx.Single;

public interface OSSImageService {
    public interface ImageInfo {
        public long fileSize();
        public String format();
        public int imageWidth();
        public int imageHeight();
    }
    
    public Single<? extends ImageInfo> info(final String pathToImage);
}
