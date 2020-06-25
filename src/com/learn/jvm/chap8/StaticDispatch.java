package com.learn.jvm.chap8;

/**
 * 方法静态分派演示
 *
 * */
public class StaticDispatch {
    static abstract class Human{}
    static class Man extends Human{}
    static class Woman extends Human{}
    public void sayHello(Human guy){
        System.out.println("hello guy");
    }
    public void sayHello(Man guy){
        System.out.println("hello gentleman");
    }
    public void sayHello(Woman guy){
        System.out.println("hello young lady");
    }

    /**
     * 代码中的human称为变量的静态类型或者叫做外观类型，后面的man称为变量的实际类型
     * 静态类型是在编译期可知的；实际类型的变化结果在运行期才可确定
     * jvm在重载时通过参数的静态类型而不是实际类型作为判定依据
     * 所有依赖静态类型来定位方法执行版本的分派动作称为静态分派，静态分派的典型应用是方法重载
     * 静态分派发生在编译阶段
     * */
    public static void main(String[] args) {
        Human man = new Man();
        Human woman = new Woman();
        StaticDispatch sd = new StaticDispatch();
        //执行结果为human的重载
        sd.sayHello(man);
        sd.sayHello(woman);
    }
}
