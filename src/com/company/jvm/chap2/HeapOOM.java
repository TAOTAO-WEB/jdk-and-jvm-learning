package com.company.jvm.chap2;

import java.util.ArrayList;
import java.util.List;

//java堆溢出 不断创建实例，导致异常
public class HeapOOM {
    static class OOMObject{}

    public static void main(String[] args) {
        List<OOMObject> list = new ArrayList<>();
        while (true) list.add(new OOMObject());
    }
}
