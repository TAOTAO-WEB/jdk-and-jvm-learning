package com.learn.jvm.chap2;

//java虚拟机栈和本地方法栈OOM测试
public class JavaVMStackSOF {
    private int stackLength = 1;
    public void stackLeak(){
        stackLength++;
        stackLeak();
    }

    public static void main(String[] args) throws Throwable{
        JavaVMStackSOF oom = new JavaVMStackSOF();
        try {
            oom.stackLeak();
        }catch (Throwable e){
            System.out.println("stack length"+ oom.stackLength);
            throw e;
        }
    }


}
