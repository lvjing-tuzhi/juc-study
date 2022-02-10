package com.tuzhi.commonclass;

import java.util.concurrent.CountDownLatch;

/**
 * @program: JUC-study
 * @description:
 * @author: 兔子
 * @create: 2022-02-10 18:16
 **/

public class CountDownLatchTest {
    public static void main(String[] args) throws InterruptedException {
//        初始化计数6
        CountDownLatch countDownLatch = new CountDownLatch(6);
        for (int i = 0; i < 6; i++) {
            new Thread(() -> {
                System.out.println(Thread.currentThread().getName() + "出去了");
//                计数减一
                countDownLatch.countDown();
            }, String.valueOf(i)).start();
        }
//        阻塞，只有计数为0时才可以执行await后面的代码
        countDownLatch.await();
        System.out.println("都出去了");
    }
}
