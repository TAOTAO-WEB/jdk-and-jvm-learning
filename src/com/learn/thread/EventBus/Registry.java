package com.learn.thread.EventBus;

import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentLinkedQueue;


/**
 * 注册表Register
 * 注册表维护了topic和subscriber之间的关系，当有Event被post之后，Dispatcher需要知道该消息应该发送给
 * 哪个Subscriber的实例和对应的方法，Subscriber对象没用任何特殊要求
 *
 *
 * */
public class Registry {
    //存储Subscriber集合和topic之间关系的map
    private final ConcurrentHashMap<String, ConcurrentLinkedQueue<Subscriber>> subscriberContainer = new ConcurrentHashMap<>();

    public void bind(Object subscriber){
        //获取subscriber object的方法集合然后进行绑定
        List<Method> subscribeMethods = getSubscribeMethods(subscriber);
        subscribeMethods.forEach(method -> tierSubscriber(subscriber,method));
    }

    public void unbind(Object subscriber){
        //unbind为了提高速度，只对subscriber进行失效操作
        subscriberContainer.forEach((key,queue)->queue.forEach(s->{
            if(s.getSubscribeObject() == subscriber){
                s.setDisable(true);
            }
        }));
    }

    public ConcurrentLinkedQueue<Subscriber> scanSubscriber(final String topic){
        return subscriberContainer.get(topic);
    }

    private void tierSubscriber(Object subscriber ,Method method){
        final Subscribe subscribe = method.getDeclaredAnnotation(Subscribe.class);
        String topic = subscribe.topic();
        //当某topic没用Subscriber Queue的时候创建一个
        subscriberContainer.computeIfAbsent(topic,key->new ConcurrentLinkedQueue<>());
        //创建一个Subscriber并且加入Subscriber列表中
        subscriberContainer.get(topic).add(new Subscriber(subscriber,method));
    }

    private List<Method> getSubscribeMethods(Object subscriber){
        final List<Method> methods = new ArrayList<>();
        Class<?> temp = subscriber.getClass();
        //不断获取当前类和父类的所有@Subscribe方法
        while (temp!=null){
            //获取所有的方法
            Method[] declaredMethods = temp.getDeclaredMethods();
            //只有public方法 && 有一个入参 && 最重要的是被@Subscribe标记的方法才符合回调方法
            Arrays.stream(declaredMethods).filter(
                    method -> method.isAnnotationPresent(Subscribe.class) &&
                    method.getParameterCount() == 1 &&
                    method.getModifiers() == Modifier.PUBLIC).forEach(methods::add);
            temp = temp.getSuperclass();
        }
        return methods;
    }


}
