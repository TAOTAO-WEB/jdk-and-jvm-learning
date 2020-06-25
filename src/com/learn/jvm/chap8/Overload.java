package com.learn.jvm.chap8;

import java.io.Serializable;

//重载方法的优先级
//自动类型转换顺序 char->int->long->float->double
public class Overload {

//    public static void sayHello(Object obj){
//        System.out.println("hello world ");
//    }

//    public static void sayHello(int obj){
//        System.out.println("hello int");
//    }

//    public static void sayHello(long obj){
//        System.out.println("hello long");
//    }

    //自动装箱
//    public static void sayHello(Character obj){
//        System.out.println("hello character");
//    }

//    public static void sayHello(char obj){
//        System.out.println("hello char");
//    }

    //变长参数重载优先级最低
    public static void sayHello(char... chars){
        System.out.println("hello char...");
    }

    //java.lang.Serializable是java.lang.Character类实现的一个接口，自动装箱类找不到，就找实现了的接口类型
    //char可以转int，但是Character不会转Integer，只能安全的转型为他实现的接口或者父类
    //Character还实现了Comparable接口，如果同时出现优先级一样，编译器拒绝编译
//    public static void sayHello(Serializable obj){
//        System.out.println("hello Serializable");
//    }

    public static void main(String[] args) {
        sayHello('a');   //通过注释掉方法，会得到不同的结果
    }
}
