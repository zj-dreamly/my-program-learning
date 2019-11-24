package com.github.zj.dreamly.concurrent.collections.blocking;

import org.junit.Test;

import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.IsNull.nullValue;
import static org.junit.Assert.fail;

/**
 * @author 苍海之南
 */
public class ArrayBlockingQueueTest {

	/**
	 * Inserts the specified element at the tail of this queue if it is
	 * possible to do so immediately without exceeding the queue's capacity,
	 * returning {@code true} upon success and throwing an
	 * {@code IllegalStateException} if this queue is full.
	 */
	@Test
	public void testAddMethodNotExceedCapacity() {
		ArrayBlockingQueue<String> queue = new ArrayBlockingQueue<>(5);
		assertThat(queue.add("Hello1"), equalTo(true));
		assertThat(queue.add("Hello2"), equalTo(true));
		assertThat(queue.add("Hello3"), equalTo(true));
		assertThat(queue.add("Hello4"), equalTo(true));
		assertThat(queue.add("Hello5"), equalTo(true));
		assertThat(queue.size(), equalTo(5));
	}

	@Test(expected = IllegalStateException.class)
	public void testAddMethodExceedCapacity() {
		ArrayBlockingQueue<String> queue = new ArrayBlockingQueue<>(5);
		assertThat(queue.add("Hello1"), equalTo(true));
		assertThat(queue.add("Hello2"), equalTo(true));
		assertThat(queue.add("Hello3"), equalTo(true));
		assertThat(queue.add("Hello4"), equalTo(true));
		assertThat(queue.add("Hello5"), equalTo(true));
		assertThat(queue.add("Hello6"), equalTo(true));
		fail("should not process to here.");
	}

	@Test
	public void testOfferMethodNotExceedCapacity() {
		ArrayBlockingQueue<String> queue = new ArrayBlockingQueue<>(5);
		assertThat(queue.offer("Hello1"), equalTo(true));
		assertThat(queue.offer("Hello2"), equalTo(true));
		assertThat(queue.offer("Hello3"), equalTo(true));
		assertThat(queue.offer("Hello4"), equalTo(true));
		assertThat(queue.offer("Hello5"), equalTo(true));

		assertThat(queue.size(), equalTo(5));
	}

	@Test
	public void testOfferMethodExceedCapacity() {
		ArrayBlockingQueue<String> queue = new ArrayBlockingQueue<>(5);
		assertThat(queue.offer("Hello1"), equalTo(true));
		assertThat(queue.offer("Hello2"), equalTo(true));
		assertThat(queue.offer("Hello3"), equalTo(true));
		assertThat(queue.offer("Hello4"), equalTo(true));
		assertThat(queue.offer("Hello5"), equalTo(true));
		assertThat(queue.offer("Hello6"), equalTo(false));
		assertThat(queue.size(), equalTo(5));
	}

	@Test
	public void testPutMethodNotExceedCapacity() throws InterruptedException {
		ArrayBlockingQueue<String> queue = new ArrayBlockingQueue<>(5);
		queue.put("Hello1");
		queue.put("Hello2");
		queue.put("Hello3");
		queue.put("Hello4");
		queue.put("Hello5");
		assertThat(queue.size(), equalTo(5));
	}

	/**
	 * Inserts the specified element at the tail of this queue, waiting
	 * for space to become available if the queue is full.
	 *
	 * @throws InterruptedException InterruptedException
	 */
	@Test
	public void testPutMethodExceedCapacity() throws InterruptedException {
		ArrayBlockingQueue<String> queue = new ArrayBlockingQueue<>(5);
		ScheduledExecutorService executorService = Executors.newSingleThreadScheduledExecutor();
		executorService.schedule(() -> {
			try {
				assertThat(queue.take(), equalTo("Hello1"));
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}, 1, TimeUnit.SECONDS);
		executorService.shutdown();
		queue.put("Hello1");
		queue.put("Hello2");
		queue.put("Hello3");
		queue.put("Hello4");
		queue.put("Hello5");
		queue.put("Hello6");

	}

	@Test
	public void testPoll() {
		ArrayBlockingQueue<String> queue = new ArrayBlockingQueue<>(2);
		queue.add("Hello1");
		queue.add("Hello2");
		///////////////////////////////////////
		assertThat(queue.poll(), equalTo("Hello1"));
		assertThat(queue.poll(), equalTo("Hello2"));
		assertThat(queue.poll(), nullValue());
	}

	@Test
	public void testPeek() {
		ArrayBlockingQueue<String> queue = new ArrayBlockingQueue<>(2);
		queue.add("Hello1");
		queue.add("Hello2");
		///////////////////////////////////////
		assertThat(queue.peek(), equalTo("Hello1"));
		assertThat(queue.peek(), equalTo("Hello1"));
		assertThat(queue.peek(), equalTo("Hello1"));
		assertThat(queue.peek(), equalTo("Hello1"));
		assertThat(queue.peek(), equalTo("Hello1"));
	}

	@Test(expected = NoSuchElementException.class)
	public void testElement() {
		ArrayBlockingQueue<String> queue = new ArrayBlockingQueue<>(2);
		queue.add("Hello1");
		queue.add("Hello2");
		///////////////////////////////////////
		assertThat(queue.element(), equalTo("Hello1"));
		assertThat(queue.element(), equalTo("Hello1"));
		assertThat(queue.element(), equalTo("Hello1"));
		queue.clear();
		assertThat(queue.element(), equalTo("Hello1"));
	}

	@Test(expected = NoSuchElementException.class)
	public void testRemove() {
		ArrayBlockingQueue<String> queue = new ArrayBlockingQueue<>(2);
		queue.add("Hello1");
		queue.add("Hello2");
		assertThat(queue.remove(), equalTo("Hello1"));
		assertThat(queue.remove(), equalTo("Hello2"));
		assertThat(queue.remove(), equalTo("Hello1"));
	}

	@Test
	public void testDrainTo() {
		ArrayBlockingQueue<String> queue = new ArrayBlockingQueue<>(5);
		queue.add("Hello1");
		queue.add("Hello2");
		queue.add("Hello3");
		assertThat(queue.size(), equalTo(3));
		assertThat(queue.remainingCapacity(), equalTo(2));
		assertThat(queue.remove(), equalTo("Hello1"));
		assertThat(queue.remainingCapacity(), equalTo(3));
		assertThat(queue.size(), equalTo(2));
		List<String> list = new ArrayList<>();
		queue.drainTo(list);

		assertThat(queue.remainingCapacity(), equalTo(5));
		assertThat(queue.size(), equalTo(0));

		assertThat(list.size(), equalTo(2));
	}
}
