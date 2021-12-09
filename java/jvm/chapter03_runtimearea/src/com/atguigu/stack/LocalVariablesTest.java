package com.atguigu.stack;

import java.util.Date;

/**
 * 测试局部变量表
 *
 * @author shkstart
 * @create 2020 下午 6:13
 */
public class LocalVariablesTest {
    private int count = 0;

    public static void main(String[] args) {
        LocalVariablesTest test = new LocalVariablesTest();
        int num = 10;
        test.test1();
    }
    //关于Slot的使用的理解
    public void test1() {
        Date date = new Date();
        String name1 = "atguigu.com";
        test2(date, name1);
        System.out.println(date + name1);
    }

    public static void testStatic(){
        LocalVariablesTest test = new LocalVariablesTest();
        Date date = new Date();
        int count = 10;
        System.out.println(count);
        //因为this变量不存在于当前方法的局部变量表中！！
//        System.out.println(this.count);
    }


    public LocalVariablesTest(){
        this.count = 1;

    }


    public String test2(Date dateP, String name2) {
        dateP = null;
        name2 = "songhongkang";
        double weight = 130.5;// double 和 long 会占据两个slot
        char gender = '男';
        return dateP + name2;
    }

    public void test3() {
        this.count++;
    }

    public void test4() {
        int a = 0;
        {
            int b = 0;
            b = a + 1;
        }
        //变量c的位置:复用了变量b的slot
        int c = a + 1;
    }

    public void test5Temp(){
        int num;
//        System.out.println(num);//错误信息：变量num未进行初始化
    }

}
