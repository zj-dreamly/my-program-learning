package com.github.zj.dreamly.concurrent.util.phaser;

import org.junit.Test;

import java.util.concurrent.Phaser;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

public class AwaitAdvanceInterruptiblyTest {

	/**
	 * awaitAdvanceInterruptibly
	 */
	@Test
	public void test1() throws InterruptedException {
		final Phaser phaser = new Phaser(3);
		Thread thread = new Thread(phaser::arriveAndAwaitAdvance);
		thread.start();
		System.out.println("=======================");
		TimeUnit.SECONDS.sleep(10);

		thread.interrupt();
		System.out.println("===thread.interrupt===");
	}

	@Test
	public void test2() throws InterruptedException {
		final Phaser phaser = new Phaser(3);
		Thread thread = new Thread(() -> {
			try {
				phaser.awaitAdvanceInterruptibly(10);
				System.out.println("*****************");
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		});

		thread.start();

		System.out.println("=======================");
		TimeUnit.SECONDS.sleep(10);
		thread.interrupt();
		System.out.println("===thread.interrupt===");
	}

	@Test
	public void test3() throws InterruptedException {

		final Phaser phaser = new Phaser(3);
		Thread thread = new Thread(() -> {
			try {
				phaser.awaitAdvanceInterruptibly(10, 10, TimeUnit.SECONDS);
				System.out.println("*****************");
			} catch (InterruptedException | TimeoutException e) {
				e.printStackTrace();
			}
		});

		thread.start();
	}
}
