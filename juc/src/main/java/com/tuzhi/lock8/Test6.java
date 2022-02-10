package com.tuzhi.lock8;

import java.util.concurrent.TimeUnit;

/**
 * @program: JUC-study
 * @description:答案是：发短信 打电话 虽然有两个对象但是调用的方法全加static了，static是静态，类一加载就有了，两个都加载到了Class模板，所有只有一个锁，锁定是class
 * @author: 兔子
 * @create: 2022-02-10 11:08
 **/

public class Test6 {
    public static void main(String[] args) throws Exception {
        Phone6 phone = new Phone6();
        Phone6 phone1 = new Phone6();
        new Thread(() -> {phone.sendMessage();}, "A").start();

        TimeUnit.SECONDS.sleep(1);

        new Thread(() -> {phone1.call();}, "B").start();
    }
}

class Phone6 {
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
