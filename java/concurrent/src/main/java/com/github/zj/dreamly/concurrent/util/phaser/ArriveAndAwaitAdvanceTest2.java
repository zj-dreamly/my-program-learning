package com.github.zj.dreamly.concurrent.util.phaser;

import java.util.Random;
import java.util.concurrent.Phaser;
import java.util.concurrent.TimeUnit;

/**
 * @author 苍海之南
 */
public class ArriveAndAwaitAdvanceTest2 {

	private final static Random random = new Random(System.currentTimeMillis());

	/**
	 * running
	 * <p>
	 * bicycle
	 * <p>
	 * long jump
	 */
	public static void main(String[] args) {
		final Phaser phaser = new Phaser(5);

		for (int i = 1; i < 6; i++) {
			new Athletes(i, phaser).start();
		}

	}

	static class Athletes extends Thread {

		private final int no;

		private final Phaser phaser;

		Athletes(int no, Phaser phaser) {
			this.no = no;
			this.phaser = phaser;
		}

		@Override
		public void run() {
			try {
				System.out.println(no + ": start running.");
				TimeUnit.SECONDS.sleep(random.nextInt(5));
				System.out.println(no + ": end running.");
				System.out.println("getPhase()=>" + phaser.getPhase());
				phaser.arriveAndAwaitAdvance();

				System.out.println(no + ": start bicycle.");
				TimeUnit.SECONDS.sleep(random.nextInt(5));
				System.out.println(no + ": end bicycle.");
				System.out.println("getPhase()=>" + phaser.getPhase());
				phaser.arriveAndAwaitAdvance();

				System.out.println(no + ": start long jump.");
				TimeUnit.SECONDS.sleep(random.nextInt(5));
				System.out.println(no + ": end long jump.");
				System.out.println("getPhase()=>" + phaser.getPhase());
				phaser.arriveAndAwaitAdvance();

			} catch (InterruptedException e) {
				e.printStackTrace();
			}

		}
	}
}
