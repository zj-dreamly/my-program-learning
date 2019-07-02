package com.github.zj.dreamly.spring.annotation.tx;

import org.junit.Test;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

/**
 * <h2>TxTest</h2>
 *
 * @author: 苍海之南
 * @since: 2019-07-02 08:25
 **/
public class TxTest {
	@Test
	public void test01(){
		AnnotationConfigApplicationContext applicationContext =
			new AnnotationConfigApplicationContext(TxConfig.class);

		UserService userService = applicationContext.getBean(UserService.class);

		userService.insertUser();
		applicationContext.close();
	}
}
