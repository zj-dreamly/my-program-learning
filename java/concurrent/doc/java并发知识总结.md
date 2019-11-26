## 并发概念

同时拥有2个或者多个线程，如果程序在单核处理器上运行，多个线程将交替的换入或者换出内存，这些线程是同时“存在”的，每个线程都处与执行过程中的某个状态，如果运行在多核处理器上，此时，程序中的每个线程都将分配到一个处理器核上，因此可以同时运行。

## 高并发

高并发（High Concurrency）是指互联网分布式系统架构设计中必须考虑的因素之一，它通常是指，通过设计保证系统能够同时并发处理很多请求。

## 对比

并发：多个线程操作相同的资源，保证线程安全，合理使用资源

高并发：服务能同时处理很多请求，提高程序性能

## CPU多级缓存

CPU缓存的背景原因： CPU 的频率越来越快，快到主存跟不上，这样在处理器时钟周期内，CPU常常需要等待主存，浪费资源。所以cache的出现，是为了缓解CPU和内存之间速度的不匹配问题。

(结构： cpu -> cache ->memory)

### CPU cache的意义

（1）时间局部性：如果某个数据被访问，那么在不久的将来，它很可能再次被访问

（2）空间局部性：如果某个数据被访问，那么与它相邻的数据很快也可能被访问

### CPU多级缓存-缓存一致性（MESI）

用于保证多个CPU cache之间的缓存共享数据的一致

缓存块的几种状态：

- modified（修改）：缓存块已经被修改，必须被写回主存，其他处理器不能再缓存这个块
- exclusive（互斥）：缓存块还没有被修改，且其他处理器不能装入这个缓存块
- share（共享）：缓存块未被修改，且其他处理器可以装入这个缓存块
- invalid（无效）：缓存块中的数据无效

CPU的读取遵循下面几点：

- 如果缓存状态是I，那么就从内存中读取，否则就从缓存中直接读取。
- 如果缓存处于M或E的CPU读取到其他CPU有读操作，就把自己的缓存写入到内存中，并将自己的状态设置为S。
- 只有缓存状态是M或E的时候，CPU才可以修改缓存中的数据，修改后，缓存状态变为M。

![](..\image\325120-bc5c8371e69048d4.jpg)

上图展示了MESI高速缓存一致性协议的状态转换实例。

- 在1中，处理器A从地址a读取数据，将数据存入它的缓存并置为exclusive
- 在2中，当处理器B试图从相同地址a读取数据时，A检测到地址冲突，以相关数据做出响应。此时a同时被A和B以shared状态装入缓存
- 在3中，当B要对共享地址a进行写操作，则将状态改为modified,并广播提醒A,让它将它的缓存块状态设置为Invalid
- 在4中，当A试图从a读取数据，会广播它的请求，B则把它修改的数据发送到A和主存，并设置两个副本的状态为shared来做出响应

### CPU多级缓存-乱序执行优化

处理器为提高运算速度而做出违背代码原有顺序的优化

## Java内存模型（JMM）

Java内存模型（Java Memory Model），定义了JVM在计算机内存上的工作方式。JVM是对整个计算机的虚拟模型，所以JMM是隶属于JVM的。如果我们要想深入了解Java并发编程，就要先理解好Java内存模型。Java内存模型定义了多线程之间共享变量的可见性以及如何在需要的时候对共享变量进行同步。

![](..\image\325120-82daf8a71caae552.jpg)

1、堆：（存放由new创建的对象和数组）

- 引用类型的变量，内存分配一般在堆上或者常量池（字符串常量，基本数据类型常量），需要通过new等方式来创建。
- 首先堆内存主要作用是存放运行时new的对象和数组，存取速度慢，可以运行时动态分配内存。

2、栈：（基本数据类型变量，对象的引用变量）

- 基本数据类型变量（int，short，long，byte，float，double，boolean，char）以及对象的引用变量，内存分配在栈上。变量出了作用域就会自动释放。
- 由于栈是后进先出模式的。主要用于执行程序，存取速度快，大小生存期必须确定，也就是有作用域，缺乏灵活性。

3、补充说明： 变量存储

- 一个本地变量如果是原始类型，那么它会被完全存储到栈区。
- 一个本地变量也有可能是一个对象的引用，这种情况下，这个本地引用会被存储到栈中，但是对象本身仍然存储在堆区。
- 对于一个对象的成员方法，这些方法中包含本地变量，仍需要存储在栈区，即使它们所属的对象在堆区。
- 对于一个对象的成员变量，不管它是原始类型还是包装类型，都会被存储到堆区。
- Static类型的变量以及类本身相关信息都会随着类本身存储在堆区

### jvm主内存与工作内存

首先，JVM将内存组织为主内存和工作内存两个部分。

- 主内存主要包括本地方法区和堆。每个线程都有一个工作内存，工作内存中主要包括两个部分，一个是属于该线程私有的栈和对主存部分变量拷贝的寄存器(包括程序计数器PC和cup工作的高速缓存区)**。**  
- 所有的变量都存储在主内存中(虚拟机内存的一部分)，对于所有线程都是共享的。
- 每条线程都有自己的工作内存，工作内存中保存的是主存中某些变量的拷贝，线程对变量的所有操作都必须在工作内存中进行，而不能直接读写主内存中的变量。
- 线程之间无法直接访问对方的工作内存中的变量，线程间变量的传递均需要通过主内存来完成。

### Java内存模型-同步8种操作

lock（锁定）：作用于主内存的变量，把一个变量标示成一条线程独占状态

unlock（解锁）：作用于主内存的变量，把一个处于锁定状态的变量释放出来，释放后的变量才可以被其他线程锁定

read（读取）：作用于主内存的变量，把一个变量值从主内存传输到线程的工作内存中，以便随后的load操作使用

load（载入）：作用于工作内存的变量，它把read操作从主内存得到的变量值放入到工作内存的变量副本中

use（使用）：作用于工作内存的变量，把工作内存中的一个变量值传递到执行引擎

assign（赋值）：作用于工作内存的变量，它把一个从执行引擎收到的值赋值给工作内存的变量

store（存储）：作用与工作内存的变量，把工作内存的一个变量的值传送到主内存中，以便随后的write操作

write（写入）：作用与主内存的变量，它把store操作从工作内存中一个变量的值传送到主内存的变量中

### Java内存模型-同步规则

不允许一个线程无原因地（没有发生过任何assign操作）把数据从工作内存同步会主内存中

一个新的变量只能在主内存中诞生，不允许在工作内存中直接使用一个未被初始化（load或者assign）的变量。即就是对一个变量实施use和store操作之前，必须先自行assign和load操作。

一个变量在同一时刻只允许一条线程对其进行lock操作，但lock操作可以被同一线程重复执行多次，多次执行lock后，只有执行相同次数的unlock操作，变量才会被解锁。lock和unlock必须成对出现。

如果对一个变量执行lock操作，将会清空工作内存中此变量的值，在执行引擎使用这个变量之前需要重新执行load或assign操作初始化变量的值。

如果一个变量事先没有被lock操作锁定，则不允许对它执行unlock操作；也不允许去unlock一个被其他线程锁定的变量。

对一个变量执行unlock操作之前，必须先把此变量同步到主内存中（执行store和write操作）

## 并发的优势和风险

![1537329735402](..\image\1537329735402.png)

## 并发模拟

Postman：http请求模拟工具

Apache Bench（AB）：Apache附带的工具，测试网站性能

JMeter：Apache组织开发的压力测试工具

代码实现：CountDownLatch，Semaphore

## 线程安全性

**定义**

当多个线程访问某个类时，不管运行时环境采用何种调度方式或者这些进程将如何进行交替执行，并且在主调代码中不需要任何额外的同步或协同，这个类都能表现出正确的行为，我们就称这个类是线程安全的。

**特性**

- 原子性：提供了互斥访问，同一时刻只能有一个线程来对它操作
- 可见性：一个线程对主内存的修改可以及时的被其他线程观察到
- 有序性：一个线程观察其他线程中的指令执行顺序，由于指令重排序的存在，该观察结果一般杂乱无序

### 原子性

#### **1. atomic包实现**

`AtomicLong`，`Unsafe.compareAndSwapInt`

`AtomicLong`，`LongAdder`

`AtomicReference`，`AtomicReferenceFieldUpdater`

`AtomicStampReference`：CAS的ABA问题

`AtomicLongArray`，`AtomicBoolean`

**示例代码**

```java
public class CountExample {

    //请求总数
    public static int clientTotal  = 5000;
    //同时并发执行的线程数
    public static int threadTotal = 200;
    //变量声明：计数
    public static AtomicInteger count = new AtomicInteger(0);

    public static void main(String[] args) throws InterruptedException {
        //创建线程池
        ExecutorService executorService = Executors.newCachedThreadPool();
        //定义信号量，给出允许并发的数目
        final Semaphore semaphore = new Semaphore(threadTotal);
        //定义计数器闭锁
        final CountDownLatch countDownLatch = new CountDownLatch(clientTotal);
        for (int i = 0;i<clientTotal;i++){
            executorService.execute(()->{
                try {
                    //判断进程是否允许被执行
                    semaphore.acquire();
                    add();
                    //释放进程
                    semaphore.release();
                } catch (InterruptedException e) {
                    log.error("excption",e);
                }
                countDownLatch.countDown();
            });
        }
        //保证信号量减为0
        countDownLatch.await();
        //关闭线程池
        executorService.shutdown();
        //变量取值
        log.info("count:{}",count.get());
    }

    private static void add(){
        //变量操作
        count.incrementAndGet();
    }
}
```

**`AtomicInteger`**
上边的示例代码就是通过`AtomicInteger`类保证了线程的原子性。 

那么它是如何保证原子性的呢？我们接下来分析一下它的源码。示例中，对count变量的+1操作，采用的是`incrementAndGet`方法，此方法的源码中调用了一个名为`unsafe.getAndAddInt`的方法

```java
public final int getAndAddInt(Object var1, long var2, int var4) {
    int var5;
    do {
        var5 = this.getIntVolatile(var1, var2);
    } while(!this.compareAndSwapInt(var1, var2, var5, var5 + var4));
    return var5;
}
```

在此方法中，方法参数为要操作的对象`Object var1`、期望底层当前的数值为`var2`、要修改的数值`var4`。定义的`var5`为真正从底层取出来的值。采用do..while循环的方式去获取底层数值并与期望值进行比较，比较成功才将值进行修改。而这个比较再进行修改的方法就是`compareAndSwapInt`就是我们所说的`CAS`，它是一系列的接口，比如下面罗列的几个接口。使用native修饰，是底层的方法。`CAS`取的是`compareAndSwap`三个单词的首字母。

**`AtomicLong` 与 `LongAdder`**

`LongAdder`是java8为我们提供的新的类，跟`AtomicLong`有相同的效果

那么问题来了，为什么有了`AtomicLong`还要新增一个`LongAdder`呢？ 
原因是：CAS底层实现是在一个死循环中不断地尝试修改目标值，直到修改成功。如果竞争不激烈的时候，修改成功率很高，否则失败率很高。在失败的时候，这些重复的原子性操作会耗费性能。

`LongAdder`类的实现核心是将热点数据分离，比如说它可以将`AtomicLong`内部的内部核心数据value分离成一个数组，每个线程访问时，通过hash等算法映射到其中一个数字进行计数，而最终的计数结果则为这个数组的求和累加，其中热点数据value会被分离成多个单元的cell，每个cell独自维护内部的值。当前对象的实际值由所有的cell累计合成，这样热点就进行了有效地分离，并提高了并行度。这相当于将`AtomicLong`的单点的更新压力分担到各个节点上。在低并发的时候通过对base的直接更新，可以保障和`AtomicLong`的性能基本一致。而在高并发的时候通过分散提高了性能。

> 知识点： 对于普通类型的long、double变量，JVM允许将64位的读操作或写操作拆成两个32位的操作。

**缺点：如果在统计的时候，如果有并发更新，可能会有统计数据有误差。实际使用中在处理高并发计数的时候优先使用`LongAdder`，而不是`AtomicLong`在线程竞争很低的时候，使用`AtomicLong`会简单效率更高一些。比如序列号生成（准确性）**

**`AtomicBoolean`**

这个类中值得一提的是它包含了一个名为`compareAndSet`的方法，这个方法可以做到的是控制一个boolean变量在一件事情执行之前为false，事情执行之后变为true。或者也可以理解为可以控制某一件事只让一个线程执行，并仅能执行一次。

```java
    //是否发生过
    private static AtomicBoolean isHappened = new AtomicBoolean(false);
    // 请求总数
    public static int clientTotal = 5000;
    // 同时并发执行的线程数
    public static int threadTotal = 200;

    public static void main(String[] args) throws Exception {
        ExecutorService executorService = Executors.newCachedThreadPool();
        final Semaphore semaphore = new Semaphore(threadTotal);
        final CountDownLatch countDownLatch = new CountDownLatch(clientTotal);
        for (int i = 0; i < clientTotal ; i++) {
            executorService.execute(() -> {
                try {
                    semaphore.acquire();
                    test();
                    semaphore.release();
                } catch (Exception e) {
                    log.error("exception", e);
                }
                countDownLatch.countDown();
            });
        }
        countDownLatch.await();
        executorService.shutdown();
        log.info("isHappened:{}", isHappened.get());
    }

    private static void test() {
        //控制某有一段代码只执行一次
        if (isHappened.compareAndSet(false, true)) {
            log.info("execute");
        }
    }
```

**`AtomicIntegerFieldUpdater`**

这个类的核心作用是要更新一个指定的类的某一个字段的值。并且这个字段一定要用volatile修饰，同时还不能是static的。

```java
@Slf4j
public class AtomicExample5 {

    //原子性更新某一个类的一个实例
    private static AtomicIntegerFieldUpdater<AtomicExample5> updater
            = AtomicIntegerFieldUpdater.newUpdater(AtomicExample5.class,"count");

    @Getter
    public volatile int count = 100;//必须要volatile标记，且不能是static

    public static void main(String[] args) {
        AtomicExample5 example5 = new AtomicExample5();

        if(updater.compareAndSet(example5,100,120)){
            log.info("update success 1,{}",example5.getCount());
        }

        if(updater.compareAndSet(example5,100,120)){
            log.info("update success 2,{}",example5.getCount());
        }else{
            log.info("update failed,{}",example5.getCount());
        }
    }
}
```

**`AtomicStampReference`与`CAS`的`ABA`问题**
什么是ABA问题？ 
CAS操作的时候，其他线程将变量的值A改成了B，但是随后又改成了A，本线程在CAS方法中使用期望值A与当前变量进行比较的时候，发现变量的值未发生改变，于是CAS就将变量的值进行了交换操作。但是实际上变量的值已经被其他的变量改变过，这与设计思想是不符合的。所以就有了`AtomicStampReference`。

**`AtomicStampReference`源码**

```java
private static class Pair<T> {
        final T reference;
        final int stamp;
        private Pair(T reference, int stamp) {
            this.reference = reference;
            this.stamp = stamp;
        }
        static <T> Pair<T> of(T reference, int stamp) {
            return new Pair<T>(reference, stamp);
        }
    }

private volatile Pair<V> pair;

private boolean casPair(Pair<V> cmp, Pair<V> val) {
        return UNSAFE.compareAndSwapObject(this, pairOffset, cmp, val);
    }

public boolean compareAndSet(V   expectedReference,
                                 V   newReference,
                                 int expectedStamp,
                                 int newStamp) {
        Pair<V> current = pair;
        return
            expectedReference == current.reference &&
            expectedStamp == current.stamp &&
             //排除新的引用和新的版本号与底层的值相同的情况
            ((newReference == current.reference &&  
              newStamp == current.stamp) ||
             casPair(current, Pair.of(newReference, newStamp)));
}
```

`AtomicStampReference`的处理思想是，每次变量更新的时候，将变量的版本号+1，之前的ABA问题中，变量经过两次操作以后，变量的版本号就会由1变成3，也就是说只要线程对变量进行过操作，变量的版本号就会发生更改。从而解决了ABA问题。

解释一下上边的源码： 
类中维护了一个volatile修饰的Pair类型变量current，Pair是一个私有的静态类，current可以理解为底层数值。 
compareAndSet方法的参数部分分别为期望的引用、新的引用、期望的版本号、新的版本号。 

return的逻辑为判断了期望的引用和版本号是否与底层的引用和版本号相符，并且排除了新的引用和新的版本号与底层的值相同的情况（即不需要修改）的情况（return代码部分3、4行）。条件成立，执行casPair方法，调用CAS操作。

**`AtomicLongArray`**

这个类实际上维护了一个Array数组，我们在对数值进行更新的时候，会多一个索引值让我们更新

#### **2.  用锁实现**

synchronized（依赖JVM）

- 修饰代码块：大括号括起来的代码，作用于调用的对象
- 修饰方法：整个方法，作用于调用的对象
- 修饰静态方法：整个静态方法，作用于所有对象
- 修饰类：括号括起来的部分，作用于所有对象

> 注意：`synchronized`不属于方法声明的一部分，父类方法上的`synchronized`并不会作用于子类
>

Lock：依赖特殊的CPU指令，代码实现，ReentrantLock

**总结**

synchronized：不可中断锁，适合竞争不激烈，可读性好

Lock：可中断锁，多样化同步，竞争激烈时能维持常态

Atomic：竞争激烈时能维持常态，比Lock性能好；只能同步一个值 

### 可见性

**导致共享变量在线程之间不可见的原因**

- 线程交叉执行
- 重排序结合线程交叉执行
- 共享变量更新后的值没有在工作内存和主存间及时更新

**JMM关于synchronized的2条规定**

- 线程解锁前，必须把共享变量的最新值刷新到主内存
- 线程加锁时，将清空工作内存中共享变量的值，从而使用共享变量时需要从主内存中重新读取最新的值（注意：加锁和解锁是同一把锁）

**volatile**

- 通过加入内存屏障和禁止重排序优化来实现可见性

- 对valatile变量写操作时，会在写操作后加入一条store屏障指令，将本地内存中的共享变量值刷新到主内存

- 对volatile变量读操作时候，会在读操作之前加入一个load屏障命令，从主内存中读取共享变量

那么volatile适合做什么呢？其实它比较适合做状态标记量（不会涉及到多线程同时读写的操作），而且要保证两点： 
（1）对变量的写操作不依赖于当前值 
（2）该变量没有包含在具有其他变量的不变的式子中 

volatile的使用：

```java
volatile boolean inited = false;

//线程1
context = loadContext();
inited = true;

//线程2
while(!inited){
  sleep();
}

doSomethingWithConfig(context);

```

### 有序性

- Java内存模型中，允许编译器和处理器对指令进行重排序，但是重排序过程不会影响到单线程程序的执行，却会影响到多线程并发执行的正确性
- Java通过volatile，synchronized，lock来保证有序性

**happens-before原则**

1. 程序次序规则：一个线程内，按照代码顺序，书写在前面的操作先行发生于书写在后面的操作；
2. 锁定规则：一个unLock操作先行发生于后面对同一个锁额lock操作；
3. volatile变量规则：对一个变量的写操作先行发生于后面对这个变量的读操作；
4. 传递规则：如果操作A先行发生于操作B，而操作B又先行发生于操作C，则可以得出操作A先行发生于操作C；
5. 线程启动规则：Thread对象的start()方法先行发生于此线程的每个一个动作；
6. 线程中断规则：对线程interrupt()方法的调用先行发生于被中断线程的代码检测到中断事件的发生；
7. 线程终结规则：线程中所有的操作都先行发生于线程的终止检测，我们可以通过Thread.join()方法结束、Thread.isAlive()的返回值手段检测到线程已经终止执行；
8. 对象终结规则：一个对象的初始化完成先行发生于他的finalize()方法的开始；

## 发布对象

有些时候，我们希望在多个线程间共享对象，此时必须确保安全地进行共享，那么就牵扯到对象的发布问题

- 发布对象：使一个对象能够被当前范围之外的代码所使用

- 对象逸出：一种错误的发布，当一个对象还没有构造完成的时候，就使它被其他线程所见


### 不安全的发布

**发布对象**

```java
@Slf4j
@NotThreadSafe
public class UnsafePublish {

   private String[] states = {"a", "b", "c"};

   public String[] getStates() {
       return states;
   }

   public static void main(String[] args) {
       UnsafePublish unsafePublish = new UnsafePublish();
       log.info("{}", Arrays.toString(unsafePublish.getStates()));

       unsafePublish.getStates()[0] = "d";
       log.info("{}", Arrays.toString(unsafePublish.getStates()));
   }
}
```

- 这个代码通过public访问级别发布了类的域，在类的任何外部的线程都可以访问这些域
- 我们无法保证其他线程会不会修改这个域，从而使私有域内的值错误（上述代码中就对私有域进行了修改）

**对象逸出**

```java
@Slf4j
@NotThreadSafe
@NotRecommend
public class Escape {

   private int thisCanBeEscape = 0;

   public Escape () {
       new InnerClass();
   }

   private class InnerClass {

       public InnerClass() {
           log.info("{}", Escape.this.thisCanBeEscape);
       }
   }

   public static void main(String[] args) {
       new Escape();
   }
}
```

- 这个内部类的实例里面包含了对封装实例的私有域对象的引用，在对象没有被正确构造完成之前就会被发布，有可能有不安全的因素在里面，会导致this引用在构造期间溢出的错误。
- 上述代码在函数构造过程中启动了一个线程。无论是隐式的启动还是显式的启动，都会造成这个this引用的溢出。新线程总会在所属对象构造完毕之前就已经看到它了。
- 因此要在构造函数中创建线程，那么不要启动它，而是应该采用一个专有的start或者初始化的方法统一启动线程
- 这里其实我们可以采用工厂方法和私有构造函数来完成对象创建和监听器的注册等等，这样才可以避免错误

- 如果不正确的发布对象会导致两种错误： 
  （1）发布线程意外的任何线程都可以看到被发布对象的过期的值 
  （2）线程看到的被发布线程的引用是最新的，然而被发布对象的状态却是过期的

### 如何安全发布对象

- 在静态初始化函数中初始化一个对象引用
- 将对象的引用保存到volatile类型域或者AtomicReference对象中
- 将对象的引用保存到某个正确构造对象的final类型域中
- 将对象的引用保存到一个由锁保护的域中

1. 懒汉模式

```java
/**
 * 懒汉模式 -》 双重同步锁单例模式
 * 单例实例在第一次使用时进行创建
 */
@ThreadSafe
public class SingletonExample5 {

    // 私有构造函数
    private SingletonExample5() {

    }

    // 1、memory = allocate() 分配对象的内存空间
    // 2、ctorInstance() 初始化对象
    // 3、instance = memory 设置instance指向刚分配的内存

    // 单例对象 volatile + 双重检测机制 -> 禁止指令重排
    private volatile static SingletonExample5 instance = null;

    // 静态的工厂方法
    public static SingletonExample5 getInstance() {
        if (instance == null) { // 双重检测机制        // B
            synchronized (SingletonExample5.class) { // 同步锁
                if (instance == null) {
                    instance = new SingletonExample5(); // A - 3
                }
            }
        }
        return instance;
    }
}
```

2. 饿汉模式

```java
/**
 * 饿汉模式
 * 单例实例在类装载时进行创建
 */
@ThreadSafe
public class SingletonExample6 {

    // 私有构造函数
    private SingletonExample6() {

    }

    // 单例对象
    private static SingletonExample6 instance = null;

    static {
        instance = new SingletonExample6();
    }

    // 静态的工厂方法
    public static SingletonExample6 getInstance() {
        return instance;
    }

    public static void main(String[] args) {
        System.out.println(getInstance().hashCode());
        System.out.println(getInstance().hashCode());
    }
}
```

3. 枚举模式

```java
/**
 * 枚举模式：最安全
 */
@ThreadSafe
@Recommend
public class SingletonExample7 {

    // 私有构造函数
    private SingletonExample7() {

    }

    public static SingletonExample7 getInstance() {
        return Singleton.INSTANCE.getInstance();
    }

    private enum Singleton {
        INSTANCE;

        private SingletonExample7 singleton;

        // JVM保证这个方法绝对只调用一次
        Singleton() {
            singleton = new SingletonExample7();
        }

        public SingletonExample7 getInstance() {
            return singleton;
        }
    }
}
```

## 不可变对象

#### 定义

- 对象创建以后其状态就不能改变
- 对象所有域都是final类型
- 对象是正确创建的（在对象创建期间，this引用没有逸出）

#### fanal关键字：

- 修饰类：不能被继承

- 修饰方法：1、锁定方法不被继承类修改；2、效率（一个类的private方法被隐式的定义为final方法）

- 修饰变量：修饰基本数据类型变量，初始化不能被修改，修饰引用类型变量，初始化之后不能指向另外一个对象


#### 构造不可变对象的其他方式

（1）自己定义 
这里可以采用的方式包括： 
1、将类声明为final，这样它就不能被继承。 
2、将所有的成员声明为私有的，这样就不允许直接访问这些成员。 
3、对变量不提供set方法，将所有可变的成员声明为final，这样就只能赋值一次。通过构造器初始化所有成员进行深度拷贝。 
4、在get方法中不直接返回对象的本身，而是克隆对象，返回对象的拷贝。

（2）使用Java中提供的Collection类中的各种unmodifiable开头的方法 

（3）使用Guava中的Immutable开头的类

### 线程封闭

（1）Ad-hoc 线程封闭：

​        Ad-hoc线程封闭是指，维护线程封闭性的职责完全由程序实现来承担。Ad-hoc线程封闭是非常脆弱的，因为没有任何一种语言特性，例如可见性修饰符或局部变量，能将对象封闭到目标线程上。事实上，对线程封闭对象（例如，GUI应用程序中的可视化组件或数据模型等）的引用通常保存在公有变量中。

（2）堆栈封闭： 
        堆栈封闭其实就是方法中定义局部变量。不存在并发问题。多个线程访问一个方法的时候，方法中的局部变量都会被拷贝一份到线程的栈中（Java内存模型），所以局部变量是不会被多个线程所共享的。

（3）`ThreadLocal`线程封闭：

​        它是一个特别好的封闭方法，其实ThreadLocal内部维护了一个map，map的key是每个线程的名称，而map的value就是我们要封闭的对象。`hreadLocal`提供了get、set、remove方法，每个操作都是基于当前线程的，所以它是线程安全的。

### 线程不安全类与写法

- `StringBuilder`（线程不安全） -> `StringBuffer`（线程安全）
- `SimpleDateFormat`（线程不安全） -> `JodaTime`（线程安全）
- `ArrayList`，`HashSet`，`HashMap`等`Collections`都是不安全的
- 先检查再执行：`if(condition(a)){handle(a);}`（线程不安全）

#### Vector、ArrayList、LinkedList

- 1、Vector： 
  - Vector与ArrayList一样，也是通过数组实现的，不同的是它支持线程的同步，即某一时刻只有一个线程能够写Vector，避免多线程同时写而引起的不一致性，但实现同步需要很高的花费，因此，访问它比访问ArrayList慢。
- 2、ArrayList： 
  - a. 当操作是在一列数据的后面添加数据而不是在前面或者中间，并需要随机地访问其中的元素时，使用ArrayList性能比较好。
  - b. ArrayList是最常用的List实现类，内部是通过数组实现的，它允许对元素进行快速随机访问。数组的缺点是每个元素之间不能有间隔，当数组大小不满足时需要增加存储能力，就要讲已经有数组的数据复制到新的存储空间中。当从ArrayList的中间位置插入或者删除元素时，需要对数组进行复制、移动、代价比较高。因此，它适合随机查找和遍历，不适合插入和删除。
- 3、LinkedList： 
  - a. 当对一列数据的前面或者中间执行添加或者删除操作时，并且按照顺序访问其中的元素时，要使用LinkedList。
  - b. LinkedList是用链表结构存储数据的，很适合数据的动态插入和删除，随机访问和遍历速度比较慢。另外，他还提供了List接口中没有定义的方法，专门用于操作表头和表尾元素，可以当作堆栈、队列和双向队列使用。
     　　
     Vector和ArrayList在使用上非常相似，都可以用来表示一组数量可变的对象应用的集合，并且可以随机的访问其中的元素。

#### HashTable、HashMap、HashSet

- HashTable和HashMap采用的存储机制是一样的，不同的是： 
  - 1、HashMap：
    - a. 采用数组方式存储key-value构成的Entry对象，无容量限制；
    - b. 基于key hash查找Entry对象存放到数组的位置，对于hash冲突采用链表的方式去解决；
    - c. 在插入元素时，可能会扩大数组的容量，在扩大容量时须要重新计算hash，并复制对象到新的数组中；
    - d. 是非线程安全的；
    - e. 遍历使用的是Iterator迭代器；
  - 2、HashTable：
    - a. 是线程安全的；
    - b. 无论是key还是value都不允许有null值的存在；在HashTable中调用Put方法时，如果key为null，直接抛出NullPointerException异常；
    - c. 遍历使用的是Enumeration列举；
  - 3、HashSet：
    - a. 基于HashMap实现，无容量限制；
    - b. 是非线程安全的；
    - c. 不保证数据的有序；

#### TreeSet、TreeMap

- TreeSet和TreeMap都是完全基于Map来实现的，并且都不支持get(index)来获取指定位置的元素，需要遍历来获取。另外，TreeSet还提供了一些排序方面的支持，例如传入Comparator实现、descendingSet以及descendingIterator等。 
  - 1、TreeSet：
    - a. 基于TreeMap实现的，支持排序；
    - b. 是非线程安全的；
  - 2、TreeMap：
    - a. 典型的基于红黑树的Map实现，因此它要求一定要有key比较的方法，要么传入Comparator比较器实现，要么key对象实现Comparator接口；
    - b. 是非线程安全的；

### 线程安全-同步容器（并不能完全做到线程安全）

`ArrayList` ->` Vector`，`stack`

`HashMap` -> `HashTable`（key，value不能为null）

`Collections.synchronizedXXX`（List，Set，Map）

**注意：在遍历集合的同时并删除特定元素时，foreach和iterator都会抛出`java.util.ConcurrentModificationException`，推荐使用常规遍历方式来删除元素，或者在遍历的时候打上标记，遍历结束再进行删除操作**

### 线程安全 - 并发容器J.U.C

#### `ArrayList` ->` CopyOnWriteArrayList`（读写分离）

`CopyOnWriteArrayList` 写操作时复制，当有新元素添加到集合中时，从原有的数组中拷贝一份出来，然后在新的数组上作写操作，将原来的数组指向新的数组。整个数组的add操作都是在锁的保护下进行的，防止并发时复制多份副本。读操作是在原数组中进行，不需要加锁。

缺点： 

- 写操作时复制消耗内存，如果元素比较多时候，容易导致`young gc` 和full gc。 
- 不能用于实时读的场景.由于复制和add操作等需要时间，故读取时可能读到旧值。 
- 能做到最终一致性，但无法满足实时性的要求，更适合读多写少的场景。 
- 如果无法知道数组有多大，或者add,set操作有多少，慎用此类,在大量的复制副本的过程中很容易出错

设计思想： 

- 读写分离 
- 最终一致性 
- 使用时另外开辟空间，防止并发冲突

源码分析：

```java
//构造方法
public CopyOnWriteArrayList(Collection<? extends E> c) {
    Object[] elements;//使用对象数组来承载数据
    if (c.getClass() == CopyOnWriteArrayList.class)
        elements = ((CopyOnWriteArrayList<?>)c).getArray();
    else {
        elements = c.toArray();
        // c.toArray might (incorrectly) not return Object[] (see 6260652)
        if (elements.getClass() != Object[].class)
            elements = Arrays.copyOf(elements, elements.length, Object[].class);
    }
    setArray(elements);
}

//添加数据方法
public boolean add(E e) {
    final ReentrantLock lock = this.lock;//使用重入锁，保证线程安全
    lock.lock();
    try {
        Object[] elements = getArray();//获取当前数组数据
        int len = elements.length;
        Object[] newElements = Arrays.copyOf(elements, len + 1);//复制当前数组并且扩容+1
        newElements[len] = e;//将要添加的数据放入新数组
        setArray(newElements);//将原来的数组指向新的数组
        return true;
    } finally {
        lock.unlock();
    }
}
```

#### `HashSet` -> `CopyOnWriteArraySet`

它是线程安全的，底层实现使用的是`CopyOnWriteArrayList`，因此它也适用于大小很小的set集合，只读操作远大于可变操作。因为他需要copy整个数组，所以包括add、remove、set它的开销相对于大一些。
迭代器不支持可变的remove操作。使用迭代器遍历的时候速度很快，而且不会与其他线程发生冲突。

源码分析：

```java
//构造方法
public CopyOnWriteArraySet() {
    al = new CopyOnWriteArrayList<E>();//底层使用CopyOnWriteArrayList
}

//添加元素方法，基本实现原理与CopyOnWriteArrayList相同
private boolean addIfAbsent(E e, Object[] snapshot) {
    final ReentrantLock lock = this.lock;
    lock.lock();
    try {
        Object[] current = getArray();
        int len = current.length;
        if (snapshot != current) {//添加了元素去重操作
            // Optimize for lost race to another addXXX operation
            int common = Math.min(snapshot.length, len);
            for (int i = 0; i < common; i++)
                if (current[i] != snapshot[i] && eq(e, current[i]))
                    return false;
            if (indexOf(e, current, common, len) >= 0)
                    return false;
        }
        Object[] newElements = Arrays.copyOf(current, len + 1);
        newElements[len] = e;
        setArray(newElements);
        return true;
    } finally {
        lock.unlock();
    }
}
```

#### `TreeSet` -> `ConcurrentSkipListSet`

它是JDK6新增的类，同TreeSet一样支持自然排序，并且可以在构造的时候自己定义比较器。

- 同其他set集合，是基于map集合的（基于ConcurrentSkipListMap），在多线程环境下，里面的contains、add、remove操作都是线程安全的。
- 多个线程可以安全的并发的执行插入、移除、和访问操作。但是对于批量操作addAll、removeAll、retainAll和containsAll并不能保证以原子方式执行，原因是addAll、removeAll、retainAll底层调用的还是contains、add、remove方法，只能保证每一次的执行是原子性的，代表在单一执行操纵时不会被打断，但是不能保证每一次批量操作都不会被打断。在使用批量操作时，还是需要手动加上同步操作的。
- 不允许使用null元素，它无法可靠的将参数及返回值与不存在的元素区分开来。
  源码分析：

```java
//构造方法
public ConcurrentSkipListSet() {
    m = new ConcurrentSkipListMap<E,Object>();//使用ConcurrentSkipListMap实现

}
```

#### `HashMap` -> `ConcurrentHashMap`

- 不允许空值，在实际的应用中除了少数的插入操作和删除操作外，绝大多数我们使用map都是读取操作。而且读操作大多数都是成功的。基于这个前提，它针对读操作做了大量的优化。因此这个类在高并发环境下有特别好的表现。
- ConcurrentHashMap作为Concurrent一族，其有着高效地并发操作，相比Hashtable的笨重，ConcurrentHashMap则更胜一筹了。
- 在1.8版本以前，ConcurrentHashMap采用分段锁的概念，使锁更加细化，但是1.8已经改变了这种思路，而是利用CAS+Synchronized来保证并发更新的安全，当然底层采用数组+链表+红黑树的存储结构。

#### `TreeMap` -> `ConcurrentSkipListMap`

- 底层实现采用SkipList跳表

- 曾经有人用ConcurrentHashMap与ConcurrentSkipListMap做性能测试，在4个线程1.6W的数据条件下，前者的数据存取速度是后者的4倍左右。但是后者有几个前者不能比拟的优点： 
  1、Key是有序的 

  2、支持更高的并发，存储时间与线程数无关

### 安全共享对象策略-总结

- 线程限制：一个被线程限制的对象，由线程独占，并且只能被占有它的线程修改
- 共享只读：一个共享只读的对象，在没有额外同步的情况下， 可以被多个线程并发访问，但是任何线程都不能修改它
- 线程安全对象：一个线程安全的对象或者容器，在内部通过同步机制来保证线程安全，所以其他线程无需额外的同步就可以通过公共接口随意访问它们
- 被守护对象：被守护的对象只能通过获取特定的锁来访问

## J.U.C之AQS介绍

AQS（`AbstractQueuedSynchronizer`）是并发容器J.U.C（`java.lang.concurrent`）下locks包内的一个抽象类，它实现了一个**FIFO**(FirstIn、FisrtOut先进先出)的队列。底层实现的数据结构是一个**双向列表**。 它实现了一个**FIFO**(FirstIn、FisrtOut先进先出)的队列。底层实现的数据结构是一个**双向列表**。 

AQS主要是以继承的方式使用。AQS本身是没有实现任何同步接口的，它仅仅只是定义了同步状态的获取和释放的方法来供自定义的同步组件的使用。在java的同步组件中，AQS的子类（Sync等）一般是同步组件的静态内部类，即通过组合的方式使用

### AQS原理

- AQS的实现依赖内部的同步队列（FIFO双向队列），如果当前线程获取同步状态失败，AQS会将该线程以及等待状态等信息构造成一个Node，将其加入同步队列的尾部，同时阻塞当前线程，当同步状态释放时，唤醒队列的头节点。
- 上面说的有点抽象，来具体看下，首先来看AQS最主要的三个成员变量：

```java
private transient volatile Node head;
private transient volatile Node tail;
private volatile int state;
```

- 上面提到的同步状态就是这个int型的变量state. head和tail分别是同步队列的头结点和尾结点。假设state=0表示同步状态可用（如果用于锁，则表示锁可用），state=1表示同步状态已被占用（锁被占用）。

![](..\image\11.jpg)

**Sync queue**：同步队列，是一个双向列表。包括head节点和tail节点。head节点主要用作后续的调度。 
**Condition queue**：非必须，单向列表。当程序中存在cindition的时候才会存在此列表。

### AQS设计思想

- 使用Node实现FIFO队列，可以用于构建锁或者其他同步装置的基础框架
- 利用了一个int类型表示状态
- 使用方法是继承
- 子类通过继承并实现方法管理其状态{`acquire()`和`release()`}方法操纵状态
- 可以同时实现排它锁和共享锁模式（独占、共享）

### AQS同步组件

- CountDownLatch
- Semaphore
- CyclicBarrier
- ReentrantLock
- Condition
- FutureTask

#### CountDownLatch

- CountDownLatch是通过一个计数器来实现的，计数器的初始值为线程的数量。每当一个线程完成了自己的任务后，计数器的值就会减1。当计数器值到达0时，它表示所有的线程已经完成了任务，然后在闭锁上等待的线程就可以恢复执行任务。



![img](..\image\325120-eec3a6a29903a918.jpg)



- 与CountDownLatch的第一次交互是主线程等待其他线程。主线程必须在启动其他线程后立即调用CountDownLatch.await()方法。这样主线程的操作就会在这个方法上阻塞，直到其他线程完成各自的任务。
- 其他N 个线程必须引用闭锁对象，因为他们需要通知CountDownLatch对象，他们已经完成了各自的任务。这种通知机制是通过CountDownLatch.countDown()方法来完成的；每调用一次这个方法，在构造函数中初始化的count值就减1。所以当N个线程都调 用了这个方法，count的值等于0，然后主线程就能通过await()方法，恢复执行自己的任务。

```java
@Slf4j
public class CountDownLatchExample2 {

    private final static int threadCount = 200;

    public static void main(String[] args) throws Exception {

        ExecutorService exec = Executors.newCachedThreadPool();

        final CountDownLatch countDownLatch = new CountDownLatch(threadCount);

        for (int i = 0; i < threadCount; i++) {
            final int threadNum = i;
            exec.execute(() -> {
                try {
                    test(threadNum);
                } catch (Exception e) {
                    log.error("exception", e);
                } finally {
                    countDownLatch.countDown();
                }
            });
        }
        
        //所有线程执行完毕才会执行（可设置超时时间，即使设置超时时间，线程也会执行完，如果不设置时间，         那么在线程结束之前，程序是处于阻塞状态的）
        countDownLatch.await(10, TimeUnit.MILLISECONDS);
        log.info("finish");
        exec.shutdown();
    }

    private static void test(int threadNum) throws Exception {
        Thread.sleep(100);
        log.info("{}", threadNum);
    }
```

#### Semaphore（信号量）

- Semaphore也叫信号量，在JDK1.5被引入，可以用来控制同时访问特定资源的线程数量，通过协调各个线程，以保证合理的使用资源。
- Semaphore内部维护了一组虚拟的许可，许可的数量可以通过构造函数的参数指定。
- 访问特定资源前，必须使用acquire方法获得许可，如果许可数量为0，该线程则一直阻塞，直到有可用许可。
- 访问资源后，使用release释放许可。
- Semaphore和ReentrantLock类似，获取许可有公平策略和非公平许可策略，默认情况下使用非公平策略。
- 获取和释放一个许可

```java
private final static int threadCount = 20;

   public static void main(String[] args) throws Exception {

       ExecutorService exec = Executors.newCachedThreadPool();

       final Semaphore semaphore = new Semaphore(3);

       for (int i = 0; i < threadCount; i++) {
           final int threadNum = i;
           exec.execute(() -> {
               try {
                   // 获取一个许可
                   semaphore.acquire(); 
                   test(threadNum);
                   semaphore.release(); 
                   // 释放一个许可
               } catch (Exception e) {
                   log.error("exception", e);
               }
           });
       }
       exec.shutdown();
   }

   private static void test(int threadNum) throws Exception {
       log.info("{}", threadNum);
       Thread.sleep(1000);
   }
```

- 一次获取和释放三个许可

```java
private final static int threadCount = 20;

    public static void main(String[] args) throws Exception {

        ExecutorService exec = Executors.newCachedThreadPool();

        final Semaphore semaphore = new Semaphore(3);

        for (int i = 0; i < threadCount; i++) {
            final int threadNum = i;
            exec.execute(() -> {
                try {
                    // 获取多个许可
                    semaphore.acquire(3); 
                    test(threadNum);
                    // 释放多个许可
                    semaphore.release(3); 
                } catch (Exception e) {
                    log.error("exception", e);
                }
            });
        }
        exec.shutdown();
    }

    private static void test(int threadNum) throws Exception {
        log.info("{}", threadNum);
        Thread.sleep(1000);
    }
```

- 尝试获取一个许可

```java
private final static int threadCount = 20;

    public static void main(String[] args) throws Exception {

        ExecutorService exec = Executors.newCachedThreadPool();

        final Semaphore semaphore = new Semaphore(3);

        for (int i = 0; i < threadCount; i++) {
            final int threadNum = i;
            exec.execute(() -> {
                try {
                    // 尝试获取一个许可
                    if (semaphore.tryAcquire()) { 
                        test(threadNum);
                        // 释放一个许可
                        semaphore.release(); 
                    }
                } catch (Exception e) {
                    log.error("exception", e);
                }
            });
        }
        exec.shutdown();
    }

    private static void test(int threadNum) throws Exception {
        log.info("{}", threadNum);
        Thread.sleep(1000);
    }
```

- 带有超时时间的许可

```java
private final static int threadCount = 20;

   public static void main(String[] args) throws Exception {

       ExecutorService exec = Executors.newCachedThreadPool();

       final Semaphore semaphore = new Semaphore(3);

       for (int i = 0; i < threadCount; i++) {
           final int threadNum = i;
           exec.execute(() -> {
               try {
                   // 尝试获取一个许可
                   if (semaphore.tryAcquire(5000, TimeUnit.MILLISECONDS)) { 
                       test(threadNum);
                       // 释放一个许可
                       semaphore.release(); 
                   }
               } catch (Exception e) {
                   log.error("exception", e);
               }
           });
       }
       exec.shutdown();
   }

   private static void test(int threadNum) throws Exception {
       log.info("{}", threadNum);
       Thread.sleep(1000);
   }
```

#### CyclicBarrier



![img](..\image\325120-810222c987209828.jpg)



- CyclicBarrier也是一个同步辅助类 , 它允许一组线程相互等待 , 直到到达某个公共的屏障点 , 通过它可以完成多个线程之间相互等待 ,只有当每个线程都准备好之后, 才能各自继续往下执行后续的操作, 和 CountDownLatch相似的地方就是, 它也是通过计数器来实现的. 当某个线程调用了 await()方法之后, 该线程就进入了等待状态 . 而且计数器就进行 +1 操作 , 当计数器的值达到了我们设置的初始值的时候 , 之前调用了await() 方法而进入等待状态的线程会被唤醒继续执行后续的操作. 因为 CyclicBarrier释放线程之后可以重用, 所以又称之为循环屏障 . CyclicBarrier 使用场景和  CountDownLatch 很相似 , 可以用于多线程计算数据, 最后合并计算结果的应用场景 .
- CyclicBarrier 与 CountDownLatch 区别
  - CountDownLatch的计数器只能使用一次 , 而 CyclicBarrier 的计数器可以使用 reset重置 循环使用
  - CountDownLatch 主要事项 1 个 或者 n 个线程需要等待其它线程完成某项操作之后才能继续往下执行 , 其描述的是 1 个 或者 n 个线程与其它线程的关系 ; CyclicBarrier 主要是实现了 1 个或者多个线程之间相互等待,直到所有的线程都满足条件之后, 才执行后续的操作 , 其描述的是内部各个线程相互等待的关系 .
  - CyclicBarrier 假如有 5 个线程都调用了 await() 方法 , 那这个 5 个线程就等着 , 当这 5 个线程都准备好之后, 它们有各自往下继续执行 , 如果这 5 个线程在后续有一个计算发生错误了 , 这里可以重置计数器 , 并让这 5 个线程再执行一遍 .

例子一：

```java
   private static CyclicBarrier barrier = new CyclicBarrier(5);

    public static void main(String[] args) throws Exception {

        ExecutorService executor = Executors.newCachedThreadPool();

        for (int i = 0; i < 10; i++) {
            final int threadNum = i;
            Thread.sleep(1000);
            executor.execute(() -> {
                try {
                    race(threadNum);
                } catch (Exception e) {
                    log.error("exception", e);
                }
            });
        }
        executor.shutdown();
    }

    private static void race(int threadNum) throws Exception {
        Thread.sleep(1000);
        log.info("{} is ready", threadNum);
        barrier.await();
        log.info("{} continue", threadNum);
    }
```

例子二： 带有等待时间

```java
 private static CyclicBarrier barrier = new CyclicBarrier(5);

    public static void main(String[] args) throws Exception {

        ExecutorService executor = Executors.newCachedThreadPool();

        for (int i = 0; i < 10; i++) {
            final int threadNum = i;
            Thread.sleep(1000);
            executor.execute(() -> {
                try {
                    race(threadNum);
                } catch (Exception e) {
                    log.error("exception", e);
                }
            });
        }
        executor.shutdown();
    }

    private static void race(int threadNum) throws Exception {
        Thread.sleep(1000);
        log.info("{} is ready", threadNum);
        try {
            barrier.await(2000, TimeUnit.MILLISECONDS);
        } catch (Exception e) {
            log.warn("BarrierException", e);
        }
        log.info("{} continue", threadNum);
    }
```

例子三： 到达屏障，优先执行回调方法

```java
private static CyclicBarrier barrier = new CyclicBarrier(5, () -> {
       log.info("callback is running");
   });

   public static void main(String[] args) throws Exception {

       ExecutorService executor = Executors.newCachedThreadPool();

       for (int i = 0; i < 10; i++) {
           final int threadNum = i;
           Thread.sleep(1000);
           executor.execute(() -> {
               try {
                   race(threadNum);
               } catch (Exception e) {
                   log.error("exception", e);
               }
           });
       }
       executor.shutdown();
   }

   private static void race(int threadNum) throws Exception {
       Thread.sleep(1000);
       log.info("{} is ready", threadNum);
       barrier.await();
       log.info("{} continue", threadNum);
   }
```

#### ReentrantLock与锁

- 首先要知道 Java 中的锁主要分两类锁 , 一种是 synchronized锁 , 另外一种就是 J.U.C中 提供的锁 , J.U.C里核心的锁是 ReentrantLock。

- ReentrantLock (可重入锁)与 synchronized 的区别 

  - 可重入性 
    - ReentrantLock 字面意思就是 再进入 锁 , 所以称之为可重入锁 , synchronized 使用的锁也是可重入的. 它俩都是同一个线程进入一次锁的计数器就自增 1,所以要等到锁的计数器下降为 0 时才释放锁 .
  - 锁的实现 
    - synchronized的锁是基于 JVM 来实现的 , ReentrantLock 是jdk 实现的. 通俗的来讲就是 操作系统来控制实现和用户编码实现的区别 .
  - 性能区别 
    - 在 synchronized 关键字优化之前, 其性能比 ReentrantLock 差 , 但是优化过后 , 在两者都可以使用的情况下, 建议使用 synchronized, 主要是其写法比较容易
  - 功能 
    - synchronized 写起来更简洁 , 它是由编译器来实现锁的加锁和释放 , 而ReentrantLock 需要我们手工申明加锁和释放锁 , 为了避免手工忘记释放锁而造成死锁 , 所以建议在final里申明和释放锁.
  - ReentrantLock 独有的功能 
    - ReentrantLock可指定是公平锁还是非公平锁 , 公平锁就是先等待的线程先获得锁.有先来后到之说. 而synchronized是非公平锁。
    - 提供了一个 Condition 类 , 可以分组唤醒需要唤醒的线程 , 不像 synchronized要么随机唤醒一个线程 , 要么唤醒全部线程。
    - 提供能够中断等待锁的线程的机制。

  ##### 要放弃synchronized？

  从上边的介绍，看上去`ReentrantLock`不仅拥有`synchronized`的所有功能，而且有一些功能`synchronized`无法实现的特性。性能方面，`ReentrantLock`也不比`synchronized`差，那么到底我们要不要放弃使用`synchronized`呢？答案是不要这样做。

  J.U.C包中的锁定类是用于高级情况和高级用户的工具，除非说你对Lock的高级特性有特别清楚的了解以及有明确的需要，或这有明确的证据表明同步已经成为可伸缩性的瓶颈的时候，否则我们还是继续使用`synchronized`。相比较这些高级的锁定类，synchronized还是有一些优势的，比如synchronized不可能忘记释放锁。还有当JVM使用synchronized管理锁定请求和释放时，JVM在生成线程转储时能够包括锁定信息，这些信息对调试非常有价值，它们可以标识死锁以及其他异常行为的来源。

```java
/**
 *  实例说明 模拟并发 发送5000 个请求 , 每次最多200 个请求 ,
 *  每次计数自增1 , 最后结果应该是5000 
 */
 // 请求总数
    public static int clientTotal = 5000;

    // 同时并发执行的线程数
    public static int threadTotal = 200;

    public static int count = 0;

    private final static Lock lock = new ReentrantLock();

    public static void main(String[] args) throws Exception {
        ExecutorService executorService = Executors.newCachedThreadPool();
        final Semaphore semaphore = new Semaphore(threadTotal);
        final CountDownLatch countDownLatch = new CountDownLatch(clientTotal);
        for (int i = 0; i < clientTotal ; i++) {
            executorService.execute(() -> {
                try {
                    semaphore.acquire();
                    add();
                    semaphore.release();
                } catch (Exception e) {
                    log.error("exception", e);
                }
                countDownLatch.countDown();
            });
        }
        countDownLatch.await();
        executorService.shutdown();
        log.info("count:{}", count);
    }

    private static void add() {
        lock.lock();
        try {
            count++;
        } finally {
            lock.unlock();
        }
    }
```

#### ReentrantReadWriteLock

ReentrantReadWriteLock是Lock的另一种实现方式，我们已经知道了ReentrantLock是一个排他锁，同一时间只允许一个线程访问，而ReentrantReadWriteLock允许多个读线程同时访问，但不允许写线程和读线程、写线程和写线程同时访问。相对于排他锁，提高了并发性。在实际应用中，大部分情况下对共享数据（如缓存）的访问都是读操作远多于写操作，这时ReentrantReadWriteLock能够提供比排他锁更好的并发性和吞吐量。

读写锁内部维护了两个锁，一个用于读操作，一个用于写操作。所有 ReadWriteLock实现都必须保证 writeLock操作的内存同步效果也要保持与相关 readLock的联系。也就是说，成功获取读锁的线程会看到写入锁之前版本所做的所有更新。

ReentrantReadWriteLock支持以下功能：

- 1、支持公平和非公平的获取锁的方式；
- 2、支持可重入。读线程在获取了读锁后还可以获取读锁；写线程在获取了写锁之后既可以再次获取写锁又可以获取读锁；
- 3、还允许从写入锁降级为读取锁，其实现方式是：先获取写入锁，然后获取读取锁，最后释放写入锁。但是，从读取锁升级到写入锁是不允许的；
- 4、读取锁和写入锁都支持锁获取期间的中断；
- 5、Condition支持。仅写入锁提供了一个 Conditon 实现；读取锁不支持 Conditon ，readLock().newCondition() 会抛出 UnsupportedOperationException。

```java
@Slf4j
public class LockExample3 {

    private final Map<String, Data> map = new TreeMap<>();

    private final ReentrantReadWriteLock lock = new ReentrantReadWriteLock();

    private final Lock readLock = lock.readLock();

    private final Lock writeLock = lock.writeLock();

    public Data get(String key) {
        readLock.lock();
        try {
            return map.get(key);
        } finally {
            readLock.unlock();
        }
    }

    public Set<String> getAllKeys() {
        readLock.lock();
        try {
            return map.keySet();
        } finally {
            readLock.unlock();
        }
    }

    public Data put(String key, Data value) {
        writeLock.lock();
        try {
            return map.put(key, value);
        } finally {
            writeLock.unlock();
        }
    }

    class Data {

    }
}
```

#### StampedLock

`StampedLock`类，在JDK1.8时引入，是对读写锁`ReentrantReadWriteLock`的增强，该类提供了一些功能，优化了读锁、写锁的访问，同时使读写锁之间可以互相转换，更细粒度控制并发。它控制锁有三种模式（写、读、乐观读）。一个`StempedLock`的状态是由版本和模式两个部分组成。锁获取方法返回一个数字作为票据（stamp），他用相应的锁状态表示并控制相关的访问。数字0表示没有写锁被锁写访问，在读锁上分为悲观锁和乐观锁。

**原理分析**

```java
public class LockExample4 {

    class Point {
        private double x, y;
        private final StampedLock sl = new StampedLock();

        void move(double deltaX, double deltaY) { // an exclusively locked method
            long stamp = sl.writeLock();
            try {
                x += deltaX;
                y += deltaY;
            } finally {
                sl.unlockWrite(stamp);
            }
        }

        //下面看看乐观读锁案例
        double distanceFromOrigin() { // A read-only method
            long stamp = sl.tryOptimisticRead(); //获得一个乐观读锁
            double currentX = x, currentY = y;  //将两个字段读入本地局部变量
            if (!sl.validate(stamp)) { //检查发出乐观读锁后同时是否有其他写锁发生？
                stamp = sl.readLock();  //如果没有，我们再次获得一个读悲观锁
                try {
                    currentX = x; // 将两个字段读入本地局部变量
                    currentY = y; // 将两个字段读入本地局部变量
                } finally {
                    sl.unlockRead(stamp);
                }
            }
            return Math.sqrt(currentX * currentX + currentY * currentY);
        }

        //下面是悲观读锁案例
        void moveIfAtOrigin(double newX, double newY) { // upgrade
            // Could instead start with optimistic, not read mode
            long stamp = sl.readLock();
            try {
                while (x == 0.0 && y == 0.0) { //循环，检查当前状态是否符合
                    long ws = sl.tryConvertToWriteLock(stamp); //将读锁转为写锁
                    if (ws != 0L) { //这是确认转为写锁是否成功
                        stamp = ws; //如果成功 替换票据
                        x = newX; //进行状态改变
                        y = newY;  //进行状态改变
                        break;
                    } else { //如果不能成功转换为写锁
                        sl.unlockRead(stamp);  //我们显式释放读锁
                        stamp = sl.writeLock();  //显式直接进行写锁 然后再通过循环再试
                    }
                }
            } finally {
                sl.unlock(stamp); //释放读锁或写锁
            }
        }
    }
}
```

**例子**

```java
public class LockExample5 {

    // 请求总数
    public static int clientTotal = 5000;

    // 同时并发执行的线程数
    public static int threadTotal = 200;

    public static int count = 0;

    private final static StampedLock lock = new StampedLock();

    public static void main(String[] args) throws Exception {
        ExecutorService executorService = Executors.newCachedThreadPool();
        final Semaphore semaphore = new Semaphore(threadTotal);
        final CountDownLatch countDownLatch = new CountDownLatch(clientTotal);
        for (int i = 0; i < clientTotal ; i++) {
            executorService.execute(() -> {
                try {
                    semaphore.acquire();
                    add();
                    semaphore.release();
                } catch (Exception e) {
                    log.error("exception", e);
                }
                countDownLatch.countDown();
            });
        }
        countDownLatch.await();
        executorService.shutdown();
        log.info("count:{}", count);
    }

    private static void add() {
        long stamp = lock.writeLock();
        try {
            count++;
        } finally {
            lock.unlock(stamp);
        }
    }
}
```

#### Condition的使用

Condition可以非常灵活的操作线程的唤醒，下面是一个线程等待与唤醒的例子，其中用1234序号标出了日志输出顺序。

```java
public class LockExample6 {

    public static void main(String[] args) {
        ReentrantLock reentrantLock = new ReentrantLock();
        Condition condition = reentrantLock.newCondition();

        new Thread(() -> {
            try {
                reentrantLock.lock();
                log.info("wait signal"); // 1
                condition.await();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            log.info("get signal"); // 4
            reentrantLock.unlock();
        }).start();

        new Thread(() -> {
            reentrantLock.lock();
            log.info("get lock"); // 2
            try {
                Thread.sleep(3000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            condition.signalAll();
            log.info("send signal ~ "); // 3
            reentrantLock.unlock();
        }).start();
    }
}

```

输出过程讲解：

1、线程1调用了reentrantLock.lock()，线程进入AQS等待队列，输出1号log 
2、接着调用了awiat方法，线程从AQS队列中移除，锁释放，直接加入condition的等待队列中 
3、线程2因为线程1释放了锁，拿到了锁，输出2号log 
4、线程2执行condition.signalAll()发送信号，输出3号log 
5、condition队列中线程1的节点接收到信号，从condition队列中拿出来放入到了AQS的等待队列,这时线程1并没有被唤醒。 
6、线程2调用unlock释放锁，因为AQS队列中只有线程1，因此AQS释放锁按照从头到尾的顺序，唤醒线程1 

7、线程1继续执行，输出4号log，并进行unlock操作。

## 并发扩展组件

### FutureTask

FutureTask是J.U.C中的类，是一个可删除的异步计算类。这个类提供了Future接口的的基本实现，使用相关方法启动和取消计算，查询计算是否完成，并检索计算结果。只有在计算完成时才能使用get方法检索结果;如果计算尚未完成，get方法将会阻塞。一旦计算完成，计算就不能重新启动或取消(除非使用runAndReset方法调用计算)。

#### Runnable与Callable对比

通常实现一个线程我们会使用继承Thread的方式或者实现Runnable接口，这两种方式有一个共同的缺陷就是在执行完任务之后无法获取执行结果。从Java1.5之后就提供了Callable与Future，这两个接口就可以实现获取任务执行结果。

- Runnable接口：代码非常简单，只有一个方法run

  ```java
  public interface RunnableFuture<V> extends Runnable, Future<V> {
      void run();
  }
  ```

- Callable泛型接口：有泛型参数，提供了一个call方法，**执行后可返回传入的泛型参数类型的结果**

  ```java
  public interface Callable<V> {
      V call() throws Exception;
  }
  ```

#### Future接口

Future接口提供了一系列方法用于控制线程执行计算，如下：

```java
public interface Future<V> {
    //取消任务
    boolean cancel(boolean mayInterruptIfRunning);
    //是否被取消
    boolean isCancelled();
    //计算是否完成
    boolean isDone();
    //获取计算结果，在执行过程中任务被阻塞
    V get() throws InterruptedException, ExecutionException;
    //timeout等待时间、unit时间单位
    V get(long timeout, TimeUnit unit)
        throws InterruptedException, ExecutionException, TimeoutException;
}
```

例子：

```java
public class FutureExample {

    static class MyCallable implements Callable<String> {
        @Override
        public String call() throws Exception {
            log.info("do something in callable");
            Thread.sleep(5000);
            return "Done";
        }
    }

    public static void main(String[] args) throws Exception {
        ExecutorService executorService = Executors.newCachedThreadPool();
        Future<String> future = executorService.submit(new MyCallable());//线程池提交任务
        log.info("do something in main");
        Thread.sleep(1000);
        String result = future.get();//获取不到一直阻塞
        log.info("result：{}", result);
    }
}
```

#### `FutureTask`

Future实现了`RunnableFuture`接口，而`RunnableFuture`接口继承了Runnable与Future接口，所以它既可以作为Runnable被线程中执行，又可以作为callable获得返回值。

源码：

```java
public class FutureTask<V> implements RunnableFuture<V> {
    ...
}

public interface RunnableFuture<V> extends Runnable, Future<V> {
    void run();
}
```

`FutureTask`支持两种参数类型，Callable和Runnable，在使用Runnable 时，还可以多指定一个返回结果类型。

例子：

```java
public class FutureTaskExample {

    public static void main(String[] args) throws Exception {
        FutureTask<String> futureTask = new FutureTask<>(new Callable<String>() {
            @Override
            public String call() throws Exception {
                log.info("do something in callable");
                Thread.sleep(5000);
                return "Done";
            }
        });

        new Thread(futureTask).start();
        log.info("do something in main");
        Thread.sleep(1000);
        String result = futureTask.get();
        log.info("result：{}", result);
    }
}
```

**`java8`实现**

```java
@Slf4j
public class FutureTaskExample {

    public static void main(String[] args) throws Exception {
        FutureTask<String> futureTask = new FutureTask<>(() -> {
                log.info("do something in callable");
                Thread.sleep(5000);
                return "Done";
        });

        new Thread(futureTask).start();
        log.info("do something in main");
        Thread.sleep(1000);
        String result = futureTask.get();
        log.info("result：{}", result);
    }
}
```



### `ForkJoin`

`ForkJoin`是`Java7`提供的一个并行执行任务的框架，是把大任务分割成若干个小任务，待小任务完成后将结果汇总成大任务结果的框架。主要采用的是**工作窃取算法**，工作窃取算法是指某个线程从其他队列里窃取任务来执行。

![](..\image\20180502173012969.png)

局限性：

- 任务只能使用fork和join作为同步机制，如果使用了其他同步机制，当他们在同步操作时，工作线程就不能执行其他任务了。比如在fork框架使任务进入了睡眠，那么在睡眠期间内在执行这个任务的线程将不会执行其他任务了。 
- 我们所拆分的任务不应该去执行IO操作，如读和写数据文件。 
- 任务不能抛出检查异常。必须通过必要的代码来处理他们。

框架核心：
核心有两个类：`ForkJoinPool` ，`ForkJoinTask` 

`ForkJoinPool`：负责来做实现，包括工作窃取算法、管理工作线程和提供关于任务的状态以及他们的执行信息。 

`ForkJoinTask`：提供在任务中执行fork和join的机制。

例子：加法运算

```java
@Slf4j
public class ForkJoinTaskExample extends RecursiveTask<Integer> {

    public static final int threshold = 2;//设定不大于两个数相加就直接for循环，不适用框架
    private int start;
    private int end;

    public ForkJoinTaskExample(int start, int end) {
        this.start = start;
        this.end = end;
    }

    @Override
    protected Integer compute() {
        int sum = 0;
        //如果任务足够小就计算任务
        boolean canCompute = (end - start) <= threshold;
        if (canCompute) {
            for (int i = start; i <= end; i++) {
                sum += i;
            }
        } else {
            // 如果任务大于阈值，就分裂成两个子任务计算（分裂算法，可依情况调优）
            int middle = (start + end) / 2;
            ForkJoinTaskExample leftTask = new ForkJoinTaskExample(start, middle);
            ForkJoinTaskExample rightTask = new ForkJoinTaskExample(middle + 1, end);

            // 执行子任务
            leftTask.fork();
            rightTask.fork();

            // 等待任务执行结束合并其结果
            int leftResult = leftTask.join();
            int rightResult = rightTask.join();

            // 合并子任务
            sum = leftResult + rightResult;
        }
        return sum;
    }

    public static void main(String[] args) {
        ForkJoinPool forkjoinPool = new ForkJoinPool();

        //生成一个计算任务，计算1+2+3+4...100
        ForkJoinTaskExample task = new ForkJoinTaskExample(1, 100);

        //执行一个任务
        Future<Integer> result = forkjoinPool.submit(task);

        try {
            log.info("result:{}", result.get());
        } catch (Exception e) {
            log.error("exception", e);
        }
    }
}
```



### `BlockingQueue`（阻塞队列）

主要应用场景：生产者消费者模型，是线程安全的

- 当一个线程试图对一个满的队列进行入队操作时会发生阻塞，除非有另一个线程进行出队列操作
- 当一个线程试图对一个空的队列进行出队操作时会发生阻塞，除非有另一个线程进行入队列操作

`BlockingQueue`提供了四套方法，分别来进行插入、移除、检查。每套方法在不能立刻执行时都有不同的反应

#### 实现类

- `ArrayBlockingQueue`：它是一个有界的阻塞队列，内部实现是数组，初始化时指定容量大小，一旦指定大小就不能再变。采用FIFO方式存储元素。
- `DelayQueue`：阻塞内部元素，内部元素必须实现Delayed接口，Delayed接口又继承了Comparable接口，原因在于`DelayQueue`内部元素需要排序，一般情况按过期时间优先级排序。

- `LinkedBlockingQueue`：大小配置可选，如果初始化时指定了大小，那么它就是有边界的。不指定就无边界（最大整型值）。内部实现是链表，采用FIFO形式保存数据。
- `PriorityBlockingQueue`:带优先级的阻塞队列。无边界队列，允许插入null。插入的对象必须实现Comparator接口，队列优先级的排序规则就是按照我们对Comparable接口的实现来指定的。我们可以从`PriorityBlockingQueue`中获取一个迭代器，但这个迭代器并不保证能按照优先级的顺序进行迭代。
- `SynchronusQueue`：只能插入一个元素，同步队列，无界非缓存队列，不存储元素。

## 线程池

### new Thread的弊端

- 每次new Thread 新建对象，性能差
- 线程缺乏统一管理，可能无限制的新建线程，相互竞争，可能占用过多的系统资源导致死机或者OOM（out of memory 内存溢出），这种问题的原因不是因为单纯的new一个Thread，而是可能因为程序的bug或者设计上的缺陷导致不断new Thread造成的。
- 缺少更多功能，如更多执行、定期执行、线程中断。

### 线程池的好处

- 重用存在的线程，减少对象创建、消亡的开销，性能好
- 可有效控制最大并发线程数，提高系统资源利用率，同时可以避免过多资源竞争，避免阻塞。
- 提供定时执行、定期执行、单线程、并发数控制等功能。

### 相关接口

- `Executor`：运行新任务的简单接口
- `ExecutorService`：扩展了Executor，添加了用来管理执行器生命周期和任务生命周期的方法
- `ScheduleExcutorService`：扩展了`ExecutorService`，支持Future和定期执行任务

### 线程池核心类-`ThreadPoolExecutor`

参数说明：ThreadPoolExecutor一共有七个参数，这七个参数配合起来，构成了线程池强大的功能。

- `corePoolSize`：核心线程数量
- `maximumPoolSize`：线程最大线程数
- `workQueue`：阻塞队列，存储等待执行的任务，很重要，会对线程池运行过程产生重大影响
- keepAliveTime：线程没有任务执行时最多保持多久时间终止（当线程中的线程数量大于corePoolSize的时候，如果这时没有新的任务提交核心线程外的线程不会立即销毁，而是等待，直到超过keepAliveTime）
- `unit：keepAliveTime`的时间单位
- `threadFactory`：线程工厂，用来创建线程，有一个默认的工场来创建线程，这样新创建出来的线程有相同的优先级，是非守护线程、设置好了名称）
- `rejectHandler`：当拒绝处理任务时(阻塞队列满)的策略（`AbortPolicy`默认策略直接抛出异常、`CallerRunsPolicy`用调用者所在的线程执行任务、`DiscardOldestPolicy`丢弃队列中最靠前的任务并执行当前任务、`DiscardPolicy`直接丢弃当前任务） 

> 当我们提交一个新的任务到线程池，线程池会根据当前池中正在运行的线程数量来决定该任务的处理方式。处理方式有三种： 
> 1、直接切换（`SynchronusQueue`） 
> 2、无界队列（`LinkedBlockingQueue`）能够创建的最大线程数为`corePoolSize`,这时`maximumPoolSize`就不会起作用了。当线程池中所有的核心线程都是运行状态的时候，新的任务提交就会放入等待队列中。 
> 3、有界队列（`ArrayBlockingQueue`）最大`maximumPoolSize`，能够降低资源消耗，但是这种方式使得线程池对线程调度变的更困难。因为线程池与队列容量都是有限的。所以想让线程池的吞吐率和处理任务达到一个合理的范围，又想使我们的线程调度相对简单，并且还尽可能降低资源的消耗，我们就需要合理的限制这两个数量 
>
> 分配技巧： [如果想降低资源的消耗包括降低`cpu`使用率、操作系统资源的消耗、上下文切换的开销等等，可以设置一个较大的队列容量和较小的线程池容量，这样会降低线程池的吞吐量。如果我们提交的任务经常发生阻塞，我们可以调整`maximumPoolSize`。如果我们的队列容量较小，我们需要把线程池大小设置的大一些，这样`cpu`的使用率相对来说会高一些。但是如果线程池的容量设置的过大，提高任务的数量过多的时候，并发量会增加，那么线程之间的调度就是一个需要考虑的问题。这样反而可能会降低处理任务的吞吐量。

其他方法：

| 序号 | 方法名                   | 描述                                      |
| ---- | ------------------------ | ----------------------------------------- |
| 1    | `execute()`              | 提交任务，交给线程池执行                  |
| 2    | `submit()`               | 提交任务，能够返回执行结果 execute+Future |
| 3    | `shutdown()`             | 关闭线程池，等待任务都执行完              |
| 4    | `shutdownNow()`          | 关闭线程池，不等待任务执行完              |
| 5    | `getTaskCount()`         | 线程池已执行和未执行的任务总数            |
| 6    | `getCompleteTaskCount()` | 已完成的任务数量                          |
| 7    | `getPoolSize()`          | 线程池当前的线程数量                      |
| 8    | `getActiveCount()`       | 当前线程池中正在执行任务的线程数量        |

### 线程池生命周期

![](..\image\201805051421294.png)

- running：能接受新提交的任务，也能处理阻塞队列中的任务
- shutdown：不能处理新的任务，但是能继续处理阻塞队列中任务
- stop：不能接收新的任务，也不处理队列中的任务
- tidying：如果所有的任务都已经终止了，这时有效线程数为0
- terminated：最终状态

### 使用Executor创建线程池

#### `Executors.newCachedThreadPool`

`CachedThreadPool` 是通过 `java.util.concurrent.Executors` 创建的 `ThreadPoolExecutor` 实例。这个实例会根据需要，在线程可用时，重用之前构造好的池中线程。这个线程池在执行 **大量短生命周期的异步任务时（many short-lived asynchronous task）**，可以显著提高程序性能。调用 **execute** 时，可以重用之前已构造的可用线程，如果不存在可用线程，那么会重新创建一个新的线程并将其加入到线程池中。如果线程超过 60 秒还未被使用，就会被中止并从缓存中移除。因此，线程池在长时间空闲后不会消耗任何资源。

例子：

```java
public static void main(String[] args) {
    ExecutorService executorService = Executors.newCachedThreadPool();
        for (int i = 0; i < 10; i++) {
            final int index = i;
            executorService.execute(() -> log.info("task:{}", index));
        }
    executorService.shutdown();
}
```

> 值得注意的一点是，`newCachedThreadPool`的返回值是`ExecutorService`类型，该类型只包含基础的线程池方法，但却不包含线程监控相关方法，因此在使用返回值为`ExecutorService`的线程池类型创建新线程时要考虑到具体情况。

#### `Executors.newFixedThreadPool` 

`FixedThreadPool` 是通过 `java.util.concurrent.Executors` `创建的` `ThreadPoolExecutor` 实例。这个实例会复用 **固定数量的线程** 处理一个 **共享的无边界队列** 。任何时间点，最多有 `nThreads` 个线程会处于活动状态执行任务。如果当所有线程都是活动时，有多的任务被提交过来，那么它会一致在队列中等待直到有线程可用。如果任何线程在执行过程中因为错误而中止，新的线程会替代它的位置来执行后续的任务。所有线程都会一致存于线程池中，直到显式的执行 `ExecutorService.shutdown()` 关闭。

例子：

```java
public static void main(String[] args) {
    ExecutorService executorService = Executors.newFixedThreadPool(3);
    for (int i = 0; i < 10; i++) {
        final int index = i;
        executorService.execute(new Runnable() {
            @Override
            public void run() {
                log.info("task:{}", index);
            }
        });
    }
    executorService.shutdown();
}
```

#### `Executors.newSingleThreadPool`

`SingleThreadPool` 是通过 `java.util.concurrent.Executors` 创建的 `ThreadPoolExecutor` 实例。这个实例只会使用单个工作线程来执行一个无边界的队列。（注意，如果单个线程在执行过程中因为某些错误中止，新的线程会替代它执行后续线程）。它可以保证认为是按顺序执行的，任何时候都不会有多于一个的任务处于活动状态。和 **`newFixedThreadPool`** 的区别在于，如果线程遇到错误中止，它是无法使用替代线程的。

例子：

```java
public static void main(String[] args) {
    ExecutorService executorService = Executors.newSingleThreadExecutor();
    for (int i = 0; i < 10; i++) {
        final int index = i;
        executorService.execute(new Runnable() {
            @Override
            public void run() {
                log.info("task:{}", index);
            }
        });
    }
    executorService.shutdown();
}
```

#### `Executors.newScheduledThreadPool` 

创建一个定长线程池,支持定时及周期性任务执行

延迟执行

```java
public static void main(String[] args) {
    ScheduledExecutorService executorService = Executors.newScheduledThreadPool(1);
    executorService.schedule(new Runnable() {
        @Override
        public void run() {
            log.warn("schedule run");
        }
    }, 3, TimeUnit.SECONDS);//延迟3秒执行
    executorService.shutdown();
}
```

周期执行

```java
executorService.scheduleAtFixedRate(new Runnable() {
    @Override
    public void run() {
        log.warn("schedule run");
    }
}, 1, 3, TimeUnit.SECONDS);//延迟一秒后每隔3秒执行
```

#### Timer类

```java
Timer timer = new Timer();
timer.schedule(new TimerTask() {
    @Override
    public void run() {
        log.warn("timer run");
    }
}, new Date(), 5 * 1000);//立即执行，并每隔5秒执行一次
```

## 线程死锁

### 什么是死锁？

通俗的说，死锁就是两个或者多个线程，相互占用对方需要的资源，而都不进行释放，导致彼此之间都相互等待对方释放资源，产生了无限制等待的现象。死锁一旦发生，如果没有外力介入，这种等待将永远存在，从而对程序产生严重影响。

用来描述死锁的问题最有名的场景就是“哲学家就餐问题”。哲学家就餐问题可以这样表述：假设有五位哲学家围坐在一张圆形餐桌旁，做以下两件事之一：吃饭或者思考。吃东西的时候他们就停止思考，思考的时候也停止吃东西。餐桌中间有一大碗意大利面，每两个哲学家之间有一只餐叉。因为只用一只餐叉很难吃到意大利面，所以假设哲学家必须用两只餐叉吃东西。他们只能使用自己左右手边的那两只餐。哲学家从来不交谈，这就跟危险，可能产生死锁，每个哲学家都拿着左手的餐叉永远等右边的餐叉（或者相反）….

### 死锁产生的必要条件

- 互斥条件：进程对锁分配的资源进行排他性使用
- 请求和保持条件：线程已经保持了一个资源，但是又提出了其他请求，而该资源已被其他线程占用
- 不剥夺条件：在使用时不能被剥夺，只能自己用完释放
- 环路等待条件：资源调用是一个环形的链

示例：

```java
@Slf4j
public class DeadLock implements Runnable {
    public int flag = 1;
    //静态对象是类的所有对象共享的
    private static Object o1 = new Object(), o2 = new Object();

    @Override
    public void run() {
        log.info("flag:{}", flag);
        if (flag == 1) {
            synchronized (o1) {
                try {
                    Thread.sleep(500);
                } catch (Exception e) {
                    e.printStackTrace();
                }
                synchronized (o2) {
                    log.info("1");
                }
            }
        }
        if (flag == 0) {
            synchronized (o2) {
                try {
                    Thread.sleep(500);
                } catch (Exception e) {
                    e.printStackTrace();
                }
                synchronized (o1) {
                    log.info("0");
                }
            }
        }
    }

    public static void main(String[] args) {
        DeadLock td1 = new DeadLock();
        DeadLock td2 = new DeadLock();
        td1.flag = 1;
        td2.flag = 0;
        //td1,td2都处于可执行状态，但JVM线程调度先执行哪个线程是不确定的。
        //td2的run()可能在td1的run()之前运行
        new Thread(td1).start();
        new Thread(td2).start();
    }
}
```

上述代码出现死锁原因：

当`DeadLock`类的对象flag==1时（`td1`），先锁定`o1`,睡眠500毫秒；

而`td1`在睡眠的时候另一个flag==0的对象（`td2`）线程启动，先锁定`o2`,睡眠500毫秒；

`td1`睡眠结束后需要锁定`o2`才能继续执行，而此时`o2`已被`td2`锁定；

`td2`睡眠结束后需要锁定`o1`才能继续执行，而此时`o1`已被`td1`锁定；

`td1`、`td2`相互等待，都需要得到对方锁定的资源才能继续执行，从而死锁。

### 避免死锁

- 注意加锁顺序（这个很好理解，就像上边的例子）

- 加锁时限（超过时限放弃加锁） 

- 实现方式–使用重入锁。

- 死锁检测（较难，就像分析上边的线程情况）

## 多线程并发最佳实践

- 使用本地变量（局部变量）

- 使用不可变量

- 最小化锁的作用域范围：s=1/(1-a=a/n)

- 使用线程池的Executor，而不是直接new Thread执行
- 宁可使用同步也不要使用线程的wait和notify
- 使用`BlockingQueue`实现生产-消费模式
- 使用并发集合而不是加了锁的同步集合
- 使用Semaphore创建有界的访问
- 宁可使用同步代码块，也不使用同步的方法
- 避免使用静态变量

## Spring与线程安全

- Spring been：singleton（单例：线程安全）、prototype（多例：线程不安全）

- 无状态对象的设定保证了线程安全

## `HashMap`与`ConcurrentHashMap`

## 多线程并发与线程安全总结

![](..\image\2.jpg)

## 高并发处理思路与手段

### 扩容

垂直扩容（纵向扩展）：提高系统部件能力

水平扩容（横向扩展）：增加更多系统成员来实现

#### 扩容-数据库

读操作扩展：memcache、redis、CDN等缓存

写操作扩展：Cassandra、Hbase等

### 缓存

![](..\image\19.jpg)

#### 缓存特征

- 命中率：命中数/(命中数 + 没有命中数)

- 最大元素（空间）

- 清空策略

  1. FIFO（First In First out）：先见先出，淘汰最先近来的页面，新进来的页面最迟被淘汰，完全符合队列。

  2. LRU（Least recently used）:最近最少使用，淘汰最近不使用的页面

  3. LFU（Least frequently used）: 最近使用次数最少， 淘汰使用次数最少的页面

  4. 过期时间，随机等

#### 缓存命中率影响因素

- 业务场景和业务需求

- 缓存的设计（粒度和策略）

- 缓存容量和基础设施

#### 缓存分类和应用场景

- 本地缓存：编程实现（成员变量，局部变量，静态变量）、Guava Cache
- 分布式缓存：Memcache、Redis

#### 高并发场景缓存常见问题

- 缓存一致性

当数据时效性要求很高时，需要保证缓存中的数据与数据库中的保持一致，而且需要保证缓存节点和副本中的数据也保持一致，不能出现差异现象。这就比较依赖缓存的过期和更新策略。一般会在数据发生更改的时，主动更新缓存中的数据或者移除对应的缓存。

![](..\image\24.png)

- 缓存并发问题

缓存过期后将尝试从后端数据库获取数据，这是一个看似合理的流程。但是，在高并发场景下，有可能多个请求并发的去从数据库获取数据，对后端数据库造成极大的冲击，甚至导致 “雪崩”现象。此外，当某个缓存key在被更新时，同时也可能被大量请求在获取，这也会导致一致性的问题。那如何避免类似问题呢？我们会想到类似“锁”的机制，在缓存更新或者过期的情况下，先尝试获取到锁，当更新或者从数据库获取完成后再释放锁，其他的请求只需要牺牲一定的等待时间，即可直接从缓存中继续获取数据。

![](..\image\25.png)

- 缓存穿透问题

  缓存穿透在有些地方也称为“击穿”。很多朋友对缓存穿透的理解是：由于缓存故障或者缓存过期导致大量请求穿透到后端数据库服务器，从而对数据库造成巨大冲击。

  这其实是一种误解。真正的缓存穿透应该是这样的：

  在高并发场景下，如果某一个key被高并发访问，没有被命中，出于对容错性考虑，会尝试去从后端数据库中获取，从而导致了大量请求达到数据库，而当该key对应的数据本身就是空的情况下，这就导致数据库中并发的去执行了很多不必要的查询操作，从而导致巨大冲击和压力。

  可以通过下面的几种常用方式来避免缓存传统问题：

  1. 缓存空对象

  对查询结果为空的对象也进行缓存，如果是集合，可以缓存一个空的集合（非null），如果是缓存单个对象，可以通过字段标识来区分。这样避免请求穿透到后端数据库。同时，也需要保证缓存数据的时效性。这种方式实现起来成本较低，比较适合命中不高，但可能被频繁更新的数据。

  1. 单独过滤处理

  对所有可能对应数据为空的key进行统一的存放，并在请求前做拦截，这样避免请求穿透到后端数据库。这种方式实现起来相对复杂，比较适合命中不高，但是更新不频繁的数据。

  ![](..\image\26.png)

- 缓存的雪崩现象

  缓存雪崩就是指由于缓存的原因，导致大量请求到达后端数据库，从而导致数据库崩溃，整个系统崩溃，发生灾难。导致这种现象的原因有很多种，上面提到的“缓存并发”，“缓存穿透”，“缓存颠簸”等问题，其实都可能会导致缓存雪崩现象发生。这些问题也可能会被恶意攻击者所利用。还有一种情况，例如某个时间点内，系统预加载的缓存周期性集中失效了，也可能会导致雪崩。为了避免这种周期性失效，可以通过设置不同的过期时间，来错开缓存过期，从而避免缓存集中失效。

  从应用架构角度，我们可以通过限流、降级、熔断等手段来降低影响，也可以通过多级缓存来避免这种灾难。

  此外，从整个研发体系流程的角度，应该加强压力测试，尽量模拟真实场景，尽早的暴露问题从而防范。

  ![](..\image\27.png)

## 高并发之消息队列

## 高并发之应用拆分

## 高并发之应用限流

## 高并发之服务降级与服务熔断

## 高并发之数据库分库分表

### 数据库瓶颈

- 单个库数据量太大（1T到2T）：多个库
- 单个数据库服务器压力过大、读写瓶颈：多个库
- 单个表数据量过大：分表

## 高可用的一些手段

- 任务调度系统分布式：elastic-job + zookeeper
- 主备切换：apache curator + zookeeper 分布式锁实现
- 监控报警机制

