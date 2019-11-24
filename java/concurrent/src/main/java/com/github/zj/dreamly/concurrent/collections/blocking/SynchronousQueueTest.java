package com.github.zj.dreamly.concurrent.collections.blocking;

import org.junit.Test;

import java.util.concurrent.Executors;
import java.util.concurrent.SynchronousQueue;
import java.util.concurrent.TimeUnit;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.junit.Assert.fail;

public class SynchronousQueueTest {

	@Test
	public void testAdd() throws InterruptedException {
		SynchronousQueue<String> queue = new SynchronousQueue<>();

		Executors.newSingleThreadExecutor().submit(() -> {
			try {
				assertThat(queue.remove(), equalTo("SynchronousQueue"));
			} catch (Exception e) {
				e.printStackTrace();
			}
		});

		TimeUnit.MILLISECONDS.sleep(5);
		assertThat(queue.add("SynchronousQueue"), equalTo(true));
	}

	/**
	 * Producer Consumer
	 * <p>
	 * P------>queue<-----Consumer
	 */
	@Test
	public void testOffer() throws InterruptedException {
		SynchronousQueue<String> queue = new SynchronousQueue<>();
		apply(queue);
		assertThat(queue.offer("SynchronousQueue"), equalTo(true));

	}

	/**
	 * Producer Consumer
	 * <p>
	 * P------>queue<-----Consumer
	 */
	@Test
	public void testPut() throws InterruptedException {
		SynchronousQueue<String> queue = new SynchronousQueue<>();

		apply(queue);
		queue.put("SynchronousQueue");
		fail("should not process to here.l");
	}

	private static void apply(SynchronousQueue<String> queue) throws InterruptedException {
		Executors.newSingleThreadExecutor().submit(() -> {
			try {
				assertThat(queue.take(), equalTo("SynchronousQueue"));
			} catch (Exception e) {
				e.printStackTrace();
			}
		});

		TimeUnit.MILLISECONDS.sleep(5);
	}

}
