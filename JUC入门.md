# 1、什么是JUC

1. JUC是java.util.concurrent的简写，是java并发编程工具包
2. java实现多线程有三种方式：Thread、Runnable、Callable、其中Callable位于concurrent包下。
2. juc下有三个接口：Lock、Condition、ReadWriteLock

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

1. 重要：Lock锁支持非阻塞的获取锁，而Synchronized则会阻塞。

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

## 1、八锁测试

https://gitee.com/tuzhilv/juc-study/tree/master/juc/src/main/java/com/tuzhi/lock8

## 2、小结

1. 没加static new出来的锁的是当前对象。
2. 加static的锁的是class。

# 8、高并发下集合类不安全

> 如果在高并发下使用集合类会报错ConcurrentModificationException

## 1、List

### 1、解决办法

1. List list = new Vector();(这个方法是add类加了关键字Synchronized)
2. List list = Collections.synchronizedList(new ArrayList());(这个方法里面是加了Synchronized块)
3. List list = CopyOnWriteArrayList();()这个add方法是用Lock锁的默认锁非公平锁

### 2、使用

1. 在高并发下应该使用CopyOnWriteArrayList()。
2. CopyOnWirteArrayList底层源码实现是用Lock锁，前两个解决方法的底层源码实现是用Synchronized关键字，Lock锁默认用的是非公平锁，效率比Synchronized高。

## 2、Set

### 1.解决办法

1. Set set = Collections.synchronizedSet(new HashSet())：使用工具类：这个add方法里面是加了Synchronized块。
2. Set set = CopyOnWriteArraySet()：这个add方法是用Lock锁的默认锁非公平锁。

### 2、使用

1. 在高并发下应该使用CopyOnWriteArraySet()。
2. CopyOnWriteArraySet()底层源码实现是用Lock锁，前两个解决方法的底层源码实现是用Synchronized关键字，Lock锁默认用的是非公平锁，效率比Synchronized高。

## 3、Map

### 1、解决办法

1. Map map = Collections.synchronizedMap(new HashMap())
2. Map map = new ConcureentHashMap()：这个add方法是用Lock锁的默认锁非公平锁。

## 2、ConcurrentHashMap类原理

1. 支持检索的完全并发性和更新的高预期并发性的哈希表。这个类服从相同功能规范如Hashtable，并且包括对应于每个方法版本Hashtable。不过，尽管所有操作都是线程安全的，检索操作并不意味着锁定，并没有为防止所有访问的方式锁定整个表的任何支持。这个类可以在依赖于线程安全性的程序中与Hashtable完全互HashTable，但不依赖于其同步细节。
2. 检索操作（包括get）通常不阻止，因此可能与更新操作重叠（包括put和remove)。检索反映了最近完成的更新操作的结果，（更正式的，对于给定密钥的更新操作之前发生与任何（非空关系检索该键报告更新的值））对于聚合操作，比如putAll和clear，并发检索可能反映插入或移除1只有一些条目。类似低，迭代器，分割器和枚举返回在反映迭代器/枚举创建过程中或之后反映哈希表状态的元素。他们不抛出ConcurrentModificationException。然而，迭代器被设计为一次只能由一个线程使用。

### 3、Hashtable

1. 该类实现类一个哈希表，它将键映射到值。任何非null对象都可以用作键值或值。
2. Hashtable一个实例有两个影响其性能的参数：初始容量和负载因子。
3. 通常负载因子（0.75f）提供了时间和空间成本之间的良好折中。更高的值会减少空间开销，但会增加查询条目的时间成本（这反映在大多数Hashtable操作中，包括get和put）。

# 9、Callable

> 1. Callable接口类似于Runnable，因为它们都是为其实例肯呢个由另一个线程类执行的类设计的。然而，Runnable不返回结果，也不能抛出异常，而Callable可以。
> 2. Callable还具有缓存。

## 1、使用

```java
public class CallableTest {
    public static void main(String[] args) throws ExecutionException, InterruptedException {
        FutureTask futureTask = new FutureTask(new MyThread());
        new Thread(futureTask,"A") .start();
        new Thread(futureTask,"B") .start();
        System.out.println(futureTask.get());
    }
}
class MyThread implements Callable<String> {
    @Override
    public String call() throws Exception {
        System.out.println("call()执行了");
        return "我结束了";
    }
}
```

# 10、常用的辅助类

## 1、CountDownLatch

### 1、CountDownLatch是什么

1. 允许一个或多个线程等到直到在其他线程中执行的一组操作完成的同步辅助。

2. CountDownLatch用给定的计数初始化，await方法阻塞，直到用countDown()方法的调用而导致当前计数达到零，之后所有等待线程被释放，并且任何后续的await调用立即返回。

3. CountDownLatch是一种通用的同步工具，可用于多种用途。一个CountDownLatch为一个计数的CountDownLatch用作一个简单的开/关锁存器，或者门：所有线程调用await在门口等待，直到被调用countDwoen()的线程打开。
4. CountDownLatch一个有用的属性是，它不要求调用countDown线程等待计数到达零之前继续，它只是阻止任何线程通过await，直到所有线程可以通过。

### 2、使用

```java
public class CountDownLatchTest {
    public static void main(String[] args) throws InterruptedException {
//        初始化计数6
        CountDownLatch countDownLatch = new CountDownLatch(6);
        for (int i = 0; i < 6; i++) {
            new Thread(() -> {
                System.out.println(Thread.currentThread().getName() + "出去了");
//                计数减一
                countDownLatch.countDown();
            },String.valueOf(i)).start();
        }
//        阻塞，只有计数为0时才可以执行await后面的代码
        countDownLatch.await();
        System.out.println("都出去了");
    }
}
```

## 2、CyclicBarrier

### 1、CyclicBarrier

1. 当等待的线程到达规定数量的时候执行某一个线程。

### 2、使用

```java
public class CyclicBarrierTest {
    public static void main(String[] args){
//        可以传入一个数值和一个线程
        CyclicBarrier cyclicBarrier = new CyclicBarrier(4,new Thread(() -> {
            System.out.println("符合条件，我被执行了");
        }));
        for (int i = 0; i < 10; i++) {
            new Thread(() -> {
                System.out.println(Thread.currentThread().getName() + "执行了");
                try {
//                    当等待的线程到达规定数量的时候执行某一个线程。
                    cyclicBarrier.await();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                } catch (BrokenBarrierException e) {
                    e.printStackTrace();
                }
            },String.valueOf(i)).start();
        }
    }
}
```

## 3、Semaphore

## 1、什么是Semaphore

> 可用作限流等。

1. 一个计数信号量。在概念上，信号量维持一组许可证。如果有必要，每个acquire()都会阻塞，直到许可证被释放可以被其他人用。每个release()释放许可证，潜在地释放阻塞获取方。Semaphone只保留可用数量的计数，并相应地执行，
2. 信号量通常用于限制线程数，而不是访问某些（物理或逻辑）资源。例如，这是一个使用信号量来控制对一个项目池的访问类。
3. 常用的两个方法：
   * acquire()：获取许可证，如果可以获取许可证就可以执行之后的代码
   * release()：释放许可证

### 2、使用

```java
public class SemaphoreTest {
    public static void main(String[] args) {
//        可以初始化许可证数量，当到达许可证数量的时候，则停止
        Semaphore semaphore = new Semaphore(3);
        for (int i = 0; i < 6; i++) {
            new Thread(() -> {
                try {
                    semaphore.acquire();
                    System.out.println(Thread.currentThread().getName() + "获得许可证");
                    //获取许可证
                    TimeUnit.SECONDS.sleep(2);
                    System.out.println(Thread.currentThread().getName() + "释放许可证");
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }finally {
//                   2秒后释放许可证
                    semaphore.release();
                }
            }, String.valueOf(i)).start();
        }
    }
}
```

# 11、ReadWriteLock

## 1、什么是ReadWriteLock

1. ReadWriteLock是一对关联的的locks，一个用于只读操作，一个用于写入，read lock可以由多个线程同时进行，而write lock只能一个线程进行。
2. 有两个方法:
   * readLock()：读锁，共享锁，可以多个线程一起读。
   * writeLock()：写锁，排他锁，独占锁，只能一个线程写。

## 2、使用

```java
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
```









































































