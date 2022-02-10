package com.tuzhi.lock8;

import java.util.concurrent.TimeUnit;

/**
 * @program: JUC-study
 * @description:答案是：打电话 发短信 有两个锁，一个锁是静态的锁class，另外一个锁是对象本身的方法，又因为第一个阻塞，所以先执行打电话
 * @author: 兔子
 * @create: 2022-02-10 11:08
 **/

public class Test7 {
    public static void main(String[] args) throws Exception {
        Phone7 phone = new Phone7();
        new Thread(() -> {phone.sendMessage();}, "A").start();

        TimeUnit.SECONDS.sleep(1);

        new Thread(() -> {phone.call();}, "B").start();
    }
}

class Phone7 {
    public static synchronized void sendMessage(){
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
