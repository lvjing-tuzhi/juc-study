package com.tuzhi.loks;

/**
 * @program: JUC-study
 * @description: 可重复锁
 * @author: 兔子
 * @create: 2022-02-16 14:45
 **/

public class RepeatAbleLock {
    public static void main(String[] args) {
        Phone phone = new Phone();
        new Thread(() -> {
            phone.sendMassge();
        },"A").start();

        new Thread(() -> {
            phone.sendMassge();
        },"B").start();
    }
}
class Phone {
    public synchronized void sendMassge() {
        System.out.println(Thread.currentThread().getName() + "发短信");
        call();
    }
    public void call() {
        System.out.println(Thread.currentThread().getName() + "打电话");
    }
}
