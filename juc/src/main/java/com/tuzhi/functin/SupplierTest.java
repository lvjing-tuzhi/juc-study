package com.tuzhi.functin;

import java.util.function.Supplier;

/**
 * @program: JUC-study
 * @description: 供给型接口没有入参只有返回值
 * @author: 兔子
 * @create: 2022-02-13 15:49
 **/

public class SupplierTest {
    public static void main(String[] args) {
        Supplier<Integer> supplier = new Supplier<Integer>() {
            @Override
            public Integer get() {
                return 1;
            }
        };
        Supplier<Integer> supplier1 = () -> {return 1;};
    }
}
