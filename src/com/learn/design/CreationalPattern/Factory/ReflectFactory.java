package com.learn.design.CreationalPattern.Factory;

import java.util.HashMap;
import java.util.Map;

/**
 * 使用反射机制进行类注册的简单工厂模式
 * 在某些情况下反射机制并不适用，比如需要运行时的权限在某些特定环境下无法实现
 * 反射机制也会降低运行效率
 *
 * */
public class ReflectFactory {
    private Map<String,Class> registeredProducts = new HashMap<>();

    public void registerVehicle(String vehicleId,Class vehicleClass){
        registeredProducts.put(vehicleId,vehicleClass);
    }

    public Vehicle createVehicle(String type) throws InstantiationException,IllegalAccessException{
        Class productClass = registeredProducts.get(type);
        return (Vehicle) productClass.newInstance();
    }
}
