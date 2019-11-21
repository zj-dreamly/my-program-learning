package com.github.zj.dreamly.concurrent.executors;

import java.util.List;
import java.util.concurrent.*;
import java.util.stream.IntStream;

import static java.util.stream.Collectors.toList;

/**
 * @author 苍海之南
 */
public class CompletableFutureExample1 {
	public static void main(String[] args) throws InterruptedException {
		testCompletableFuture();
		testFuture();
		Thread.currentThread().join();
	}

	/**
	 * 调用只能一批一批进行，无法达到接力赛的那种效果
	 */
	private static void testFuture() throws InterruptedException {
		ExecutorService executorService = Executors.newFixedThreadPool(10);

		List<Callable<Integer>> tasks = IntStream.range(0, 10).boxed()
			.map(i -> (Callable<Integer>) CompletableFutureExample1::get).collect(toList());

		executorService.invokeAll(tasks).stream().map(future -> {
			try {
				return future.get();
			} catch (Exception e) {
				throw new RuntimeException(e);
			}
		}).parallel().forEach(CompletableFutureExample1::display);
	}

	/**
	 * 可以达到接力赛的效果，虽然是2批任务，但是单个线程跑完part1就能直接跑part2
	 */
	private static void testCompletableFuture() {
		IntStream.range(0, 10).boxed()
			.forEach(i -> CompletableFuture.supplyAsync(CompletableFutureExample1::get)
				.thenAccept(CompletableFutureExample1::display)
				.whenComplete((v, t) -> System.out.println(i + " DONE"))
			);
	}

	private static void display(int data) {
		int value = ThreadLocalRandom.current().nextInt(20);
		try {
			System.out.println(Thread.currentThread().getName() + "display will be sleep " + value);
			TimeUnit.SECONDS.sleep(value);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		System.out.println(Thread.currentThread().getName() + "display execute done " + data);
	}

	private static int get() {
		int value = ThreadLocalRandom.current().nextInt(20);
		try {
			System.out.println(Thread.currentThread().getName() + " get will be sleep " + value);
			TimeUnit.SECONDS.sleep(value);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		System.out.println(Thread.currentThread().getName() + "get execute done " + value);
		return value;
	}
}
