package com.learn.jvm.chap7;

/**
 *  字段解析
 *  1.如果本身就包含了简单名称和字段描述都与目标匹配的字段，返回这个字段直接引用
 *  2.否则，如果实现了接口，将会按照继承关系，从下往上，递归搜索各个接口和他的父接口，如果接口中包含了字段，则返回
 *  3.否则，如果不是java.lang.Object的话，将会按照继承关系从下往上递归搜索其父类，如果父类中包含匹配字段，则返回
 *  4.否则，查找失败，抛出java.lang.NoSuchFieldError
 *  在查找过程中成功返回了引用，将会进行权限验证，如果发现不具备字段访问权限，抛出java.lang.IllegalAccessError
 *  在实际中，编译器会比上述严格，如果有同名字段出现在方法的接口和父类中，那么编译器可能会拒绝编译
 *
 *
 * */
public class FieldResolution {

    interface Interface0{
        int A = 0;
    }

    interface Interface1 extends Interface0{
        int A = 1;
    }

    interface Interface2 {
        int A = 2;
    }

    static class Parent implements Interface1{
        public static int A = 3;
    }

    static class Sub extends Parent implements Interface2{
        public static int A = 4;
    }

    public static void main(String[] args) {
        System.out.println(Sub.A); //注释掉会编译错误
    }
}
