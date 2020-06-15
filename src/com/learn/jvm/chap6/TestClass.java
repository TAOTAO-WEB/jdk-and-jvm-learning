package com.learn.jvm.chap6;

//class文件结构采用一种类似c结构体的伪结构来存储数据，这种伪结构只有两种数据：无符号数和表
//无符号数属于基本数据类型，u1，u2，u3分别代表1，2，3个字节的无符号数
//表由多个无符号数或者其他表作为数据项构成的复合数据类型习惯性按照“_info”结尾，整个class文件本质上是一张表
//常量池存放两类常量，字面量（java语音层面的常量，如文本字符串），符号引用（三类：类和接口全限定名，字段名称和描述符，方法名称和描述符）
public class TestClass {
    private int m;
    public int inc(){
        return m+1;
    }

    public static void main(String[] args) {
        TestClass testClass = new TestClass();
        testClass.inc();
    }
}
