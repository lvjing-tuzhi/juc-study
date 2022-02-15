package com.tuzhi.single;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;

/**
 * @program: JUC-study
 * @description: DLC懒汉式双重检测锁模式
 * @author: 兔子
 * @create: 2022-02-15 22:00
 **/

public class DLCLazyMan {
    private DLCLazyMan() {
    }

//    只提供一个实例的引用，不创建对象
//    使用volatile可以避免指令重排：
//        创建对象的步骤：1.分配空间 2.执行构造方法，3.初始对象 4.把这个对象指向这个空间
//            而没有volatile的话则可能会指令重排，导致变成132
//    volatie对于一个线程的工作内存发生变化，主存会同步，其他线程的工作内存会同步主存
//    也会被反射破坏
    private volatile static DLCLazyMan instance;

    //提供公共的获取方法,因为不是在类加载时就创建对象，因此存在线程安全问题，使用同步代码块提高效率
    //现在不需要对整个方法进行同步，缩小了锁的范围，只有第一次会进入创建对象的方法，提高了效率
    //当第一个线程执行到创建对象的方法时，但还未出方法返回，此时第二个线程进入，发现instance不为空，但第一个线程此时还未出去，
    public static DLCLazyMan getInstance() {
        if (instance == null) {
            synchronized (DLCLazyMan.class) {}
            if (instance == null) {
                instance = new DLCLazyMan();
            }
        }
        return instance;
    }
}
class DLCLazyManTest {
    public static void main(String[] args) throws NoSuchMethodException, InvocationTargetException, InstantiationException, IllegalAccessException {
        for (int i = 0; i < 2; i++) {
            DLCLazyMan instance = DLCLazyMan.getInstance();
            System.out.println(instance);
        }
//        不安全也会被反射破坏
        Constructor<DLCLazyMan> declaredConstructor = DLCLazyMan.class.getDeclaredConstructor();
        declaredConstructor.setAccessible(true);
        DLCLazyMan dlcLazyMan = declaredConstructor.newInstance();
        System.out.println(dlcLazyMan);
    }
}
