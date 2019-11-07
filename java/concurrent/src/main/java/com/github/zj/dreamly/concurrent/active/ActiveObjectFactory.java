package com.github.zj.dreamly.concurrent.active;

/**
 * @author 苍海之南
 */
public final class ActiveObjectFactory {

	private ActiveObjectFactory() {
	}

	public static ActiveObject createActiveObject() {
		Servant servant = new Servant();
		ActivationQueue queue = new ActivationQueue();
		SchedulerThread schedulerThread = new SchedulerThread(queue);
		ActiveObjectProxy proxy = new ActiveObjectProxy(schedulerThread, servant);
		schedulerThread.start();
		return proxy;
	}
}
