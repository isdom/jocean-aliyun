package org.jocean.aliyun.oss;

import rx.Observable;

public interface OSSImageService {
    public interface ImageInfo {
        public long fileSize();
        public String format();
        public int imageWidth();
        public int imageHeight();
    }
    
    public Observable<? extends ImageInfo> info(final String pathToImage);
}
