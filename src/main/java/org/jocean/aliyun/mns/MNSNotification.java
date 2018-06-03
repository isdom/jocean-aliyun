package org.jocean.aliyun.mns;

import com.alibaba.fastjson.annotation.JSONField;

public class MNSNotification {
	@JSONField(name="TopicOwner")
	public String getTopicOwner() {
		return _topicOwner;
	}
	@JSONField(name="TopicOwner")
	public void setTopicOwner(final String topicOwner) {
		_topicOwner = topicOwner;
	}
	@JSONField(name="TopicName")
	public String getTopicName() {
		return _topicName;
	}
	@JSONField(name="TopicName")
	public void setTopicName(final String topicName) {
		_topicName = topicName;
	}
	@JSONField(name="Subscriber")
	public String getSubscriber() {
		return _subscriber;
	}
	@JSONField(name="Subscriber")
	public void setSubscriber(final String subscriber) {
		_subscriber = subscriber;
	}
	@JSONField(name="SubscriptionName")
	public String getSubscriptionName() {
		return _subscriptionName;
	}
	@JSONField(name="SubscriptionName")
	public void setSubscriptionName(final String subscriptionName) {
		_subscriptionName = subscriptionName;
	}
	@JSONField(name="MessageId")
	public String getMessageId() {
		return _messageId;
	}
	@JSONField(name="MessageId")
	public void setMessageId(final String messageId) {
		_messageId = messageId;
	}
	@JSONField(name="Message")
	public String getMessage() {
		return _message;
	}
	@JSONField(name="Message")
	public void setMessage(final String message) {
		_message = message;
	}
	@JSONField(name="MessageMD5")
	public String getMessageMD5() {
		return _messageMD5;
	}
	@JSONField(name="MessageMD5")
	public void setMessageMD5(final String messageMD5) {
		_messageMD5 = messageMD5;
	}
	@JSONField(name="MessageTag")
	public String getMessageTag() {
		return _messageTag;
	}
	@JSONField(name="MessageTag")
	public void setMessageTag(final String messageTag) {
		_messageTag = messageTag;
	}
	@JSONField(name="PublishTime")
	public String getPublishTime() {
		return _publishTime;
	}
	@JSONField(name="PublishTime")
	public void setPublishTime(final String publishTime) {
		_publishTime = publishTime;
	}

    @Override
    public String toString() {
        final StringBuilder builder = new StringBuilder();
        builder.append("MNSNotificationRequest [TopicOwner=").append(_topicOwner).append(", TopicName=")
                .append(_topicName).append(", Subscriber=").append(_subscriber).append(", SubscriptionName=")
                .append(_subscriptionName).append(", MessageId=").append(_messageId).append(", Message=").append(_message)
                .append(", MessageMD5=").append(_messageMD5).append(", MessageTag=").append(_messageTag)
                .append(", PublishTime=").append(_publishTime).append("]");
        return builder.toString();
    }

    private String _topicOwner;//    被订阅主题的拥有者
    private String _topicName;// 被订阅主题的名称
    private String _subscriber;//    订阅者
    private String _subscriptionName;//  订阅名称
    private String _messageId;// 消息编号
    private String _message;//   消息正文
    private String _messageMD5;//    消息的 MD5 值
    private String _messageTag;//    消息标签（用于消息过滤）
    private String _publishTime;//   消息的发布时间，从 1970-1-1 00:00:00 000 到消息发布时的毫秒值
}
