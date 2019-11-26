package com.github.zj.dreamly.concurrent.collections;

import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;
import java.util.stream.IntStream;

/**
 * <h2>LockFreeQueueTest</h2>
 *
 * @author: 苍海之南
 * @since: 2019-11-26 10:27
 **/
public class LockFreeQueueTest {
	public static void main(String[] args) throws InterruptedException {
		final ConcurrentHashMap<Long, Object> data = new ConcurrentHashMap<>();
		final LockFreeQueue<Long> queue = new LockFreeQueue<>();
		ExecutorService service = Executors.newFixedThreadPool(10);
		IntStream.range(0, 5).boxed().map(l -> (Runnable) () -> {
			int counter = 0;
			while ((counter++) <= 10) {
				try {
					TimeUnit.MILLISECONDS.sleep(5);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
				queue.addLast(System.nanoTime());
			}
		}).forEach(service::submit);

		IntStream.range(0, 5).boxed().map(l -> (Runnable) () -> {
			int counter = 10;
			while (counter >= 0) {
				try {
					TimeUnit.MILLISECONDS.sleep(20);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
				Long value = queue.removeFirst();
				if (value == null) {
					continue;
				}
				counter--;
				System.out.println(value);
				data.put(value, new Object());
			}
		}).forEach(service::submit);

		service.shutdown();
		service.awaitTermination(1, TimeUnit.HOURS);

		System.out.println(data.size());
		System.out.println("=================");
		System.out.println(data.keys());
	}
}
