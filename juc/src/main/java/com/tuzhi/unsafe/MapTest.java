package com.tuzhi.unsafe;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.CopyOnWriteArraySet;

/**
 * @program: JUC-study
 * @description:
 * @author: 兔子
 * @create: 2022-02-10 13:50
 **/

public class MapTest {
    public static void main(String[] args) {
//        解决方法一使用工具类：这个add方法里面是加了Synchronized块
//        Map map = Collections.synchronizedMap(new HashMap());
//        解决方法二：这个add方法是用Lock锁的默认锁非公平锁
        Map map = new ConcurrentHashMap();
        for (int i = 0; i < 30; i++) {
            int finalI = i;
            new Thread(() -> {
                map.put(finalI,finalI);
                System.out.println(map);
            },String.valueOf(i)).start();
        }
    }
}
