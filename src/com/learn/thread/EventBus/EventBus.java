package com.learn.thread.EventBus;

import java.util.concurrent.Executor;

public class EventBus implements Bus{

    //用于维护Subscriber的注册表
    private final Registry registry = new Registry();

    //Event Bus的名字
    private String busName;

    //默认的Event bus的名字
    private final static String DEFAULT_BUS_NAME = "default";

    //默认的topic的名字
    private final static String DEFAULT_TOPIC = "default-topic";

    //用于广播消息到各个Subscriber的类
    private final Dispatcher dispatcher;

    public EventBus(String busName){
        this(busName,null,Dispatcher.SEQ_EXECUTOR_SERVICE);
    }

    public EventBus(String busName, EventExceptionHandler eventExceptionHandler, Executor executor){
        this.busName = busName;
        this.dispatcher = Dispatcher.newDispatcher(eventExceptionHandler,executor);
    }

    public EventBus(EventExceptionHandler eventExceptionHandler){
        this(DEFAULT_BUS_NAME,eventExceptionHandler,Dispatcher.SEQ_EXECUTOR_SERVICE);
    }

    public EventBus(){
        this(DEFAULT_BUS_NAME,null,Dispatcher.SEQ_EXECUTOR_SERVICE);
    }

    @Override
    public void register(Object subscriber) {
        this.registry.bind(subscriber);
    }

    @Override
    public void unregister(Object subscriber) {
        this.registry.unbind(subscriber);
    }

    @Override
    public void post(Object event) {
        this.post(event,DEFAULT_TOPIC);
    }

    //提交Event到具体的topic，具体的动作是由Dispatcher来完成的
    @Override
    public void post(Object event, String topic) {
        this.dispatcher.dispatch(this,registry,event,topic);
    }

    @Override
    public void close() {
        this.dispatcher.close();
    }

    @Override
    public String getBusName() {
        return this.busName;
    }
}
