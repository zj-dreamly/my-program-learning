package com.atguigu.juc.atomics;

import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicIntegerFieldUpdater;

class BankAccount
{
    String bankName = "ccb";

    //以一种线程安全的方式操作非线程安全对象内的某些字段

    //1 更新的对象属性必须使用 public volatile 修饰符。
    public volatile int money = 0;

    //2 因为对象的属性修改类型原子类都是抽象类，所以每次使用都必须
    // 使用静态方法newUpdater()创建一个更新器，并且需要设置想要更新的类和属性。
    AtomicIntegerFieldUpdater FieldUpdater = AtomicIntegerFieldUpdater.newUpdater(BankAccount.class,"money");

    public void transfer(BankAccount bankAccount)
    {
        FieldUpdater.incrementAndGet(bankAccount);
    }
}


/**
 * @auther zzyy
 * @create 2021-03-18 17:20
 */
public class AtomicIntegerFieldUpdaterDemo
{
    public static void main(String[] args) throws InterruptedException
    {
        BankAccount bankAccount = new BankAccount();

        for (int i = 1; i <=1000; i++) {
            new Thread(() -> {
                bankAccount.transfer(bankAccount);
            },String.valueOf(i)).start();
        }

        //暂停几秒钟线程
        try { TimeUnit.SECONDS.sleep(1); } catch (InterruptedException e) { e.printStackTrace(); }


        System.out.println(Thread.currentThread().getName()+"\t"+"---bankAccount: "+bankAccount.money);
    }
}
