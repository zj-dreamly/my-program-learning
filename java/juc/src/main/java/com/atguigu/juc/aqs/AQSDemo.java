package com.atguigu.juc.aqs;

import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.ReentrantLock;

/**
 * @auther zzyy
 * @create 2021-03-27 15:46
 */
public class AQSDemo
{
    public static void main(String[] args)
    {
        ReentrantLock reentrantLock = new ReentrantLock();//非公平锁

        // A B C三个顾客，去银行办理业务，A先到，此时窗口空无一人，他优先获得办理窗口的机会，办理业务。
        // A 耗时严重，估计长期占有窗口
        new Thread(() -> {
            reentrantLock.lock();
            try
            {
                System.out.println("----come in A");
                //暂停50分钟线程
                try { TimeUnit.MILLISECONDS.sleep(50); } catch (InterruptedException e) { e.printStackTrace(); }
            }finally {
                reentrantLock.unlock();
            }
        },"A").start();

        //B是第2个，B一看到受理窗口被A占用，只能去候客区等待，进入AQS队列，等待着A办理完成，尝试去抢占受理窗口。
        new Thread(() -> {
            reentrantLock.lock();
            try
            {
                System.out.println("----come in B");
            }finally {
                reentrantLock.unlock();
            }
        },"B").start();


        //C是第3个，C一看到受理窗口被A占用，只能去候客区等待，进入AQS队列，等待着A办理完成，尝试去抢占受理窗口,前面是B顾客，FIFO
        new Thread(() -> {
            reentrantLock.lock();
            try
            {
                System.out.println("----come in C");
            }finally {
                reentrantLock.unlock();
            }
        },"C").start();

    }
}
