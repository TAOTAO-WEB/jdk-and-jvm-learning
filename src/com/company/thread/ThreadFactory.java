package com.company.thread;

@FunctionalInterface
public interface ThreadFactory {
    Thread createThread(Runnable runnable);
}
