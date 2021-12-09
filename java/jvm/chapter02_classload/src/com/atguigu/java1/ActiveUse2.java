package com.atguigu.java1;

import org.junit.Test;

import java.util.Random;

/**
 * @author shkstart
 * @create 16:49
 *
 * 3. 当使用类、接口的静态字段时(final修饰特殊考虑)，比如，使用getstatic或者putstatic指令。
 *
 */
public class ActiveUse2 {
    @Test
    public void test1(){
//        System.out.println(User.num);
//        System.out.println(User.num1);
        System.out.println(User.num2);
    }

    @Test
    public void test2(){
//        System.out.println(CompareA.NUM1);
        System.out.println(CompareA.NUM2);
    }
}

class User{
    static{
        System.out.println("User类的初始化过程");
    }

    public static int num = 1;
    public static final int num1 = 1;
    public static final int num2 = new Random().nextInt(10);

}

interface CompareA{
    public static final Thread t = new Thread(){
        {
            System.out.println("CompareA的初始化");
        }
    };

    public static final int NUM1 = 1;
    public static final int NUM2 = new Random().nextInt(10);

}