package com.tuzhi.produce_consume;

import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * @program: JUC-study
 * @description:使用多个监视器Condition来控制多条线程的执行顺序
 * @author: 兔子
 * @create: 2022-02-09 12:06
 **/

public class Controller_Lock_Order {
    public void main(String[] args) {
        Data data = new Data();
        new Thread(() -> {
            for (int i = 0; i < 10; i++) {
                data.theadA();
            }
        },"A").start();
        new Thread(() -> {
            for (int i = 0; i < 20; i++) {
                data.theadB();
            }
        },"B").start();
        new Thread(() -> {
            for (int i = 0; i < 20; i++) {
                data.theadC();
            }
        },"C").start();
    }
}
class  Data {
    final Lock lock = new ReentrantLock();
    Condition condition1 = lock.newCondition();
    Condition condition2 = lock.newCondition();
    Condition condition3 = lock.newCondition();
    private String id = "A";
    public void theadA() {
        lock.lock();
        try {
            while (!id.equals("A")) {
                condition1.await();
            }
            System.out.println(Thread.currentThread().getName()+"调用了AAAA");
            id = "B";
            condition2.signal();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            lock.unlock();
        }
    }
    public void theadB() {
        lock.lock();
        try {
            while (!id.equals("B")) {
                condition2.await();
            }
            System.out.println(Thread.currentThread().getName()+"调用了BBBB");
            id = "C";
            condition3.signal();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            lock.unlock();
        }
    }
    public void theadC() {
        lock.lock();
        try {
            while (!id.equals("C")) {
                condition3.await();
            }
            System.out.println(Thread.currentThread().getName()+"调用了CCCC");
            id = "A";
            condition1.signal();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            lock.unlock();
        }
    }
}
