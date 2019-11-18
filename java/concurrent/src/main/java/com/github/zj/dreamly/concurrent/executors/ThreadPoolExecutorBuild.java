package com.github.zj.dreamly.concurrent.executors;

import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

public class ThreadPoolExecutorBuild {

	public static void main(String[] args) {
		ThreadPoolExecutor executorService = (ThreadPoolExecutor) buildThreadPoolExecutor();

		int activeCount = -1;
		int queueSize = -1;

		while (true) {
			if (activeCount != executorService.getActiveCount() ||
				queueSize != executorService.getQueue().size()) {
				System.out.println("getActiveCount：" + executorService.getActiveCount());
				System.out.println("getCorePoolSize：" + executorService.getCorePoolSize());
				System.out.println("queueSize：" + executorService.getQueue().size());
				System.out.println("getMaximumPoolSize：" + executorService.getMaximumPoolSize());
				activeCount = executorService.getActiveCount();
				queueSize = executorService.getQueue().size();
				System.out.println("============================================");
			}
		}
	}

	/**
	 * Testing point.
	 * <p>
	 * 1.coreSize=1,MaxSize=2 blockingQueue size =1. what happen when submit 3 task?
	 * 2.coreSize=1,MaxSize=2 blockingQueue size =5  what happen when submit 7 task?
	 * 3.coreSize=1,MaxSize=2 blockingQueue size =5  what happen when submit 8 task?
	 * <p>
	 * <p>
	 * <p>
	 * int corePoolSize,
	 * int maximumPoolSize,
	 * long keepAliveTime,
	 * TimeUnit unit,
	 * BlockingQueue<Runnable> workQueue,
	 * ThreadFactory threadFactory,
	 * RejectedExecutionHandler handler
	 */
	private static ExecutorService buildThreadPoolExecutor() {
		ExecutorService executorService = new ThreadPoolExecutor(1, 2, 30, TimeUnit.SECONDS,
			new ArrayBlockingQueue<>(1), Thread::new, new ThreadPoolExecutor.AbortPolicy());
		System.out.println("== The ThreadPoolExecutor create done.");
		executorService.execute(() -> sleepSeconds(100));
		executorService.execute(() -> sleepSeconds(10));
		executorService.execute(() -> sleepSeconds(10));

		return executorService;

	}

	private static void sleepSeconds(long seconds) {
		try {
			System.out.println("* " + Thread.currentThread().getName() + " *");
			TimeUnit.SECONDS.sleep(seconds);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}
}
