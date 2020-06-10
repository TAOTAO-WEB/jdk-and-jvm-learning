package com.company.thread;

import java.util.ArrayDeque;
import java.util.Queue;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;

public class BasicThreadPool extends Thread implements ThreadPool{
//    初始化线程数量
    private final int initSize;
//    初始化线程池最大线程数量
    private final int maxSize;
//    线程池核心线程数量
    private final int coreSize;
//    当前活跃的线程数量
    private int activeCount;
//    创建线程所需要的工厂
    private final ThreadFactory threadFactory;
//    任务队列
    private final RunnableQueue runnableQueue;
//    线程池是否已经被shutdown
    private volatile boolean isShutdown = false;
//    工作线程队列
    private final Queue<ThreadTask> threadTaskQueue = new ArrayDeque<>();

    private final static DenyPolicy DEFAULT_DENY_POLICY = new DenyPolicy.DiscardDenyPolicy();

    private final static ThreadFactory DEFAULT_THREAD_FACTORY = new DefaultThreadFactory();

    private final long keepAliveTime;

    private final TimeUnit timeUnit;

    //构造需要传递的参数：初始化线程数量，最大线程数量，核心线程数量，任务队列的最大数量
    public BasicThreadPool(int initSize,int maxSize,int coreSize,int queueSize){
        this(initSize,maxSize,coreSize,queueSize,DEFAULT_DENY_POLICY,
                DEFAULT_THREAD_FACTORY, 10,TimeUnit.SECONDS);
    }

    //构造线程池需要传入的参数
    public BasicThreadPool(int initSize, int maxSize, int coreSize, int queueSize,DenyPolicy denyPolicy,
                           ThreadFactory threadFactory, long keepAliveTime, TimeUnit timeUnit) {
        this.initSize = initSize;
        this.maxSize = maxSize;
        this.coreSize = coreSize;
        this.threadFactory = threadFactory;
        this.keepAliveTime = keepAliveTime;
        this.timeUnit = timeUnit;
        this.runnableQueue = new LinkedRunnableQueue(queueSize,denyPolicy,this);
        this.init();
    }

    private void init(){
        start();
        for(int i=0;i<initSize;i++){
            newThread();
        }
    }

    @Override
    public void run() {
        //run方法继承自thread，主要用于维护线程数量，比如扩容，回收工作
        while (!isShutdown && isInterrupted()){
            try {
                timeUnit.sleep(keepAliveTime);
            }catch (InterruptedException e){
                isShutdown = true;
                break;
            }
            synchronized (this){
                if(isShutdown) break;
                //当前队列中有任务尚未处理，并且activeCount< coreSize 则继续扩容
                if(runnableQueue.size() > 0 && activeCount <coreSize){
                    for(int i=initSize;i<coreSize;i++){
                        newThread();
                    }
                    //continue 的目的在于不想让线程的扩容直接达到maxsize
                    continue;
                }
                //当前队列中有任务尚未处理，并且activeCount<maxCount则继续扩容
                if(runnableQueue.size()>0 && activeCount < maxSize){
                    for(int i=coreSize;i<maxSize;i++){
                        newThread();
                    }
                }
                //如果任务队列中没有任务，则需要回收，回收至coreSize
                if(runnableQueue.size()==0 && activeCount>coreSize){
                    for(int i=coreSize;i<activeCount;i++){
                        removeThread();
                    }
                }
            }
        }


    }

    private void removeThread(){
        //从线程池中移除某个线程
        ThreadTask threadTask = threadTaskQueue.remove();
        threadTask.internalTask.stop();
        this.activeCount--;
    }

    private void newThread(){
        //创建任务线程，并且启动
        InternalTask internalTask = new InternalTask(runnableQueue);
        Thread thread = this.threadFactory.createThread(internalTask);
        ThreadTask threadTask = new ThreadTask(thread,internalTask);
        threadTaskQueue.offer(threadTask);
        this.activeCount++;
        thread.start();

    }

    private static class ThreadTask{
        Thread thread;
        InternalTask internalTask;
        public ThreadTask(Thread thread,InternalTask internalTask){
            this.thread = thread;
            this.internalTask = internalTask;
        }
    }

    private static class DefaultThreadFactory implements ThreadFactory{
        private static final AtomicInteger GROUP_COUNTER = new AtomicInteger(1);
        private static final ThreadGroup group = new ThreadGroup("MyThreadPool-"+GROUP_COUNTER.getAndDecrement());
        private static final AtomicInteger COUNTER = new AtomicInteger(0);

        @Override
        public Thread createThread(Runnable runnable) {
            return new Thread(group,runnable,"com.company.thread-pool-"+COUNTER.getAndDecrement());
        }
    }

    @Override
    public void execute(Runnable runnable) {
        if(this.isShutdown) throw new IllegalStateException("The com.company.thread pool is destroy");
        this.runnableQueue.offer(runnable);
    }

    @Override
    public void shutdown() {
        synchronized (this){
            if(isShutdown) return;
            isShutdown=true;
            threadTaskQueue.forEach(threadTask -> {
                threadTask.internalTask.stop();
                threadTask.thread.interrupt();
            });
            this.interrupt();
        }
    }

    @Override
    public int getInitSize() {
        if(isShutdown) throw new IllegalStateException("the com.company.thread pool is destroy");
        return this.initSize;
    }

    @Override
    public int getMaxSize() {
        if(isShutdown) throw new IllegalStateException("the com.company.thread pool is destroy");
        return this.maxSize;
    }

    @Override
    public int getCoreSize() {
        if(isShutdown) throw new IllegalStateException("the com.company.thread pool is destroy");
        return this.coreSize;
    }

    @Override
    public int getQueueSize() {
        if(isShutdown) throw new IllegalStateException("the com.company.thread pool is destroy");
        return runnableQueue.size();
    }

    @Override
    public int getActiveCount() {
        synchronized (this){
            return this.activeCount;
        }
    }

    @Override
    public boolean isShutdown() {
        return this.isShutdown;
    }
}
