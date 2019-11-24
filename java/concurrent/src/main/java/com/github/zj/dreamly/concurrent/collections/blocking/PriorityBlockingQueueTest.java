package com.github.zj.dreamly.concurrent.collections.blocking;

import org.junit.Test;

import java.util.Comparator;
import java.util.concurrent.Executors;
import java.util.concurrent.PriorityBlockingQueue;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.junit.Assert.fail;

/**
 * @author 苍海之南
 */
public class PriorityBlockingQueueTest {

	/**
	 * The {@link PriorityBlockingQueue#add(Object)}
	 * {@link PriorityBlockingQueue#offer(Object)}
	 * {@link PriorityBlockingQueue#put(Object)}
	 * <p>
	 * all of them are same.
	 */
	@Test
	public void testAddNewElement() {
		PriorityBlockingQueue<String> queue = new PriorityBlockingQueue<>(5);
		assertThat(queue.add("hello1"), equalTo(true));
		assertThat(queue.add("hello2"), equalTo(true));
		assertThat(queue.add("hello3"), equalTo(true));
		assertThat(queue.add("hello4"), equalTo(true));
		assertThat(queue.add("hello5"), equalTo(true));
		assertThat(queue.add("hello6"), equalTo(true));
		assertThat(queue.size(), equalTo(6));
	}

	@Test
	public void testGetElement() throws InterruptedException {
		PriorityBlockingQueue<String> queue = new PriorityBlockingQueue<>(3);
		assertThat(queue.add("hello4"), equalTo(true));
		assertThat(queue.add("hello2"), equalTo(true));
		assertThat(queue.add("hello3"), equalTo(true));

		assertThat(queue.element(), equalTo("hello2"));
		assertThat(queue.size(), equalTo(3));
		assertThat(queue.element(), equalTo("hello2"));
		assertThat(queue.size(), equalTo(3));
		///////////////peek/////////////////

		assertThat(queue.peek(), equalTo("hello2"));
		assertThat(queue.size(), equalTo(3));
		assertThat(queue.peek(), equalTo("hello2"));
		assertThat(queue.size(), equalTo(3));
		///////////////poll//////////////////

		assertThat(queue.poll(), equalTo("hello2"));
		assertThat(queue.size(), equalTo(2));
		assertThat(queue.poll(), equalTo("hello3"));
		assertThat(queue.size(), equalTo(1));

		///////////////////////////////
		//queue.remove()
		/////////////////////////////////

		assertThat(queue.take(), equalTo("hello4"));
		assertThat(queue.size(), equalTo(0));

		//////////////////////////////////////////////
		//take when empty.
		//////////////////////////////////////////////
		ScheduledExecutorService executorService = Executors.newSingleThreadScheduledExecutor();
		executorService.schedule(() -> queue.add("NEW_DATA"), 1, TimeUnit.SECONDS);
		executorService.shutdown();
		assertThat(queue.take(), equalTo("NEW_DATA"));
	}

	@Test(expected = NullPointerException.class)
	public void testAddNullElement() {
		PriorityBlockingQueue<String> queue = new PriorityBlockingQueue<>(3);
		queue.add(null);
	}

	@Test(expected = ClassCastException.class)
	public void testAddObject_WithNoComparable_WithNoComparator() {
		PriorityBlockingQueue<UserWithNoComparable> queue = new PriorityBlockingQueue<>(3);
		queue.add(new UserWithNoComparable());
		fail("should not process to here");
	}

	@Test
	public void testAddObject_WithNoComparable_WithComparator() {
		PriorityBlockingQueue<UserWithNoComparable> queue = new PriorityBlockingQueue<>(3,
			Comparator.comparingInt(Object::hashCode));
		queue.add(new UserWithNoComparable());
	}

	static class UserWithNoComparable implements Comparable<UserWithNoComparable> {

		@Override
		public int compareTo(UserWithNoComparable o) {
			return 0;
		}
	}

}
