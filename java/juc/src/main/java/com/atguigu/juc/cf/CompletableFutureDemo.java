package com.atguigu.juc.cf;


import java.util.concurrent.*;

/**
 * @auther zzyy
 * @create 2021-03-02 11:56
 */
public class CompletableFutureDemo
{
    public static void main(String[] args)throws Exception
    {
        ThreadPoolExecutor threadPoolExecutor = new ThreadPoolExecutor(1, 20, 1L, TimeUnit.SECONDS, new LinkedBlockingQueue<>(50), Executors.defaultThreadFactory(), new ThreadPoolExecutor.AbortPolicy());



        threadPoolExecutor.shutdown();
    }
}
