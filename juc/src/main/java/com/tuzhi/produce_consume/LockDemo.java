package com.tuzhi.produce_consume;

import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * @program: JUC-study
 * @description:Lock锁实现生产者消费者模式
 * @author: 兔子
 * @create: 2022-02-09 11:31
 **/

public class LockDemo {
    public static void main(String[] args) {
        FactoryLock factoryLock = new FactoryLock();
        new Thread(() -> {
            for (int i = 0; i < 20; i++) {
                factoryLock.produce();
            }
        },"A工厂").start();
        new Thread(() -> {
            for (int i = 0; i < 20; i++) {
                factoryLock.consume();
            }
        },"B消费者").start();
        new Thread(() -> {
            for (int i = 0; i < 20; i++) {
                factoryLock.consume();
            }
        },"C消费者").start();
        new Thread(() -> {
            for (int i = 0; i < 20; i++) {
                factoryLock.consume();
            }
        },"D消费者").start();
    }
}
class FactoryLock {
    private int number = 0;
    final Lock lock = new ReentrantLock();
    final Condition condition = lock.newCondition();
    public void produce() {
        lock.lock();
        try {
            while (number != 0) {
                condition.await();
            }
            condition.signalAll();
            System.out.println(Thread.currentThread().getName() + "生产了：" + ++number + "个票");
        }catch (Exception e) {
            e.printStackTrace();
        }finally {
            lock.unlock();
        }
    }
    public void consume() {
        lock.lock();
        try {
            while (number == 0) {
                condition.await();
            }
            condition.signalAll();
            System.out.println(Thread.currentThread().getName() + "消费了： " + --number + "个票");
        }catch (Exception e) {
            e.printStackTrace();
        }finally {
            lock.unlock();
        }
    }
}
