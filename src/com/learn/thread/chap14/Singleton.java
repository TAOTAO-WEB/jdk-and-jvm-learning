package com.learn.thread.chap14;

import java.net.Socket;
import java.sql.Connection;

/**
 * 单例设计是设计模式中最常用的设计模式之一，单例设计提供了一种在多线程情况下保证实例唯一
 * 的解决方法，单例设计模式有多种实现方式，书中介绍了七种
 * */

//1.饿汉式
//final不能被继承
public final class Singleton {
    /**
     * 饿汉式的关键是instance作为类变量并且得到初始化，在多线程情况下不可能被实例化两次
     * 如果一个类中成员属性比较少，饿汉的方式比较推荐，但是如果资源比较重，就不推荐，因为实例所开辟的内存
     * 会驻留比较久
     *
     * */

    //实例变量
    private byte[] data = new byte[1024];

    //在定义实例对象的时候直接初始化
    private static Singleton instance = new Singleton();

    //私有构造函数，不允许外部new
    private Singleton(){}

    public static Singleton getInstance(){
        return instance;
    }
}

//2.懒汉式
//所谓懒汉式就是在使用类实例的时候再去创建
final class Singleton2{

    //实例变量
    private byte[] data = new byte[1024];

    //在定义实例对象的时候直接初始化
    private static Singleton2 instance = null;

    private Singleton2(){}

    public static Singleton2 getInstance(){
        if(instance==null) {
            instance = new Singleton2();
        }
        return instance;
    }

    /**
     * 在多线程环境下无法保证只被实例化一次
     *
     * */

}

//3.懒汉式+同步方法
final class Singleton3{

    //实例变量
    private byte[] data = new byte[1024];

    //在定义实例对象的时候直接初始化
    private static Singleton3 instance = null;

    private Singleton3(){}

    public static synchronized Singleton3 getInstance(){
        if(null==instance) {
            instance = new Singleton3();
        }
        return instance;
    }

}

//4.Double-Check
//高效的数据同步策略，首次初始化时候加锁，之后则允许多个线程同时进行getInstance方法的调用来获得实例
final class Singleton4{

    //实例变量
    private byte[] data = new byte[1024];

    //在定义实例对象的时候直接初始化
    private static Singleton4 instance = null;

    Connection conn;

    Socket socket;

//    private Singleton4(){
//        this.conn; //初始化conn
//        this.socket; //初始化socket
//    }

    public static Singleton4 getInstance(){
        //当instance为null的时候，进入同步代码块，同时该判断避免了每次都需要进入同步代码块
        //可以提高效率
        if(null==instance){
            //只有一个线程能够获得Singleton.class关联的monitor
            synchronized (Singleton4.class){
                //判断如果install为null则创建
                if(null==instance) instance=new Singleton4();
            }
        }
        return instance;
    }
}

//5.Volatile+Double-Check
//DOuble-check虽然是一种巧妙的程序设计但是可能会引起成员变量实例化conn和socket发生在instance之后
//这是jvm指令重排后导致的，而volatile关键字可以防止这种重排序的发生
//只需要将代码修改为 private volatile static Singleton instance = null


//6.Holder方式 是单例设计最好的设计之一也说目前使用比较广的设计之一
//singleton类中并没有instance静态成员，而是将其放到静态内部类中，因此在初始化singleton类的过程中并不会创建实例
//Holder类中定义了静态变量，并且直接实例化
final class Singleton6{
    //实例变量
    private byte[] data = new byte[1024];
    private Singleton6(){}

    //在静态内部类中持有Singleton的实例，并且可以被直接初始化
    private static class Holder{
        private static Singleton6 instance = new Singleton6();
    }

    //调用geiInstance方法实际上是获取Holder的静态属性
    public static Singleton6 getInstance(){
        return Holder.instance;
    }

}


//7枚举方式 枚举类型本身不能被继承，同样是线程安全只能被实例化一次，但是不能被懒加载
enum Singleton7{
    INSTANCE;
    //实例变量
    private byte[] data = new byte[1024];

    Singleton7(){
        System.out.println("instance will be initialized immediately");
    }

    public static void method(){
        //调用该方法会主动使用Singleton，INSTANCE将会被实例化
    }

}

//增加懒特性，类似于holder的方式
class SingletonPro{
    //实例变量
    private byte[] data = new byte[1024];

    private SingletonPro(){}

    //使用枚举充当holder
    private enum EnumHolder{
        INSTANCE;
        private SingletonPro instance;
        EnumHolder(){
            this.instance = new SingletonPro();
        }

        private SingletonPro getInstance(){
            return instance;
        }
    }

    public static SingletonPro getInstance(){
        return EnumHolder.INSTANCE.getInstance();
    }
}
