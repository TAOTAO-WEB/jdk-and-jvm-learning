package com.learn.thread.myThreadPool;

public class InternalTask implements Runnable {
    private final RunnableQueue runnableQueue;

    private volatile boolean running = true;

    public InternalTask(RunnableQueue runnableQueue) {
        this.runnableQueue = runnableQueue;
    }

    @Override
    public void run() {
        //如果当前任务为running并且没被中断，则其将不断从queue中获取runnable，然后执行run方法
        while (running && !Thread.currentThread().isInterrupted()){
            Runnable task = runnableQueue.take();
            task.run();

        }

    }

    //停止当前任务，主要会在线程池的shutdown方法中使用
    public void stop(){
        this.running = false;
    }
}
