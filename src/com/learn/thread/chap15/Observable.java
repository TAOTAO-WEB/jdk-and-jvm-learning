package com.learn.thread.chap15;

/**
 * 当某个对象的发生状态需要通知第三方的时候，观察者模式就很适合
 *
 *
 * */
public interface Observable {
   //任务生命周期枚举类型
    enum Cycle{
       STARTED, RUNNING,DONE,ERROR;
    }

    //获取当前任务的生命周期状态
    Cycle getCycle();

    //定义启动线程的方法，主要作用是为了屏蔽Thread的其他方法
    void start();

    //定义线程的打断方法，作用与start方法一样，也是为了屏蔽thread的其他方法
    void interrupt();
}
