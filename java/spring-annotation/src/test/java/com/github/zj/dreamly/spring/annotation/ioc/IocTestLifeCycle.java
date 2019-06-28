package com.github.zj.dreamly.spring.annotation.ioc;

import com.github.zj.dreamly.spring.annotation.config.TestConfigOfLifeCycle;
import org.junit.Test;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

public class IocTestLifeCycle {

	@Test
	public void test01() {
		//1、创建ioc容器
		AnnotationConfigApplicationContext applicationContext = new AnnotationConfigApplicationContext(TestConfigOfLifeCycle.class);
		System.out.println("complete...");

		//applicationContext.getBean("car");
		//关闭容器
		applicationContext.close();
	}

}
