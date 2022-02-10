package com.tuzhi.lock8;

import java.util.concurrent.TimeUnit;

/**
 * @program: JUC-study
 * @description:答案是：打电话 发短信 两个对象两把锁，A对象先进去phone锁但是延迟4秒，然后延迟一秒B进入phone2锁执行，所以打电话先执行完后3秒执行发短信
 * @author: 兔子
 * @create: 2022-02-10 11:08
 **/

public class Test4 {
    public static void main(String[] args) throws Exception {
        Phone4 phone = new Phone4();
        Phone4 phone1 = new Phone4();
        new Thread(() -> {phone.sendMessage();}, "A").start();
        TimeUnit.SECONDS.sleep(1);
        new Thread(() -> {phone1.call();}, "B").start();
    }
}

class Phone4 {
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
