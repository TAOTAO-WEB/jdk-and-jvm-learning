package com.learn.thread.myThreadPool;

@FunctionalInterface
public interface ThreadFactory {
    Thread createThread(Runnable runnable);
}
