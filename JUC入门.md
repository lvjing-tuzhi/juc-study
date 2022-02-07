# 1、什么是JUC

1. JUC是java.util.concurrent的简写，是java并发编程工具包
2. java实现多线程有三种方式：Thread、Runnable、Callable、其中Callable位于concurrent包下。

# 2、线程和进程

1. 一个进程包括多个线程。
2. 一个线程只属于一个进程。
3. java默认线程有两个是main方法和GC

# 3、线程

## 1、线程状态

new（新生）、runnable（运行）、blocked（阻塞）、waiting（等待）、timed_waiting（超时等待）、terminated（终止）

## 2、wait和sleep区别

1. wait来自Object类，sleep来自Thread类。
2. wait会释放同步锁，sleep不会释放同步锁。
3. wait必须在同步代码块中使用，sleep可以在任何地方使用。
4. sleep可以使用指定时间来使他自动醒来，如果时间不到只能调用interreput()来强行打断。wait()可以使用notify()直接唤起。

# 4、并行和并发

1. 并发：多线程操作同一个资源，多个事件在同一时刻间隔发生
2. 并行：多个事件在同一时刻发生，并排走，多台处理器同时处理多个任务。