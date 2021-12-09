package com.atguigu.java;

import org.junit.Test;

/**
 * @author shkstart
 * @create 12:31
 */
public class ByteCodeInterview {
    //面试题： i++和++i有什么区别？
    @Test
    public void test1(){
        int i = 10;
        i++;
//        ++i;

        System.out.println(i);
    }

    @Test
    public void test2(){
        int i = 10;
        i = i++;
        System.out.println(i);//
    }

    @Test
    public void test3(){
        int i = 2;
        i *= i++; //i = i * i++
        System.out.println(i); //4
    }

    @Test
    public void test4(){
        int k = 10;
        k = k + (k++) + (++k); //
        System.out.println(k);//32
    }

    //包装类对象的缓存问题
    @Test
    public void test5(){

        Integer i1 = 10;
        Integer i2 = 10;
        System.out.println(i1 == i2);//true

        Integer i3 = 128;
        Integer i4 = 128;
        System.out.println(i3 == i4);//false

        Boolean b1 = true;
        Boolean b2 = true;
        System.out.println(b1 == b2);//true
    }

    //String声明的字面量数据都放在字符串常量池中
    //jdk 6中字符串常量池存放在方法区（即永久代中）
    //jdk7 及以后字符串常量池存放在堆空间
    @Test
    public void test6(){
        String str = new String("hello") + new String("world");
        String str1 = "helloworld";
        str.intern();
        System.out.println(str == str1);//false --> true (加上intern() 在str声明之前)

    }
}
