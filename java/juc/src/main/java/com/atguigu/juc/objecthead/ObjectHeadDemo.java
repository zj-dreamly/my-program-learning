package com.atguigu.juc.objecthead;

import org.openjdk.jol.info.ClassLayout;

/**
 * @auther zzyy
 * @create 2021-03-26 15:27
 */
public class ObjectHeadDemo
{
    public static void main(String[] args)
    {
        Object object = new Object();

        //引入了JOL，直接使用
        System.out.println(ClassLayout.parseInstance(object).toPrintable());

        //java5之前 只有重量级锁
        new Thread(() -> {
            synchronized (object){
                System.out.println("----hello juc");
            }
        },"t1").start();
    }
}

