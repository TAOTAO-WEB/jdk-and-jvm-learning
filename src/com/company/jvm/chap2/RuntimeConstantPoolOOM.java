package com.company.jvm.chap2;

import java.util.ArrayList;
import java.util.List;

//运行时，常量池是方法区的一部分
//常量池导致内存溢出异常 jdk1.7之后while循环会一直循环下去
//方法区用于存放class相关信息比如类名，访问修饰符，常量池，字段描述，方法描述
public class RuntimeConstantPoolOOM {

    public static void main(String[] args) {
        //1.7以上版本不会复制实例，只是在常量池里记录首次出现的实例引用，
        // java这个字符串在执行下面语句之前已经出现过，字符串常量池中已经有他的引用，不符合首次出现
        String str1 = new StringBuilder("计算机").append("软件").toString();
        System.out.println(str1.intern()==str1);

        String str2 = new StringBuilder("ja").append("va").toString();
        System.out.println(str2.intern()==str2);
    }

//    public static void main(String[] args) {
//        //使用list保持常量池引用，避免full GC回收常量池行为
//        List<String> list = new ArrayList<>();
//        //10mb的PerSize在integer范围内足够产生oom了
//        int i = 0;
//        while (true){
//            list.add(String.valueOf(i++).intern());
//        }
//    }

}
