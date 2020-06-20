package com.learn.jvm.chap7;

/**类与类加载器
 * 比较类是否相等只有在同一个类加载器的前提下才有意义
 * 相等包括Class对象的equals（）方法，isAssignableFrom()方法，isInstance()，
 *
 *
 * */

import java.io.IOException;
import java.io.InputStream;

/**
 * 类加载器与instance关键字演示
 * 一个是由系统应用程序加载器加载的，另外一个是由我们自定义的类加载器加载的，虽然来自同一个class文件
 * 但依然是两个独立的类
 * */
public class ClassLoaderTest {
    public static void main(String[] args) throws Exception {
        ClassLoader myLoader = new ClassLoader() {
            @Override
            public Class<?> loadClass(String name) throws ClassNotFoundException {
                try {
                    String fileName = name.substring(name.lastIndexOf(".")+1) + ".class";
                    InputStream is = getClass().getResourceAsStream(fileName);
                    if(is==null) return super.loadClass(name);
                    byte[] b = new byte[is.available()];
                    is.read(b);
                    return defineClass(name,b,0,b.length);
                } catch (IOException e) {
                    throw new ClassNotFoundException(name);
                }
            }
        };

        Object obj = myLoader.loadClass("com.learn.jvm.chap7.ClassLoaderTest").newInstance();
        System.out.println(obj.getClass());
        System.out.println(obj instanceof com.learn.jvm.chap7.ClassLoaderTest);
    }
}
