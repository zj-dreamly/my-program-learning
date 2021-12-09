package com.atguigu.juc.atomics;

import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicMarkableReference;

/**
 * @auther zzyy
 * @create 2021-03-22 14:14
 */
public class AtomicMarkableReferenceDemo
{
    static AtomicMarkableReference atomicMarkableReference = new AtomicMarkableReference(100,false);

    public static void main(String[] args)
    {
        new Thread(() -> {
            boolean marked = atomicMarkableReference.isMarked();
            System.out.println(Thread.currentThread().getName()+"\t"+"---默认修改标识："+marked);
            try { TimeUnit.SECONDS.sleep(1); } catch (InterruptedException e) { e.printStackTrace(); }
            atomicMarkableReference.compareAndSet(100,101,marked,!marked);
        },"t1").start();

        new Thread(() -> {
            boolean marked = atomicMarkableReference.isMarked();
            System.out.println(Thread.currentThread().getName()+"\t"+"---默认修改标识："+marked);
            try { TimeUnit.SECONDS.sleep(2); } catch (InterruptedException e) { e.printStackTrace(); }
            boolean b = atomicMarkableReference.compareAndSet(100, 20210308, marked, !marked);

            System.out.println(Thread.currentThread().getName()+"\t"+"---操作是否成功:"+b);
            System.out.println(Thread.currentThread().getName()+"\t"+atomicMarkableReference.getReference());
            System.out.println(Thread.currentThread().getName()+"\t"+atomicMarkableReference.isMarked());

        },"t2").start();
    }
}
