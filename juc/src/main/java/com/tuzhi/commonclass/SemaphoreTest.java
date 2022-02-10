package com.tuzhi.commonclass;

import java.util.concurrent.Semaphore;
import java.util.concurrent.TimeUnit;

/**
 * @program: JUC-study
 * @description:停车位
 * @author: 兔子
 * @create: 2022-02-10 20:29
 **/

public class SemaphoreTest {
    public static void main(String[] args) {
//        可以初始化许可证数量，当到达许可证数量的时候，则停止
        Semaphore semaphore = new Semaphore(3);
        for (int i = 0; i < 6; i++) {
            new Thread(() -> {
                try {
                    semaphore.acquire();
                    System.out.println(Thread.currentThread().getName() + "获得许可证");
                    //获取许可证
                    TimeUnit.SECONDS.sleep(2);
                    System.out.println(Thread.currentThread().getName() + "释放许可证");
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }finally {
//                   2秒后释放许可证
                    semaphore.release();
                }
            }, String.valueOf(i)).start();
        }
    }
}
