package com.atguigu.juc.test;

import lombok.extern.slf4j.Slf4j;

import java.util.concurrent.ExecutionException;

/**
 * @auther zzyy
 * @create 2021-03-01 21:38
 */

@Slf4j
public class T1
{

    ThreadLocal<Integer> threadLocal = ThreadLocal.withInitial(() -> 0);

    public void m1()
    {
        Integer value = threadLocal.get();
        ++value;
        threadLocal.set(value);
    }

    public static void main(String[] args) throws ExecutionException, InterruptedException
    {

        T1 t1 = new T1();

        new Thread(() -> {
            try {
                for (int i = 1; i <=300; i++) {
                    t1.m1();
                }
            } finally {
                //t1.threadLocal.remove();
            }
            System.out.println(t1.threadLocal.get());
        },"a").start();

        new Thread(() -> {
            try {
                for (int i = 1; i <=5; i++) {
                    t1.m1();
                }
                System.out.println(t1.threadLocal.get());
            } finally {
                t1.threadLocal.remove();
            }
        },"b").start();

    }
}
