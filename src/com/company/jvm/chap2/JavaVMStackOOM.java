package com.company.jvm.chap2;

//创建线程导致内存溢出异常 非常危险 java的线程是映射到操作系统的内核线程上的
// 电脑操作系统可能会假死
public class JavaVMStackOOM {
    private void dontStop(){
        while (true){}
    }

    public void StackLeakByThread(){
        while (true){
            Thread thread = new Thread(new Runnable() {
                @Override
                public void run() {
                    dontStop();
                }
            });
            thread.start();
        }
    }

    public static void main(String[] args) {
        JavaVMStackOOM oom = new JavaVMStackOOM();
        oom.StackLeakByThread();
    }
}
