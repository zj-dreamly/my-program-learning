package com.github.zj.dreamly.concurrent.util.phaser;

import java.util.concurrent.Phaser;
import java.util.concurrent.TimeUnit;

/**
 * @author 苍海之南
 */
public class OnAdvanceTest {
	public static void main(String[] args) throws InterruptedException {
		final Phaser phaser = new Phaser(1);

		System.out.println(phaser.getPhase());

		phaser.arriveAndAwaitAdvance();
		System.out.println(phaser.getPhase());

		phaser.arriveAndAwaitAdvance();
		System.out.println(phaser.getPhase());

		phaser.arriveAndAwaitAdvance();
		System.out.println(phaser.getPhase());

		System.out.println(phaser.getRegisteredParties());

		phaser.register();

		System.out.println(phaser.getRegisteredParties());

		phaser.register();

		System.out.println(phaser.getRegisteredParties());

		System.out.println(phaser.getArrivedParties());
		System.out.println(phaser.getUnarrivedParties());

		phaser.bulkRegister(10);
		System.out.println(phaser.getRegisteredParties());
		System.out.println(phaser.getArrivedParties());
		System.out.println(phaser.getUnarrivedParties());
		new Thread(phaser::arriveAndAwaitAdvance).start();
		TimeUnit.SECONDS.sleep(1);
		System.out.println("===========================");
		System.out.println(phaser.getRegisteredParties());
		System.out.println(phaser.getArrivedParties());
		System.out.println(phaser.getUnarrivedParties());

		final Phaser pr = new Phaser(2) {
			@Override
			protected boolean onAdvance(int phase, int registeredParties) {
				return false;
			}
		};

		new OnAdvanceTask("Alex", pr).start();
		new OnAdvanceTask("Jack", pr).start();

		TimeUnit.SECONDS.sleep(2);
		System.out.println(pr.getUnarrivedParties());
		System.out.println(pr.getArrivedParties());
	}

	static class OnAdvanceTask extends Thread {
		private final Phaser phaser;

		OnAdvanceTask(String name, Phaser phaser) {
			super(name);
			this.phaser = phaser;
		}

		@Override
		public void run() {
			System.out.println(getName() + " I am start and the phase " + phaser.getPhase());
			phaser.arriveAndAwaitAdvance();
			System.out.println(getName() + " I am end!");

			System.out.println("isTerminated->" + phaser.isTerminated());

			try {
				TimeUnit.SECONDS.sleep(1);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			if ("Alex".equals(getName())) {
				System.out.println(getName() + " I am start and the phase " + phaser.getPhase());
				phaser.arriveAndAwaitAdvance();
				System.out.println(getName() + " I am end!");
			}

		}
	}
}
