package com.learn.jvm.chap3;

//引用计数法的缺陷 java主流是使用可达性分析算法判定对象是否存活
//垃圾收集算法主要使用复制算法/分代收集算法
//-Xlog:gc* 来查看gc日志
public class ReferenceCountingGC {
    public static void main(String[] args) {
        ReferenceCountingGC.testGC();
    }
    public Object instance = null;
    private static final int _1mb = 1024* 1024;
    //这个成员的唯一意义就是站点内存，以便于在GC日志中看清楚是否被回收过
    private static void testGC(){
        ReferenceCountingGC objA = new ReferenceCountingGC();
        ReferenceCountingGC objB = new ReferenceCountingGC();
        objA.instance = objB;
        objB.instance = objA;
        objA=null;
        objB=null;

        System.gc();
    }

}
