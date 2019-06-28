package com.github.zj.dreamly.spring.annotation.condition;

import org.springframework.beans.factory.config.ConfigurableListableBeanFactory;
import org.springframework.beans.factory.support.BeanDefinitionRegistry;
import org.springframework.context.annotation.Condition;
import org.springframework.context.annotation.ConditionContext;
import org.springframework.core.env.Environment;
import org.springframework.core.type.AnnotatedTypeMetadata;

/**
 * <h2>LinuxCondition</h2>
 *
 * @author: 苍海之南
 * @since: 2019-06-28 08:39
 **/
@SuppressWarnings("NullableProblems")
public class LinuxCondition implements Condition {
	@Override
	public boolean matches(ConditionContext conditionContext, AnnotatedTypeMetadata annotatedTypeMetadata) {

		//1、能获取到ioc使用的beanfactory
		ConfigurableListableBeanFactory beanFactory = conditionContext.getBeanFactory();

		//2、获取类加载器
		ClassLoader classLoader = conditionContext.getClassLoader();

		//4、获取到bean定义的注册类
		BeanDefinitionRegistry registry = conditionContext.getRegistry();

		//3、获取当前环境信息
		Environment environment = conditionContext.getEnvironment();
		String property = environment.getProperty("os.name");

		//可以判断容器中的bean注册情况，也可以给容器中注册bean
		boolean definition = registry.containsBeanDefinition("person");
		assert property != null;
		return property.contains("linux");
	}
}
