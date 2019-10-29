package com.github.zj.dreamly.guava.eventbus;

import com.github.zj.dreamly.guava.eventbus.listeners.DeadEventListener;
import com.google.common.eventbus.EventBus;

/**
 * @author 苍海之南
 */
public class DeadEventBusExample {

	public static void main(String[] args) {

		final DeadEventListener deadEventListener = new DeadEventListener();
		final EventBus eventBus = new EventBus("DeadEventBus") {
			@Override
			public String toString() {
				return "DEAD-EVENT-BUS";
			}
		};
		eventBus.register(deadEventListener);
		eventBus.post("Hello");
	}
}
