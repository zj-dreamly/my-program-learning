package com.github.zj.dreamly.concurrent.executors;

import org.junit.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.*;

/**
 * @author 苍海之南
 */
public class CompletionServiceExample2 {

	public static void main(String[] args) throws InterruptedException, ExecutionException {

		ExecutorService service = Executors.newFixedThreadPool(2);
		ExecutorCompletionService<Event> completionService = new ExecutorCompletionService<>(service);
		final Event event = new Event(1);
		completionService.submit(new MyTask(event), event);

		System.out.println(completionService.take().get().result);
	}

	@Test
	public void test() throws InterruptedException, ExecutionException {
		ExecutorService service = Executors.newFixedThreadPool(2);
		final List<Callable<Integer>> callableList = Arrays.asList(
			() -> {
				sleep(10);
				System.out.println("The 10 finished");
				return 10;
			},
			() -> {
				sleep(20);
				System.out.println("The 20 finished");
				return 20;
			}
		);
		ExecutorCompletionService<Integer> completionService = new ExecutorCompletionService<>(service);

		List<Future<Integer>> futures = new ArrayList<>();
		callableList.stream().forEach(callable -> futures.add(completionService.submit(callable)));

		Future<Integer> future;

		//completionService.poll()，此方法为非阻塞的，queue空直接返回null
		while ((future = completionService.take()) != null) {
			System.out.println(future.get());
		}

		System.out.println(completionService.poll(11, TimeUnit.SECONDS).get());
	}

	private static class MyTask implements Runnable {

		private final Event event;

		private MyTask(Event event) {
			this.event = event;
		}

		@Override
		public void run() {
			sleep(10);
			event.setResult("I AM SUCCESSFULLY.");

		}
	}

	private static class Event {
		final private int eventId;
		private String result;

		private Event(int eventId) {
			this.eventId = eventId;
		}

		public int getEventId() {
			return eventId;
		}

		public String getResult() {
			return result;
		}

		public void setResult(String result) {
			this.result = result;
		}
	}

	/**
	 * sleep the specify seconds
	 */
	private static void sleep(long seconds) {
		try {
			TimeUnit.SECONDS.sleep(seconds);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}
}
