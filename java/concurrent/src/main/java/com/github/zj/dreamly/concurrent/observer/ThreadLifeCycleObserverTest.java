package com.github.zj.dreamly.concurrent.observer;

import java.util.Arrays;

/**
 * <h2>ThreadLifeCycleObserverTest</h2>
 *
 * @author: 苍海之南
 * @since: 2019-11-05 11:16
 **/
public class ThreadLifeCycleObserverTest {
	public static void main(String[] args) {
		new ThreadLifeCycleObserver()
			.concurrentQuery(Arrays.asList("1", "2"));
	}
}
