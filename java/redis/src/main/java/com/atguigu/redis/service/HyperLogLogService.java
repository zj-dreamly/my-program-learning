package com.atguigu.redis.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import javax.annotation.Resource;
import java.util.Random;
import java.util.concurrent.TimeUnit;

/**
 * @auther zzyy
 * @create 2021-05-02 18:16
 */
@Service
@Slf4j
public class HyperLogLogService
{
    @Resource
    private RedisTemplate redisTemplate;

    /**
     * 模拟有用户来点击首页，每个用户就是不同的ip，不重复记录，重复不记录
     */
    @PostConstruct
    public void init()
    {
        log.info("------模拟后台有用户点击，每个用户ip不同");
        //自己启动线程模拟，实际上产不是线程
        new Thread(() -> {
            String ip = null;
            for (int i = 1; i <=200; i++) {
                Random random = new Random();
                ip = random.nextInt(255)+"."+random.nextInt(255)+"."+random.nextInt(255)+"."+random.nextInt(255);

                Long hll = redisTemplate.opsForHyperLogLog().add("hll", ip);
                log.info("ip={},该ip访问过的次数={}",ip,hll);
                //暂停3秒钟线程
                try { TimeUnit.SECONDS.sleep(3); } catch (InterruptedException e) { e.printStackTrace(); }
            }
        },"t1").start();
    }

}
