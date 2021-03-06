package com.learn.jvm.chap8;

/**
 * 虚拟机字节码的虚拟引擎
 * 执行引擎是jvm最核心的组成部分之一，输入的是字节码文件，处理过程是字节码解析的等效结果
 *
 * 栈帧是用于支持虚拟机进行方法调用和方法执行的数据结构，他是jvm运行时数据区中的虚拟机栈的栈元素
 * 栈帧存储了方法的局部变量表，操作数栈，动态连接和方法返回地址等信息
 * 每一个方法从调用开始至完成的过程都对应一个栈帧在jvm里面从入栈到出栈的过程
 *
 *
 * */


/**
 * 局部变量表是一组变量值存储空间，用于存放方法参数和方法内定义的局部变量
 * 局部变量表的容量以变量槽为最小单位Slot，
 *
 * 操作数栈也常称操作栈，后入先出，当一个方法刚刚开始执行，操作数栈是空的
 * 有时候为了优化两个栈帧的部分局部变量表和部分操作数栈会重叠在一起
 *
 * 动态连接。每个栈帧都包含一个指向运行时常量池中该栈帧所属方法的引用，持有这个引用是为了支持方法
 * 调用过程中的动态连接
 *
 * 方法返回地址。方法退出之后，都需要返回到方法被调用的位置，程序才能继续执行，方法返回时可能需要在栈帧中保存一些信息
 * 用来帮助他的上层方法的执行状态。方法正常退出，调用者的pc计数器的值可以作为返回地址，栈帧中很可能会保存这个计数器值。方法异常退出时，返回地址是要通过异常处理器
 * 表来确定的。
 *
 * */
public class SlotTest {
    //局部变量表对垃圾回收的影响

    //处于作用域内没有回收
//    public static void main(String[] args) {
//        byte[] place = new byte[64*1024*1024];
//        System.gc();
//    }

    //处于作用域外也未回收
//    public static void main(String[] args) {
//        {
//            byte[] place = new byte[64*1024*1024];
//        }
//        System.gc();
//    }

    /**成功回收，根本原因是局部变量表slot是否还有place数组对象的引用，第一次修改时，代码离开作用域
     * 但这之后，没有任何对局部变量读写操作，place原本所占用的slot还没有被其他变量所复用，
     * 所以一部分局部变量表任然保持对他的关联
     *
     *
     */
    public static void main(String[] args) {
        {
            byte[] place = new byte[64*1024*1024];
        }
        int a = 0;
        System.gc();
    }


}
