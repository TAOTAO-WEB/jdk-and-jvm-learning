package com.learn.jvm.chap13;

import java.util.Vector;

/**
 * 按照线程安全程度由强至弱排序，分为5类，不可变，绝对线程安全，相对线程安全，线程兼容，线程对立
 *
 * 1.不可变：final关键字，一定是线程安全的
 * 2.绝对线程安全：java.util.Vector是线程安全容器，都被synchronized修饰,但是依然需要同步手段
 * 3.相对线程安全：通常意义上所讲的线程安全，需要保证单独操作是线程安全的，在调用时候不需要做额外保障
 * 4.线程兼容，对象本身不是线程安全的，比如ArrayLisr和HashMap
 * 5：线程对立：无论调用端是否采取了同步措施，都无法在多线程环境中并发使用代码
 *
 *
 * 线程安全的实现方法：
 * 1.互斥同步（阻塞同步）：synchronized,ReentrantLock
 * 2.非阻塞同步：乐观的并发策略，先进性操作，如果没有其他线程争用共享数据，操作就成功了；如果有产生冲突，在采取补偿措施（不断重试...）
 * 3.无同步方案
 *
 *
 * */
public class SafeTest {
    private static Vector<Integer> vector = new Vector<>();

    public static void main(String[] args) {
        Thread remove = new Thread(new Runnable() {
            @Override
            public void run() {
                synchronized (vector){
                    for(int i=0;i<vector.size();i++) vector.remove(i);
                }
            }
        });

        Thread print = new Thread(new Runnable() {
            @Override
            public void run() {
                synchronized (vector){
                    for(int i=0;i<vector.size();i++) System.out.println(vector.get(i));
                }
            }
        });
    }
}
