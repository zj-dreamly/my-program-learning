package com.atguigu.redis;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import tk.mybatis.spring.annotation.MapperScan;

@SpringBootApplication
@MapperScan("com.atguigu.redis.mapper") //import tk.mybatis.spring.annotation.MapperScan;
public class Redis20210511Application
{

    public static void main(String[] args)
    {
        SpringApplication.run(Redis20210511Application.class, args);
    }

}
