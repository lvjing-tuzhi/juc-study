package com.tuzhi.single;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;

/**
 * @program: JUC-study
 * @description: 懒汉式
 * @author: 兔子
 * @create: 2022-02-15 20:14
 **/

public class LazyMan {
    private LazyMan() {}
//    在类加载的时候不会马上创建对象，而是只生成一个引用，即延时加载，等要用的时候在加载
    private static LazyMan instance;

//    提供一个公开的获取方法，因为不是在类加载的时候创建对象，而是在使用的时候创建对象所以有可能存在安全问题，所以要加synchronized,但是加了synchronized效率就
//    变低了，可能会有阻塞，并且在多线程下会有指令重排，导致数据不一致，而且会有被反射破坏的危险
    public synchronized static LazyMan getInstance() {
        if (instance == null) {
            instance = new LazyMan();
        }
        return instance;
    }
}
class LazyManTest {
    public static void main(String[] args) throws NoSuchMethodException, InvocationTargetException, InstantiationException, IllegalAccessException {
        for (int i = 0; i < 2; i++) {
            LazyMan instance = LazyMan.getInstance();
            System.out.println(instance);
        }
        for (int i = 0; i < 10; i++) {
            new Thread(() -> {
                LazyMan instance = LazyMan.getInstance();
                System.out.println(instance);
            }).start();
        }
//        反射可以破坏不安全
        Constructor<LazyMan> declaredConstructor = LazyMan.class.getDeclaredConstructor();
        declaredConstructor.setAccessible(true);
        LazyMan lazyMan = declaredConstructor.newInstance();
        System.out.println(lazyMan);
    }
}
