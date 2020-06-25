package com.learn.jvm.chap8;

/**
 * 方法静态解析演示
 *
 * 方法调用不等于方法执行
 * 静态方法，私有方法，实例构造器，父方法，在类加载的时候就会把符合引用解析为该方法的直接引用称之为非虚方法
 *
 * */
public class StaticResolution {
    public static void sayHello(){
        System.out.println("hello world");
    }

    public static void main(String[] args) {
        StaticResolution.sayHello();
    }
}

