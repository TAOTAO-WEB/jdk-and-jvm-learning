package com.learn.thread.EventBus;

/**
 * Bus接口定义了EventBus的所有使用方法
 * */
public interface Bus {

    //将某个对象注册到bus上，从此之后该类成为subscriber
    void register(Object subscriber);

    //将某个对象从bus上取消注册，取消注册之后不会再收到来自bus的任何消息
    void unregister(Object subscriber);

    //提交event到默认topic
    void post(Object event);

    //提交event到指定topic
    void post(Object Event,String topic);

    //关闭bus
    void close();

    //返回bus的名称表示
    String getBusName();

}
