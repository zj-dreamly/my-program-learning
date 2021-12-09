package com.atguigu.juc.bashthread;

import java.util.concurrent.TimeUnit;

/**
 * @auther zzyy
 * @create 2021-03-08 15:12
 *
 * 演示守护线程和用户线程
 */
public class DaemonDemo
{
    public static void main(String[] args)
    {
        Thread a = new Thread(() -> {
            System.out.println(Thread.currentThread().getName()+" come in：\t"
                    +(Thread.currentThread().isDaemon() ? "守护线程":"用户线程"));
            while (true)
            {

            }
        }, "a");
        a.setDaemon(true);
        a.start();

        //暂停几秒钟线程
        try { TimeUnit.SECONDS.sleep(2); } catch (InterruptedException e) { e.printStackTrace(); }

        System.out.println(Thread.currentThread().getName()+"\t"+" ----task is over");
    }
}
