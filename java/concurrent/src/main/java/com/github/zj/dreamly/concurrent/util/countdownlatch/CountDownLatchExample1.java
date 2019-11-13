package com.github.zj.dreamly.concurrent.util.countdownlatch;

import java.util.Random;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * <h2>CountDownLatchExample1</h2>
 *
 * @author: 苍海之南
 * @since: 2019-11-13 10:32
 **/
public class CountDownLatchExample1 {
	private static Random random = new Random(System.currentTimeMillis());

	private static ExecutorService executor = Executors.newFixedThreadPool(2);

	private static final CountDownLatch latch = new CountDownLatch(10);

	public static void main(String[] args) throws InterruptedException {
		//(1)
		int[] data = query();
		//(2)
		for (int i = 0; i < data.length; i++) {
			executor.execute(new SimpleRunnable(data, i, latch));
		}

		//(3)
		latch.await();
		System.out.println("all of work finish done.");
		executor.shutdown();

	}

	static class SimpleRunnable implements Runnable {

		private final int[] data;

		private final int index;
		private final CountDownLatch latch;

		SimpleRunnable(int[] data, int index, CountDownLatch latch) {
			this.data = data;
			this.index = index;
			this.latch = latch;
		}

		@Override
		public void run() {
			try {
				Thread.sleep(random.nextInt(2000));
			} catch (InterruptedException e) {
				e.printStackTrace();
			}

			int value = data[index];
			if (value % 2 == 0) {
				data[index] = value * 2;
			} else {
				data[index] = value * 10;
			}

			System.out.println(Thread.currentThread().getName() + " finished.");
			latch.countDown();
		}
	}

	private static int[] query() {
		return new int[]{1, 2, 3, 4, 5, 6, 7, 8, 9, 10};
	}
}
