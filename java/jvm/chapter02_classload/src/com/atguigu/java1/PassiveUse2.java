package com.atguigu.java1;

import org.junit.Test;

import java.util.Random;

/**
 * @author shkstart
 * @create 2020-09-14 17:30
 *
 *  * 3. 引用常量不会触发此类或接口的初始化。因为常量在链接阶段就已经被显式赋值了。
 *  * 4. 调用ClassLoader类的loadClass()方法加载一个类，并不是对类的主动使用，不会导致类的初始化。
 */
public class PassiveUse2 {
    @Test
    public void test1(){
        System.out.println(Person.NUM);
        System.out.println(Person.NUM1);
    }

    @Test
    public void test2(){
        System.out.println(SerialA.ID);
//        System.out.println(SerialA.ID1);
    }

    @Test
    public void test3(){
        try {
            Class clazz = ClassLoader.getSystemClassLoader().loadClass("com.atguigu.java1.Order");
            System.out.println(clazz);
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

}

class Person{
    static{
        System.out.println("Person类的初始化");
    }
    public static final int NUM = 1;//在链接过程的准备环节就被赋值为1了。
    public static final int NUM1 = new Random().nextInt(10);//此时的赋值操作需要在<clinit>()中执行
}

interface SerialA{
    public static final Thread t = new Thread() {
        {
            System.out.println("SerialA的初始化");
        }
    };

    static final int ID = 1;
    int ID1 = new Random().nextInt(10);//此时的赋值操作需要在<clinit>()中执行
}

