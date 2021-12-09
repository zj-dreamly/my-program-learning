package com.atguigu.demo.bean;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Scope;

/**
 * <pre>
 *    @author  : shkstart
 *    email   : shkstart@126.com
 *    time    : 09:10
 *    desc    : 学生对象
 *    version : v1.0
 * </pre>
 */
public class People {
    private Integer id;
    private String name;
    private Integer age;
    private String job;
    private String sex;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getAge() {
        return age;
    }

    public void setAge(Integer age) {
        this.age = age;
    }

    public String getJob() {
        return job;
    }

    public void setJob(String job) {
        this.job = job;
    }

    public String getSex() {
        return sex;
    }

    public void setSex(String sex) {
        this.sex = sex;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }
    public void print(){
        System.out.println("我是print本人");
    }
}