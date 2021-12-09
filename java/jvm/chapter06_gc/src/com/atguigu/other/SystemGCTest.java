package com.atguigu.other;

/**
 * @author shkstart  shkstart@126.com
 * @create 2020  14:49
 */
public class SystemGCTest {
    public static void main(String[] args) {
        new SystemGCTest();
        System.gc();//提醒jvm的垃圾回收器执行full gc,但是不确定是否马上执行gc
//        与Runtime.getRuntime().gc();的作用一样。

//        System.runFinalization();//强制调用使用引用的对象的finalize()方法
    }

    //此方法什么时候会被执行？ 当一个对象首次考虑要被回收时，会调用其finalize()
    @Override
    protected void finalize() throws Throwable {
        super.finalize();
        System.out.println("SystemGCTest 重写了finalize()");
    }
}
