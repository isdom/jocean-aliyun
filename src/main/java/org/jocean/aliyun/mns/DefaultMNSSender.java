package org.jocean.aliyun.mns;

import javax.inject.Inject;

import org.jocean.aliyun.MNSSender;
import org.springframework.beans.factory.annotation.Value;

public class DefaultMNSSender implements MNSSender {

    @Override
    public void send(final String msg) {
        _producer.send(msg,  this._topicId, this._tag);
    }

    @Inject 
    MNSProducer _producer;
    
    @Value("${mns.topicid}")
    String _topicId;

    @Value("${mns.tag}")
    String _tag;
}
