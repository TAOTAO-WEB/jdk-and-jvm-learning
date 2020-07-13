package com.learn.thread.chap16;

/**
 * single thread execution 模式是指在同一时间实可只能有一个线程去访问共享资源
 *
 * */
public class Single {
    String boardPass;
    String idCard;
    //避免多线程之中赋值出现线程交错的现象
    public synchronized void pass(String boardPass,String idCard){
        this.boardPass = boardPass;
        this.idCard = idCard;
    }

    //如果不得当使用会导致死锁情况

}
