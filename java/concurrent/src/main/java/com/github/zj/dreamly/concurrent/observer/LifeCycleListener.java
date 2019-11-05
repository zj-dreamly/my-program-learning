package com.github.zj.dreamly.concurrent.observer;

/**
 * <h2>LifeCycleListener</h2>
 *
 * @author: 苍海之南
 * @since: 2019-11-05 11:12
 **/
public interface LifeCycleListener {
	void onEvent(ObservableRunnable.RunnableEvent event);
}
