package com.tuzhi.myvolatile;

import java.util.concurrent.atomic.AtomicInteger;

/**
 * @program: JUC-study
 * @description:
 * @author: 兔子
 * @create: 2022-02-15 15:43
 **/

public class Demo01 {
//    volatile不能保证原子性，要用原子类
//    private volatile static int num = 0;
    private volatile static AtomicInteger num = new AtomicInteger();
    public static void add() {num.getAndIncrement();}

    public static void main(String[] args) {
        for (int i = 1; i <= 20; i++) {
            new Thread(() -> {
                for (int i1 = 1; i1 <= 1000; i1++) {
                    add();
                }
            }).start();
        }
        while (Thread.activeCount() > 2) {
            Thread.yield();
        }
        System.out.println(Thread.currentThread().getName() + num);
    }
}
