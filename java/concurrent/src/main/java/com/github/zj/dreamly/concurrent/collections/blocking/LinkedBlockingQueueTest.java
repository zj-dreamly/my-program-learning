package com.github.zj.dreamly.concurrent.collections.blocking;

import org.junit.Test;

import java.util.concurrent.LinkedBlockingQueue;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.junit.Assert.fail;

/**
 * {@link LinkedBlockingQueue}
 * @author 苍海之南
 */
public class LinkedBlockingQueueTest {

	/**
	 * Test the {@link LinkedBlockingQueue#add(Object)}
	 * Test the {@link LinkedBlockingQueue#offer(Object)}
	 * Test the {@link LinkedBlockingQueue#put(Object)}
	 */
	@Test
	public void testInsertData() throws InterruptedException {
		LinkedBlockingQueue<String> queue = new LinkedBlockingQueue<>(2);
		assertThat(queue.offer("data1"), equalTo(true));
		assertThat(queue.offer("data2"), equalTo(true));
		assertThat(queue.offer("data3"), equalTo(false));

		queue.clear();

		assertThat(queue.add("data1"), equalTo(true));
		assertThat(queue.add("data2"), equalTo(true));

		//enqueue
		//enter queue
		queue.put("data");

	}

	/**
	 * Test the {@link LinkedBlockingQueue#add(Object)}
	 * Test the {@link LinkedBlockingQueue#offer(Object)}
	 * Test the {@link LinkedBlockingQueue#put(Object)}
	 */
	@Test
	public void testRemoveData() throws InterruptedException {
		LinkedBlockingQueue<String> queue = new LinkedBlockingQueue<>(2);
		assertThat(queue.offer("data1"), equalTo(true));
		assertThat(queue.offer("data2"), equalTo(true));
		assertThat(queue.offer("data3"), equalTo(false));

		assertThat(queue.element(), equalTo("data1"));
		assertThat(queue.element(), equalTo("data1"));

		assertThat(queue.peek(), equalTo("data1"));
		assertThat(queue.peek(), equalTo("data1"));

		assertThat(queue.remove(), equalTo("data1"));
		assertThat(queue.poll(), equalTo("data2"));
		assertThat(queue.size(), equalTo(0));
		assertThat(queue.remainingCapacity(), equalTo(2));

		assertThat(queue.take(), equalTo("xxx"));
		fail("should not process to here.");

	}
}

