package org.jocean.aliyun.ons;

import java.util.Iterator;
import java.util.Properties;

import org.jocean.idiom.SimpleCache;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.aliyun.openservices.ons.api.ONSFactory;
import com.aliyun.openservices.ons.api.Producer;
import com.aliyun.openservices.ons.api.PropertyKeyConst;

import rx.functions.Func1;

public class ONSProducerFactory {

    private static final Logger LOG = 
            LoggerFactory.getLogger(ONSProducerFactory.class);

    public ONSProducerFactory(final String accessKey, final String secretKey) {
        this._accessKey = accessKey;
        this._secretKey = secretKey;
    }
    
    public void stop() {
        final Iterator<Producer> itr = 
                this._producers.snapshot().values().iterator();
        while (itr.hasNext()) {
            itr.next().shutdown();
        }
        _producers.clear();
        
        LOG.info("ONSProducerFactory shutdown");
    }
    
    public Producer getProducer(final String producerId) {
        return this._producers.get(producerId);
    }
    
    private final String _accessKey;
    private final String _secretKey;
    private final SimpleCache<String, Producer> _producers = new SimpleCache<String, Producer>(
            new Func1<String, Producer>() {
        @Override
        public Producer call(final String producerId) {
            final Properties properties = new Properties();
            properties.put(PropertyKeyConst.ProducerId, producerId);
            properties.put(PropertyKeyConst.AccessKey, _accessKey);
            properties.put(PropertyKeyConst.SecretKey, _secretKey);
            final Producer producer = ONSFactory.createProducer(properties);

            //在发送消息前，必须调用start方法来启动Producer，只需调用一次即可。
            producer.start();
            
            return producer;
        }});
}
