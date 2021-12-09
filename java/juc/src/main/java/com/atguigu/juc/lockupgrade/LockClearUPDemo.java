package com.atguigu.juc.lockupgrade;

/**
 * @auther zzyy
 * @create 2021-03-27 15:17
 * 锁消除
 * 从JIT角度看相当于无视它，synchronized (o)不存在了,这个锁对象并没有被共用扩散到其它线程使用，
 * 极端的说就是根本没有加这个锁对象的底层机器码，消除了锁的使用
 */
public class LockClearUPDemo
{
    static Object objectLock = new Object();//正常的,有且仅有同一把锁


    public void m1()
    {
        Object objectLock = new Object();//锁消除

        synchronized (objectLock)
        {
            System.out.println("----hello lock");
        }
    }

    public static void main(String[] args)
    {

    }
}
