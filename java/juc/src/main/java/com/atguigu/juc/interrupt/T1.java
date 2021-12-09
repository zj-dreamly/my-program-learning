package com.atguigu.juc.interrupt;


import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicBoolean;


class MyNumber
{
    boolean flag = true;
}
/**
 * @auther zzyy
 * @create 2021-03-03 18:25
 */
public class T1
{
    static volatile boolean isStop = false;
    static AtomicBoolean atomicBoolean = new AtomicBoolean(false);

    public static void main(String[] args)
    {
        MyNumber myNumber = new MyNumber();

        new Thread(() -> {
            System.out.println("----come in");
            while(myNumber.flag)
            {
                new Integer(5);
            }
            System.out.println("-----t1 process is over");
        },"t1").start();

        //暂停几秒钟线程
        try { TimeUnit.SECONDS.sleep(2); } catch (InterruptedException e) { e.printStackTrace(); }

        new Thread(() -> {
            myNumber.flag = false;
        },"t2").start();

        //暂停几秒钟线程
        try { TimeUnit.SECONDS.sleep(1); } catch (InterruptedException e) { e.printStackTrace(); }

        System.out.println(Thread.currentThread().getName()+"\t"+myNumber.flag);

    }

    public static void m3()
    {
        Thread t1 = new Thread(() -> {
            while (true) {
                if (Thread.currentThread().isInterrupted()) {
                    System.out.println(Thread.currentThread().getName() + "线程------isInterrupted() = true,自己退出了");
                    break;
                }
                System.out.println("-------hello interrupt3");
            }
        }, "t1");
        t1.start();

        //暂停几秒钟线程
        try { TimeUnit.SECONDS.sleep(1); } catch (InterruptedException e) { e.printStackTrace(); }
        t1.interrupt();
    }

    public static void m2()
    {
        new Thread(() -> {
            while(true)
            {
                if(atomicBoolean.get())
                {
                    System.out.println(Thread.currentThread().getName()+"线程------atomicBoolean.get() = true,自己退出了");
                    break;
                }
                //暂停毫秒
                try { TimeUnit.MILLISECONDS.sleep(300); } catch (InterruptedException e) { e.printStackTrace(); }
                System.out.println("-------hello interrupt2");
            }
        },"t1").start();

        //暂停几秒钟线程
        try { TimeUnit.SECONDS.sleep(1); } catch (InterruptedException e) { e.printStackTrace(); }
        atomicBoolean.set(true);
    }

    public static void m1()
    {
        new Thread(() -> {
            while(true)
            {
                if(isStop)
                {
                    System.out.println(Thread.currentThread().getName()+"线程------isStop = true,自己退出了");
                    break;
                }
                try { TimeUnit.MILLISECONDS.sleep(300); } catch (InterruptedException e) { e.printStackTrace(); }
                System.out.println("-------hello interrupt");
            }
        },"t1").start();

        //暂停几秒钟线程
        try { TimeUnit.SECONDS.sleep(1); } catch (InterruptedException e) { e.printStackTrace(); }
        isStop = true;
    }
}