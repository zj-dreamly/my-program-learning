package com.github.zj.dreamly.spring.annotation.condition;

import com.github.zj.dreamly.spring.annotation.bean.RainBow;
import org.springframework.beans.factory.support.BeanDefinitionRegistry;
import org.springframework.beans.factory.support.RootBeanDefinition;
import org.springframework.context.annotation.ImportBeanDefinitionRegistrar;
import org.springframework.core.type.AnnotationMetadata;

/**
 * <h2>MyImportBeanDefinitionRegistrar</h2>
 *
 * @author: 苍海之南
 * @since: 2019-06-28 09:39
 **/
public class MyImportBeanDefinitionRegistrar implements ImportBeanDefinitionRegistrar {

	/**
	 * AnnotationMetadata：当前类的注解信息
	 * BeanDefinitionRegistry:BeanDefinition注册类；
	 * 把所有需要添加到容器中的bean；调用BeanDefinitionRegistry.registerBeanDefinition手动注册进来
	 */
	@Override
	public void registerBeanDefinitions(AnnotationMetadata annotationMetadata, BeanDefinitionRegistry beanDefinitionRegistry) {

		boolean definition = beanDefinitionRegistry.containsBeanDefinition("com.github.zj.dreamly.spring.annotation.bean.Red");
		boolean definition2 = beanDefinitionRegistry.containsBeanDefinition("com.github.zj.dreamly.spring.annotation.bean.Blue");
		if (definition && definition2) {
			//指定Bean定义信息；（Bean的类型，Bean。。。）
			RootBeanDefinition beanDefinition = new RootBeanDefinition(RainBow.class);
			//注册一个Bean，指定bean名
			beanDefinitionRegistry.registerBeanDefinition("rainBow", beanDefinition);
		}

	}
}
