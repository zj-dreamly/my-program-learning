package com.github.zj.dreamly.concurrent.executors.factory;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;
import java.util.stream.IntStream;

/**
 * <h2>CachedThreadPool</h2>
 *
 * 自动结束
 *
 * @author: 苍海之南
 * @since: 2019-11-18 17:14
 **/
public class CachedThreadPool {

	public static void main(String[] args) throws InterruptedException {
        useCachedThreadPool();
	}

	/**
	 * These pools will typically improve the performance
	 * of programs that execute many short-lived asynchronous tasks.
	 * <p>
	 * return new ThreadPoolExecutor(0, Integer.MAX_VALUE,
	 * 60L, TimeUnit.SECONDS,
	 * new SynchronousQueue<Runnable>());
	 */
	private static void useCachedThreadPool() throws InterruptedException {
		ExecutorService executorService = Executors.newCachedThreadPool();
		System.out.println(((ThreadPoolExecutor) executorService).getActiveCount());

		executorService.execute(() -> System.out.println("=========="));
		System.out.println(((ThreadPoolExecutor) executorService).getActiveCount());

		IntStream.range(0, 100).boxed().forEach(i -> executorService
			.execute(() -> {
				try {
					TimeUnit.SECONDS.sleep(10);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
				System.out.println(Thread.currentThread().getName() + " [" + i + "]");
			}));

		TimeUnit.SECONDS.sleep(1);
		System.out.println(((ThreadPoolExecutor) executorService).getActiveCount());
	}
}
