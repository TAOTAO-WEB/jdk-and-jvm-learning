package com.learn.thread.EventBus;

import java.lang.reflect.Method;
import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.concurrent.Executor;
import java.util.concurrent.ExecutorService;

/**
 * Dispatcher的主要作用是将EventBus post的event推送给每一个注册到topic的subscriber上
 * 具体的推送其实就是执行被@Subscribe的注解方法
 *
 * */
public class Dispatcher {
    private final Executor executorService;

    private final EventExceptionHandler eventExceptionHandler;

    public static final Executor SEQ_EXECUTOR_SERVICE = SeqExecutorService.INSTANCE;

    public static final Executor PRE_THREAD_EXECUTOR_SERVICE = PreThreadExecutorService.INSTANCE;

    private Dispatcher(Executor executorService, EventExceptionHandler eventExceptionHandler){
        this.executorService = executorService;
        this.eventExceptionHandler = eventExceptionHandler;
    }

    public void dispatch(Bus bus,Registry registry,Object event,String topic){
        //根据topic获取所有的Subcriber列表
        ConcurrentLinkedQueue<Subscriber> subscribers = registry.scanSubscriber(topic);
        if(null==subscribers){
            if(eventExceptionHandler!=null){
                eventExceptionHandler.handle(new IllegalArgumentException("the topic"+topic+"not bind yet"),new BaseEventContext(bus.getBusName(),null,event));
            }
            return;
        }

        //遍历所有的方法并且通过反射的方式进行方法调用
        subscribers.stream().filter(subscriber -> !subscriber.isDisable()).
                filter(subscriber -> {
                    Method subscribeMethod = subscriber.getSubscribeMethod();
                    Class<?> aClass = subscribeMethod.getParameterTypes()[0];
                    return (aClass.isAssignableFrom(event.getClass()));
                }).forEach(subscriber -> realInvokeSubscribe(subscriber,event,bus));
    }

    private void realInvokeSubscribe(Subscriber subscriber,Object event,Bus bus){
        Method subscribeMethod = subscriber.getSubscribeMethod();
        Object subscribeObject = subscriber.getSubscribeObject();
        executorService.execute(()->{
            try{
                subscribeMethod.invoke(subscribeObject,event);
            }catch (Exception e){
                if(null != eventExceptionHandler){
                    eventExceptionHandler.handle(e,new BaseEventContext(bus.getBusName(),subscriber,event));
                }
            }
        });
    }

    public void close(){
        if(executorService instanceof ExecutorService){
            ((ExecutorService)executorService).shutdown();
        }
    }


    static Dispatcher newDispatcher(EventExceptionHandler eventExceptionHandler,Executor executorService){
        return new Dispatcher(executorService,eventExceptionHandler);
    }

    static Dispatcher seqDispatcher(EventExceptionHandler eventExceptionHandler){
        return new Dispatcher(SEQ_EXECUTOR_SERVICE,eventExceptionHandler);
    }

    //每个线程负责一次消息推送
    private static class PreThreadExecutorService implements Executor{
        private final static PreThreadExecutorService INSTANCE = new PreThreadExecutorService();

        @Override
        public void execute(Runnable command) {
            new Thread(command).start();
        }
    }

    //顺序执行的ExecutorService
    private static class SeqExecutorService implements Executor{

        private final static SeqExecutorService INSTANCE = new SeqExecutorService();

        @Override
        public void execute(Runnable command) {
            command.run();
        }
    }

    //默认的EventContext实现
    private static class BaseEventContext implements EventContext{

        private final String eventBusName;
        private final Subscriber subscriber;
        private final Object event;

        private BaseEventContext(String eventBusName, Subscriber subscriber, Object event) {
            this.eventBusName = eventBusName;
            this.subscriber = subscriber;
            this.event = event;
        }

        @Override
        public String getSource() {
            return this.eventBusName;
        }

        @Override
        public Object getSubscriber() {
            return subscriber != null?subscriber.getSubscribeObject():null;
        }

        @Override
        public Method getSubscribe() {
            return subscriber!=null?subscriber.getSubscribeMethod():null;
        }

        @Override
        public Object getEvent() {
            return this.event;
        }
    }

}
