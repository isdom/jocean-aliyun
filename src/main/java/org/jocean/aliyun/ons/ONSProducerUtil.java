package org.jocean.aliyun.ons;

import java.io.UnsupportedEncodingException;
import java.util.Properties;

import org.jocean.idiom.ExceptionUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.aliyun.openservices.ons.api.Message;
import com.aliyun.openservices.ons.api.Producer;
import com.aliyun.openservices.ons.api.SendResult;

public class ONSProducerUtil {
    
    private static final Logger LOG = LoggerFactory.getLogger(ONSProducerUtil.class);
    
    private static final byte[] EMPTY_BYTES = new byte[0];
    
    public static void send (final String topicId,final Producer producer,final String content,final String tag) {
        send(topicId, producer, content, -1, null,tag);
    }

    public static void send(final String topicId, final Producer producer, final String content,final long sendTimeStamp,final String tag) {
        send(topicId, producer, content, sendTimeStamp, null,tag);
    }
    
    public static void send(final String topicId, final Producer producer,
            final String content,final Properties userProperties,final String tag) {
        send(topicId, producer, content, -1, userProperties,tag);
    }
    
    public static void send(final String topicId, final Producer producer,
            final String content,final long sendTimeStamp,
            final Properties userProperties,final String tag) {
        byte[] body = EMPTY_BYTES;
        try {
            body = content.getBytes("utf-8");
            final Message msg = new Message(
                    //Message Topic
                    topicId,
                    //Message Tag,
                    //可理解为Gmail中的标签，对消息进行再归类，方便Consumer指定过滤条件在ONS服务器过滤       
                    tag,
                    //Message Body
                    //任何二进制形式的数据，ONS不做任何干预，需要Producer与Consumer协商好一致的序列化和反序列化方式
                    topicId + System.currentTimeMillis(),
                    body
                    );
            if (sendTimeStamp > 0) {
                msg.setStartDeliverTime(sendTimeStamp);
            }
            
            if( null != userProperties) {
                msg.setUserProperties(userProperties);
            }
            
            //发送消息，只要不抛异常就是成功
            final SendResult sendResult = producer.send(msg);
            if (LOG.isInfoEnabled()) {
                LOG.info("send Message ({})/ result: {}", msg, sendResult);
            }
        } catch (UnsupportedEncodingException e) {
            LOG.warn("exception when content.getBytes, content:{}, detail: {}", 
                    content, ExceptionUtils.exception2detail(e));
        }
    }
}
