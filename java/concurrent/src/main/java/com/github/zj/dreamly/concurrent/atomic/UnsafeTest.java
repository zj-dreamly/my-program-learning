package com.github.zj.dreamly.concurrent.atomic;

import sun.misc.Unsafe;

import java.lang.reflect.Field;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicLong;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * @author 苍海之南
 */
public class UnsafeTest {

	/**
	 * Java is a safe programming language and prevents programmer
	 * from doing a lot of stupid mistakes,
	 * most of which based on memory management.
	 * But, there is a way to do such mistakes intentionally,
	 * using Unsafe class.
	 */
	public static void main(String[] args) throws Exception {


		/*
		 * StupidCounter
		 *  Counter result:9459754
		 *  Time passed in ms:3580
		 *
		 * SyncCounter
		 *  Counter result:10000000
		 *  Time passed in ms:4894
		 *
		 *LockCounter
		 *  Counter result:10000000
		 *  Time passed in ms:2670
		 *
		 *AtomicCounter
		 *Counter result:10000000
		 *  Time passed in ms:4077
		 *
		 *Counter result:10000000
		 *Time passed in ms:3875s
		 */
		ExecutorService service = Executors.newFixedThreadPool(1000);
		Counter counter = new CasCounter();

		long start = System.currentTimeMillis();
		for (int i = 0; i < 1000; i++) {
			service.submit(new CounterRunnable(counter, 10000));
		}
		service.shutdown();
		service.awaitTermination(1, TimeUnit.HOURS);
		long end = System.currentTimeMillis();

		System.out.println("Counter result:" + counter.getCounter());
		System.out.println("Time passed in ms:" + (end - start));
	}

	private static Unsafe getUnsafe() {
		try {
			Field f = Unsafe.class.getDeclaredField("theUnsafe");
			f.setAccessible(true);
			return (Unsafe) f.get(null);
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	interface Counter {
		void increment();

		long getCounter();
	}

	static class StupidCounter implements Counter {

		private long counter = 0;

		@Override
		public void increment() {
			counter++;
		}

		@Override
		public long getCounter() {
			return counter;
		}
	}

	static class SyncCounter implements Counter {

		private long counter = 0;

		@Override
		public synchronized void increment() {
			counter++;
		}

		@Override
		public long getCounter() {
			return counter;
		}
	}

	static class LockCounter implements Counter {

		private long counter = 0;

		private final Lock lock = new ReentrantLock();

		@Override
		public void increment() {
			try {
				lock.lock();
				counter++;
			} finally {
				lock.unlock();
			}
		}

		@Override
		public long getCounter() {
			return counter;
		}
	}

	static class AtomicCounter implements Counter {

		private AtomicLong counter = new AtomicLong();

		@Override
		public void increment() {
			counter.incrementAndGet();
		}

		@Override
		public long getCounter() {
			return counter.get();
		}
	}

	static class CasCounter implements Counter {

		private volatile long counter = 0;
		private Unsafe unsafe;
		private long offset;

		CasCounter() throws Exception {
			unsafe = getUnsafe();
			offset = unsafe.objectFieldOffset(CasCounter.class.getDeclaredField("counter"));
		}

		@Override
		public void increment() {
			long current = counter;
			while (!unsafe.compareAndSwapLong(this, offset, current, current + 1)) {
				current = counter;
			}
		}

		@Override
		public long getCounter() {
			return counter;
		}
	}

	static class CounterRunnable implements Runnable {
		private final Counter counter;
		private final int num;

		CounterRunnable(Counter counter, int num) {
			this.counter = counter;
			this.num = num;
		}

		@Override
		public void run() {
			for (int i = 0; i < num; i++) {
				counter.increment();
			}
		}
	}
}
