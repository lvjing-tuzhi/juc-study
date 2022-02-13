package com.tuzhi.pool;

import java.util.concurrent.Executors;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/**
 * @program: JUC-study
 * @description:ThreadPoolExecutor
 * @author: 兔子
 * @create: 2022-02-11 20:12
 **/

public class ThreadPoolExecutorTest {
    public static void main(String[] args) {
        ThreadPoolExecutor threadPoolExecutor = new ThreadPoolExecutor(2, 9999999, 3, TimeUnit.SECONDS, new LinkedBlockingQueue(3), new ThreadPoolExecutor.DiscardOldestPolicy());
        try {
            for (int i = 1; i <= 999999999; i++) {
                threadPoolExecutor.execute(() -> {
                    System.out.println(Thread.currentThread().getName());
                });
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            threadPoolExecutor.shutdown();
        }
    }
}
