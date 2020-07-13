package com.learn.thread.EventBus;

/**
 * 异常回调接口
 *
 * */
public interface EventExceptionHandler {
    void handle(Throwable cause,EventContext context);
}
