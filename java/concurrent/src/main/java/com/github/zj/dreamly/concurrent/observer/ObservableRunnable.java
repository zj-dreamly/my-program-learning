package com.github.zj.dreamly.concurrent.observer;

/**
 * <h2>ObservableRunnable</h2>
 *
 * @author: 苍海之南
 * @since: 2019-11-05 11:12
 **/
public abstract class ObservableRunnable implements Runnable {

	private final LifeCycleListener listener;

	public ObservableRunnable(final LifeCycleListener listener) {
		this.listener = listener;
	}

	protected void notifyChange(final RunnableEvent event) {
		listener.onEvent(event);
	}

	public enum RunnableState {
		/**
		 * the thread state
		 */
		RUNNING, ERROR, DONE
	}

	public static class RunnableEvent {
		private final RunnableState state;
		private final Thread thread;
		private final Throwable cause;

		public RunnableEvent(RunnableState state, Thread thread, Throwable cause) {
			this.state = state;
			this.thread = thread;
			this.cause = cause;
		}

		public RunnableState getState() {
			return state;
		}

		public Thread getThread() {
			return thread;
		}

		public Throwable getCause() {
			return cause;
		}
	}
}
