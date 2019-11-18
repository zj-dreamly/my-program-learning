package com.github.zj.dreamly.concurrent.executors.factory;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;
import java.util.stream.IntStream;

/**
 * <h2>FixedThreadPool</h2>
 *
 * @author: 苍海之南
 * @since: 2019-11-18 17:11
 **/
public class FixedThreadPool {

	public static void main(String[] args) throws InterruptedException {
        useFixedSizePool();
	}

	/**
	 * new ThreadPoolExecutor(nThreads, nThreads,
	 * 0L, TimeUnit.MILLISECONDS,
	 * new LinkedBlockingQueue<Runnable>());
	 */
	private static void useFixedSizePool() throws InterruptedException {
		ExecutorService executorService = Executors.newFixedThreadPool(10);
		System.out.println(((ThreadPoolExecutor) executorService).getActiveCount());

		IntStream.range(0, 10).boxed().forEach(i -> executorService
			.execute(() -> {
				try {
					TimeUnit.MILLISECONDS.sleep(10000);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
				System.out.println(Thread.currentThread().getName() + " [" + i + "]");
			}));

		TimeUnit.MILLISECONDS.sleep(1000);
		System.out.println(((ThreadPoolExecutor) executorService).getActiveCount());
	}
}
