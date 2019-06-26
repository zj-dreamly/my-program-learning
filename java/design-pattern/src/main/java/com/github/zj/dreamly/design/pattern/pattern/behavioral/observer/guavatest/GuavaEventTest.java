package com.github.zj.dreamly.design.pattern.pattern.behavioral.observer.guavatest;

import com.google.common.eventbus.EventBus;

/**
 * @author 苍海之南
 */
public class GuavaEventTest {
	public static void main(String[] args) {
		EventBus eventbus = new EventBus();
		GuavaEvent guavaEvent = new GuavaEvent();
		eventbus.register(guavaEvent);
		eventbus.post("post的内容");
	}

}
