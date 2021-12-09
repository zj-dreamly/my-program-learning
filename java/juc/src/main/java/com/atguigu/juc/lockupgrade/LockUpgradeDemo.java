package com.atguigu.juc.lockupgrade;

import org.openjdk.jol.info.ClassLayout;

/**
 * @auther zzyy
 * @create 2021-03-27 10:59
 */
public class LockUpgradeDemo
{
    public static void main(String[] args)
    {
        //-XX:+UseBiasedLocking -XX:BiasedLockingStartupDelay=0
        Object o = new Object();


        for (int i = 0; i < 3; i++) {
            new Thread(() -> {
                synchronized (o)
                {
                    System.out.println(ClassLayout.parseInstance(o).toPrintable());
                }
            }).start();
        }
    }

    public static void noLock()
    {
        Object o = new Object();
        System.out.println(o.hashCode());
        System.out.println(Integer.toHexString(o.hashCode()));

        System.out.println(ClassLayout.parseInstance(o).toPrintable());
    }
}
