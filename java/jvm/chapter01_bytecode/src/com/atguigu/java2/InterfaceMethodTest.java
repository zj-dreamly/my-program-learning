package com.atguigu.java2;

/**
 * @author shkstart
 * @create 2020-09-10 17:26
 * 补充：方法调用指令的补充说明
 */
public class InterfaceMethodTest {
    public static void main(String[] args) {
        AA aa = new BB();

        aa.method2();

        AA.method1();
    }
}


interface AA{
    public static void method1(){

    }

    public default void method2(){

    }
}

class BB implements AA{

}
