package com.tuzhi.lock8;

import java.util.concurrent.TimeUnit;

/**
 * @program: JUC-study
 * @description:答案是：发短信 打电话 调用的方法全加static了，static是静态，类一加载就有了，两个都加载到了Class模板，所有只有一个锁，锁定是class
 * @author: 兔子
 * @create: 2022-02-10 11:08
 **/

public class Test5 {
    public static void main(String[] args) throws Exception {
        Phone5 phone = new Phone5();
        new Thread(() -> {phone.sendMessage();}, "A").start();

        TimeUnit.SECONDS.sleep(1);

        new Thread(() -> {phone.call();}, "B").start();
    }
}

class Phone5 {
    public static synchronized void sendMessage(){
        try {
            TimeUnit.SECONDS.sleep(4);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.out.println("发短信");
    }
    public static synchronized void call() {
        System.out.println("打电话");
    }
//    public void hello() {
//        System.out.println("hello");
//    }
}
