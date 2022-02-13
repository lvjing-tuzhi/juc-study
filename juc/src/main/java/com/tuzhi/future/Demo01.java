package com.tuzhi.future;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;

/**
 * @program: JUC-study
 * @description:
 * @author: 兔子
 * @create: 2022-02-13 21:58
 **/

public class Demo01 {
    public static void main(String[] args) throws ExecutionException, InterruptedException {
//        CompletableFuture<Void> completableFuture = CompletableFuture.runAsync(() -> {
//            try {
//                TimeUnit.SECONDS.sleep(2);
//            } catch (InterruptedException e) {
//                e.printStackTrace();
//            }
//            System.out.println(Thread.currentThread().getName() + "我被执行了");
//        });
//        System.out.println("aaa");
//        completableFuture.get();
//        System.out.println("bbb");


        CompletableFuture<String> completableFuture = CompletableFuture.supplyAsync(() -> {
            System.out.println(Thread.currentThread().getName() + "我被执行了");
            int i = 1/0;
            return "执行到了";
        });

//        异步调用
        System.out.println(completableFuture.whenComplete((t, u) -> {
            //            调用成功执行这个方法
            System.out.println("t: " + t);
            System.out.println("u" + u);
        }).exceptionally(e -> {
            //            调用失败执行这个方法
            System.out.println("e: " + e.getMessage());
            return "exceptionally";
        }).get());
    }
}
