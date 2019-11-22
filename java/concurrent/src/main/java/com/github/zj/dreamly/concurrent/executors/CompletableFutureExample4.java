package com.github.zj.dreamly.concurrent.executors;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.TimeUnit;

/**
 * @author 苍海之南
 */
public class CompletableFutureExample4 {
	public static void main(String[] args) throws InterruptedException {
		thenAcceptBoth();
		acceptEither();
		runAfterBoth();
		runAfterEither();
		combine();
		compose();
		Thread.currentThread().join();
	}

	private static void compose() {
		CompletableFuture.supplyAsync(() -> {
			System.out.println("start the compose1");
			sleep(5);
			System.out.println("end the compose1");
			return "compose-1";
		}).thenCompose(s -> CompletableFuture.supplyAsync(() -> {
			System.out.println("start the compose2");
			sleep(3);
			System.out.println("end the compose2");
			return s.length();
		})).thenAccept(System.out::println);
	}

	private static void combine() {
		CompletableFuture.supplyAsync(() -> {
			System.out.println("start the combine1");
			sleep(5);
			System.out.println("end the combine1");
			return "combine-1";
		}).thenCombine(CompletableFuture.supplyAsync(() -> {
			System.out.println("start the combine2");
			sleep(3);
			System.out.println("end the combine2");
			return 100;
		}), (s, i) -> s.length() > i).whenComplete((v, t) -> System.out.println(v));
	}

	private static void runAfterEither() {
		CompletableFuture.supplyAsync(() -> {
			System.out.println("start the runAfterEither1");
			sleep(5);
			System.out.println("end the runAfterEither1");
			return "acceptEither-1";
		}).runAfterEither(CompletableFuture.supplyAsync(() -> {
			System.out.println("start the runAfterEither2");
			sleep(3);
			System.out.println("end the runAfterEither2");
			return 100;
		}), () -> System.out.println("DONE"));
	}

	private static void runAfterBoth() {
		CompletableFuture.supplyAsync(() -> {
			System.out.println("start the runAfterBoth");
			sleep(5);
			System.out.println("end the runAfterBoth");
			return "acceptEither-1";
		}).runAfterBothAsync(CompletableFuture.supplyAsync(() -> {
			System.out.println("start the runAfterBoth");
			sleep(3);
			System.out.println("end the runAfterBoth");
			return 100;
		}), () -> System.out.println("DONE"));
	}

	private static void acceptEither() {
		CompletableFuture.supplyAsync(() -> {
			System.out.println("start the acceptEither1");
			sleep(5);
			System.out.println("end the acceptEither1");
			return "acceptEither-1";
		}).acceptEither(CompletableFuture.supplyAsync(() -> {
			System.out.println("start the acceptEither2");
			sleep(3);
			System.out.println("end the acceptEither2");
			return "acceptEither-2";
		}), System.out::println);
	}

	private static void thenAcceptBoth() {
		CompletableFuture.supplyAsync(() -> {
			System.out.println("start the supplyAsync");
			sleep(5);
			System.out.println("end the supplyAsync");
			return "thenAcceptBoth";
		}).thenAcceptBoth(CompletableFuture.supplyAsync(() -> {
			System.out.println("start the thenAcceptBoth");
			sleep(5);
			System.out.println("end the thenAcceptBoth");
			return 100;
		}), (s, i) -> System.out.println(s + "--" + i));
	}

	/**
	 * Sleep the specify seconds.
	 */
	private static void sleep(long seconds) {
		try {
			TimeUnit.SECONDS.sleep(seconds);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}
}
