package com.learn.jvm.chap10;

import java.util.Arrays;
import java.util.Iterator;
import java.util.List;

/**
 * 编译期：
 * 前端编译器：把.java转变为.class文件的过程 例如sun的javac
 * 后端运行期编译器（JIT编译器）：把字节码转变成机器码的过程
 * 静态提前编译器（AOT编译器）：直接把.java文件编译成本地机器码的过程
 *
 * 编译过程三个过程：
 * 1.解析与填充符号表
 * 2.插入式注解处理器的注解处理过程
 * 3.分析与字节码生成过程
 *
 * 一.解析与填充符号表
 *  1.语法，词法分析
 *      词法分析是将字符流转为标记集合（关键字，变量名，字面量，运算符）
 *      语法分析是根据标记序列构造抽象语法树的过程
 *  2.填充符号表
 *
 *  final修饰仅仅是在编译器保证，在运行期生成的class文件没有区别
 *
 * 把泛型代码编译后重新反编译回去之后，就会发现变回了原生类型，、
 * 其实ArrayList<int>和ArrayList<String>就是同一个类，泛型技术其实是java的一颗语法糖
 *
 * */
public class ContentLearn {
    //自动装箱，拆箱与遍历循环
    public static void main(String[] args) {
//        List<Integer> list = [1,2,3,4];
//        int sum = 0;
//        for(int i:list) sum+=i;
//        System.out.println(sum);

        //上面代码编译后的变化
//        List list = Arrays.asList(new Integer[]{
//                Integer.valueOf(1),
//                Integer.valueOf(2),
//                Integer.valueOf(3)
//        });
//        int sum = 0;
//        for(Iterator iterator=list.iterator();iterator.hasNext();){
//            int i = ((Integer)iterator.next()).intValue();
//            sum+=i;
//        }
//        System.out.println(sum);

        /**
         * 自动装箱的陷阱
         * ==运算符在不遇到算术运算的情况下不会自动拆箱，equals方法不处理数据转型关系
         *
         * */
        Integer a = 1;
        Integer b = 2;
        Integer c = 3;
        Integer d = 3;
        Integer e = 321;
        Integer f = 321;
        Long g = 3L;
        System.out.println(c == d); //true
        System.out.println(e == f); //false
        System.out.println(c == (a+b));
        System.out.println(c.equals(a+b));
        System.out.println(g==(a+b));
        System.out.println(g.equals(a+b));

        /**
         *
         * 在-128~127的Integer值并且以Integer x = value 的方式赋值的
         * Integer值在进行==和equals比较时，都会返回true，
         * 因为Java里面对处在在-128~127之间的Integer值，用的是原生数据类型int，会在内存里供重用，
         * 也就是说这之间的Integer值进行==比较时只是进行int原生数据类型的数值比较，
         * 而超出-128~127的范围，进行==比较时是进行地址及数值比较。
         *
         * */

    }

}
