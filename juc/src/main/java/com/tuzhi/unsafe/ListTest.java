package com.tuzhi.unsafe;

import java.util.*;
import java.util.concurrent.CopyOnWriteArrayList;

/**
 * @program: JUC-study
 * @description:
 * @author: 兔子
 * @create: 2022-02-10 13:50
 **/

public class ListTest {
    public static void main(String[] args) {
//        解决方法一:这个方法是add类加了关键字Synchronized
//        List list = new Vector();
//        解决方法二使用工具类：这个add方法里面是加了Synchronized块
//        List<Object> list = Collections.synchronizedList(new ArrayList<>());
//        解决方法三：这个add方法是用Lock锁的默认锁非公平锁
        List list = new CopyOnWriteArrayList();
        for (int i = 0; i < 10; i++) {
            int finalI = i;
            new Thread(() -> {
                list.add(finalI);
                System.out.println(list);
            },String.valueOf(i)).start();
        }
    }
}
