package com.learn.thread;

@FunctionalInterface
public interface ThreadFactory {
    Thread createThread(Runnable runnable);
}
