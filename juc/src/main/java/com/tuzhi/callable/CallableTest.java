package com.tuzhi.callable;

import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.FutureTask;

/**
 * @program: JUC-study
 * @description:
 * @author: 兔子
 * @create: 2022-02-10 16:05
 **/

public class CallableTest {
    public static void main(String[] args) throws ExecutionException, InterruptedException {
        FutureTask futureTask = new FutureTask(new MyThread());
        new Thread(futureTask,"A") .start();
        new Thread(futureTask,"B") .start();
        System.out.println(futureTask.get());
    }
}
class MyThread implements Callable<String> {
    @Override
    public String call() throws Exception {
        System.out.println("call()执行了");
        return "我结束了";
    }
}