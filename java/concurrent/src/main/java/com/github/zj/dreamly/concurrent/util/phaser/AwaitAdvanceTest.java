package com.github.zj.dreamly.concurrent.util.phaser;

import java.util.concurrent.Phaser;
import java.util.concurrent.TimeUnit;
import java.util.stream.IntStream;

public class AwaitAdvanceTest {

	/**
	 * awaitAdvance can decremental the arrived parties?
	 */
	public static void main(String[] args) throws InterruptedException {

		final Phaser phaser = new Phaser(7);

		IntStream.rangeClosed(0, 5).boxed().map(i -> phaser).forEach(AwaitAdvanceTask::new);

		phaser.awaitAdvance(phaser.getPhase());
		System.out.println("=======================");

	}

	private static class AwaitAdvanceTask extends Thread {

		private final Phaser phaser;

		private AwaitAdvanceTask(Phaser phaser) {
			this.phaser = phaser;
			start();
		}

		@Override
		public void run() {
			try {
				TimeUnit.SECONDS.sleep(5);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}

			phaser.arrive();
			System.out.println(getName() + " finished work.");
		}
	}
}
