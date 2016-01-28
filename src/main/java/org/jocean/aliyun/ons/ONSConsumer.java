package org.jocean.aliyun.ons;

import java.util.Properties;
import java.util.concurrent.atomic.AtomicReference;

import javax.inject.Inject;

import org.jocean.event.api.EndReasonAware;
import org.jocean.event.api.EventEngine;
import org.jocean.event.api.EventReceiver;
import org.jocean.event.api.FlowLifecycleListener;
import org.jocean.event.api.internal.EventHandler;
import org.jocean.idiom.BeanHolder;
import org.jocean.idiom.BeanHolderAware;
import org.jocean.idiom.StopWatch;
import org.jocean.j2se.stats.FlowStats;
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

    @SuppressWarnings({ "unchecked", "rawtypes" })
    public void start(){
        final Properties properties = new Properties();
        properties.put(PropertyKeyConst.ConsumerId, _consumerId);
        properties.put(PropertyKeyConst.AccessKey, _accessKey);
        properties.put(PropertyKeyConst.SecretKey, _secretKey);
        properties.put(PropertyKeyConst.ConsumeThreadNums,2);
        this._consumer = ONSFactory.createConsumer(properties);
        this._consumer.subscribe(this._topicId, "*", new MessageListener() {
            public Action consume(Message message, ConsumeContext context) {
                return invokeFlow(message);
            }
        });
        this._consumer.start();
        this._stats.addFlows(this._topicId, "SUBSCRIBE", (Class)this._flowCls);
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

    private Action invokeFlow(final Message message) {
        final Object flow = this._beanHolder.getBean(this._flowCls);
        if (null!=flow) {
            final StopWatch clock = new StopWatch();
            final AtomicReference<String> endReasonRef = new AtomicReference<>("default");
            try {
                final EventReceiver receiver = this._engine.create(
                        flow.getClass().getSimpleName(),
                        (EventHandler)flow.getClass().getField(_initState).get(flow),
                        flow,
                        new EndReasonAware() {
                            @Override
                            public void setEndReason(final Object endreason) {
                                endReasonRef.set(endreason.toString());
                            }},
                        new FlowLifecycleListener() {
                            @Override
                            public void afterEventReceiverCreated(final EventReceiver receiver)
                                    throws Exception {
                            }

                            @Override
                            public void afterFlowDestroy() throws Exception {
                                final int count = _stats.incExecutedCount(_flowCls);
                                _stats.recordExecutedInterval(_flowCls, endReasonRef.get(), clock.stopAndRestart());
                                if (LOG.isDebugEnabled()) {
                                    LOG.debug("{}'s afterFlowDestroy, so record biz count: {}", 
                                            flow, count);
//                                    LOG.debug("and all flows biz record:\n{}", Arrays.toString(getFlows()));
                                }
                            }}                        );
                receiver.acceptEvent(this._event, message);
                if (LOG.isDebugEnabled()) {
                    LOG.debug("Receive: {}", message);
                }
            } catch (Exception e) {
                LOG.warn("exception when get field({}) for {}", _initState, flow);
            }
            return Action.CommitMessage;
        } else {
            LOG.warn("can't get flow instance for class:{}, reply ONS with ReconsumeLater", this._flowCls);
            return Action.ReconsumeLater;
        }
    }
    
    @Inject
    private FlowStats _stats;
}
