package com.learn.jvm.chap8;

/**
 * 方法的接收者与方法的参数统称为方法的宗量
 * 单分派是根据一个宗量对目标方法的多种选择
 * 多分派是根据多于一个宗量对目标方法进行选择
 *
 *
 * */
public class Dispatch {
    static class QQ{}
    static class _360{}

    public static class Father{
        public void hardChoice(QQ arg){
            System.out.println("father choose qq");
        }

        public void hardChoice(_360 arg){
            System.out.println("father choose 360");
        }
    }

    public static class Son extends Father{
        public void hardChoice(QQ arg){
            System.out.println("son choose qq");
        }
        public void hardChoice(_360 arg){
            System.out.println("son choose 360");
        }
    }

    public static void main(String[] args) {
        Father father = new Father();
        Father son = new Son();
        father.hardChoice(new _360());
        son.hardChoice(new QQ());
    }

}
