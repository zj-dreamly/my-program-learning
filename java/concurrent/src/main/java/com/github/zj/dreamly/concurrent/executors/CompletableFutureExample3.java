package com.github.zj.dreamly.concurrent.executors;

import java.util.Optional;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;
import java.util.function.Supplier;

/**
 * @author 苍海之南
 */
public class CompletableFutureExample3 {

	public static void main(String[] args) throws ExecutionException, InterruptedException {
		testHandleAsync();
		testThenRunAsync();
		testWhenComplete();
		testWhenCompleteAsync();
		Thread.currentThread().join();
	}

	private static void testWhenCompleteAsync() {
		CompletableFuture<String> future = CompletableFuture.supplyAsync(() -> "Hello");
		future.whenCompleteAsync((v, t) -> {
			try {
				System.out.println("==========");
				TimeUnit.SECONDS.sleep(2);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			System.out.println("===over==");
		});
	}

	private static void testWhenComplete() {
		CompletableFuture<String> future = CompletableFuture.supplyAsync(() -> "Hello");
		future.whenComplete((v, t) -> {
			try {
				System.out.println("==========");
				TimeUnit.SECONDS.sleep(2);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			System.out.println("===over==");
		});
		System.out.println("4444");
	}

	private static void testThenRunAsync() throws ExecutionException, InterruptedException {
		CompletableFuture<String> future = CompletableFuture.supplyAsync(() -> "Hello");
		future.thenRun(() -> System.out.println("done")).thenRunAsync(() -> System.out.println("done"));
		future.thenAcceptAsync(System.out::println);
		System.out.println(future.get());
	}

	private static void testHandleAsync() throws ExecutionException, InterruptedException {
		CompletableFuture<Integer> future = CompletableFuture.supplyAsync(
			(Supplier<String>) () -> {
				throw new RuntimeException("not get the data");
			}).handleAsync((s, t) -> {
			Optional.of(t).ifPresent(e -> System.out.println("Error"));
			return (s == null) ? 0 : s.length();
		});
		System.out.println(future.get());
	}
}
