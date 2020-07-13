package com.learn.thread.chap15;

public class ObervableThread<T> extends Thread implements Observable {

    private final Task<T> task;

    private final TaskLifecycle<T> lifecycle;

    private Cycle cycle;

    public ObervableThread(Task<T> task){
        this(new TaskLifecycle.EmptyLifecycle<>(),task);
    }

    public ObervableThread(TaskLifecycle<T> lifecycle, Task<T> task){
        super();
        //task不允许为null
        if(task==null) throw new IllegalArgumentException("the task is required");
        this.lifecycle = lifecycle;
        this.task = task;
    }



    @Override
    public final void run() {
        //在执行线程逻辑单元的时候分别触发相应的事件
        this.update(Cycle.STARTED,null,null);
        try {
            this.update(Cycle.RUNNING,null,null);
            T result = this.task.call();
            this.update(Cycle.DONE,result,null);
        }catch (Exception e){
            this.update(Cycle.ERROR,null,e);
        }
    }

    private void update(Cycle cycle,T result ,Exception e){
        this.cycle = cycle;
        if(lifecycle==null) return;
        try{
            switch (cycle){
                case STARTED:
                    this.lifecycle.onStart(currentThread());
                    break;
                case RUNNING:
                    this.lifecycle.onRunning(currentThread());
                    break;
                case DONE:
                    this.lifecycle.onFinish(currentThread(),result);
                    break;
                case ERROR:
                    this.lifecycle.onError(currentThread(),e);
                    break;
            }
        }catch (Exception ex){
            throw ex;
        }
    }



    @Override
    public Cycle getCycle() {
        return this.cycle;
    }
}
