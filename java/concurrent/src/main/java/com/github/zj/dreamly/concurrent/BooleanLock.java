package com.github.zj.dreamly.concurrent;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Optional;

/**
 * <h2>BooleanLock</h2>
 *
 * @author: 苍海之南
 * @since: 2019-11-01 18:29
 **/
public class BooleanLock implements Lock {
	/**
	 * The initValue is true indicated the lock have be get.
	 * The initValue is false indicated the lock is free (other thread can get this.)
	 */
	private boolean initValue;

	private Collection<Thread> blockedThreadCollection = new ArrayList<>();

	private Thread currentThread;

	public BooleanLock() {
		this.initValue = false;
	}

	@Override
	public synchronized void lock() throws InterruptedException {
		while (initValue) {
			blockedThreadCollection.add(Thread.currentThread());
			this.wait();
		}

		blockedThreadCollection.remove(Thread.currentThread());
		this.initValue = true;
		this.currentThread = Thread.currentThread();
	}

	@Override
	public synchronized void lock(long mills) throws InterruptedException, TimeOutException {
		if (mills <= 0) {
			lock();
		}

		long hasRemaining = mills;
		long endTime = System.currentTimeMillis() + mills;
		while (initValue) {
			if (hasRemaining <= 0) {
				throw new TimeOutException("Time out");
			}
			blockedThreadCollection.add(Thread.currentThread());
			this.wait(mills);
			hasRemaining = endTime - System.currentTimeMillis();
		}

		this.initValue = true;
		this.currentThread = Thread.currentThread();

	}

	@Override
	public synchronized void unlock() {
		if (Thread.currentThread() == currentThread) {
			this.initValue = false;
			Optional.of(Thread.currentThread().getName() + " release the lock monitor.")
				.ifPresent(System.out::println);
			this.notifyAll();
		}
	}

	@Override
	public Collection<Thread> getBlockedThread() {
		return Collections.unmodifiableCollection(blockedThreadCollection);
	}

	@Override
	public int getBlockedSize() {
		return blockedThreadCollection.size();
	}
}
