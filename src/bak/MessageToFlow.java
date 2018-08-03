package org.jocean.aliyun.ons;

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
import org.jocean.j2se.stats.ApiStats;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.aliyun.openservices.ons.api.Action;
import com.aliyun.openservices.ons.api.Message;

public class MessageToFlow implements BeanHolderAware{
    
    private static final Logger LOG = 
            LoggerFactory.getLogger(MessageToFlow.class);
    
    public void setFlowCls(final Class<?> flowCls) {
        this._flowCls = flowCls;
    }
    
    public void start(){
        this._stats.addApi(this._messageType, "SUBSCRIBE");
    }

    public void setMessageType(final String messageType) {
        this._messageType = messageType;
    }

    public void setInitState(final String initState) {
        this._initState = initState;
    }
    
    public void setEvent(final String event) {
        this._event = event;
    }
    
    @Inject
    private EventEngine _engine;
    
    @Inject
    private ApiStats _stats;
    
    private BeanHolder _beanHolder;
    
    private String _messageType;
    
    private Class<?> _flowCls;
    
    private String _initState;
    
    private String _event;

    @Override
    public void setBeanHolder(final BeanHolder beanHolder) {
        this._beanHolder = beanHolder;
    }

    public Action invokeFlow(final Message message) {
        final Object flow = _beanHolder.getBean(this._flowCls);
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
                                final int count = _stats.incExecutedCount(_messageType);
                                _stats.recordExecutedInterval(_messageType, endReasonRef.get(), clock.stopAndRestart());
                                if (LOG.isDebugEnabled()) {
                                    LOG.debug("{}'s afterFlowDestroy, so record biz count: {}", 
                                            flow, count);
//                                    LOG.debug("and all flows biz record:\n{}", Arrays.toString(getFlows()));
                                }
                            }}
                            );
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
}
