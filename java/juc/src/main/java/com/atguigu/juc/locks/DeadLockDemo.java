package com.atguigu.juc.locks;

import java.util.concurrent.TimeUnit;

/**
 * @auther zzyy
 * @create 2020-07-09 18:43
 */
public class DeadLockDemo
{
    static Object lockA = new Object();
    static Object lockB = new Object();


    public static void main(String[] args)
    {

        Thread a = new Thread(() -> {
            synchronized (lockA) {
                System.out.println(Thread.currentThread().getName() + "\t" + " 自己持有A锁，期待获得B锁");

                try {
                    TimeUnit.SECONDS.sleep(1);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }

                synchronized (lockB) {
                    System.out.println(Thread.currentThread().getName() + "\t 获得B锁成功");
                }
            }
        }, "a");
        a.start();

        new Thread(() -> {
            synchronized (lockB)
            {
                System.out.println(Thread.currentThread().getName()+"\t"+" 自己持有B锁，期待获得A锁");

                try { TimeUnit.SECONDS.sleep(1); } catch (InterruptedException e) { e.printStackTrace(); }

                synchronized (lockA)
                {
                    System.out.println(Thread.currentThread().getName()+"\t 获得A锁成功");
                }
            }
        },"b").start();


    }
}
