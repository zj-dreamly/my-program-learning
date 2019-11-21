package com.github.zj.dreamly.concurrent.executors;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;

/**
 * @author 苍海之南
 */
public class CompletableFutureExample2 {
	public static void main(String[] args) throws InterruptedException, ExecutionException {
		supplyAsync();

		runAsync();

		Future<Void> future = completed("HEllo");
		System.out.println(future.isDone());

		System.out.println(">>>>>" + anyOf().get());

		//allOf();
		Thread.currentThread().join();
	}

	private static void create() {
		CompletableFuture future = new CompletableFuture();

		Object s = null;

		CompletableFuture.<Object>supplyAsync(() -> s);
	}

	private static void allOf() {
		CompletableFuture.allOf(CompletableFuture.runAsync(() -> {
				try {
					System.out.println("1=====Start");
					TimeUnit.SECONDS.sleep(5);
					System.out.println("1=====End");
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}).whenComplete((v, t) -> System.out.println("=====over===")),
			CompletableFuture.supplyAsync(CompletableFutureExample2::apply)
				.whenComplete((v, t) -> System.out.println(v + "=====over==="))
		);
	}

	private static Future<?> anyOf() {
		return CompletableFuture.anyOf(CompletableFuture.runAsync(() -> {
				try {
					System.out.println("1=====Start");
					TimeUnit.SECONDS.sleep(5);
					System.out.println("1=====End");
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}).whenComplete((v, t) -> System.out.println("=====over===")),
			CompletableFuture.supplyAsync(CompletableFutureExample2::apply)
				.whenComplete((v, t) -> System.out.println(v + "=====over==="))
		);
	}

	private static String apply() {
		try {
			System.out.println("2=====Start");
			TimeUnit.SECONDS.sleep(4);
			System.out.println("2=====End");
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		return "Hello";
	}

	private static Future<Void> completed(String data) {
		return CompletableFuture.completedFuture(data).thenAccept(System.out::println);
	}

	private static Future<?> runAsync() {
		return CompletableFuture.runAsync(() -> {
			try {
				System.out.println("Obj=====Start");
				TimeUnit.SECONDS.sleep(5);
				System.out.println("Obj=====End");
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}).whenComplete((v, t) -> System.out.println("=====over==="));
	}

	private static void supplyAsync() {

		CompletableFuture.supplyAsync(Object::new)
			.thenAcceptAsync(obj -> {
				try {
					System.out.println("Obj=====Start");
					TimeUnit.SECONDS.sleep(5);
					System.out.println("Obj=====" + obj);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}).runAfterBoth(CompletableFuture.supplyAsync(() -> "Hello")
			.thenAcceptAsync(s -> {
				try {
					System.out.println("String=====Start");
					TimeUnit.SECONDS.sleep(3);

					System.out.println("String=====" + s);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}), () -> System.out.println("===Finished==="));
	}
}
