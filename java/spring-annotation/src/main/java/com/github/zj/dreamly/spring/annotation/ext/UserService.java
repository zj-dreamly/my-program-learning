package com.github.zj.dreamly.spring.annotation.ext;

import org.springframework.context.ApplicationEvent;
import org.springframework.context.event.EventListener;

/**
 * <h2>UserService</h2>
 *
 * @author: 苍海之南
 * @since: 2019-07-02 09:43
 **/
public class UserService {
	@EventListener(classes = {ApplicationEvent.class})
	public void listen(ApplicationEvent event) {
		System.out.println("UserService。。监听到的事件：" + event);
	}
}
