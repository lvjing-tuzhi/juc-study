package com.tuzhi.single;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;

/**
 * @program: JUC-study
 * @description: 枚举单例模式安全，不会被反射破坏
 * @author: 兔子
 * @create: 2022-02-15 22:22
 **/

public enum EnumSingle {
    INSTANCE;
    public static EnumSingle getInstance() {
        return INSTANCE;
    }
}
class EnumSingleTest {
    public static void main(String[] args) throws NoSuchMethodException, InvocationTargetException, InstantiationException, IllegalAccessException {
        for (int i = 0; i < 2; i++) {
            EnumSingle instance = EnumSingle.getInstance();
            System.out.println(instance);
        }
//        不能进行反射破坏，会报Cannot reflectively create enum objects
        Constructor<EnumSingle> declaredConstructor = EnumSingle.class.getDeclaredConstructor(String.class,int.class);
        declaredConstructor.setAccessible(true);
        EnumSingle enumSingle = declaredConstructor.newInstance();
        System.out.println(enumSingle);
    }
}
