package com.atguigu.juc.atomics;

import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicReferenceFieldUpdater;

class MyVar
{
    public volatile Boolean isInit = Boolean.FALSE;
    AtomicReferenceFieldUpdater<MyVar,Boolean> FieldUpdater = AtomicReferenceFieldUpdater.newUpdater(MyVar.class,Boolean.class,"isInit");

    public void init(MyVar myVar)
    {
        if(FieldUpdater.compareAndSet(myVar,Boolean.FALSE,Boolean.TRUE))
        {
            System.out.println(Thread.currentThread().getName()+"\t"+"---start init");
            try { TimeUnit.SECONDS.sleep(3); } catch (InterruptedException e) { e.printStackTrace(); }
            System.out.println(Thread.currentThread().getName()+"\t"+"---end init");
        }else{
            System.out.println(Thread.currentThread().getName()+"\t"+"---抢夺失败，已经有线程在修改中");
        }
    }

}


/**
 * @auther zzyy
 * @create 2021-03-22 15:20
 *  多线程并发调用一个类的初始化方法，如果未被初始化过，将执行初始化工作，要求只能初始化一次
 */
public class AtomicReferenceFieldUpdaterDemo
{
    public static void main(String[] args)
    {
        MyVar myVar = new MyVar();

        for (int i = 1; i <=5; i++) {
            new Thread(() -> {
                myVar.init(myVar);
            },String.valueOf(i)).start();
        }
    }
}
