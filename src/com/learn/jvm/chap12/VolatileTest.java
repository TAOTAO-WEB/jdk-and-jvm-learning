package com.learn.jvm.chap12;

/**
 * java内存模型与线程
 * java内存模型，JMM
 * 线程之间变量的传递需要通过主内存来完成， 线程，工作内存，主内存关系：
 * 线程 <--> 工作内存<--> save and load操作 <--> 主内存
 * 为了获取更好的运行速度，jvm可能会让工作内存优先存储与寄存器和高速缓存中
 *
 * 关于主内存和工作内存之间的交互，java内存模型定义了8种操作：
 * lock，unlock，read，load，use，assign，store，write
 *
 * */

public class VolatileTest {
    public static volatile int race = 0;
    public static void increase(){
        race++;
    }

    private static final int THREADS_COUNT = 20;

    public static void main(String[] args) {
        Thread[] threads = new Thread[THREADS_COUNT];
        for(int i=0;i<THREADS_COUNT;i++){
            threads[i] = new Thread(new Runnable() {
                @Override
                public void run() {
                    for(int i=0;i<100;i++) increase();
                }
            });
            threads[i].start();
        }
        //等待所有累加线程都结束
        while (Thread.activeCount()>1) Thread.yield();
        System.out.println(race);
    }
}
