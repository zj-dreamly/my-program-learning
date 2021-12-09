package com.atguigu.juc.locks;

/**
 * @auther zzyy
 * @create 2021-03-03 15:21
 * 从字节码角度分析synchronized实现
 */
public class LockByteCodeDemo
{
    final Object object = new Object();



    public void m1()
    {
        synchronized (object){
            System.out.println("----------hello sync");
            throw new RuntimeException("----ex");
        }
    }

    /*public synchronized void m2()
    {

    }*/

    public static synchronized void m2()
    {

    }
}
