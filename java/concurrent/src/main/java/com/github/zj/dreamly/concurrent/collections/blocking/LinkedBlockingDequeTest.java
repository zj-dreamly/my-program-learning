package com.github.zj.dreamly.concurrent.collections.blocking;

import org.junit.Test;

import java.util.concurrent.Executors;
import java.util.concurrent.LinkedBlockingDeque;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.MatcherAssert.assertThat;

/**
 * @author 苍海之南
 */
public class LinkedBlockingDequeTest {

	@Test
	public void testAddFirst() {
		LinkedBlockingDeque<String> deque = new LinkedBlockingDeque<>();
		deque.addFirst("Java");
		deque.addFirst("Scala");
		assertThat(deque.removeFirst(), equalTo("Scala"));
		assertThat(deque.removeFirst(), equalTo("Java"));

	}

	@Test
	public void testAdd() {
		LinkedBlockingDeque<String> deque = new LinkedBlockingDeque<>();
		deque.add("Java");
		deque.add("Scala");
		assertThat(deque.remove(), equalTo("Java"));
		assertThat(deque.remove(), equalTo("Scala"));
	}

	@Test
	public void testTakeFirst() throws InterruptedException {
		LinkedBlockingDeque<String> deque = new LinkedBlockingDeque<>();

		ScheduledExecutorService service = Executors.newSingleThreadScheduledExecutor();
		service.schedule(() -> deque.add("Deque"), 1, TimeUnit.SECONDS);
		service.shutdown();
		long currentTime = System.currentTimeMillis();
		assertThat(deque.takeFirst(), equalTo("Deque"));
		assertThat((System.currentTimeMillis() - currentTime) >= 1000, equalTo(true));
	}

}

