package com.github.zj.dreamly.concurrent.util;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;

/**
 * @author 苍海之南
 */
public class CountDownLatchExample3 {

	public static void main(String[] args) throws InterruptedException {
		final CountDownLatch latch = new CountDownLatch(1);

		new Thread() {
			@Override
			public void run() {
				try {
					Thread.sleep(10000);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
				latch.countDown();

			}
		}.start();

		latch.await(1000, TimeUnit.MILLISECONDS);
		System.out.println("=============");
		latch.countDown();

	}
}
