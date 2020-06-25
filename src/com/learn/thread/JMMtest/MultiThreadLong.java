package com.learn.thread.JMMtest;

/**原子性是指一个操作是不可中断的
 * 即使是多个线程一起执行的时候，一个操作一旦开始就不会被其他线程干扰
 * 两个线程对int型赋值是没有干扰的，long型数据读写不是原子性的，两个线程同时操作是有干扰的
 *
 */
public class MultiThreadLong {
    public static long t = 0;
    public static class ChangeT implements Runnable{
        private long to;
        public ChangeT(long to){
            this.to = to;
        }

        @Override
        public void run() {
            while (true){
                MultiThreadLong.t = to;
                Thread.yield();
            }
        }
    }

    public static class ReadT implements Runnable{

        @Override
        public void run() {
            while (true){
                long tmp = MultiThreadLong.t;
                if(tmp!=111L && tmp != -999L && tmp!=333L && tmp!=-444L) System.out.println(tmp);
                Thread.yield();
            }
        }
    }

    //如果是32位虚拟机会大量输出不存在的数
    public static void main(String[] args) {
        new Thread(new ChangeT(111L)).start();
        new Thread(new ChangeT(-999L)).start();
        new Thread(new ChangeT(333L)).start();
        new Thread(new ChangeT(-444L)).start();
        new Thread(new ReadT()).start();
    }

}
