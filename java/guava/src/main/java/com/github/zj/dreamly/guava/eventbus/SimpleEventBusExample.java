package com.github.zj.dreamly.guava.eventbus;

import com.github.zj.dreamly.guava.eventbus.listeners.SimpleListener;
import com.google.common.eventbus.EventBus;

/**
 * @author 苍海之南
 */
public class SimpleEventBusExample {
	public static void main(String[] args) {
		final EventBus eventBus = new EventBus();
		eventBus.register(new SimpleListener());
		System.out.println("post the simple event.");
		eventBus.post("Simple Event");
	}
}
