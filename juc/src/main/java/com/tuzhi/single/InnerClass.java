package com.tuzhi.single;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;

/**
 * @program: JUC-study
 * @description: 内部类方式
 * @author: 兔子
 * @create: 2022-02-15 22:15
 **/

public class InnerClass {
    private InnerClass() {};

//    静态内部类不会在外部类初始化的时候直接加载，只有当调用的时候才会，保证了线程安全，并且final保证了线程只有一份
    private static class Inner {
        private static final InnerClass instance = new InnerClass();
    }
    public static InnerClass getInstance() {
        return Inner.instance;
    }
}
class InnerClassTest {
    public static void main(String[] args) throws NoSuchMethodException, InvocationTargetException, InstantiationException, IllegalAccessException {
        for (int i = 0; i < 2; i++) {
            InnerClass instance = InnerClass.getInstance();
            System.out.println(instance);
        }
//        也会被反射破坏
        Constructor<InnerClass> declaredConstructor = InnerClass.class.getDeclaredConstructor();
        declaredConstructor.setAccessible(true);
        InnerClass innerClass = declaredConstructor.newInstance();
        System.out.println(innerClass);
    }
}
