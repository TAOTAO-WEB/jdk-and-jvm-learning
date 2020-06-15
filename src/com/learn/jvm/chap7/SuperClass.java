package com.learn.jvm.chap7;

/**
    类的生命周期包括：加载，验证，准备，解析，初始化，使用，卸载
    其中加载，验证，准备，初始化，卸载顺序确定，解析在某些情况可以在初始化之后开始（支持运行时绑定）
    加载的时间没有强制约束，初始化有严格规定，有5种情况
    1.使用new关键字实例化对象，读取或者设置一个类的静态字段（被final修饰，已经在编译期把结果放入常量池的除外），以及调用类的静态方法的时候
    2.对类进行反射调用时候，如果类没有经过初始化，则先初始化
    3.当初始化一个类时候，若其父类未经过初始化，则先触发其父类初始化
    4.当虚拟机启动时候，用户需要指定一个要执行的主类（包含main），虚拟机会先初始化这个主类
    5.暂时看不懂
    除以上5种情况，所有引用类的方式不会触发初始化，称为被动引用

 */

/**
 * 被动使用类字段演示一：
 * 通过子类引用父类的静态字段，不会导致子类初始化
 *
 * */
class SuperClass {
    static {
        System.out.println("superclass init!");
    }
    public static int value = 123;
}

class subClass extends SuperClass{
    static {
        System.out.println("subclass init!");
    }
}

/**
 * 非主动使用类字段演示
 * */
class NotInitialization{
    public static void main(String[] args) {
        System.out.println(subClass.value);
    }
}

/**
 * 被动使用类字段演示二：
 * 通过数组定义来引用类，不会触发此类的初始化
 *
 * */
class NotInitialization2{
    public static void main(String[] args) {
        SuperClass[] sca = new SuperClass[10];
    }
}

/**
 * 被动使用类字段演示三：
 * 常量在编译阶段会存入调用类的常量池中，本质上并没有引用到定义常量的类，因此不会触发定义常量的类
 * 的初始化
 *
 **/
class ConstClass{
    static {
        System.out.println("constantClass init");
    }
    public static final String HELLO = "hello world";
}

class NotInitialization3{
    public static void main(String[] args) {
        System.out.println(ConstClass.HELLO);
    }
}
