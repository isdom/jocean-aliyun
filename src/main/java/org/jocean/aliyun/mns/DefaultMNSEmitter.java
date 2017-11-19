package org.jocean.aliyun.mns;

import javax.inject.Inject;

import org.jocean.aliyun.MNSEmitter;
import org.springframework.beans.factory.annotation.Value;

public class DefaultMNSEmitter implements MNSEmitter {

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
}
