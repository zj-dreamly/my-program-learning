package com.github.zj.dreamly.spring.annotation.bean;

import org.springframework.beans.BeansException;
import org.springframework.beans.factory.BeanNameAware;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.context.EmbeddedValueResolverAware;
import org.springframework.util.StringValueResolver;

/**
 * <h2>Red</h2>
 *
 * @author: 苍海之南
 * @since: 2019-06-28 09:37
 **/
public class Red implements ApplicationContextAware, BeanNameAware, EmbeddedValueResolverAware {

	private ApplicationContext applicationContext;

	@Override
	public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
		System.out.println("传入的ioc：" + applicationContext);
		this.applicationContext = applicationContext;
	}

	@Override
	public void setBeanName(String name) {
		System.out.println("当前bean的名字：" + name);
	}

	@Override
	public void setEmbeddedValueResolver(StringValueResolver resolver) {
		String resolveStringValue = resolver.resolveStringValue("hello ${os.name} I'm #{20*18}");
		System.out.println("string：" + resolveStringValue);
	}
}
