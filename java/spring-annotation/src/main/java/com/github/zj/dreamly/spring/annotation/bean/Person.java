package com.github.zj.dreamly.spring.annotation.bean;

import lombok.Builder;
import lombok.Data;
import org.springframework.beans.factory.annotation.Value;
/**
 * <h2>person</h2>
 *
 * @author: 苍海之南
 * @since: 2019-06-27 17:12
 **/
@Data
@Builder
public class Person {
	
	//使用@Value赋值；
	//1、基本数值
	//2、可以写SpEL； #{}
	//3、可以写${}；取出配置文件【properties】中的值（在运行环境变量里面的值）
	
	@Value("张三")
	private String name;
	@Value("#{20-2}")
	private Integer age;
	
	@Value("${person.nickName}")
	private String nickName;
}
