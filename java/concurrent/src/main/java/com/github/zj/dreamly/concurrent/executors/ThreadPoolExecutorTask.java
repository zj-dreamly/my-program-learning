package com.github.zj.dreamly.concurrent.executors;

import java.util.List;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;
import java.util.stream.IntStream;

/**
 * @author 苍海之南
 * <p>
 * <p>
 * shutdown
 * <p>
 * 20 threads
 * 10 threads work
 * 10 idle
 * <p>
 * shutdown invoked
 * <p>
 * 1. 10 waiting to finished the work.
 * 2. 10 interruped the idle works.
 * 3. 20 idle threads will exist
 * <p>
 * <p>
 * <p>
 * shutdownNow
 * <p>
 * 10 threads queue elements 10
 * 10 running
 * 10 stored in the blocking queue.
 * <p>
 * 1.return list<Runnable> remain 10 un handle runnable.
 * 2.interruped all of threads in the pool.
 */

public class ThreadPoolExecutorTask {
	public static void main(String[] args) {
		ExecutorService executorService = new ThreadPoolExecutor(10, 20,
			30, TimeUnit.SECONDS,
			new ArrayBlockingQueue<>(10), Thread::new, new ThreadPoolExecutor.AbortPolicy());

		IntStream.range(0, 20).boxed().forEach(i ->
			executorService.execute(() -> {
				try {
					TimeUnit.SECONDS.sleep(20);
					System.out.println(Thread.currentThread().getName() + " [" + i + "] finish done.");
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			})
		);

		List<Runnable> runnableList = null;
		try {
			runnableList = executorService.shutdownNow();
		} catch (Exception e) {
			e.printStackTrace();
		}

		///executorService.awaitTermination(1, TimeUnit.HOURS);
		System.out.println("==============over=================");
		System.out.println(runnableList);
		System.out.println(runnableList.size());
	}
}
