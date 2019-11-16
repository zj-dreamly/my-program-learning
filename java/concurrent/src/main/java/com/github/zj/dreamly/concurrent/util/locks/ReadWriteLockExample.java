package com.github.zj.dreamly.concurrent.util.locks;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantReadWriteLock;

/**
 * @author 苍海之南
 */
public class ReadWriteLockExample {

	/**
	 * W W X
	 * W R X
	 * R W X
	 * R R O
	 */
	private final static ReentrantReadWriteLock READ_WRITE_LOCK = new ReentrantReadWriteLock(true);

	private final static Lock READ_LOCK = READ_WRITE_LOCK.readLock();

	private final static Lock WRITE_LOCK = READ_WRITE_LOCK.writeLock();

	private final static List<Long> DATA = new ArrayList<>();

	public static void main(String[] args) throws InterruptedException {

		Thread thread1 = new Thread(ReadWriteLockExample::write);
		thread1.start();

		TimeUnit.SECONDS.sleep(1);

		Thread thread2 = new Thread(ReadWriteLockExample::read);
		thread2.start();

	}

	private static void write() {
		try {
			WRITE_LOCK.lock();
			DATA.add(System.currentTimeMillis());
			TimeUnit.SECONDS.sleep(5);
		} catch (InterruptedException e) {
			e.printStackTrace();
		} finally {
			WRITE_LOCK.unlock();
		}
	}

	private static void read() {
		try {
			READ_LOCK.lock();
			DATA.forEach(System.out::println);
			TimeUnit.SECONDS.sleep(5);
			System.out.println(Thread.currentThread().getName() + "=========================");
		} catch (InterruptedException e) {
			e.printStackTrace();
		} finally {
			READ_LOCK.unlock();
		}
	}

}
