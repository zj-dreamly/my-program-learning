package com.github.zj.dreamly.spring.annotation.ext;

import org.springframework.context.ApplicationEvent;
import org.springframework.context.ApplicationListener;

/**
 * <h2>MyApplicationListener</h2>
 *
 * @author: 苍海之南
 * @since: 2019-07-02 09:39
 **/
public class MyApplicationListener implements ApplicationListener<ApplicationEvent> {
	@Override
	public void onApplicationEvent(ApplicationEvent applicationEvent) {
		System.out.println("收到事件：" + applicationEvent);
	}
}
