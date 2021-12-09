package com.atguigu.java;

import java.io.PrintStream;
import java.sql.SQLOutput;
import java.util.Random;

/**
 * @author shkstart
 * @create 18:55
 *
 * 一、说明：使用static + final修饰的字段的显式赋值的操作，到底是在哪个阶段进行的赋值？
 * 情况1：在链接阶段的准备环节赋值
 * 情况2：在初始化阶段<clinit>()中赋值
 *
 * 二、结论：
 * 1. 在链接阶段的准备环节赋值的情况：
 * 1）对于基本数据类型的字段来说，如果使用static final修饰，则显式赋值(直接赋值常量，而非调用方法）通常是在链接阶段的准备环节进行
 * 2）对于String来说，如果使用字面量的方式赋值，使用static final修饰的话，则显式赋值通常是在链接阶段的准备环节进行
 *
 * 2. 在初始化阶段<clinit>()中赋值的情况：
 * 排除上述的在准备环节赋值的情况之外的情况。
 *
 *
 *  总结：
 *  使用static + final 修饰的成员变量，称为：全局常量。
 *  什么时候在链接阶段的准备环节：给此全局常量附的值是字面量或常量。不涉及到方法或构造器的调用。
 *  除此之外，都是在初始化环节赋值的。
 *
 */
public class InitializationTest2 {
//    public static int a = 1;   //在初始化阶段赋值
//    public static final int INT_CONSTANT = 10;   //在链接阶段的准备环节赋值
//
//    public static Integer INTEGER_CONSTANT1 = Integer.valueOf(100); //在初始化阶段赋值
//    public static final Integer INTEGER_CONSTANT2 = Integer.valueOf(1000); //在初始化阶段赋值
//
    public static final String s0 = "helloworld0";   //在链接阶段的准备环节赋值
    public static final String s1 = new String("helloworld1"); //在初始化阶段赋值
//
    public static String s2 = "helloworld2";  //在初始化阶段赋值
//
    public static final int NUM1 = new Random().nextInt(10);  //在初始化阶段赋值

    static int a = 9;//在初始化阶段赋值
    static final int b = a; //在初始化阶段赋值

}
