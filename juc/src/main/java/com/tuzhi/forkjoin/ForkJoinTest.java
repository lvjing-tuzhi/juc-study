package com.tuzhi.forkjoin;

import java.util.concurrent.ExecutionException;
import java.util.concurrent.ForkJoinPool;
import java.util.concurrent.ForkJoinTask;
import java.util.concurrent.RecursiveTask;
import java.util.stream.LongStream;

/**
 * @program: JUC-study
 * @description:
 * @author: 兔子
 * @create: 2022-02-13 19:25
 **/

public class ForkJoinTest {
    public static void main(String[] args) throws ExecutionException, InterruptedException {
//        test1();
//        结果是： 161596所花的时间是：3
        test2();
//        test3();
    }
//    第一种普通的方法
    public static void test1() {
        System.out.println("开始执行");
        long sum = 0;
        Long l = System.currentTimeMillis();
        for (Long i = 0L; i <= 1_0000_0000_0000L; i++) {
            sum += i;
        }
        Long l1 = System.currentTimeMillis();
        System.out.println("结果是： " + sum + "所花的时间是：" + (l1 - l));
        System.out.println("执行结束");
    }
//    第二种ForkJoin的方式
    public static void test2() throws ExecutionException, InterruptedException {
        Long l = System.currentTimeMillis();
        ForkJoinPool forkJoinPool = new ForkJoinPool();
        ForkJoinUse forkJoinUse = new ForkJoinUse(0L, 1_0000_0000_0000_0000L);
        ForkJoinTask<Long> submit = forkJoinPool.submit(forkJoinUse);
        Long l1 = System.currentTimeMillis();
        System.out.println("结果是： " + submit.get() + "所花的时间是：" + (l1 - l));
    }
//    第三种并行流
    public static void test3() {
        long l = System.currentTimeMillis();
        long reduce = LongStream.rangeClosed(0, 1_0000_0000_0000L).parallel().reduce(0, Long::sum);
        long l1 = System.currentTimeMillis();
        System.out.println("结果是： " + reduce + "所花的时间是：" + (l1 - l));

    }
}
class ForkJoinUse extends RecursiveTask<Long> {
    private long start;
    private long end;

    //临界值，也就是二分的界线，这个数值调整可以影响执行效率
    private long temp = 1000;
    public ForkJoinUse(Long start, Long end) {
        this.start = start;
        this.end = end;
    }
    @Override
    protected Long compute() {
        if ((end - start) < temp) {
            long sum = 0;
            for (long i = start; i <= end; i++) {
                sum += i;
            }
            return sum;
        }else {
//            求中间值，这样每次可以二分递归
            long mid = (start + end) / 2;
//            左边
            ForkJoinUse forkJoinUseLeft = new ForkJoinUse(start, mid);
//            加入ForkJoin进行拆分，压入线程队列中开始并行处理
            forkJoinUseLeft.fork();
//            右边
            ForkJoinUse forkJoinUseRight = new ForkJoinUse(mid + 1, start);
//            加入ForkJoin进行拆分，压入线程队列中开始并行处理
            forkJoinUseRight.fork();
//            算出当前拆分的结果，最后把所有结果汇总相加，就是真正的结果
            return forkJoinUseLeft.join() + forkJoinUseRight.join();
        }
    }
}
