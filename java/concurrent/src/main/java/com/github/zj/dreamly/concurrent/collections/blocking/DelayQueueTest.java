package com.github.zj.dreamly.concurrent.collections.blocking;

import org.junit.Test;

import java.util.NoSuchElementException;
import java.util.concurrent.*;

import static org.hamcrest.CoreMatchers.*;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.junit.Assert.fail;

/**
 * @author 苍海之南
 */
public class DelayQueueTest {

	/**
	 * 1.Add method must add the Delayed element.
	 * 2.peek method will return null/element(but not remove) be quickly.
	 *
	 * @throws InterruptedException InterruptedException
	 */
	@Test
	public void testAdd1() throws InterruptedException {

		DelayQueue<DelayElement<String>> delayQueue = new DelayQueue<>();

		DelayElement<String> delayed1 = DelayElement.of("Delayed1", 1000);
		delayQueue.add(delayed1);
		assertThat(delayQueue.size(), equalTo(1));
		long startTime = System.currentTimeMillis();
		///assertThat(delayQueue.take(), is(delayed1));
		DelayElement<String> take = delayQueue.take();
		System.out.println(System.currentTimeMillis() - startTime);
	}

	@Test
	public void testAdd2() {

		DelayQueue<DelayElement<String>> delayQueue = new DelayQueue<>();

		delayQueue.add(DelayElement.of("Delayed1", 1000));
		delayQueue.add(DelayElement.of("Delayed2", 800));
		delayQueue.add(DelayElement.of("Delayed3", 11000));
		delayQueue.add(DelayElement.of("Delayed4", 20000));
		assertThat(delayQueue.size(), equalTo(4));
		long startTime = System.currentTimeMillis();
		for (DelayElement<String> stringDelayElement : delayQueue) {
			assertThat(stringDelayElement, notNullValue());
		}
		System.out.println(System.currentTimeMillis() - startTime);
		assertThat((System.currentTimeMillis() - startTime) < 5, equalTo(true));
	}

	@Test
	public void testAdd3() throws InterruptedException {

		DelayQueue<DelayElement<String>> delayQueue = new DelayQueue<>();
		delayQueue.add(DelayElement.of("Delayed1", 100));
		delayQueue.add(DelayElement.of("Delayed2", 80));
		delayQueue.add(DelayElement.of("Delayed3", 1000));
		delayQueue.add(DelayElement.of("Delayed4", 2000));
		assertThat(delayQueue.size(), equalTo(4));
		assertThat(delayQueue.take().getData(), equalTo("Delayed2"));
		assertThat(delayQueue.take().getData(), equalTo("Delayed1"));
		assertThat(delayQueue.take().getData(), equalTo("Delayed3"));
		assertThat(delayQueue.take().getData(), equalTo("Delayed4"));
	}

	@Test(expected = NoSuchElementException.class)
	public void testAdd4() {

		DelayQueue<DelayElement<String>> delayQueue = new DelayQueue<>();
		delayQueue.add(DelayElement.of("Delayed1", 100));
		delayQueue.add(DelayElement.of("Delayed2", 80));
		delayQueue.add(DelayElement.of("Delayed3", 1000));
		delayQueue.add(DelayElement.of("Delayed4", 2000));
		assertThat(delayQueue.size(), equalTo(4));
		assertThat(delayQueue.remove().getData(), equalTo("Delayed2"));
	}

	@Test
	public void testAdd5() throws InterruptedException {

		DelayQueue<DelayElement<String>> delayQueue = new DelayQueue<>();
		delayQueue.add(DelayElement.of("Delayed1", 100));
		delayQueue.add(DelayElement.of("Delayed2", 80));
		delayQueue.add(DelayElement.of("Delayed3", 1000));
		delayQueue.add(DelayElement.of("Delayed4", 2000));
		TimeUnit.MILLISECONDS.sleep(81);
		assertThat(delayQueue.size(), equalTo(4));
		assertThat(delayQueue.poll().getData(), equalTo("Delayed2"));
	}

	@Test
	public void testAdd6() {
		DelayQueue<DelayElement<String>> delayQueue = new DelayQueue<>();
		try {
			delayQueue.add(null);
			fail("should not process to here.");
		} catch (Exception e) {
			//assertThat((NullPointerException) e, isA(NullPointerException.class));
			assertThat(e instanceof NullPointerException, equalTo(true));
		}
	}

	@Test
	public void testPoll() throws InterruptedException {
		DelayQueue<DelayElement<String>> delayQueue = new DelayQueue<>();
		assertThat(delayQueue.poll(), nullValue());
	}

	@Test
	public void testTake() throws InterruptedException {
		DelayQueue<DelayElement<String>> delayQueue = new DelayQueue<>();
		ScheduledExecutorService executorService = Executors.newSingleThreadScheduledExecutor();
		executorService.schedule(() -> delayQueue.add(DelayElement.of("Test", 1000)), 1, TimeUnit.SECONDS);
		executorService.shutdown();
		long startTime = System.currentTimeMillis();
		assertThat(delayQueue.take().getData(), equalTo("Test"));
		assertThat((System.currentTimeMillis() - startTime) >= 1000, equalTo(true));
	}

	static class DelayElement<E> implements Delayed {

		private final E e;

		private final long expireTime;

		DelayElement(E e, long delay) {
			this.e = e;
			this.expireTime = System.currentTimeMillis() + delay;
		}

		static <T> DelayElement<T> of(T e, long delay) {
			return new DelayElement<>(e, delay);
		}

		@Override
		public long getDelay(TimeUnit unit) {
			long diff = expireTime - System.currentTimeMillis();
			return unit.convert(diff, TimeUnit.MILLISECONDS);
		}

		@Override
		public int compareTo(Delayed delayedObject) {
			DelayElement that = (DelayElement) delayedObject;
			if (this.expireTime < that.getExpireTime()) {
				return -1;
			} else if (this.expireTime > that.getExpireTime()) {
				return 1;
			} else {
				return 0;
			}
		}

		public E getData() {
			return e;
		}

		public long getExpireTime() {
			return expireTime;
		}
	}
}
