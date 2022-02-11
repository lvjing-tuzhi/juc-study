package com.tuzhi.blockingqueue;

import java.util.concurrent.SynchronousQueue;
import java.util.concurrent.TimeUnit;

/**
 * @program: JUC-study
 * @description:同步队列
 * @author: 兔子
 * @create: 2022-02-11 16:05
 **/

public class SynchronousQueueTest {
    public static void main(String[] args) throws InterruptedException {
        SynchronousQueue<Integer> queue = new SynchronousQueue<>();
        new Thread(() -> {
            for (int i = 0; i < 4; i++) {
                System.out.println(Thread.currentThread().getName() + "需要一把牙刷");
                try {
                    System.out.println(Thread.currentThread().getName() + "买了一把id为" + queue.take() + "的牙刷");
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }, "消费者").start();
        TimeUnit.SECONDS.sleep(2);
        new Thread(() -> {
            for (int i = 0; i < 4; i++) {
                try {
                    queue.put(i);
                    System.out.println(Thread.currentThread().getName() + "生产了id为"+i+"把牙刷");
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }, "生产者").start();
    }
}
