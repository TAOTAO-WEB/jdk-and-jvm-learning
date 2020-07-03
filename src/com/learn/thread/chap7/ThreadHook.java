package com.learn.thread.chap7;


import java.util.concurrent.TimeUnit;

/**
 * 在jvm进程退出的时候，hook线程（钩子线程）会启动执行，用过runtime可以为jvm注入多个hook线程
 * 在jvm进程即将退出时候，两个hook线程会被启动并且运行
 *
 * */
public class ThreadHook {
    public static void main(String[] args) {
        Runtime.getRuntime().addShutdownHook(new Thread(new Runnable() {
            @Override
            public void run() {
                try{
                    System.out.println("The hook thread 1 is running");
                    TimeUnit.SECONDS.sleep(1);
                }catch (InterruptedException e){
                    e.printStackTrace();
                }
                System.out.println("the hook thread 1 will exit");
            }
        }));

        Runtime.getRuntime().addShutdownHook(new Thread(new Runnable() {
            @Override
            public void run() {
                try{
                    System.out.println("the hook thread 2 is running");
                    TimeUnit.SECONDS.sleep(1);
                }catch (InterruptedException e){
                    e.printStackTrace();
                }
                System.out.println("the hook thread 2 will exit");
            }
        }));

        System.out.println("the program will is stopping");
    }
}
