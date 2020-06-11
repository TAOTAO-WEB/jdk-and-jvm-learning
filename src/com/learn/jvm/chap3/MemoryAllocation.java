package com.learn.jvm.chap3;

//内存分配与回收策略
//Java中的堆是JVM所管理的最大的一块内存空间
//堆被划分成两个不同的区域：新生代、老年代
//新生代又被划分为三个区域：Eden、survivor(From Survivor、To Survivor)
//Full GC 就是收集整个堆，包括新生代，老年代，永久代
//(在JDK 1.8及以后，永久代会被移除，换为metaspace)等收集所有部分的模式
//新生代的垃圾收集叫做 Minor GC
public class MemoryAllocation {

    //对象优先在Eden分配
    private static final int _1mb = 1024*1024;
    public static void testAllocation(){
        byte[] allocation1,allocation2,allocation3,allocation4;
        allocation1 = new byte[2*_1mb];
        allocation2 = new byte[2*_1mb];
        allocation3 = new byte[2*_1mb];
        allocation4 = new byte[2*_1mb];
    }

    //大对象直接进入老年代，大对象是指需要大量连续内存空间的java对象，比如长字符串和数组
    public static void testPretenureSizeThreshold(){
        byte[] allocation;
        allocation = new byte[4*_1mb]; //直接分配到老年代里
    }

    //长期存活对象进入老年代
    public static void testTenuringThreshold(){
        byte[] allocation1,allocation2,allocation3;
        allocation1 = new byte[_1mb/4];
        //什么时候进入老年代取决于XX：MaxTenuringThreshold设置
        allocation2 = new byte[4*_1mb];
        allocation3 = new byte[4*_1mb];
        allocation3 = null;
        allocation3 = new byte[4*_1mb];
    }

    //动态对象年龄判断
    public static void testTenuringThreshold2(){
        byte[] allocation1,allocation2,allocation3,allocation4;
        allocation1 = new byte[_1mb / 4];
        //allocation1+allocation2>survivor空间的一半
        allocation2 = new byte[_1mb / 4];
        allocation3 = new byte[4 * _1mb];
        allocation4 = new byte[4 * _1mb];
        allocation4 = null;
        allocation4 = new byte[4 * _1mb];
    }

    //空间分配担保
    //进行minor gc之前，虚拟机会先检查老年代最大可用连续空间是否大于新生代所有对象总空间
    public static void testHandlePromotion(){
        byte[] allocation1,allocation2,allocation3,allocation4,allocation5,allocation6,allocation7;
        allocation1 = new byte[2 * _1mb];
        allocation2 = new byte[2 * _1mb];
        allocation3 = new byte[2 * _1mb];
        allocation1 = null;
        allocation4 = new byte[2 * _1mb];
        allocation5 = new byte[2 * _1mb];
        allocation6 = new byte[2 * _1mb];
        allocation4 = null;
        allocation5 = null;
        allocation6 = null;
        allocation7 = new byte[2 * _1mb];
    }

}
