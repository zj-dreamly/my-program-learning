package com.github.zj.dreamly.concurrent.util.cyclicbarrier;

import java.util.concurrent.BrokenBarrierException;
import java.util.concurrent.CyclicBarrier;
import java.util.concurrent.TimeUnit;

/**
 * @author 苍海之南
 */
public class CyclicBarrierExample1 {
	public static void main(String[] args) throws InterruptedException {

		final CyclicBarrier cyclicBarrier = new CyclicBarrier(2, new Runnable() {
			@Override
			public void run() {
				System.out.println("all of finished.");
			}
		});

		new Thread(() -> {
			try {
				TimeUnit.SECONDS.sleep(20);
				System.out.println("T1 finished.");
				cyclicBarrier.await();

				System.out.println("T1 The other thread finished too.");
			} catch (InterruptedException | BrokenBarrierException e) {
				e.printStackTrace();
			}
		}).start();

		new Thread(() -> {
			try {
				TimeUnit.SECONDS.sleep(10);
				System.out.println("T2 finished.");
				cyclicBarrier.await();
				System.out.println("T2 The other thread finished too.");
			} catch (InterruptedException | BrokenBarrierException e) {
				e.printStackTrace();
			}
		}).start();

		///cyclicBarrier.await();

		while (true) {
			System.out.println(cyclicBarrier.getNumberWaiting());
			System.out.println(cyclicBarrier.getParties());
			System.out.println(cyclicBarrier.isBroken());
			TimeUnit.MILLISECONDS.sleep(1);
		}

	}
}
