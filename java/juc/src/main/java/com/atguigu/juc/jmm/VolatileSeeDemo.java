package com.atguigu.juc.jmm;

/**
 * @auther zzyy
 * @create 2021-03-15 19:13
 */
public class VolatileSeeDemo
{
    //static          boolean flag = true;       //不加volatile，没有可见性
    volatile boolean flag = true;       //加了volatile，保证可见性

    /*public static void main(String[] args)
    {
        new Thread(() -> {
            System.out.println(Thread.currentThread().getName()+"\t"+"---come in");
            while(flag)
            {
                new Integer(308);
            }
            System.out.println("t1 over");
        },"t1").start();

        try { TimeUnit.SECONDS.sleep(1); } catch (InterruptedException e) { e.printStackTrace(); }

        new Thread(() -> {
            flag = false;
        },"t2").start();
    }*/
}
