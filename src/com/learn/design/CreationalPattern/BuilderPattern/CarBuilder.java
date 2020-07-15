package com.learn.design.CreationalPattern.BuilderPattern;

/**
 * 当需要实例化一个复杂的类，已得到不同结构和不同内部状态的对象时，可以使用不同的类对他们的实例化操作进行封装
 * 这些类就被称之为建造者
 *
 *
 * 建造者模式包含Builder（抽象建造者类）用于声明构建产品类的组成部分的抽象类或者接口
 * ConcreteBuilder（具体建造者类）用于具体实现抽象建造者类中声明的方法，返回构建好的产品类
 * Director（导演类）用于指导如何构建对象的类
 * Product（产品类）具用不同表现形式的复杂对象
 *
 * */
abstract class CarBuilder {
    //基类
    abstract void buildCar();
    abstract void addEngine(String type);
    abstract void addWheel(String type);

}

//具体建造类 实现CarBuilder
class ElectricCarBuilder extends CarBuilder{

    @Override
    void buildCar() {

    }

    @Override
    void addEngine(String type) {

    }

    @Override
    void addWheel(String type) {

    }
}
//具体建造类
class GasolineCarBuilder extends CarBuilder{

    @Override
    void buildCar() {

    }

    @Override
    void addEngine(String type) {

    }

    @Override
    void addWheel(String type) {

    }
}

//导演类
class CarBuilderDirector{
//    public Car buildElectricCar(CarBuilder builder){
//
//    }
//
//    public Car buildHybridCar(CarBuilder builder){
//
//    }
}

//产品类
class Car{

}

/**
 * 方法链是指返回当前对象（this）的一种技术，可以按照链的形式调用方法
 * public Builder setColor{
 *     //set color
 *     return this;
 * }
 *
 * builder.setColor('blue').setEngine('1500cc').build();
 *
 * */
