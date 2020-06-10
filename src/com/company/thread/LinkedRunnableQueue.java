package com.company.thread;

import java.util.LinkedList;

public class LinkedRunnableQueue implements RunnableQueue {
    //任务队列的最大容量
    private final int limit;
    //若任务队列中的任务已经满了，则需要执行拒绝策略
    private final DenyPolicy denyPolicy;
    //存放任务的队列
    private final LinkedList<Runnable> runnableList = new LinkedList<>();

    private final ThreadPool threadPool;

    public LinkedRunnableQueue(int limit, DenyPolicy denyPolicy, ThreadPool threadPool) {
        this.limit = limit;
        this.denyPolicy = denyPolicy;
        this.threadPool = threadPool;
    }

    @Override
    public void offer(Runnable runnable) {
        synchronized (runnableList){
            if(runnableList.size()>=limit){
                //无法容纳新的任务执行拒绝策略
                denyPolicy.reject(runnable,threadPool);
            }
            else {
                //将任务加入队尾，并且唤醒阻塞中的线程
                runnableList.addFirst(runnable);
                runnableList.notifyAll();
            }
        }
    }

    @Override
    public Runnable take() {
        synchronized (runnableList){
            while (runnableList.isEmpty()){
                try {
                    //如果队列中没有可以执行的任务，则当前线程将会挂起，进入runnableList关联的monitor wait set中等待唤醒（新的任务加入）
                    runnableList.wait();
                } catch (InterruptedException e) {
                    //被中断需要把异常抛出
                    e.printStackTrace();
                }
            }
            return runnableList.removeFirst();
        }
    }

    @Override
    public int size() {
        synchronized (runnableList){
            return runnableList.size();
        }
    }
}
