package org.jocean.aliyun.ons;

import java.util.Properties;

import javax.inject.Inject;

import org.jocean.event.api.EventEngine;
import org.jocean.event.api.EventReceiver;
import org.jocean.event.api.internal.EventHandler;
import org.jocean.idiom.BeanHolder;
import org.jocean.idiom.BeanHolderAware;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.aliyun.openservices.ons.api.Action;
import com.aliyun.openservices.ons.api.ConsumeContext;
import com.aliyun.openservices.ons.api.Consumer;
import com.aliyun.openservices.ons.api.Message;
import com.aliyun.openservices.ons.api.MessageListener;
import com.aliyun.openservices.ons.api.ONSFactory;
import com.aliyun.openservices.ons.api.PropertyKeyConst;

public class ONSConsumer implements BeanHolderAware{
    
    private static final Logger LOG = LoggerFactory.getLogger(ONSConsumer.class);


    private final String _consumerId;
    private final String _accessKey;
    private final String _secretKey;
    private final String _topicId;
    private Consumer _consumer = null;

    public ONSConsumer(
            String consumerId, 
            String accessKey, 
            String secretKey,
            String topicId) {
        this._consumerId = consumerId;
        this._accessKey = accessKey;
        this._secretKey = secretKey;
        this._topicId = topicId;
    }

    public void start(){
        Properties properties = new Properties();
        properties.put(PropertyKeyConst.ConsumerId, _consumerId);
        properties.put(PropertyKeyConst.AccessKey, _accessKey);
        properties.put(PropertyKeyConst.SecretKey, _secretKey);
        properties.put(PropertyKeyConst.ConsumeThreadNums,2);
        _consumer = ONSFactory.createConsumer(properties);
        _consumer.subscribe(_topicId, "*", new MessageListener() {
            public Action consume(Message message, ConsumeContext context) {
                final Object flow = _beanHolder.getBean(_flowCls);
                try {
                    final EventReceiver _receiver = _engine.create(
                            flow.getClass().getSimpleName(),
                            (EventHandler)flow.getClass().getField(_initState).get(flow),
                            flow);
                    _receiver.acceptEvent(_event, message);
                    if (LOG.isDebugEnabled()) {
                        LOG.debug("Receive: {}", message);
                    }
                } catch (Exception e) {
                    LOG.warn("exception when get field({}) for {}", _initState, flow);
                }
                return Action.CommitMessage;
            }
        });
        _consumer.start();
        LOG.info("Consumer Started");
    }
    
    public void stop(){
        if(_consumer != null){
            _consumer.shutdown();
            LOG.info("OnsConsumer shutdown");
        }
    }
    
    public void setFlowCls(final Class<?> flowCls) {
        this._flowCls = flowCls;
    }
    
    public void setInitState(final String initState) {
        this._initState = initState;
    }
    
    public void setEvent(final String event) {
        this._event = event;
    }
    
    @Inject
    private EventEngine _engine;
    
    private BeanHolder _beanHolder;
    
    private Class<?> _flowCls;
    
    private String _initState;
    
    private String _event;

    @Override
    public void setBeanHolder(final BeanHolder beanHolder) {
        this._beanHolder = beanHolder;
    }
}
