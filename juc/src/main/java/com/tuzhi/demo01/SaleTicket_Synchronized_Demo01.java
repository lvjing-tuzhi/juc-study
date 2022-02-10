package com.tuzhi.demo01;

import java.util.concurrent.locks.Lock;

/**
 * @program: JUC-study
 * @description:卖票
 * @author: 兔子
 * @create: 2022-02-08 13:39
 **/

public class SaleTicket_Synchronized_Demo01 {
    public static void main(String[] args) {
        Tick1 tick = new Tick1();
        new Thread(() -> {
            for (int i = 0; i < 50; i++) {
                tick.saleTick();
            }
        },"A").start();
        new Thread(() -> {
            for (int i = 0; i < 50; i++) {
                tick.saleTick();
            }
        },"B").start();
        new Thread(() -> {
            for (int i = 0; i < 50; i++) {
                tick.saleTick();
            }
        },"C").start();
    }
}
class Tick1 {
    private int ticks = 50;
    public synchronized void saleTick() {
        if (ticks > 0) {
            System.out.println(Thread.currentThread().getName() + "卖出了一张票" + " 剩余：" + --ticks+" 张票");
        }
    }
}
