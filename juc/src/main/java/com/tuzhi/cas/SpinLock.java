package com.tuzhi.cas;

import java.util.concurrent.atomic.AtomicReference;

/**
 * @program: JUC-study
 * @description: 自定义自旋锁
 * @author: 兔子
 * @create: 2022-02-16 15:09
 **/

public class SpinLock {
    AtomicReference<Thread> atomicReference = new AtomicReference<>();
    public void lock() {
        Thread thread = Thread.currentThread();
        System.out.println(thread.getName() + "加锁");
        while (!atomicReference.compareAndSet(null,thread)) {}
    }
    public void unlock() {
        Thread thread = Thread.currentThread();
        System.out.println(thread.getName() + "解锁");
        atomicReference.compareAndSet(thread,null);
    }
}
