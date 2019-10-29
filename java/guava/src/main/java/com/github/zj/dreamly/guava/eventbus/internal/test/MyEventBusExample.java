package com.github.zj.dreamly.guava.eventbus.internal.test;

import com.github.zj.dreamly.guava.eventbus.internal.MyEventBus;

/**
 * @author 苍海之南
 */
public class MyEventBusExample {
	public static void main(String[] args) {
		MyEventBus myEventBus = new MyEventBus((cause, context) ->
		{
			cause.printStackTrace();
			System.out.println("==========================================");
			System.out.println(context.getSource());
			System.out.println(context.getSubscribe());
			System.out.println(context.getEvent());
			System.out.println(context.getSubscriber());
		});
		myEventBus.register(new MySimpleListener());
		myEventBus.register(new MySimpleListener2());
		myEventBus.post(123131, "alex-topic");

	}
}
