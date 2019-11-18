package com.github.zj.dreamly.concurrent.executors.factory;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;
import java.util.stream.IntStream;

/**
 * <h2>SingleThreadExecutor</h2>
 *
 * @author: 苍海之南
 * @since: 2019-11-18 17:13
 **/
public class SingleThreadExecutor {

	public static void main(String[] args) throws InterruptedException {
		useSinglePool();
	}

	/**
	 * SingleThreadExecutor difference between one Thread.
	 * <p>
	 * Thread will die after finish work, but SingleThreadExecutor can always alive.
	 * Thread can not put the submitted runnable to the cache queue but SingleThreadExecutor can do this.
	 * <p>
	 * <p>
	 * new FinalizableDelegatedExecutorService
	 * (new ThreadPoolExecutor(1, 1,
	 * 0L, TimeUnit.MILLISECONDS,
	 * new LinkedBlockingQueue<Runnable>()));
	 * <p>
	 * new ThreadPoolExecutor(1, 1,
	 * 0L, TimeUnit.MILLISECONDS,
	 * new LinkedBlockingQueue<Runnable>())
	 * =====
	 * <p>
	 * Executors.newFixedThreadPool(1);
	 */
	private static void useSinglePool() throws InterruptedException {
		ExecutorService executorService = Executors.newSingleThreadExecutor();

		IntStream.range(0, 10).boxed().forEach(i -> executorService
			.execute(() -> {
				try {
					TimeUnit.SECONDS.sleep(10);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
				System.out.println(Thread.currentThread().getName() + " [" + i + "]");
			}));

		TimeUnit.SECONDS.sleep(1);
	}
}
