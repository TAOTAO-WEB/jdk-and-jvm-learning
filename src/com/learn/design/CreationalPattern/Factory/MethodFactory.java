package com.learn.design.CreationalPattern.Factory;

/**
 * 工厂方法模式
 * 工厂类被抽象化，用于实例化特定产品类的代码被转移到实现抽象方法的子类中
 *
 * */
public abstract class MethodFactory {
    protected abstract Vehicle createVehicle(String item);

    public Vehicle orderVehicle(String size,String color){
        Vehicle vehicle = createVehicle(size);
        return vehicle;
    }
}

class CarFactory extends MethodFactory{

    @Override
    protected Vehicle createVehicle(String item) {
        if(item.equals("small")) {
            System.out.println("small car");
        }
        return null;
    }
}


