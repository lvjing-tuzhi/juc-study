package com.tuzhi.commonclass;

import java.util.concurrent.BrokenBarrierException;
import java.util.concurrent.CyclicBarrier;

/**
 * @program: JUC-study
 * @description:
 * @author: 兔子
 * @create: 2022-02-10 20:01
 **/

public class CyclicBarrierTest {
    public static void main(String[] args){
//        可以传入一个数值和一个线程
        CyclicBarrier cyclicBarrier = new CyclicBarrier(4,new Thread(() -> {
            System.out.println("符合条件，我被执行了");
        }));
        for (int i = 0; i < 10; i++) {
            new Thread(() -> {
                System.out.println(Thread.currentThread().getName() + "执行了");
                try {
//                    当等待的线程到达规定数量的时候执行某一个线程。
                    cyclicBarrier.await();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                } catch (BrokenBarrierException e) {
                    e.printStackTrace();
                }
            },String.valueOf(i)).start();
        }
    }
}
