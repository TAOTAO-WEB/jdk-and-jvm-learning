package com.company.thread;

public class RunnableDenyException extends RuntimeException{
    public RunnableDenyException(String message){
        super(message);
    }
}
