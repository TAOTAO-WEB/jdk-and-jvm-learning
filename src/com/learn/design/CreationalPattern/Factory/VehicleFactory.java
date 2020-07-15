package com.learn.design.CreationalPattern.Factory;

/**
 * 工厂模式
 * 用于实现逻辑的封装并通过公共的接口提供对象的实例化服务，在添加新的类的时候只需要进行少量的修改
 *
 *
 * */
public class VehicleFactory {
    //静态工厂模式
    public enum VehicleType{
        Bike,Car,Truck;
    }
    public static Vehicle create(VehicleType type){
        if(type.equals(VehicleType.Bike)) return new Bike();
        if(type.equals(VehicleType.Car)) return new Car();
        if(type.equals(VehicleType.Truck)) return new Truck();
        else return null;
    }
}

abstract class Vehicle{

}
class Bike extends Vehicle{}
class Car extends Vehicle{}
class Truck extends Vehicle{}
