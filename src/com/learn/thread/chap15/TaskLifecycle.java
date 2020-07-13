package com.learn.thread.chap15;

public interface TaskLifecycle<T> {
    //任务启动时候会触发onStart
    void onStart(Thread thread);
    //运行
    void onRunning(Thread thread);
    //任务结束
    void onFinish(Thread thread,T result);
    //出错
    void onError(Thread thread,Exception e);

    //生命周期接口的空实现
    class EmptyLifecycle<T> implements TaskLifecycle<T>{

        @Override
        public void onStart(Thread thread) {

        }

        @Override
        public void onRunning(Thread thread) {

        }

        @Override
        public void onFinish(Thread thread, T result) {

        }

        @Override
        public void onError(Thread thread, Exception e) {

        }
    }

}
