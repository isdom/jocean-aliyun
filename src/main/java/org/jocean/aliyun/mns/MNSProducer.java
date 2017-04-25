package org.jocean.aliyun.mns;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.aliyun.mns.client.CloudAccount;
import com.aliyun.mns.client.CloudTopic;
import com.aliyun.mns.client.MNSClient;
import com.aliyun.mns.model.TopicMessage;


public class MNSProducer {

	private static final Logger LOG = LoggerFactory.getLogger(MNSProducer.class);
	
	public MNSProducer(final String accessKey, final String secretKey,final String endpoint) {
		 final CloudAccount account = new CloudAccount(accessKey,secretKey, endpoint);
         //这个client仅初始化一次
    	 _client = account.getMNSClient(); 
    	 
	}
	
	public TopicMessage send(final String message,final String topicId,final String tag){
		final CloudTopic topic = _client.getTopicRef(topicId);
		TopicMessage msg = new TopicMessage() {
			@Override
			public String getMessageBody() {
				return message;
			}
		};
		if ( null != tag && !"".equals(tag.trim())) {
			msg.setMessageTag(tag);	
		}
        msg = topic.publishMessage(msg);
        LOG.debug(msg.getMessageBodyMD5());
        LOG.debug(msg.getMessageId());
        return msg;
	}
	
    public void stop() {
    	if ( null != _client ) {
    		_client.close();
    		LOG.info("ONSProducerFactory shutdown");
    	}
    }
	
	private MNSClient _client = null;
}
