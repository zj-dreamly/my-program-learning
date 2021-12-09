package com.atguigu.escape;

/**
 * 同步省略说明
 * @author shkstart  shkstart@126.com
 * @create 2021  11:07
 */
public class SynchronizedTest {
    public void f() {
        /*
        * 代码中对hollis这个对象进行加锁，但是hollis对象的生命周期只在f()方法中，
        * 并不会被其他线程所访问到，所以在JIT编译阶段就会被优化掉。
        *
        * 问题：字节码文件中会去掉hollis吗？
        * */
        Object hollis = new Object();
        synchronized(hollis) {
            System.out.println(hollis);
        }

        /*
        * 优化后；
        * Object hollis = new Object();
        * System.out.println(hollis);
        * */
    }
}
