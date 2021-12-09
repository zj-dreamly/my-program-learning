package com.atguigu.juc.cas;

import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicReference;

/**
 * @auther zzyy
 * @create 2021-03-17 15:21
 * 题目：实现一个自旋锁
 * 自旋锁好处：循环比较获取没有类似wait的阻塞。
 *
 * 通过CAS操作完成自旋锁，A线程先进来调用myLock方法自己持有锁5秒钟，B随后进来后发现
 * 当前有线程持有锁，不是null，所以只能通过自旋等待，直到A释放锁后B随后抢到。
 */
public class SpinLockDemo
{
    AtomicReference<Thread> atomicReference = new AtomicReference<>();

    public void MyLock()
    {
        System.out.println(Thread.currentThread().getName()+"\t"+"---come in");
        while(!atomicReference.compareAndSet(null,Thread.currentThread()))
        {

        }
        System.out.println(Thread.currentThread().getName()+"\t"+"---持有锁成功");
    }

    public void MyUnLock()
    {
        atomicReference.compareAndSet(Thread.currentThread(),null);
        System.out.println(Thread.currentThread().getName()+"\t"+"---释放锁成功");
    }

    public static void main(String[] args)
    {
        SpinLockDemo spinLockDemo = new SpinLockDemo();

        new Thread(() -> {
            spinLockDemo.MyLock();
            try { TimeUnit.SECONDS.sleep(5); } catch (InterruptedException e) { e.printStackTrace(); }
            spinLockDemo.MyUnLock();
        },"t1").start();

        new Thread(() -> {
            spinLockDemo.MyLock();
            spinLockDemo.MyUnLock();
        },"t2").start();
    }
}
