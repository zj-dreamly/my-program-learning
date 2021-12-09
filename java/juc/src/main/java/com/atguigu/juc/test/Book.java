package com.atguigu.juc.test;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @auther zzyy
 * @create 2021-03-10 10:13
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
//@Accessors(chain = true)
public class Book
{
    private Integer id;
    private String  bookName;
    private double  price;
    private String  author;
}
