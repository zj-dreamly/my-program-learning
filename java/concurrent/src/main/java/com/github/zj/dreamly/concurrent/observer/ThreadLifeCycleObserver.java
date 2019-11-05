package com.github.zj.dreamly.concurrent.observer;

import java.util.List;

/**
 * <h2>ThreadLifeCycleObserver</h2>
 *
 * @author: 苍海之南
 * @since: 2019-11-05 11:14
 **/
public class ThreadLifeCycleObserver implements LifeCycleListener {
	private final Object LOCK = new Object();

	public void concurrentQuery(List<String> ids) {
		if (ids == null || ids.isEmpty()) {
			return;
		}

		ids.forEach(id -> new Thread(new ObservableRunnable(this) {
			@Override
			public void run() {
				try {
					notifyChange(new RunnableEvent(RunnableState.RUNNING, Thread.currentThread(),
						null));
					System.out.println("query for the id " + id);
					Thread.sleep(1000L);
					notifyChange(new RunnableEvent(RunnableState.DONE, Thread.currentThread(),
						null));
				} catch (Exception e) {
					notifyChange(new RunnableEvent(RunnableState.ERROR, Thread.currentThread(), e));
				}
			}
		}, id).start());
	}

	@Override
	public void onEvent(ObservableRunnable.RunnableEvent event) {
		synchronized (LOCK) {
			System.out.println("The runnable [" + event.getThread().getName() +
				"] data changed and state is [" + event.getState() + "]");
			if (event.getCause() != null) {
				System.out.println("The runnable [" + event.getThread().getName() +
					"] process failed.");
				event.getCause().printStackTrace();
			}
		}
	}
}
