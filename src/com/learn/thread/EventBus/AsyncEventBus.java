package com.learn.thread.EventBus;

import java.util.concurrent.ThreadPoolExecutor;

//异步EventBus 继承自同步，用异步处理的Executor替换EventBus中的同步Executor
public class AsyncEventBus extends EventBus {
    AsyncEventBus(String busName, EventExceptionHandler eventExceptionHandler, ThreadPoolExecutor executor){
        super(busName,eventExceptionHandler,executor);
    }

    public AsyncEventBus(String busName,ThreadPoolExecutor executor){
        this(busName,null,executor);
    }

    public AsyncEventBus(ThreadPoolExecutor executor){
        this("default-async",null,executor);
    }

    public AsyncEventBus(EventExceptionHandler eventExceptionHandler,ThreadPoolExecutor executor){
        this("default-async",eventExceptionHandler,executor);
    }
}
