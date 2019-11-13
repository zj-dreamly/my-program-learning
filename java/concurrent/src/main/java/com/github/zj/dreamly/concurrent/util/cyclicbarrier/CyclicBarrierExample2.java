package com.github.zj.dreamly.concurrent.util.cyclicbarrier;

import java.util.concurrent.BrokenBarrierException;
import java.util.concurrent.CyclicBarrier;
import java.util.concurrent.TimeUnit;

/**
 * @author 苍海之南
 */
public class CyclicBarrierExample2 {
	public static void main(String[] args) {
		final CyclicBarrier cyclicBarrier = new CyclicBarrier(3);
		new Thread(() -> {
			try {
				TimeUnit.SECONDS.sleep(50);
				cyclicBarrier.await();
			} catch (InterruptedException | BrokenBarrierException e) {
				e.printStackTrace();
			}
		}).start();

		new Thread(() -> {
			try {
            ///TimeUnit.SECONDS.sleep(5);
				cyclicBarrier.await();
			} catch (InterruptedException | BrokenBarrierException e) {
				e.printStackTrace();
			}
		}).start();

		new Thread(() -> {
			try {
				///TimeUnit.SECONDS.sleep(5);
				cyclicBarrier.await();
			} catch (InterruptedException | BrokenBarrierException e) {
				e.printStackTrace();
			}
		}).start();

        /*//reset==initial==finished
        TimeUnit.SECONDS.sleep(4);
        System.out.println(cyclicBarrier.getNumberWaiting());
        System.out.println(cyclicBarrier.getParties());
        System.out.println(cyclicBarrier.isBroken());
        TimeUnit.SECONDS.sleep(2);

        cyclicBarrier.reset();


        System.out.println(cyclicBarrier.getNumberWaiting());
        System.out.println(cyclicBarrier.getParties());
        System.out.println(cyclicBarrier.isBroken());*/
	}
}
