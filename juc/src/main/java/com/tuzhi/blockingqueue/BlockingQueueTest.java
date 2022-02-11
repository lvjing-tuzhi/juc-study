package com.tuzhi.blockingqueue;

import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.TimeUnit;

/**
 * @program: JUC-study
 * @description:四组APi
 * @author: 兔子
 * @create: 2022-02-11 15:46
 **/

public class BlockingQueueTest {
    public static void main(String[] args) {
//        test1();
//        test2();
//        test3();
        test4();
    }

    /**
     * 有返回值，但是会抛出异常
     * 添加的时候如果容量已满会报错：Queue full
     * 移除的时候如果队列为空会报错：NoSuchElementException
     */
    public static void test1() {
        ArrayBlockingQueue<Integer> blockingQueue = new ArrayBlockingQueue<>(3);
        System.out.println(blockingQueue.add(1));
        System.out.println(blockingQueue.add(1));
        System.out.println(blockingQueue.add(1));
//        System.out.println(blockingQueue.add(1));
        System.out.println(blockingQueue.remove());
        System.out.println(blockingQueue.remove());
        System.out.println(blockingQueue.remove());
        System.out.println(blockingQueue.remove());
    }

    /**
     * 有返回值，但是会不会抛出异常
     * 添加的时候如果容量则会返回：false
     * 移除的时候如果队列为空会报错：null
     */
    public static void test2() {
        ArrayBlockingQueue<Integer> blockingQueue = new ArrayBlockingQueue<Integer>(3);
        System.out.println(blockingQueue.offer(1));
        System.out.println(blockingQueue.offer(1));
        System.out.println(blockingQueue.offer(1));
//        System.out.println(blockingQueue.offer(1));
        System.out.println(blockingQueue.poll());
        System.out.println(blockingQueue.poll());
        System.out.println(blockingQueue.poll());
        System.out.println(blockingQueue.poll());
    }

    /**
     * 阻塞
     * 添加到时候如果容量满了则会一直阻塞
     * 移除的时候如果队列为空则会一直阻塞
     */
    public static void test3() {
        ArrayBlockingQueue<Integer> blockingQueue = new ArrayBlockingQueue<Integer>(3);
        try {
            blockingQueue.put(1);
            blockingQueue.put(1);
            blockingQueue.put(1);
//            blockingQueue.put(1);
            System.out.println("put执行结束");
            System.out.println(blockingQueue.take());
            System.out.println(blockingQueue.take());
            System.out.println(blockingQueue.take());
            System.out.println(blockingQueue.take());
            System.out.println("take执行结束");
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    /**
     * 超时结束
     * 添加到时候如果容量满了则会在一定时间能等待，等待结束后容量还是满的则返回false并结束
     * 移除的时候如果队列为空则会在一定时间能等待，等待结束后队列还是空的则返回null并结束
     */
    public static void test4() {
        ArrayBlockingQueue<Integer> blockingQueue = new ArrayBlockingQueue<>(3);
        try {
            System.out.println(blockingQueue.offer(1, 2, TimeUnit.SECONDS));
            System.out.println(blockingQueue.offer(1, 2, TimeUnit.SECONDS));
            System.out.println(blockingQueue.offer(1, 2, TimeUnit.SECONDS));
//            System.out.println(blockingQueue.offer(1, 2, TimeUnit.SECONDS));
            System.out.println(blockingQueue.poll(2, TimeUnit.SECONDS));
            System.out.println(blockingQueue.poll(2, TimeUnit.SECONDS));
            System.out.println(blockingQueue.poll(2, TimeUnit.SECONDS));
            System.out.println(blockingQueue.poll(2, TimeUnit.SECONDS));
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}

