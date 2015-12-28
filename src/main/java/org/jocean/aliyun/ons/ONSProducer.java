package org.jocean.aliyun.ons;

import java.io.UnsupportedEncodingException;
import java.util.Properties;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.aliyun.openservices.ons.api.Message;
import com.aliyun.openservices.ons.api.ONSFactory;
import com.aliyun.openservices.ons.api.Producer;
import com.aliyun.openservices.ons.api.PropertyKeyConst;
import com.aliyun.openservices.ons.api.SendResult;

public class ONSProducer {

    private static final Logger LOG = LoggerFactory.getLogger(ONSProducer.class);

    private Producer _producer = null;
    
    private final String _topicId;

    public ONSProducer(String producerId, String accessKey, String secretKey, String topicId){
        this._topicId = topicId;
        Properties properties = new Properties();
        properties.put(PropertyKeyConst.ProducerId, producerId);
        properties.put(PropertyKeyConst.AccessKey, accessKey);
        properties.put(PropertyKeyConst.SecretKey, secretKey);
        _producer = ONSFactory.createProducer(properties);

        //在发送消息前，必须调用start方法来启动Producer，只需调用一次即可。
        _producer.start();
    }
    
    public void stop(){
        if(_producer !=null){
            _producer.shutdown();
            LOG.info("ONSProducer shutdown");
        }
    }
    
    public void send(final String stringJson){
        byte[] body = new byte[0];
        try {
            body = stringJson.getBytes("utf-8");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }    
        Message msg = new Message(
                //Message Topic
                _topicId,
                //Message Tag,
                //可理解为Gmail中的标签，对消息进行再归类，方便Consumer指定过滤条件在ONS服务器过滤       
                "ONSProducer",
                //Message Body
                //任何二进制形式的数据，ONS不做任何干预，需要Producer与Consumer协商好一致的序列化和反序列化方式
                "ONSProducer" + System.currentTimeMillis(),
                body
                );
            
        //发送消息，只要不抛异常就是成功
        SendResult sendResult = _producer.send(msg);
        LOG.info("{}", sendResult);
    }
}
