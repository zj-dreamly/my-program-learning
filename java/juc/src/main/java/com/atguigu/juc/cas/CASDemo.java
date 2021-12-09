package com.atguigu.juc.cas;

import java.util.concurrent.atomic.AtomicInteger;

/**
 * @auther zzyy
 * @create 2021-03-17 15:13
 */
public class CASDemo
{
    public static void main(String[] args)
    {
        AtomicInteger atomicInteger = new AtomicInteger(5);
        System.out.println(atomicInteger.get());


        System.out.println(atomicInteger.compareAndSet(5, 308)+"\t"+atomicInteger.get());

        System.out.println(atomicInteger.compareAndSet(5, 3333)+"\t"+atomicInteger.get());
    }
}
