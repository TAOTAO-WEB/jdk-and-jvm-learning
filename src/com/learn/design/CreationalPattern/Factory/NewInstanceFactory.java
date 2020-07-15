package com.learn.design.CreationalPattern.Factory;

import java.util.HashMap;
import java.util.Map;

/**
 * 使用newInstance方法进行类注册的简单工厂模式
 * 将要注册的每种对象实例添加其中，每个产品类都能创建自己的实例
 *
 * 首先在基类中添加一个抽象方法
 * 对于每种产品 基类中声明为抽象的方法都要实现
 *
 *
 * */
public class NewInstanceFactory {
    private Map<String,Vehicle2> registeredProducts = new HashMap<>();

    public void registerVehicle(String vehicleId,Vehicle2 vehicle2){
        registeredProducts.put(vehicleId,vehicle2);
    }

    public Vehicle2 createVehicle(String vehicleId){
        return registeredProducts.get(vehicleId).newInstance();
    }
}


abstract class Vehicle2{
    abstract public Vehicle2 newInstance();
}

class Car2 extends Vehicle2{

    @Override
    public Vehicle2 newInstance() {
        return new Car2();
    }
}