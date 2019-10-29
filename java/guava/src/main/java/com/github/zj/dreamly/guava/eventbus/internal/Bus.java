package com.github.zj.dreamly.guava.eventbus.internal;

/**
 * @author 苍海之南
 */
public interface Bus {

	void register(Object subscriber);

	void unregister(Object subscriber);

	void post(Object event);

	void post(Object Event, String topic);

	void close();

	String getBusName();
}
