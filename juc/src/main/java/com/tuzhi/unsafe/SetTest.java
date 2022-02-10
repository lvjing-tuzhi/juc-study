package com.tuzhi.unsafe;

import java.util.*;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.concurrent.CopyOnWriteArraySet;

/**
 * @program: JUC-study
 * @description:
 * @author: 兔子
 * @create: 2022-02-10 13:50
 **/

public class SetTest {
    public static void main(String[] args) {
//        解决方法一使用工具类：这个add方法里面是加了Synchronized块
//        Set set = Collections.synchronizedSet(new HashSet());
//        解决方法二：这个add方法是用Lock锁的默认锁非公平锁
        Set set = new CopyOnWriteArraySet();
        for (int i = 0; i < 30; i++) {
            int finalI = i;
            new Thread(() -> {
                set.add(finalI);
                System.out.println(set);
            },String.valueOf(i)).start();
        }
    }
}
