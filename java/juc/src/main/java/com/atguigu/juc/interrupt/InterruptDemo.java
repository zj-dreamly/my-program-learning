package com.atguigu.juc.interrupt;

import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicBoolean;

/**
 * @auther zzyy
 * @create 2021-03-03 18:20
 */
public class InterruptDemo
{
    static volatile boolean isStop = false;
    static AtomicBoolean atomicBoolean = new AtomicBoolean(false);

    public static void main(String[] args)
    {
        System.out.println(Thread.currentThread().getName()+"---"+Thread.interrupted());
        System.out.println(Thread.currentThread().getName()+"---"+Thread.interrupted());
        System.out.println("111111");
        Thread.currentThread().interrupt();///----false---> true
        System.out.println("222222");
        System.out.println(Thread.currentThread().getName()+"---"+Thread.interrupted());
        System.out.println(Thread.currentThread().getName()+"---"+Thread.interrupted());
    }

    public static void m5()
    {
        Thread t1 = new Thread(() -> {
            while (true) {
                if (Thread.currentThread().isInterrupted()) {
                    System.out.println("-----isInterrupted() = true，程序结束。");
                    break;
                }
                try {
                    Thread.sleep(500);
                } catch (InterruptedException e) {
                    Thread.currentThread().interrupt();//???????  //线程的中断标志位为false,无法停下，需要再次掉interrupt()设置true
                    e.printStackTrace();
                }
                System.out.println("------hello Interrupt");
            }
        }, "t1");
        t1.start();

        try { TimeUnit.SECONDS.sleep(3); } catch (InterruptedException e) { e.printStackTrace(); }

        new Thread(() -> {
            t1.interrupt();//修改t1线程的中断标志位为true
        },"t2").start();
    }

    /**
     *中断为true后，并不是立刻stop程序
     */
    public static void m4()
    {
        //中断为true后，并不是立刻stop程序
        Thread t1 = new Thread(() -> {
            for (int i = 1; i <= 300; i++) {
                System.out.println("------i: " + i);
            }
            System.out.println("t1.interrupt()调用之后02： "+Thread.currentThread().isInterrupted());
        }, "t1");
        t1.start();

        System.out.println("t1.interrupt()调用之前,t1线程的中断标识默认值： "+t1.isInterrupted());
        try { TimeUnit.MILLISECONDS.sleep(3); } catch (InterruptedException e) { e.printStackTrace(); }
        //实例方法interrupt()仅仅是设置线程的中断状态位设置为true，不会停止线程
        t1.interrupt();
        //活动状态,t1线程还在执行中
        System.out.println("t1.interrupt()调用之后01： "+t1.isInterrupted());

        try { TimeUnit.MILLISECONDS.sleep(3000); } catch (InterruptedException e) { e.printStackTrace(); }
        //非活动状态,t1线程不在执行中，已经结束执行了。
        System.out.println("t1.interrupt()调用之后03： "+t1.isInterrupted());
    }

    public static void m3()
    {
        Thread t1 = new Thread(() -> {
            while (true) {
                if (Thread.currentThread().isInterrupted()) {
                    System.out.println("-----isInterrupted() = true，程序结束。");
                    break;
                }
                System.out.println("------hello Interrupt");
            }
        }, "t1");
        t1.start();

        try { TimeUnit.SECONDS.sleep(1); } catch (InterruptedException e) { e.printStackTrace(); }

        new Thread(() -> {
            t1.interrupt();//修改t1线程的中断标志位为true
        },"t2").start();
    }

    /**
     * 通过AtomicBoolean
     */
    public static void m2()
    {
        new Thread(() -> {
            while(true)
            {
                if(atomicBoolean.get())
                {
                    System.out.println("-----atomicBoolean.get() = true，程序结束。");
                    break;
                }
                System.out.println("------hello atomicBoolean");
            }
        },"t1").start();

        //暂停几秒钟线程
        try { TimeUnit.SECONDS.sleep(1); } catch (InterruptedException e) { e.printStackTrace(); }

        new Thread(() -> {
            atomicBoolean.set(true);
        },"t2").start();
    }

    /**
     * 通过一个volatile变量实现
     */
    public static void m1()
    {
        new Thread(() -> {
            while(true)
            {
                if(isStop)
                {
                    System.out.println("-----isStop = true，程序结束。");
                    break;
                }
                System.out.println("------hello isStop");
            }
        },"t1").start();

        //暂停几秒钟线程
        try { TimeUnit.SECONDS.sleep(1); } catch (InterruptedException e) { e.printStackTrace(); }

        new Thread(() -> {
            isStop = true;
        },"t2").start();
    }
}
