package com.tuzhi.demo01;

import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * @program: JUC-study
 * @description:卖票
 * @author: 兔子
 * @create: 2022-02-08 13:39
 **/

public class SaleTicket_Lock_Demo02 {
    public static void main(String[] args) {
        Tick2 tick = new Tick2();
        new Thread(() -> {for (int i = 0; i < 50; i++) tick.saleTick();},"A").start();
        new Thread(() -> {for (int i = 0; i < 50; i++) tick.saleTick();},"B").start();
        new Thread(() -> {for (int i = 0; i < 50; i++) tick.saleTick();},"C").start();
    }
}

class Tick2 {
    private int ticks = 50;
    Lock lock = new ReentrantLock();
    public void saleTick() {
        lock.lock();
        try {
            if (ticks > 0) {
                System.out.println(Thread.currentThread().getName() + "卖出了一张票" + " 剩余：" + --ticks+" 张票");
            }
        }catch (Exception e) {
            e.printStackTrace();
        }finally {
            lock.unlock();
        }

    }
}
