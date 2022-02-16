package com.tuzhi.cas;

import java.util.concurrent.atomic.AtomicInteger;

/**
 * @program: JUC-study
 * @description:
 * @author: 兔子
 * @create: 2022-02-16 11:50
 **/

public class CASDemo {
    public static void main(String[] args) {
        AtomicInteger atomicInteger = new AtomicInteger(2022);
        atomicInteger.compareAndSet(2022,2023);
        System.out.println(atomicInteger.get());
        atomicInteger.compareAndSet(2022,2024);
        System.out.println(atomicInteger.get());

    }
}
