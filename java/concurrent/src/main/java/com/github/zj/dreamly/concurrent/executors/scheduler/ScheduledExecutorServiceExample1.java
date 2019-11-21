package com.github.zj.dreamly.concurrent.executors.scheduler;

import java.util.concurrent.ExecutionException;
import java.util.concurrent.ScheduledFuture;
import java.util.concurrent.ScheduledThreadPoolExecutor;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicLong;

/**
 * @author 苍海之南
 */
public class ScheduledExecutorServiceExample1 {

	public static void main(String[] args) throws ExecutionException, InterruptedException {

		testSchedule();
		testScheduleAtFixedRate();
	}

	private static void testSchedule() throws ExecutionException, InterruptedException {
		ScheduledThreadPoolExecutor executor = new ScheduledThreadPoolExecutor(2);
		///ScheduledFuture<?> future = executor.schedule(() -> System.out.println("===i will be invoked!"), 2, TimeUnit.SECONDS);
		///System.out.println(future.cancel(true));
		ScheduledFuture<Integer> future = executor.schedule(() -> 2, 2, TimeUnit.SECONDS);
		System.out.println(future.get());
	}

	private static void testScheduleAtFixedRate() {
		ScheduledThreadPoolExecutor executor = new ScheduledThreadPoolExecutor(2);
		exex(executor);
	}

	static void exex(ScheduledThreadPoolExecutor executor) {
		final AtomicLong interval = new AtomicLong(0L);
		ScheduledFuture<?> future = executor.scheduleAtFixedRate(() ->
		{
			long currentTimeMillis = System.currentTimeMillis();
			if (interval.get() == 0) {
				System.out.printf("The first time trigger task at %d\n", currentTimeMillis);
			} else {
				System.out.printf("The actually spend [%d]\n", currentTimeMillis - interval.get());
			}
			interval.set(currentTimeMillis);
			System.out.println(Thread.currentThread().getName());
			try {
				TimeUnit.SECONDS.sleep(5);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}

		}, 1, 2, TimeUnit.SECONDS);
	}
}
