package com.learn.jvm.chap7;

/**
 * 双亲委派模型
 * 从jvm角度来说，只存在两种不同的类加载器，一种是启动类加载器，这个使用c++实现，是虚拟机自身一部分，
 * 另一种是所以其他类加载器，这些类加载器都由java实现，独立于虚拟机外部，并且全部都继承自抽象类java.lang.ClassLoader
 * 从开发人员角度，类加载器可以再细分；启动类加载器，扩展类加载器，应用程序类加载器
 *
 * 双亲委派模型：类加载器之间的层次关系--自定义加载器->应用程序类加载器->扩展类加载器->启动类加载器
 * 双亲委派模型要求除了顶层的启动类加载器之外，其余的类加载器都应该有自己的父类加载器
 * 这里类加载器的父子关系不会继承来实习，而是组合复用父加载器代码
 *
 * 双亲委派模型工作过程：如果一个类加载器收到了类加载的请求，她首先不会自己尝试加载这个类，而是把这个请求委派给父类加载器去完成
 * 每一个层次的类加载器都是如此，因此所有请求都会传送到顶层的启动类加载器中，只有当父类加载器反馈自己无法完成这个加载请求（搜索范围没有找到所需要的类），子加载器才会尝试自己加载
 *
 * 双亲委派模型的好处：java类随着他的类加载器一起具备了一种带有优先级的层次关系
 *
 * */
public class ParentLoadClass {

//    protected synchronized Class<?> loadClass(String name,boolean resolve) throws ClassNotFoundException{
//        //首先检查请求的类是否已经被加载过了
//        Class c = findLoadedClass();
//        if(c==null){
//            try{
//                if(parent != null) c=parent.loadClass(name,false);
//                else c = findBootstrapClasOrNull(name);
//            }catch (ClassNotFoundException e){
//                //如果抛出classnotfound 说明父加载器无法完成加载请求
//            }
//            if(c==null){
//                //在父类加载器无法加载时 调用本身的findclass方法
//                c = findClass(name);
//            }
//        }
//        if(resolve) resolveClass(c);
//        return c;
    
}

