package com.atguigu.stack;

/**
 * 操作数栈的测试
 *
 * @author shkstart
 * @create 2020 下午 10:25
 */
public class OperandStackTest {
    public void testAddOperation() {
        //byte、short、char、boolean：都以int型来保存
        byte i = 15;
        short j = 8;
        int k = i + j;

        long m = 12L;
        int n = 800;
        m = m * n;//存在宽化类型转换

    }

    public int getSum(){
        int m = 10;
        int n = 20;
        int k = m + n;
        System.out.println(k);
        return k;
    }

    public void testGetSum(){
        //获取上一个栈桢返回的结果，并保存在操作数栈中
        int i = getSum();
        int j = 10;
    }

}
