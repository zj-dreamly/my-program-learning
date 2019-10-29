package com.github.zj.dreamly.guava.eventbus;

import com.github.zj.dreamly.guava.eventbus.listeners.MultipleEventListeners;
import com.google.common.eventbus.EventBus;

/**
 * @author 苍海之南
 */
public class MultipleEventBusExample {
	public static void main(String[] args) {
		final EventBus eventBus = new EventBus();
		eventBus.register(new MultipleEventListeners());
		System.out.println("post the string event");
		eventBus.post("I am string event");
		System.out.println("post the int event");
		eventBus.post(1000);

	}
}
