package com.learn.thread.myThreadPool;

public class RunnableDenyException extends RuntimeException{
    public RunnableDenyException(String message){
        super(message);
    }
}
