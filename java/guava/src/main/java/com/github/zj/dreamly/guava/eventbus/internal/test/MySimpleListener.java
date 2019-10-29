package com.github.zj.dreamly.guava.eventbus.internal.test;

import com.github.zj.dreamly.guava.eventbus.internal.MySubscribe;

/**
 * @author 苍海之南
 */
public class MySimpleListener {

	@MySubscribe
	public void test1(String x) {
		System.out.println("MySimpleListener===test1==" + x);
	}

	@MySubscribe(topic = "alex-topic")
	public void test2(Integer x) {
		System.out.println("MySimpleListener===test2==" + x);
	}
}
