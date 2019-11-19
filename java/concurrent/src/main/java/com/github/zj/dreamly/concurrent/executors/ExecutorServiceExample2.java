package com.github.zj.dreamly.concurrent.executors;

import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/**
 * @author 苍海之南
 */
public class ExecutorServiceExample2 {
	public static void main(String[] args) throws InterruptedException {
		testAbortPolicy();
		testDiscardPolicy();
		testCallerRunsPolicy();
		testDiscardOldestPolicy();
	}

	private static void testAbortPolicy() throws InterruptedException {
		ExecutorService executorService = new ThreadPoolExecutor(1,
			2, 30, TimeUnit.SECONDS,
			new ArrayBlockingQueue<>(1), Thread::new, new ThreadPoolExecutor.AbortPolicy());

		apply(executorService);
	}

	private static void apply(ExecutorService executorService) throws InterruptedException {
		for (int i = 0; i < 3; i++) {
			executorService.execute(() -> {
				try {
					TimeUnit.SECONDS.sleep(100);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			});
		}

		TimeUnit.SECONDS.sleep(1);
		executorService.execute(() -> System.out.println("x"));
	}

	private static void testDiscardPolicy() throws InterruptedException {
		ExecutorService executorService = new ThreadPoolExecutor(1,
			2, 30, TimeUnit.SECONDS,
			new ArrayBlockingQueue<>(1), Thread::new, new ThreadPoolExecutor.DiscardPolicy());

		apply(executorService);
		System.out.println("=================================");
	}

	private static void testCallerRunsPolicy() throws InterruptedException {
		ExecutorService executorService = new ThreadPoolExecutor(1,
			2, 30, TimeUnit.SECONDS,
			new ArrayBlockingQueue<>(1), Thread::new, new ThreadPoolExecutor.CallerRunsPolicy());

		for (int i = 0; i < 3; i++) {
			executorService.execute(() -> {
				try {
					TimeUnit.SECONDS.sleep(100);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			});
		}

		TimeUnit.SECONDS.sleep(1);
		executorService.execute(() -> {
			System.out.println("x");

			System.out.println(Thread.currentThread().getName());
		});
		System.out.println("=================================");
	}

	private static void testDiscardOldestPolicy() throws InterruptedException {
		ExecutorService executorService = new ThreadPoolExecutor(1,
			2, 30, TimeUnit.SECONDS,
			new ArrayBlockingQueue<>(1), Thread::new, new ThreadPoolExecutor.DiscardOldestPolicy());

		for (int i = 0; i < 3; i++) {
			executorService.execute(() -> {
				try {
					TimeUnit.SECONDS.sleep(5);
					System.out.println("I come from lambda.");
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			});
		}

		TimeUnit.SECONDS.sleep(1);
		executorService.execute(() -> {
			System.out.println("x");
			System.out.println(Thread.currentThread().getName());
		});
		System.out.println("=================================");
	}

}
