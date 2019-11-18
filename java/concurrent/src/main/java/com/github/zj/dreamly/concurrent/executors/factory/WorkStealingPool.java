package com.github.zj.dreamly.concurrent.executors.factory;

import java.util.List;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;
import java.util.stream.IntStream;

import static java.util.stream.Collectors.toList;

/**
 * <h2>WorkStealingPool</h2>
 *
 * 自动结束
 *
 * @author: 苍海之南
 * @since: 2019-11-18 17:36
 **/
public class WorkStealingPool {
	public static void main(String[] args) throws InterruptedException {

		ExecutorService executorService = Executors.newWorkStealingPool();
        /*Optional.of(Runtime.getRuntime().availableProcessors())
                .ifPresent(System.out::println);*/

		List<Callable<String>> callableList = IntStream.range(0, 20).boxed().map(i ->
			(Callable<String>) () -> {
				System.out.println("Thread " + Thread.currentThread().getName());
				sleep(2);
				return "Task-" + i;
			}
		).collect(toList());

		executorService.invokeAll(callableList).stream().map(future -> {
			try {
				return future.get();
			} catch (Exception e) {
				throw new RuntimeException(e);
			}
		}).forEach(System.out::println);

	}

	/**
	 * sleep the specify seconds.
	 */
	private static void sleep(long seconds) {
		try {
			TimeUnit.SECONDS.sleep(seconds);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}
}
