package com.learn.test;


import com.learn.thread.EventBus.Bus;
import com.learn.thread.EventBus.EventBus;
import com.learn.thread.EventBus.Subscribe;

public class EventBusTest {
    @Subscribe
    public void method1(String message){
        System.out.println("m1"+message);
    }

    @Subscribe(topic = "test")
    public void method2(String message){
        System.out.println("m2"+message);
    }

    public static void main(String[] args) {
        Bus bus = new EventBus("TestBus");
        bus.register(new EventBusTest());
        bus.register(new EventBusTest());
        bus.post("hello");
        System.out.println("---------");
        bus.post("hello","test");
    }
}
