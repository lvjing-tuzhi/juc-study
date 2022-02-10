package com.tuzhi.lock8;

/**
 * @program: JUC-study
 * @description:打电话，一个对象一个锁，这个时候两个方法都是同一个锁，而Synchronized是公平锁，按顺序执行
 * @author: 兔子
 * @create: 2022-02-10 11:08
 **/

public class Test1 {
    public static void main(String[] args) {
        Phone phone = new Phone();
        new Thread(() -> {phone.sendMessage();}, "A").start();
        new Thread(() -> {phone.call();}, "B").start();
    }
}
class Phone {
    public synchronized void sendMessage() {
        System.out.println("发短信");
    }
    public synchronized void call() {
        System.out.println("打电话");
    }
}
