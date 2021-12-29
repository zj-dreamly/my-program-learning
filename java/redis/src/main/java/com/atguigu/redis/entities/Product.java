package com.atguigu.redis.entities;

import io.swagger.annotations.ApiModel;
import lombok.Data;

/**
 * @auther zzyy
 * @create 2021-05-09 14:48
 */
@Data
@ApiModel(value = "聚划算活动producet信息")
public class Product {

    private Long id;
    /**
     * 产品名称
     */
    private String name;
    /**
     * 产品价格
     */
    private Integer price;
    /**
     * 产品详情
     */
    private String detail;

    public Product() {
    }

    public Product(Long id, String name, Integer price, String detail) {
        this.id = id;
        this.name = name;
        this.price = price;
        this.detail = detail;
    }
}
