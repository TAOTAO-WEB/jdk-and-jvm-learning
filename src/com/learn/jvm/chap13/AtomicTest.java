package com.learn.jvm.chap13;

import java.util.concurrent.atomic.AtomicInteger;

/**
 * 锁优化
 * 自旋锁与自适应自旋：
 * 锁消除：
 * 锁粗化：
 * 轻量级锁：
 * 偏向锁：
 *
 *
 * */


//atomic变量自增运算测试 原子性
public class AtomicTest {
    public static AtomicInteger race = new AtomicInteger(0);
    public static void increase(){
        race.incrementAndGet();
    }
    private static final int THREADS_COUNT = 20;

    public static void main(String[] args) {
        Thread[] threads = new Thread[THREADS_COUNT];
        for(int i=0;i<THREADS_COUNT;i++){
            threads[i] = new Thread(new Runnable() {
                @Override
                public void run() {
                    for(int i=0;i<1000;i++){
                        increase();
                    }
                }
            });
            threads[i].start();
        }
        while (Thread.activeCount()>1){
            Thread.yield();
        }
        System.out.println(race);
    }
}
