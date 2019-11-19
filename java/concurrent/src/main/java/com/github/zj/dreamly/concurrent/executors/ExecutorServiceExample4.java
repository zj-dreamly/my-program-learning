package com.github.zj.dreamly.concurrent.executors;

import java.util.Collection;
import java.util.List;
import java.util.concurrent.*;
import java.util.stream.IntStream;

import static java.util.stream.Collectors.toList;

/**
 * @author 苍海之南
 */
public class ExecutorServiceExample4 {

	public static void main(String[] args) throws ExecutionException, InterruptedException, TimeoutException {
		testInvokeAny();
		testInvokeAnyTimeOut();
		testInvokeAll();
		testInvokeAllTimeOut();
		testSubmitRunnable();
		testSubmitRunnableWithResult();
	}

	/**
	 * Question:
	 * when the result returned, other callable will be keep on process?
	 * <p>
	 * Answer:
	 * Other callable will be canceled (if other not process finished).
	 * <p>
	 * Note:
	 * The invokeAny will be blocked caller.
	 * {@link ExecutorService#invokeAny(Collection)}
	 */
	private static void testInvokeAny() throws ExecutionException, InterruptedException {
		ExecutorService executorService = Executors.newFixedThreadPool(10);

		List<Callable<Integer>> callableList = IntStream.range(0, 5).boxed().map(
			i -> (Callable<Integer>) () -> {
				TimeUnit.SECONDS.sleep(ThreadLocalRandom.current().nextInt(20));
				System.out.println(Thread.currentThread().getName() + " :" + i);
				return i;
			}
		).collect(toList());
		Integer value = executorService.invokeAny(callableList);
		System.out.println("===========finished=============");
		System.out.println(value);
	}

	/**
	 * {@link ExecutorService#invokeAny(Collection, long, TimeUnit)}
	 */
	private static void testInvokeAnyTimeOut() throws ExecutionException, InterruptedException, TimeoutException {
		ExecutorService executorService = Executors.newFixedThreadPool(10);

		Integer value = executorService.invokeAny(
			IntStream.range(0, 5).boxed().map(
				i -> (Callable<Integer>) () -> {
					TimeUnit.SECONDS.sleep(ThreadLocalRandom.current().nextInt(100));
					System.out.println(Thread.currentThread().getName() + " :" + i);
					return i;
				}
			).collect(toList()), 1, TimeUnit.SECONDS
		);
		System.out.println("===========finished=============");
		System.out.println(value);
	}

	/**
	 * {@link ExecutorService#invokeAll(Collection)}
	 * <p>
	 * RxJava
	 *
	 * @throws InterruptedException InterruptedException
	 */
	private static void testInvokeAll() throws InterruptedException {
		ExecutorService executorService = Executors.newFixedThreadPool(10);
		executorService.invokeAll(
			IntStream.range(0, 5).boxed().map(
				i -> (Callable<Integer>) () -> {
					TimeUnit.SECONDS.sleep(ThreadLocalRandom.current().nextInt(10));
					System.out.println(Thread.currentThread().getName() + " :" + i);
					return i;
				}
			).collect(toList())
		).parallelStream().map(future -> {
			try {
				return future.get();
			} catch (Exception e) {
				throw new RuntimeException(e);
			}
		}).forEach(System.out::println);

		System.out.println("===========finished=============");
	}

	/**
	 * {@link ExecutorService#invokeAll(Collection, long, TimeUnit)}
	 * <p>
	 *
	 * @throws InterruptedException InterruptedException
	 */
	private static void testInvokeAllTimeOut() throws InterruptedException {
		ExecutorService executorService = Executors.newFixedThreadPool(10);
		executorService.invokeAll(
			IntStream.range(0, 5).boxed().map(
				i -> (Callable<Integer>) () -> {
					TimeUnit.SECONDS.sleep(ThreadLocalRandom.current().nextInt(10));
					System.out.println(Thread.currentThread().getName() + " :" + i);
					return i;
				}
			).collect(toList()), 1, TimeUnit.SECONDS
		).parallelStream().map(future -> {
			try {
				return future.get();
			} catch (Exception e) {
				throw new RuntimeException(e);
			}
		}).forEach(System.out::println);

		System.out.println("===========finished=============");
	}

	/**
	 * {@link ExecutorService#submit(Runnable)}
	 */
	private static void testSubmitRunnable() throws ExecutionException, InterruptedException {
		ExecutorService executorService = Executors.newFixedThreadPool(10);
		Future<?> future = executorService.submit(() -> {
			try {
				TimeUnit.SECONDS.sleep(3);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		});
		Object res = future.get();
		System.out.println("R:" + res);
	}

	/**
	 * {@link ExecutorService#submit(Runnable, Object)}
	 */
	private static void testSubmitRunnableWithResult() throws ExecutionException, InterruptedException {
		ExecutorService executorService = Executors.newFixedThreadPool(10);
		String result = "DONE";
		Future<String> future = executorService.submit(() -> {
			try {
				TimeUnit.SECONDS.sleep(3);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}, result);
		System.out.println(future.get());

	}
}
