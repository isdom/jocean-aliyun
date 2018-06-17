package org.jocean.aliyun.oss;

import org.jocean.http.Interact;
import org.jocean.netty.BlobRepo.Blob;

import rx.Observable;
import rx.functions.Func1;

public interface OSSImageService {
    public interface ImageInfo {
        public long fileSize();
        public String format();
        public int imageWidth();
        public int imageHeight();
    }

    public Func1<Interact, Observable<? extends ImageInfo>> info(final String pathToImage);

    public Observable<? extends Blob> process(final String pathToImage, final String actions);

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

    public interface Resize extends ProcessAction {
        public Resize m(final String m);
        public Resize w(final int w);
        public Resize h(final int h);
        public Resize limit(final int limit);
        public Resize color(final String color);
    }

    public interface ActionSet {
        public Info info();
        public Crop crop();
        public Resize resize();
        public String build();
    }

    public ActionSet actions();

}
