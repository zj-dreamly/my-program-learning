package com.atguigu.stack;

/**
 * 演示栈中的异常:StackOverflowError
 *
 * @author shkstart
 * @create 2020 下午 9:08
 *
 * 设置栈的大小： -Xss   (-XX:ThreadStackSize)
 *
 * -XX:+PrintFlagsFinal
 */
public class StackErrorTest {
    private static int count = 1;

    public static void main(String[] args) {
//        try {
//            Thread.sleep(10000);
//        } catch (InterruptedException e) {
//            e.printStackTrace();
//        }
        try {
            count++;
            main(args);
        }catch (Throwable e){

            System.out.println("递归的次数为：" + count);
        }



    }

}
