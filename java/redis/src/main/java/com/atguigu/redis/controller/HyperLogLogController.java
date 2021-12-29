package com.atguigu.redis.controller;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

/**
 * @auther zzyy
 * @create 2021-05-02 18:16
 */
@Api(description = "案例实战总03:天猫网站首页亿级UV的Redis统计方案")
@RestController
@Slf4j
public class HyperLogLogController
{

    @Resource
    private RedisTemplate redisTemplate;

    @ApiOperation("获得ip去重复后的首页访问量，总数统计")
    @RequestMapping(value = "/uv",method = RequestMethod.GET)
    public long uv()
    {
        //pfcount
        return redisTemplate.opsForHyperLogLog().size("hll");
    }

}
