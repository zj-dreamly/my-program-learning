package com.atguigu.juc.interrupt;

import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.LockSupport;
import java.util.concurrent.locks.ReentrantLock;

/**
 * @auther zzyy
 * @create 2020-07-10 14:05
 */
public class LockSupportDemo
{
    static Object objectLock = new Object();

    static Lock lock = new ReentrantLock();
    static Condition condition = lock.newCondition();

    public static void main(String[] args)
    {
        Thread t1 = new Thread(() -> {
            System.out.println(Thread.currentThread().getName() + "\t" + "---come in");
            LockSupport.park();
            LockSupport.park();
            System.out.println(Thread.currentThread().getName() + "\t" + "---被唤醒");
        }, "t1");
        t1.start();

        new Thread(() -> {
            LockSupport.unpark(t1);
            try { TimeUnit.SECONDS.sleep(3); } catch (InterruptedException e) { e.printStackTrace(); }
            LockSupport.unpark(t1);
            System.out.println(Thread.currentThread().getName()+"\t"+"---发出通知");
        },"t2").start();
    }



    public static void lockAwaitSignal()
    {
        new Thread(() -> {
            //暂停几秒钟线程
            try { TimeUnit.SECONDS.sleep(3); } catch (InterruptedException e) { e.printStackTrace(); }
            lock.lock();
            try
            {
                System.out.println(Thread.currentThread().getName()+"\t"+"---come in");
                condition.await();
                System.out.println(Thread.currentThread().getName()+"\t"+"---被唤醒");
            } catch (InterruptedException e) {
                e.printStackTrace();
            } finally {
                lock.unlock();
            }
        },"t1").start();

        new Thread(() -> {
            lock.lock();
            try
            {
                condition.signal();
                System.out.println(Thread.currentThread().getName()+"\t"+"---发出通知");
            }finally {
                lock.unlock();
            }
        },"t2").start();
    }

    public static void syncWaitNotify()
    {
        new Thread(() -> {
            //暂停几秒钟线程
            try { TimeUnit.SECONDS.sleep(3); } catch (InterruptedException e) { e.printStackTrace(); }
            synchronized (objectLock){
                System.out.println(Thread.currentThread().getName()+"\t"+"---come in");
                try {
                    objectLock.wait();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                System.out.println(Thread.currentThread().getName()+"\t"+"---被唤醒");
            }
        },"t1").start();

        //暂停几秒钟线程
        try { TimeUnit.SECONDS.sleep(1); } catch (InterruptedException e) { e.printStackTrace(); }

        new Thread(() -> {
            synchronized (objectLock){
                objectLock.notify();
                System.out.println(Thread.currentThread().getName()+"\t"+"---发出通知");
            }
        },"t2").start();
    }
}