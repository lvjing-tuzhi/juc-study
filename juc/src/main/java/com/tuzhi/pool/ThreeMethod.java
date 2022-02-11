package com.tuzhi.pool;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * @program: JUC-study
 * @description:常用的三大方法：FixedThreadPool、SingleThreadPool、CachedThreadPool
 * @author: 兔子
 * @create: 2022-02-11 19:32
 **/

public class ThreeMethod {
    public static void main(String[] args) {
//        单个线程的线程池
//        ExecutorService executorService = Executors.newSingleThreadExecutor();
//        创建一个自定义个数线程的线程池
//        ExecutorService executorService = Executors.newFixedThreadPool(5);
//        按内存大小弹性的分配线程数量
        ExecutorService executorService = Executors.newCachedThreadPool();
        try {
            for (int i = 0; i < 999999999; i++) {
                executorService.execute(() -> {
                    System.out.println(Thread.currentThread().getName() + "执行了");
                });
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            executorService.shutdown();
        }
    }
}
