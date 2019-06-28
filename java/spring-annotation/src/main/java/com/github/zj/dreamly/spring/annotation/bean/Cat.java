package com.github.zj.dreamly.spring.annotation.bean;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.DisposableBean;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.stereotype.Component;

/**
 * <h2>Cat</h2>
 *
 * @author: 苍海之南
 * @since: 2019-06-28 11:07
 **/
@Slf4j
@Component
public class Cat implements InitializingBean, DisposableBean {
	@Override
	public void destroy() {
		log.info("cat is destroy");
	}

	@Override
	public void afterPropertiesSet() {
		log.info("cat is afterPropertiesSet");
	}
}
