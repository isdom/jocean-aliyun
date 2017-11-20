package org.jocean.aliyun;

public interface MNSEmitter {
    
    public String getName();
    
    public void emit(final String msg);
}
