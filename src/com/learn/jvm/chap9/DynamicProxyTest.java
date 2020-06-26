package com.learn.jvm.chap9;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;

//动态代理的演示
public class DynamicProxyTest {
    interface IHello{
        void sayHello();
    }
    static class Hello implements IHello{
        @Override
        public void sayHello() {
            System.out.println("hello world");
        }
    }

    static class DynamicProxy implements InvocationHandler{
        Object originalObj;
        Object bind(Object originalObj){
            this.originalObj = originalObj;
            //返回了一个实现的IHello接口，并且代理了new Hello（）实例为对象
            return Proxy.newProxyInstance(originalObj.getClass().getClassLoader(),
                    originalObj.getClass().getInterfaces(),this);
        }

        @Override
        public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
            System.out.println("welcome");
            return method.invoke(originalObj,args);
        }
    }

    public static void main(String[] args) {
        IHello hello = (IHello) new DynamicProxy().bind(new Hello());
        hello.sayHello();
    }

}
