package com.github.zj.dreamly.concurrent.util.phaser;

import java.util.Random;
import java.util.concurrent.Phaser;
import java.util.concurrent.TimeUnit;
import java.util.stream.IntStream;

/**
 * <h2>ArriveAndAwaitAdvanceTest</h2>
 *
 * @author: 苍海之南
 * @since: 2019-11-18 09:41
 **/
public class ArriveAndAwaitAdvanceTest1 {

	private final static Random RANDOM = new Random(System.currentTimeMillis());

	public static void main(String[] args) {

		final Phaser phaser = new Phaser();

		IntStream.rangeClosed(1, 5).boxed().map(i -> phaser).forEach(Task::new);

		phaser.register();

		phaser.arriveAndAwaitAdvance();
		System.out.println("all of worker finished the task.");

	}

	static class Task extends Thread {

		private final Phaser phaser;

		Task(Phaser phaser) {
			this.phaser = phaser;
			this.phaser.register();
			start();
		}

		@Override
		public void run() {
			System.out.println("The Worker [" + getName() + "] is working...");
			try {
				TimeUnit.SECONDS.sleep(RANDOM.nextInt(5));
			} catch (InterruptedException e) {
				e.printStackTrace();
			}

			phaser.arriveAndAwaitAdvance();

		}
	}
}
