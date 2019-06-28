package com.github.zj.dreamly.spring.annotation.ioc;

import com.github.zj.dreamly.spring.annotation.bean.Blue;
import com.github.zj.dreamly.spring.annotation.bean.Person;
import com.github.zj.dreamly.spring.annotation.config.TestConfig1;
import com.github.zj.dreamly.spring.annotation.config.TestConfig2;
import org.junit.Test;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.core.env.ConfigurableEnvironment;

import java.util.Map;

/**
 * <h2>IocTest1</h2>
 *
 * @author: 苍海之南
 * @since: 2019-06-28 08:43
 **/
public class IocTest2 {
	private AnnotationConfigApplicationContext applicationContext = new AnnotationConfigApplicationContext(TestConfig2.class);

	@Test
	public void testImport() {

		printBeans(applicationContext);
		Blue bean = applicationContext.getBean(Blue.class);
		System.out.println(bean);

		//工厂Bean获取的是调用getObject创建的对象
		Object bean2 = applicationContext.getBean("colorFactoryBean");
		Object bean3 = applicationContext.getBean("colorFactoryBean");

		System.out.println("bean的类型：" + bean2.getClass());
		System.out.println(bean2 == bean3);

		Object bean4 = applicationContext.getBean("&colorFactoryBean");
		System.out.println(bean4.getClass());
	}

	/**
	 * 打印出ioc中所有bean
	 */
	private void printBeans(AnnotationConfigApplicationContext applicationContext) {

		String[] definitionNames = applicationContext.getBeanDefinitionNames();
		for (String name : definitionNames) {
			System.out.println(name);
		}
	}

	@Test
	public void test03() {

		String[] namesForType = applicationContext.getBeanNamesForType(Person.class);
		ConfigurableEnvironment environment = applicationContext.getEnvironment();
		//动态获取环境变量的值；Windows 10
		String property = environment.getProperty("os.name");
		System.out.println(property);

		for (String name : namesForType) {
			System.out.println(name);
		}

		Map<String, Person> persons = applicationContext.getBeansOfType(Person.class);
		System.out.println(persons);

	}

	@Test
	public void test02() {

		AnnotationConfigApplicationContext applicationContext = new AnnotationConfigApplicationContext(TestConfig2.class);

		System.out.println("ioc容器创建完成....");
		Object bean = applicationContext.getBean("person");
		Object bean2 = applicationContext.getBean("person");
		System.out.println(bean == bean2);
	}

	@SuppressWarnings("resource")
	@Test
	public void test01() {
		AnnotationConfigApplicationContext applicationContext = new AnnotationConfigApplicationContext(TestConfig1.class);
		String[] definitionNames = applicationContext.getBeanDefinitionNames();
		for (String name : definitionNames) {
			System.out.println(name);
		}
	}
}
