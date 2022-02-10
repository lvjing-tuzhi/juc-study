package com.tuzhi.lock8;

import java.util.concurrent.TimeUnit;

/**
 * @program: JUC-study
 * @description:答案是：发短信 打电话，一个对象一个锁，这个时候两个方法都是同一个锁，而Synchronized是公平锁，按顺序执行
 * @author: 兔子
 * @create: 2022-02-10 11:08
 **/

public class Test2 {
    public static void main(String[] args) throws Exception {
        Phone1 phone = new Phone1();
        new Thread(() -> {phone.sendMessage();}, "A").start();
//        TimeUnit.SECONDS.sleep(1);
        new Thread(() -> {phone.call();}, "B").start();
    }
}

class Phone1 {
    public synchronized void sendMessage(){
        try {
            TimeUnit.SECONDS.sleep(4);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.out.println("发短信");
    }
    public synchronized void call() {
        System.out.println("打电话");
    }
}
