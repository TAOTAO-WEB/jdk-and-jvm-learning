package com.learn.jvm.chap7;

import java.util.Arrays;

/**
 * clinit 方法对于类和接口不是必须的，如果一个类中没有静态语句块，也没有变量赋值操作那么编译器就不会生成clinit方法
 * 接口中不能使用静态语句块，但任然有变量初始化赋值操作，因此接口雨类一样都会生成clinit方法
 * 但接口与类不同，执行clinit方法不需要先执行父接口的clinit方法，只有父接口定义变量使用，父接口才会初始化
 * 另外接口实现类在初始化一样不会执行接口的clinit方法
 *
 * 虚拟机会保证一个类的clinit方法在多线程的环境被正确加锁，同步，如果多个线程同时去初始化一个类
 * 那么只会有一个线程去执行这个类的clinit方法，其他线程都需要阻塞等待，直到活动线程执行clinit完成
 * 如果在一个类的clinit方法中有耗时很长的操作，就可能造成多个进程阻塞
 *
 * */
public class DeadLoopClass {
    static class DeadLoop{
        //不加上这个if 编译器提示 Initializer does not complete normally 拒绝编译 不过加了也报错
//        if(true){
//            System.out.println(Thread.currentThread() + "init deadLoopClass");
//            while(true){}
//        }

        public static void main(String[] args) {
            Runnable script= new Runnable() {
                @Override
                public void run() {
                    System.out.println(Thread.currentThread() + "start");
                    DeadLoop dlc = new DeadLoop();
                    System.out.println(Thread.currentThread() + "run over");
                }
            };
            Thread t1 = new Thread(script);
            Thread t2 = new Thread(script);
            t1.start();
            t2.start();
        }



    }
}
