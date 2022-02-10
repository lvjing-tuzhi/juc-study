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

# 5、Lock

> 1. java.util.concureent.locks.Lock
> 2. 有三个实现类：ReentrantLock(重复锁)、ReentrantReadWriteLock.ReadLock(读锁)、ReentrantReadWriteLock.WriteLock(写锁)

## 1、ReentrantLock

1. 有两个锁，公平锁和非公平锁，公平锁必须排队，非公平锁可以插队（默认），有参构造如果为true则为公平锁。

2. 使用：

   - ```java
     class X { private final ReentrantLock lock = new ReentrantLock(); // ... public void m() { lock.lock(); // block until condition holds try { // ... method body } finally { lock.unlock() } } } 
     ```

   * ```java
     class Tick2 {
         private int ticks = 50;
         Lock lock = new ReentrantLock();
         public void saleTick() {
             lock.lock();
             try {
                 if (ticks > 0) {
                     System.out.println(Thread.currentThread().getName() + "卖出了一张票" + " 剩余：" + --ticks+" 张票");
                 }
             }catch (Exception e) {
                 e.printStackTrace();
             }finally {
                 lock.unlock();
             }
     
         }
     }
     ```

## 2、Lock锁和Synchronized区别

1. Synchronized是java内置的关键字，Lock是java的类。
2. Lock比Synchronized更灵活。
3. Synchronized无法判断获取锁的状态，Lock可以判断是否获取到了锁。
4. Synchronized会自动释放锁，Lock则必须自己手动释放锁，如果不释放锁，则会一直执行第一个线程，死锁。
5. Synchronized如果线程获得锁并且阻塞了，则下一个线程会一直等待，Lock就不一定会等待下去，可以使用`tryLock()` 方法。
6. Synchronized可重入锁，不可用中断属于非公平锁。Lock，可重入锁，可以判断锁，公平锁和非公平锁可以自己设置。
7. Synchronized适合锁少量的代码同步问题，Lock适合锁大量的同步代码。
8. Lock实现提供比使用synchronized方法和语句可以获得更广泛的锁定操作。他们允许更灵活的结构化，可能具有完全不用的属性，并且可以支持多个相关联的对象Condition。
9. Lock中有些锁可以允许并发访问共享资源，如ReadWriteLock的读锁。
10. Lock锁可以更灵活的方式处理锁，例如，用于遍历并发访问的数据结构的一些算法，并允许获得多个锁使得能够使用这样的技术

# 6、生产者和消费者

## 1、Synchronized版

```java
public class SynchronizedDemo {
    public static void main(String[] args) {
        Factory factory = new Factory();
        new Thread(() -> {
            for (int i = 0; i < 10; i++) {
                try {
                    factory.product();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }, "A工厂").start();
        new Thread(() -> {
            for (int i = 0; i < 10; i++) {
                try {
                    factory.consume();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }, "A消费者").start();
    }
}
class Factory {
    private int number = 0;
    public synchronized void product() throws InterruptedException {
        while (number != 0) {
            this.wait();
        }
        System.out.println(Thread.currentThread().getName() + "生产了：" + ++number + "个票");
        this.notify();
    }
    public synchronized void consume() throws InterruptedException {
        while (number == 0) {
            this.wait();
        }
        System.out.println(Thread.currentThread().getName() + "消费了： " + --number + "个票");
        this.notify();
    }
}
```

## 2、虚假唤醒

如果wait()用if判断的话，因为if只会判断一次，判断完就结束了，可能会产生虚假唤醒，所以建议使用while循环进行判断不会出问题。

## 3、Lock版

1. JUC有一个Condition监视器，有三个方法类似Synchronized的wait和notify方法。await和signal(signalAll)

2. 使用

   ```java
   public class LockDemo {
       public static void main(String[] args) {
           FactoryLock factoryLock = new FactoryLock();
           new Thread(() -&gt; {
               for (int i = 0; i &lt; 20; i++) {
                   factoryLock.produce();
               }
           },&quot;A工厂&quot;).start();
           new Thread(() -&gt; {
               for (int i = 0; i &lt; 20; i++) {
                   factoryLock.consume();
               }
           },&quot;B消费者&quot;).start();
           new Thread(() -&gt; {
               for (int i = 0; i &lt; 20; i++) {
                   factoryLock.consume();
               }
           },&quot;C消费者&quot;).start();
           new Thread(() -&gt; {
               for (int i = 0; i &lt; 20; i++) {
                   factoryLock.consume();
               }
           },&quot;D消费者&quot;).start();
       }
   }
   class FactoryLock {
       private int number = 0;
       final Lock lock = new ReentrantLock();
       final Condition condition = lock.newCondition();
       public void produce() {
           lock.lock();
           try {
               while (number != 0) {
                   condition.await();
               }
               condition.signalAll();
               System.out.println(Thread.currentThread().getName() + &quot;生产了：&quot; + ++number + &quot;个票&quot;);
           }catch (Exception e) {
               e.printStackTrace();
           }finally {
               lock.unlock();
           }
       }
       public void consume() {
           lock.lock();
           try {
               while (number == 0) {
                   condition.await();
               }
               condition.signalAll();
               System.out.println(Thread.currentThread().getName() + &quot;消费了： &quot; + --number + &quot;个票&quot;);
           }catch (Exception e) {
               e.printStackTrace();
           }finally {
               lock.unlock();
           }
       }
   }
   ```

   ## 4、按指定顺序执行线程

   > 利用多个监视器Condition来具体await()和signal()

   1. 使用

      ```java
      public class Controller_Lock_Order {
          public static void main(String[] args) {
              Data data = new Data();
              new Thread(() -> {
                  for (int i = 0; i < 10; i++) {
                      data.theadA();
                  }
              },"A").start();
              new Thread(() -> {
                  for (int i = 0; i < 20; i++) {
                      data.theadB();
                  }
              },"B").start();
              new Thread(() -> {
                  for (int i = 0; i < 20; i++) {
                      data.theadC();
                  }
              },"C").start();
          }
      }
      class Data {
          final Lock lock = new ReentrantLock();
          Condition condition1 = lock.newCondition();
          Condition condition2 = lock.newCondition();
          Condition condition3 = lock.newCondition();
          private String id = "A";
          public void theadA() {
              lock.lock();
              try {
                  while (!id.equals("A")) {
                      condition1.await();
                  }
                  System.out.println(Thread.currentThread().getName()+"调用了AAAA");
                  id = "B";
                  condition2.signal();
              } catch (Exception e) {
                  e.printStackTrace();
              } finally {
                  lock.unlock();
              }
          }
          public void theadB() {
              lock.lock();
              try {
                  while (!id.equals("B")) {
                      condition2.await();
                  }
                  System.out.println(Thread.currentThread().getName()+"调用了BBBB");
                  id = "C";
                  condition3.signal();
              } catch (Exception e) {
                  e.printStackTrace();
              } finally {
                  lock.unlock();
              }
          }
          public void theadC() {
              lock.lock();
              try {
                  while (!id.equals("C")) {
                      condition3.await();
                  }
                  System.out.println(Thread.currentThread().getName()+"调用了CCCC");
                  id = "A";
                  condition1.signal();
              } catch (Exception e) {
                  e.printStackTrace();
              } finally {
                  lock.unlock();
              }
          }
      }
      ```

# 7、八锁

## 2、小结

1. 没加static new出来的锁的是当前对象。
2. 加static的锁的是class。



















