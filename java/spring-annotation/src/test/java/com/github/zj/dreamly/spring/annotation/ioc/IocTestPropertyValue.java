package com.github.zj.dreamly.spring.annotation.ioc;

import com.github.zj.dreamly.spring.annotation.bean.Person;
import com.github.zj.dreamly.spring.annotation.config.TestConfigOfPropertyValue;
import org.junit.Test;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.core.env.ConfigurableEnvironment;

/**
 * <h2>IocTestPropertyValue</h2>
 *
 * @author: 苍海之南
 * @since: 2019-06-28 14:56
 **/
public class IocTestPropertyValue {
	private AnnotationConfigApplicationContext applicationContext = new AnnotationConfigApplicationContext(TestConfigOfPropertyValue.class);

	@Test
	public void test01() {
		printBeans(applicationContext);

		Person person = (Person) applicationContext.getBean("person");
		System.out.println(person);

		ConfigurableEnvironment environment = applicationContext.getEnvironment();
		String property = environment.getProperty("person.nickName");
		System.out.println(property);
		applicationContext.close();
	}

	private void printBeans(AnnotationConfigApplicationContext applicationContext) {
		String[] definitionNames = applicationContext.getBeanDefinitionNames();
		for (String name : definitionNames) {
			System.out.println(name);
		}
	}

}
