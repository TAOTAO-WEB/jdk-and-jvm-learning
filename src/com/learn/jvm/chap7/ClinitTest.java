package com.learn.jvm.chap7;

/**
 * 初始化
 * 类初始化阶段是类加载过程的最后一步，初始化阶段是执行类构造器<clinit>()方法的过程
 * 该方法是由自动收集类中所有变量的赋值动作和静态语句块中的语句合并产生的
 * 编译器收集顺序是由语句在源文件出现的顺序所决定的，静态语句块中只能访问到定义在静态语句块之前的变量
 * 定义在静态语句块之后的变量，在前面的静态语句块可以赋值但不能访问
 *
 */
public class ClinitTest {
    //非法向前引用变量
    static {
        i = 0;   //给变量赋值能正常通过
        //System.out.println(i); //这句编译器会提示“非法向前引用”
    }
    static int i = 1;  //移到静态块前面能正常编译通过


    /**
     * <clinit>()方法与类的构造函数（或者说实例构造方法<init>()方法）不同
     * 他不需要显示的调用父类构造器，虚拟机会保证在子类的<clinit>()方法执行之前，父类的<clinit>()方法已经执行完毕
     * 由于父类<clinit>()方法先执行，意味着父类中静态语句优先于子类的变量赋值操作，字段B的值将会是2而不是1
     * */
    static class Parent{
        public static int A = 1;
        static{
            A = 2;
        }
    }
    static class Sub extends Parent{
        public static int B = A;
    }
    public static void main(String[] args) {
        System.out.println(Sub.B);
    }
}



