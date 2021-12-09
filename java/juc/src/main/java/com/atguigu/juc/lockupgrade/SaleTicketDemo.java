package com.atguigu.juc.lockupgrade;

class TrainTicket
{
    private int number = 50;

    Object objectLock = new Object();

    public void sale()
    {
        synchronized (objectLock)
        {
            if(number>0)
            {
                System.out.println(Thread.currentThread().getName()+"\t"+"---卖出第： "+(number--));
            }
        }
    }
}


/**
 * @auther zzyy
 * @create 2021-03-27 11:27
 */
public class SaleTicketDemo
{
    public static void main(String[] args)
    {
        TrainTicket trainTicket = new TrainTicket();

        new Thread(() -> { for (int i = 1; i <=55 ; i++) trainTicket.sale(); },"t1").start();
        new Thread(() -> { for (int i = 1; i <=55 ; i++) trainTicket.sale(); },"t2").start();
        new Thread(() -> { for (int i = 1; i <=55 ; i++) trainTicket.sale(); },"t3").start();
        new Thread(() -> { for (int i = 1; i <=55 ; i++) trainTicket.sale(); },"t4").start();


    }
}
