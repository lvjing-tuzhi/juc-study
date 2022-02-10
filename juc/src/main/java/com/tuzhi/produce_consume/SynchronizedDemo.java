package com.tuzhi.produce_consume;

/**
 * @program: JUC-study
 * @description:Synchronized实现生产者消费者模式
 * @author: 兔子
 * @create: 2022-02-08 18:32
 **/

public class SynchronizedDemo {
    public static void main(String[] args) {
        Factory factory = new Factory();
        new Thread(() -> {
            for (int i = 0; i < 10; i++) {
                try {
                    factory.product();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }, "A工厂").start();
        new Thread(() -> {
            for (int i = 0; i < 10; i++) {
                try {
                    factory.consume();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }, "A消费者").start();
    }
}
class Factory {
    private int number = 0;
    public synchronized void product() throws InterruptedException {
        while (number != 0) {
            this.wait();
        }
        System.out.println(Thread.currentThread().getName() + "生产了：" + ++number + "个票");
        this.notify();
    }
    public synchronized void consume() throws InterruptedException {
        while (number == 0) {
            this.wait();
        }
        System.out.println(Thread.currentThread().getName() + "消费了： " + --number + "个票");
        this.notify();
    }
}
