package com.github.zj.dreamly.concurrent.atomic;

import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicStampedReference;

/**
 * @author 苍海之南
 *
 * 解决CAS算法的ABA问题
 */
public class AtomicStampedReferenceTest {

	private static AtomicStampedReference<Integer> atomicRef =
		new AtomicStampedReference<Integer>(100, 0);

	public static void main(String[] args) throws InterruptedException {

		Thread t1 = new Thread(() -> {
			try {
				TimeUnit.SECONDS.sleep(1);
				boolean success = atomicRef.compareAndSet(100,
					101, atomicRef.getStamp(), atomicRef.getStamp() + 1);
				System.out.println(success);

				success = atomicRef.compareAndSet(101,
					100, atomicRef.getStamp(), atomicRef.getStamp() + 1);
				System.out.println(success);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		});

		Thread t2 = new Thread(new Runnable() {
			@Override
			public void run() {
				try {
					int stamp = atomicRef.getStamp();
					System.out.println("Before sleep:stamp=" + stamp);
					TimeUnit.SECONDS.sleep(2);
					boolean success = atomicRef.compareAndSet(100,
						101, stamp, stamp + 1);
					System.out.println(success);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}
		});

		t1.start();
		t2.start();
		t1.join();
		t2.join();
	}
}
