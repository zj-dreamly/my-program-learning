package com.atguigu.juc.jmm;

/**
 * @auther zzyy
 * @create 2021-03-19 19:21
 */
public class SingletonDemo
{
    private SingletonDemo() { }

    private static class SingletonDemoHandler
    {
        private static SingletonDemo instance = new SingletonDemo();
    }

    public static SingletonDemo getInstance()
    {
        return SingletonDemoHandler.instance;
    }
}
