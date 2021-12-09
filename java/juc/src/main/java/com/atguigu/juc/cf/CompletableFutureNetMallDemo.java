package com.atguigu.juc.cf;

import lombok.Getter;

import java.util.Arrays;
import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ThreadLocalRandom;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

/**
 * @auther zzyy
 * @create 2021-03-08 15:28
 *
 * 案例说明：电商比价需求
 * 1 同一款产品，同时搜索出同款产品在各大电商的售价;
 * 2 同一款产品，同时搜索出本产品在某一个电商平台下，各个入驻门店的售价是多少
 *
 * 出来结果希望是同款产品的在不同地方的价格清单列表，返回一个List<String>
 * 《mysql》 in jd price is 88.05
 * 《mysql》 in pdd price is 86.11
 * 《mysql》 in taobao price is 90.43
 *
 * 3 要求深刻理解
 *   3.1 函数式编程
 *   3.2 链式编程
 *   3.3 Stream流式计算
 */
public class CompletableFutureNetMallDemo
{
    static List<NetMall> list = Arrays.asList(
            new NetMall("jd"),
            new NetMall("pdd"),
            new NetMall("taobao"),
            new NetMall("dangdangwang"),
            new NetMall("tmall")
    );

    //同步 ，step by step

    /**
     * List<NetMall>  ---->   List<String>
     * @param list
     * @param productName
     * @return
     */
    public static List<String> getPriceByStep(List<NetMall> list,String productName)
    {
        return list
                .stream().
                map(netMall -> String.format(productName + " in %s price is %.2f", netMall.getMallName(), netMall.calcPrice(productName)))
                .collect(Collectors.toList());
    }
    //异步 ，多箭齐发

    /**
     * List<NetMall>  ---->List<CompletableFuture<String>> --->   List<String>
     * @param list
     * @param productName
     * @return
     */
    public static List<String> getPriceByASync(List<NetMall> list,String productName)
    {
        return list
                .stream()
                .map(netMall -> CompletableFuture.supplyAsync(() -> String.format(productName + " is %s price is %.2f", netMall.getMallName(), netMall.calcPrice(productName))))
                .collect(Collectors.toList())
                .stream()
                .map(CompletableFuture::join)
                .collect(Collectors.toList());
    }

    public static void main(String[] args)
    {
        long startTime = System.currentTimeMillis();
        List<String> list1 = getPriceByStep(list, "mysql");
        for (String element : list1) {
            System.out.println(element);
        }
        long endTime = System.currentTimeMillis();
        System.out.println("----costTime: "+(endTime - startTime) +" 毫秒");

        System.out.println();

        long startTime2 = System.currentTimeMillis();
        List<String> list2 = getPriceByASync(list, "mysql");
        for (String element : list2) {
            System.out.println(element);
        }
        long endTime2 = System.currentTimeMillis();
        System.out.println("----costTime: "+(endTime2 - startTime2) +" 毫秒");

    }
}

class NetMall
{
    @Getter
    private String mallName;

    public NetMall(String mallName)
    {
        this.mallName = mallName;
    }

    public double calcPrice(String productName)
    {
        //检索需要1秒钟
        try { TimeUnit.SECONDS.sleep(1); } catch (InterruptedException e) { e.printStackTrace(); }
        return ThreadLocalRandom.current().nextDouble() * 2 + productName.charAt(0);
    }
}
