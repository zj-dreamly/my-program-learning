package com.github.zj.dreamly.guava.eventbus;

import com.github.zj.dreamly.guava.eventbus.events.Apple;
import com.github.zj.dreamly.guava.eventbus.events.Fruit;
import com.github.zj.dreamly.guava.eventbus.listeners.FruitEaterListener;
import com.google.common.eventbus.EventBus;

/**
 * @author 苍海之南
 */
public class InheritEventsEventBusExample {
	public static void main(String[] args) {
		final EventBus eventBus = new EventBus();
		eventBus.register(new FruitEaterListener());
		eventBus.post(new Apple("apple"));
		System.out.println("============================");
		eventBus.post(new Fruit("apple"));
	}
}
