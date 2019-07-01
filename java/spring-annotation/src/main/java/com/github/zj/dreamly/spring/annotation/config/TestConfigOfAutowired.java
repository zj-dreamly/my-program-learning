package com.github.zj.dreamly.spring.annotation.config;

import com.github.zj.dreamly.spring.annotation.bean.Car;
import com.github.zj.dreamly.spring.annotation.bean.Color;
import com.github.zj.dreamly.spring.annotation.dao.BookDao;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;

/**
 * <h2>TestConifgOfAutowired</h2>
 *
 * @author: 苍海之南
 * @since: 2019-06-29 10:24
 **/
@Configuration
@ComponentScan({"com.github.zj.dreamly.spring.annotation.bean", "com.github.zj.dreamly.spring.annotation.service", "com.github.zj.dreamly.spring.annotation.dao", "com.github.zj.dreamly.spring.annotation.controller"})
public class TestConfigOfAutowired {


	@Primary
	@Bean("bookDao2")
	public BookDao bookDao(){
		BookDao bookDao = new BookDao();
		bookDao.setLable("2");
		return bookDao;
	}

	@Bean
	public Color color(Car car){
		Color color = new Color();
		color.setCar(car);
		return color;
	}
}
