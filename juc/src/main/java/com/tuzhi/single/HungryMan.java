package com.tuzhi.single;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;

/**
 * @program: JUC-study
 * @description: 饿汉式：每次都会创建新对象，会浪费资源
 * @author: 兔子
 * @create: 2022-02-15 19:50
 **/

public class HungryMan {
    private HungryMan() {
        System.out.println("ok");
    }
    private volatile static HungryMan instance = new HungryMan();

//    向外提供一个公开获取实例的方法，由于静态的instance在类加载的时候就存在了，所以不存在线程不安全的问题，但是耗资源，对象加载时间过长。
//    如果在该类里面存在大量开辟空间的语句，如很多数组或集合，但又不马上使用他们，这时这样的单例模式会消耗大量的内存，影响性能
    public static HungryMan getInstance() {
        return instance;
    }
}
class HungryManTest {
    public static void main(String[] args) throws NoSuchMethodException, InvocationTargetException, InstantiationException, IllegalAccessException {
        HungryMan instance = HungryMan.getInstance();
        HungryMan instance1 = HungryMan.getInstance();
        System.out.println(instance);
        System.out.println(instance1);
//        多线程下也是安全的
        for (int i = 0; i < 10; i++) {
            new Thread(() -> {
                HungryMan instance2 = HungryMan.getInstance();
                System.out.println(instance2);
            }).start();
        }
        //        使用反射进行破坏,可以进行破坏，所以饿汉式还是不安全的
        Constructor<HungryMan> declaredConstructor = HungryMan.class.getDeclaredConstructor();
        declaredConstructor.setAccessible(true);
        HungryMan hungryMan = declaredConstructor.newInstance();
        System.out.println(hungryMan);
    }
}
