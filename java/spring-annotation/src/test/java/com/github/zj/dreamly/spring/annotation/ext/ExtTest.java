package com.github.zj.dreamly.spring.annotation.ext;

import org.junit.Test;
import org.springframework.context.ApplicationEvent;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

/**
 * <h2>ExtTest</h2>
 *
 * @author: 苍海之南
 * @since: 2019-07-02 09:24
 **/
public class ExtTest {
	@Test
	public void test01(){
		AnnotationConfigApplicationContext applicationContext  = new AnnotationConfigApplicationContext(ExtConfig.class);


		//发布事件；
		applicationContext.publishEvent(new ApplicationEvent(new String("我发布的事件")) {
		});

		applicationContext.close();
	}
}
