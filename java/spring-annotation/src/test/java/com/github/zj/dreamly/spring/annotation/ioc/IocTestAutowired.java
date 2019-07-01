package com.github.zj.dreamly.spring.annotation.ioc;

import com.github.zj.dreamly.spring.annotation.bean.Boss;
import com.github.zj.dreamly.spring.annotation.bean.Car;
import com.github.zj.dreamly.spring.annotation.bean.Color;
import com.github.zj.dreamly.spring.annotation.config.TestConfigOfAutowired;
import com.github.zj.dreamly.spring.annotation.dao.BookDao;
import com.github.zj.dreamly.spring.annotation.service.BookService;
import org.junit.Test;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

/**
 * <h2>IocTestAutowired</h2>
 *
 * @author: 苍海之南
 * @since: 2019-06-29 10:29
 **/
public class IocTestAutowired {
	@Test
	public void test01() {
		AnnotationConfigApplicationContext applicationContext = new AnnotationConfigApplicationContext(TestConfigOfAutowired.class);

		BookService bookService = applicationContext.getBean(BookService.class);
		System.out.println("y-m-l bookService:" + bookService);

		BookDao bean = applicationContext.getBean(BookDao.class);
		System.out.println("yml-BookDao" + bean);

		Boss boss = applicationContext.getBean(Boss.class);
		System.out.println(boss);
		Car car = applicationContext.getBean(Car.class);
		System.out.println(car);

		Color color = applicationContext.getBean(Color.class);
		System.out.println(color);
		System.out.println(applicationContext);
		applicationContext.close();
	}
}
