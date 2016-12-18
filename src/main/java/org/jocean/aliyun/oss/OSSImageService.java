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
    
    public interface ProcessAction {
        public ActionSet and();
    }
    
    public interface Info extends ProcessAction {
        
    }
    
    public interface Crop extends ProcessAction {
        public Crop x(final int x);
        public Crop y(final int y);
        public Crop w(final int w);
        public Crop h(final int h);
    }
    
    public interface ActionSet {
        public Info info();
        public Crop crop();
        public String build();
    }
    
    public ActionSet actions();
}
