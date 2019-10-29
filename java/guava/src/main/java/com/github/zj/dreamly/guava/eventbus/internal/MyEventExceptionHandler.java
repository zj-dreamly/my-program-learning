package com.github.zj.dreamly.guava.eventbus.internal;

/**
 * @author 苍海之南
 */
public interface MyEventExceptionHandler {
	void handle(Throwable cause, MyEventContext context);
}
