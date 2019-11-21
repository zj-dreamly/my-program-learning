package com.github.zj.dreamly.concurrent.executors;

import java.util.List;
import java.util.concurrent.*;
import java.util.stream.IntStream;

import static java.util.stream.Collectors.toList;

/**
 * @author 苍海之南
 */
public class ComplexExample {
	public static void main(String[] args) throws InterruptedException {

		final ExecutorService service = Executors.newSingleThreadExecutor();
		List<Callable<Integer>> tasks = IntStream.range(0, 5).boxed().map(MyTask::new).collect(toList());
		final CompletionService<Integer> completionService = new ExecutorCompletionService<>(service);

		tasks.forEach(completionService::submit);
		TimeUnit.SECONDS.sleep(12);
		service.shutdownNow();

		tasks.stream().filter(callable -> !((MyTask) callable).isSuccess()).forEach(System.out::println);
	}

	private static class MyTask implements Callable<Integer> {

		private final Integer value;

		private boolean success = false;

		MyTask(Integer value) {
			this.value = value;
		}

		@Override
		public Integer call() throws Exception {

			System.out.printf("The Task [%d] will be executed.\n", value);
			TimeUnit.SECONDS.sleep(value * 5 + 10);
			System.out.printf("The Task [%d] execute done.\n", value);
			success = true;
			return value;
		}

		public boolean isSuccess() {
			return success;
		}
	}
}
