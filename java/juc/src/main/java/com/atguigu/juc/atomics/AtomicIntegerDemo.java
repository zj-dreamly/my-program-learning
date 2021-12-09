package com.atguigu.juc.atomics;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.atomic.AtomicInteger;

class MyNumber
{
    AtomicInteger atomicInteger = new AtomicInteger();

    public void addPlusPlus()
    {
        atomicInteger.incrementAndGet();
    }
}

/**
 * @auther zzyy
 * @create 2021-03-17 16:26
 */
public class AtomicIntegerDemo
{
    public static final int SIEZ_ = 50;

    public static void main(String[] args) throws InterruptedException
    {

        MyNumber myNumber = new MyNumber();
        CountDownLatch countDownLatch = new CountDownLatch(SIEZ_);

        for (int i = 1; i <=SIEZ_; i++) {
            new Thread(() -> {
                try
                {
                    for (int j = 1 ;j <=1000; j++) {
                        myNumber.addPlusPlus();
                    }
                }catch (Exception e){
                    e.printStackTrace();
                }finally {
                    countDownLatch.countDown();
                }
            },String.valueOf(i)).start();
        }

        //try { TimeUnit.SECONDS.sleep(1); } catch (InterruptedException e) { e.printStackTrace(); }

        countDownLatch.await();

        System.out.println(Thread.currentThread().getName()+"\t"+"---result : "+myNumber.atomicInteger.get());


    }
}

