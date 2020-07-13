package com.learn.thread.EventBus;

import java.lang.reflect.Method;

/**
 * Subscriber类封装了对象实例和被@Subscribe标记的方法，也就是一个对象实例有可能会被封装称为若干个Subscriber
 *
 *
 * */
public class Subscriber {
    private final Object subscribeObject;

    private final Method subscribeMethod;

    private boolean disable = false;

    public Subscriber(Object subscribeObject,Method subscribeMethod){
        this.subscribeMethod = subscribeMethod;
        this.subscribeObject = subscribeObject;
    }

    public Object getSubscribeObject() {
        return subscribeObject;
    }

    public Method getSubscribeMethod() {
        return subscribeMethod;
    }

    public boolean isDisable(){
        return disable;
    }

    public void setDisable(boolean disable){
        this.disable = disable;
    }
}
