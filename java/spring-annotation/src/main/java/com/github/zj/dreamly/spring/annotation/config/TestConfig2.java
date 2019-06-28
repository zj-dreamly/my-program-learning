package com.github.zj.dreamly.spring.annotation.config;

import com.github.zj.dreamly.spring.annotation.bean.Color;
import com.github.zj.dreamly.spring.annotation.bean.ColorFactoryBean;
import com.github.zj.dreamly.spring.annotation.bean.Person;
import com.github.zj.dreamly.spring.annotation.bean.Red;
import com.github.zj.dreamly.spring.annotation.condition.LinuxCondition;
import com.github.zj.dreamly.spring.annotation.condition.MyImportBeanDefinitionRegistrar;
import com.github.zj.dreamly.spring.annotation.condition.MyImportSelector;
import com.github.zj.dreamly.spring.annotation.condition.WindowsCondition;
import org.springframework.context.annotation.*;

/**
 * <h2>TestConfig2</h2>
 *
 * @author: 苍海之南
 * @since: 2019-06-28 08:32
 **/
@Conditional({WindowsCondition.class})
@Configuration
@Import({Color.class, Red.class, MyImportSelector.class, MyImportBeanDefinitionRegistrar.class})
public class TestConfig2 {

	/**
	 * ConfigurableBeanFactory#SCOPE_PROTOTYPE
	 *
	 * Scope:调整作用域
	 * prototype：多实例的：ioc容器启动并不会去调用方法创建对象放在容器中。每次获取的时候才会调用方法创建对象；
	 * singleton：单实例的（默认值）：ioc容器启动会调用方法创建对象放到ioc容器中。以后每次获取就是直接从容器（map.get()）中拿
	 * request：同一次请求创建一个实例
	 * session：同一个session创建一个实例
	 *
	 * 懒加载：
	 * 单实例bean：默认在容器启动的时候创建对象；
	 * 懒加载：容器启动不创建对象。第一次使用(获取)Bean创建对象，并初始化；
	 * @link ConfigurableBeanFactory#SCOPE_SINGLETON
	 * @link org.springframework.web.context.WebApplicationContext#SCOPE_REQUEST  request
	 * @link org.springframework.web.context.WebApplicationContext#SCOPE_SESSION  sesssion
	 */

	@Scope("prototype")
	@Lazy
	@Bean("person")
	public Person person() {
		System.out.println("给容器中添加Person....");
		return new Person("张三", 25);
	}

	/**
	 * Conditional：按照一定的条件进行判断，满足条件给容器中注册bean
	 * <p>
	 * 如果系统是windows，给容器中注册("bill")
	 * 如果是linux系统，给容器中注册("linus")
	 */

	@Bean("bill")
	public Person person01() {
		return new Person("Bill Gates", 62);
	}

	@Conditional(LinuxCondition.class)
	@Bean("linus")
	public Person person02() {
		return new Person("linus", 48);
	}

	@Bean
	public ColorFactoryBean colorFactoryBean() {
		return new ColorFactoryBean();
	}
}
