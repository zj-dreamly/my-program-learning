package com.github.zj.dreamly.guava.eventbus.internal.test;

import com.github.zj.dreamly.guava.eventbus.internal.MyAsyncEventBus;

import java.util.concurrent.Executors;
import java.util.concurrent.ThreadPoolExecutor;

/**
 * @author 苍海之南
 */
public class MyAsyncBusExample {

	public static void main(String[] args) {
		MyAsyncEventBus eventBus = new MyAsyncEventBus((ThreadPoolExecutor) Executors.newFixedThreadPool(4));
		eventBus.register(new MySimpleListener());
		eventBus.register(new MySimpleListener2());
		eventBus.post(123131, "alex-topic");
		eventBus.post("hello");

	}
}
