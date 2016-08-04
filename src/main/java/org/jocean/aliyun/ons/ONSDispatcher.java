package org.jocean.aliyun.ons;

import java.util.Map;
import java.util.Properties;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.aliyun.openservices.ons.api.Action;
import com.aliyun.openservices.ons.api.ConsumeContext;
import com.aliyun.openservices.ons.api.Consumer;
import com.aliyun.openservices.ons.api.Message;
import com.aliyun.openservices.ons.api.MessageListener;
import com.aliyun.openservices.ons.api.ONSFactory;
import com.aliyun.openservices.ons.api.PropertyKeyConst;

public class ONSDispatcher {
    
    private static final Logger LOG = 
            LoggerFactory.getLogger(ONSDispatcher.class);


    private final String _consumerId;
    private final String _accessKey;
    private final String _secretKey;
    private final String _topicId;
    private final String _tag;
    private Consumer _consumer = null;

    public ONSDispatcher(
            final Map<String, MessageToFlow> handlers,
            final String consumerId, 
            final String accessKey, 
            final String secretKey,
            final String topicId,
            final String tag) {
        this._handlers = handlers;
        this._consumerId = consumerId;
        this._accessKey = accessKey;
        this._secretKey = secretKey;
        this._topicId = topicId;
        this._tag = tag;
    }

    public void start(){
        Properties properties = new Properties();
        properties.put(PropertyKeyConst.ConsumerId, _consumerId);
        properties.put(PropertyKeyConst.AccessKey, _accessKey);
        properties.put(PropertyKeyConst.SecretKey, _secretKey);
        properties.put(PropertyKeyConst.ConsumeThreadNums,2);
        _consumer = ONSFactory.createConsumer(properties);
        // 消费者如需订阅某Topic下所有类型的消息，Tag用 * 符号表示:consumer.subscribe("MQ_TOPIC", "*", new MessageListener() 
        // 消费者如需订阅某Topic下某一种类型的消息，请明确标明Tag  : consumer.subscribe("MQ_TOPIC", "TagA", new MessageListener() 
        // 消费者如需订阅某Topic下多种类型的消息，请在多个Tag之间用 || 分隔：consumer.subscribe("MQ_TOPIC", "TagA||TagB", new MessageListener() 
        _consumer.subscribe(_topicId, _tag, new MessageListener() {
            public Action consume(Message message, ConsumeContext context) {
                return dispatchONSMessage(message);
            }
        });
        _consumer.start();
        LOG.info("ONSDispatcher Started");
    }
    
    public void stop(){
        if(_consumer != null){
            _consumer.shutdown();
            LOG.info("ONSDispatcher shutdown");
        }
    }
    
    private Action dispatchONSMessage(final Message message) {
        final String messageType = message.getUserProperties("message_type");
        if (null!=messageType) {
            final MessageToFlow handler = this._handlers.get(messageType);
            if (null!=handler) {
                return handler.invokeFlow(message);
            }
        }
        LOG.warn("ons message: {} can't found matched ArchiveToDB, just ignore.", message);
        return Action.CommitMessage;
    }

    private final Map<String, MessageToFlow> _handlers;
}
