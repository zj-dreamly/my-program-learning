package com.github.zj.dreamly.spring.annotation.bean;

import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;

/**
 * <h2>Dog</h2>
 *
 * @author: 苍海之南
 * @since: 2019-06-28 11:10
 **/
@Component
public class Dog implements ApplicationContextAware {

	private ApplicationContext applicationContext;

	/**
	 * 对象创建并赋值之后调用
	 */
	@PostConstruct
	public void init() {
		System.out.println("Dog....@PostConstruct...");
	}

	/**
	 * 容器移除对象之前
	 */
	@PreDestroy
	public void detroy() {
		System.out.println("Dog....@PreDestroy...");
	}

	@Override
	public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
		this.applicationContext = applicationContext;
	}
}
