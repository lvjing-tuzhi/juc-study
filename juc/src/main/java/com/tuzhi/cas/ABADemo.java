package com.tuzhi.cas;

import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicReference;
import java.util.concurrent.atomic.AtomicStampedReference;

/**
 * @program: JUC-study
 * @description:
 * @author: 兔子
 * @create: 2022-02-16 13:41
 **/

public class ABADemo {
    public static void main(String[] args) {
//        初始化传入两个参数，一个初始化值，一个初始化版本
        AtomicStampedReference<Integer> atomicStampedReference = new AtomicStampedReference<>(1, 1);
        new Thread(() -> {
            int stamp = atomicStampedReference.getStamp();
            System.out.println("cas1" + stamp);
            try {
                TimeUnit.SECONDS.sleep(1);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
//            进行cas操作
            System.out.println(atomicStampedReference.compareAndSet(1, 2, atomicStampedReference.getStamp(), atomicStampedReference.getStamp() + 1));
            System.out.println("cas1=>" + atomicStampedReference.getStamp());

//            进行ABA操作
            System.out.println(atomicStampedReference.compareAndSet(2, 1, atomicStampedReference.getStamp(), atomicStampedReference.getStamp() + 1));
            System.out.println("cas2=>" + atomicStampedReference.getStamp());
        }).start();

        new Thread(() -> {
            System.out.println(atomicStampedReference.compareAndSet(1, 6, atomicStampedReference.getStamp(), atomicStampedReference.getStamp() + 1));
            System.out.println("cas3=>" + atomicStampedReference.getStamp());
        }).start();
    }
}
