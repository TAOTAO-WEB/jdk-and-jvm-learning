package com.learn.thread.chap15;

@FunctionalInterface
public interface Task<T> {
    //任务执行时候，该接口允许有返回值
    T call();
}
