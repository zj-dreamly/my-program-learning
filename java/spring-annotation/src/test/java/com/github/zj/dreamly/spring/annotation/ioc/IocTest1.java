package com.github.zj.dreamly.spring.annotation.ioc;

import com.github.zj.dreamly.spring.annotation.BaseTest;
import com.github.zj.dreamly.spring.annotation.bean.Person;
import com.github.zj.dreamly.spring.annotation.config.TestConfig1;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

/**
 * <h2>config test</h2>
 *
 * @author: 苍海之南
 * @since: 2019-06-27 17:18
 **/
@Slf4j
public class IocTest1 extends BaseTest {

	@Test
	public void test1() {
		ApplicationContext applicationContext = new AnnotationConfigApplicationContext(TestConfig1.class);
		Person bean = applicationContext.getBean(Person.class);
		log.info("【person】：{}", bean);

		String[] namesForType = applicationContext.getBeanNamesForType(Person.class);
		for (String name : namesForType) {
			log.info("【name】：{}", name);
		}
	}
}
