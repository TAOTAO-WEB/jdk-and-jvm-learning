package com.learn.thread.EventBus;

import java.lang.reflect.Method;

/**
 * 获取消息源，消息体，以及消息来源于哪一个subscriber的那个subscribe方法
 * 主要用于消息推送出错的时候被回调接口EventExceptionHandler使用
 *
 * */
public interface EventContext {
   String getSource();
   Object getSubscriber();
   Method getSubscribe();
   Object getEvent();
}
