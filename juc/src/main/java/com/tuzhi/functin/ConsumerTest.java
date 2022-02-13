package com.tuzhi.functin;

import java.util.function.Consumer;
import java.util.function.Supplier;

/**
 * @program: JUC-study
 * @description: 消费型接口只有入参没有放回值
 * @author: 兔子
 * @create: 2022-02-13 15:46
 **/

public class ConsumerTest {
    public static void main(String[] args) {
        Consumer<Integer> consumer = new Consumer<Integer>() {
            @Override
            public void accept(Integer o) {
                System.out.println(o);
            }
        };
        Consumer<Integer> consumer1 = o -> {
            System.out.println(o);
        };
    }
}
