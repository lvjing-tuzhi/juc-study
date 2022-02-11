package com.tuzhi.readwritelock;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.locks.ReadWriteLock;
import java.util.concurrent.locks.ReentrantReadWriteLock;

/**
 * @program: JUC-study
 * @description:
 * @author: 兔子
 * @create: 2022-02-10 21:08
 **/

public class ReadWriteLockTest {
    public static void main(String[] args) {
        Data data = new Data();
        for (int i = 0; i < 6; i++) {
            int finalI = i;
            new Thread(() -> {
                data.write(finalI, finalI);
            }, String.valueOf(i)).start();
        }
        for (int i = 0; i < 6; i++) {
            int finalI = i;
            new Thread(() -> {
                data.read(finalI);
            }, String.valueOf(i)).start();
        }

    }
}
class Data {
    Map hash = new HashMap<Integer,Integer>();
    ReadWriteLock readWriteLock = new ReentrantReadWriteLock();

    public void write(Integer k, Integer v) {
        readWriteLock.writeLock().lock();
        try {
            System.out.println(Thread.currentThread().getName() + "开始写");
            hash.put(k,v);
            System.out.println(Thread.currentThread().getName() + "写结束");
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            readWriteLock.writeLock().unlock();
        }
    }
    public void read(Integer k) {
        readWriteLock.readLock().lock();
        try {
            System.out.println(Thread.currentThread().getName() + "开始读");
            Object o = hash.get(k);
            System.out.println(Thread.currentThread().getName() + "读到的是： " + o);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            readWriteLock.readLock().unlock();
        }
    }
}
