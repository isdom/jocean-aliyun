package org.jocean.aliyun.annotation;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

@Retention(RetentionPolicy.RUNTIME)
public @interface ResponseType {
    public abstract Class<?> value();
}