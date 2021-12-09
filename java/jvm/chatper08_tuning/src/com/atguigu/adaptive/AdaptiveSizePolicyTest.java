package com.atguigu.adaptive;

/**
 * 使用ParallelGC的情况下，不管是否开启了UseAdaptiveSizePolicy参数，默认Eden与Survivor的比例都为：6:1:1
 *
 *
 *
 * @author shkstart
 * @create 12:53
 */
public class AdaptiveSizePolicyTest {
    public static void main(String[] args) {
        try {
            Thread.sleep(1000000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
