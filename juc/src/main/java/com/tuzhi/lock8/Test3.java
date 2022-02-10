package com.tuzhi.lock8;

import java.util.concurrent.TimeUnit;

/**
 * @program: JUC-study
 * @description:答案是：hello 发短信 打电话 因为hello没有加synchronized锁，而这个时候发短信和打电话都有加锁，为同一把锁，发短信先启动进入锁，而这个时候
 * 发短信延迟执行，相当于阻塞了一段时间，所以打电话还在一直等待中，而hello没有加锁，所以在发短信阻塞期间插队执行了。
 * @author: 兔子
 * @create: 2022-02-10 11:08
 **/

public class Test3 {
    public static void main(String[] args) throws Exception {
        Phone3 phone = new Phone3();
        new Thread(() -> {phone.sendMessage();}, "A").start();
//        TimeUnit.SECONDS.sleep(1);
        new Thread(() -> {phone.call();}, "B").start();
        new Thread(() -> {phone.hello();}, "C").start();
    }
}

class Phone3 {
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
    public void hello() {
        System.out.println("hello");
    }
}
