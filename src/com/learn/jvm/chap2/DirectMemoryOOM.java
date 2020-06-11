package com.learn.jvm.chap2;

import sun.misc.Unsafe;

import java.lang.reflect.Field;

//本机直接内存溢出
public class DirectMemoryOOM {
    private static final int _1mb = 1024*1024;

    public static void main(String[] args) throws Exception{
        Field unsafeField = Unsafe.class.getDeclaredFields()[0];
        unsafeField.setAccessible(true);
        Unsafe unsafe = (Unsafe) unsafeField.get(null);
        while (true){
            unsafe.allocateMemory(_1mb);
        }
    }
}
