package com.github.zj.dreamly.concurrent.atomic;

import java.util.Collections;
import java.util.HashSet;
import java.util.Set;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * @author 苍海之南
 */
public class AtomicIntegerTest {

	private static Set<Integer> set = Collections.synchronizedSet(new HashSet<Integer>());

	public static void main(String[] args) throws InterruptedException {

		final AtomicInteger value = new AtomicInteger();
		Thread t1 = apply(value);

		Thread t2 = apply(value);

		Thread t3 = apply(value);

		t1.start();
		t2.start();
		t3.start();
		t1.join();
		t2.join();
		t3.join();

		System.out.println(set.size());
	}

	private static Thread apply(AtomicInteger value) {
		return new Thread(() -> {
			int x = 0;
			while (x < 500) {
				int v = value.getAndIncrement();
				set.add(v);
				System.out.println(Thread.currentThread().getName() + ":" + v);
				x++;
			}
		});
	}
}
