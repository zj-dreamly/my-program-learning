package com.github.zj.dreamly.concurrent;

import java.util.Collection;

/**
 * <h2>Lock</h2>
 *
 * @author: 苍海之南
 * @since: 2019-11-01 18:31
 **/
public interface Lock {
	class TimeOutException extends Exception {

		public TimeOutException(String message) {
			super(message);
		}
	}

	/**
	 * try to get a lock
	 *
	 * @throws InterruptedException it may throw {@link InterruptedException}
	 */
	void lock() throws InterruptedException;

	/**
	 * @param mills time of wait
	 *              try to get a lock
	 * @throws InterruptedException it may throw {@link InterruptedException}
	 * @throws TimeOutException  it may throw   {@link TimeOutException}
	 */
	void lock(long mills) throws InterruptedException, TimeOutException;

	/**
	 * unlock
	 */
	void unlock();

	/**
	 * get current threads of blocked
	 *
	 * @return the collection of blocked thread
	 */
	Collection<Thread> getBlockedThread();

	/**
	 * get current blocked threads size
	 *
	 * @return the num of blocked size
	 */
	int getBlockedSize();
}
