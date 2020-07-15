package com.learn.design.CreationalPattern;

/**
 * 单例模式
 * 用来保证一个对象只能创建一个实例
 * 单例构造器都要被声明为私有，再通过静态方法实现全局访问获得该单例实例
 *
 * */
public class Singleton {
    private static Singleton instance;
    private Singleton(){
        System.out.println("Singleton is Instantiated");
    }
    //
    public static Singleton getInstance(){
        if(instance==null) instance = new Singleton();
        return instance;

        //拥有双重校验锁机制的同步锁机单例模式
//        if(instance==null){
//            synchronized (Singleton.class){
//                if(instance==null) instance = new Singleton();
//            }
//        }
    }
    public void doSomething(){
        System.out.println("Something is Done");
    }

    //在单例调用对象时候，只需要镜像简单的调用，例如：Singleton.getInstance().doSomething();
}

/**
 * 无锁的线程安全单例模式
 * java中单例模式的最佳实现形式中，类只会加载一次。通过在声明时直接实例化静态成员的方式来保证一个类中只有一个实例
 * 这种方式避免了使用同步锁机制和判断实例十分被创建的额外检查
 *
 * */
class LockFreeSingleton{
    private static final LockFreeSingleton instance = new LockFreeSingleton();

    private LockFreeSingleton()
    {
        System.out.println("Singleton is Instantiated");
    }

    public static synchronized LockFreeSingleton getInstance(){
        return instance;
    }
}
