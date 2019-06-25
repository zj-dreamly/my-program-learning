package com.github.zj.dreamly.design.pattern.pattern.creational.singleton;

/**
 * @author 苍海之南
 */
public class ThreadLocalInstance {
	private static final ThreadLocal<ThreadLocalInstance> threadLocalInstanceThreadLocal
		= new ThreadLocal<ThreadLocalInstance>() {
		@Override
		protected ThreadLocalInstance initialValue() {
			return new ThreadLocalInstance();
		}
	};

	private ThreadLocalInstance() {

	}

	public static ThreadLocalInstance getInstance() {
		return threadLocalInstanceThreadLocal.get();
	}

}
