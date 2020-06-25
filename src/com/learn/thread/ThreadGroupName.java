package com.learn.thread;

//在一个系统中，如果线程数量很多，而且功能分配比较明确，就可以将功能相同的线程放置在一个线程里
public class ThreadGroupName implements Runnable{
    @Override
    public void run() {
        String group = Thread.currentThread().getThreadGroup().getName() + "-" +
                Thread.currentThread().getName();
        while (true){
            System.out.println("I am"+group);
            try {
                Thread.sleep(3000);
            }catch (InterruptedException e){
                e.printStackTrace();
            }
        }
    }

    public static void main(String[] args) {
        ThreadGroup tg = new ThreadGroup("PrintGroup");
        Thread t1 = new Thread(tg,new ThreadGroupName(),"T1");
        Thread t2 = new Thread(tg,new ThreadGroupName(),"T2");
        t1.start();
        t2.start();
        System.out.println(tg.activeCount());
        tg.list();
    }
}
