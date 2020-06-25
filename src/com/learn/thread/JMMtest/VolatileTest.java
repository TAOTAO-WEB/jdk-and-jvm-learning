package com.learn.thread.JMMtest;

/**
 * volatile解释为易变的，用这个申明变量即表示变量极有可能被某些线程修改，虚拟机要保证变量的可见性
 * volatile对于保证操作的原子性有很大帮助，但并不能完全代替锁，无法保证一些复合操作的原子性，比如i++
 *
 * */
public class VolatileTest {
    static volatile int i = 0;
    public static class PlusTask implements Runnable{

        @Override
        public void run() {
            for(int k=0;k<10000;k++) i++;
        }
    }

    //输出小于10000
    public static void main(String[] args) throws InterruptedException {
        Thread[] threads = new Thread[10];
        for(int i=0;i<10;i++){
            threads[i]=new Thread(new PlusTask());
            threads[i].start();
        }
        for(int i=0;i<10;i++){
            threads[i].join();
        }
        System.out.println(i);
    }
}
