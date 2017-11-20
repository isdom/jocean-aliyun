package org.jocean.aliyun.mns;

import javax.inject.Inject;

import org.jocean.aliyun.MNSEmitter;
import org.jocean.j2se.jmx.MBeanRegister;
import org.jocean.j2se.jmx.MBeanRegisterAware;
import org.springframework.beans.factory.annotation.Value;

public class DefaultMNSEmitter implements MNSEmitter, MBeanRegisterAware {

    @Override
    public void emit(final String msg) {
        this._producer.send(msg,  this._topicId, this._tag);
    }

    @Inject 
    MNSProducer _producer;
    
    @Value("${mns.topicid}")
    String _topicId;

    @Value("${mns.tag}")
    String _tag;
    
    @Value("${name}")
    String _name;

    @Override
    public String getName() {
        return this._name;
    }

    @Override
    public void setMBeanRegister(final MBeanRegister register) {
        register.registerMBean("mns=emitter", new MNSEmitterMXBean() {

            @Override
            public String getName() {
                return _name;
            }

            @Override
            public String getTopicId() {
                return _topicId;
            }

            @Override
            public String getTag() {
                return _tag;
            }});
    }
}
