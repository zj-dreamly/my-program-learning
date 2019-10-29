package com.github.zj.dreamly.guava.eventbus.internal;

import java.lang.reflect.Method;

/**
 * @author 苍海之南
 */
public interface MyEventContext {

	String getSource();

	Object getSubscriber();

	Method getSubscribe();

	Object getEvent();
}
