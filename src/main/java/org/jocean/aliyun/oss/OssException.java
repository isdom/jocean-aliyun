package org.jocean.aliyun.oss;

public class OssException extends RuntimeException {

    private static final long serialVersionUID = 5010627109725284412L;

    public OssException(final OssError error, final String message) {
        super(message);
        this._osserror = error;
    }

    public OssError error() {
        return this._osserror;
    }

    final OssError _osserror;

}
