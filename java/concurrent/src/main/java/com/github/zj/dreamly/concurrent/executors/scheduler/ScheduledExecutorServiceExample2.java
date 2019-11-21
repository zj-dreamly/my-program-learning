package com.github.zj.dreamly.concurrent.executors.scheduler;

import java.util.concurrent.ScheduledThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/**
 * @author 苍海之南
 */
public class ScheduledExecutorServiceExample2 {
	public static void main(String[] args) throws InterruptedException {
		testScheduleWithFixedDelay();
		testGetContinueExistingPeriodicTasksAfterShutdownPolicy();
		testGetExecuteExistingDelayedTasksAfterShutdownPolicy();
	}

	private static void testScheduleWithFixedDelay() {
		ScheduledThreadPoolExecutor executor = new ScheduledThreadPoolExecutor(2);
		executorBuild(executor);
	}

	private static void testGetContinueExistingPeriodicTasksAfterShutdownPolicy() throws InterruptedException {
		ScheduledThreadPoolExecutor executor = new ScheduledThreadPoolExecutor(2);
		System.out.println(executor.getContinueExistingPeriodicTasksAfterShutdownPolicy());
		executor.setContinueExistingPeriodicTasksAfterShutdownPolicy(true);
		TimeUnit.MILLISECONDS.sleep(1200);
		executor.shutdown();
		System.out.println("==over==");

	}

	private static void testGetExecuteExistingDelayedTasksAfterShutdownPolicy() throws InterruptedException {
		ScheduledThreadPoolExecutor executor = new ScheduledThreadPoolExecutor(2);
		System.out.println(executor.getExecuteExistingDelayedTasksAfterShutdownPolicy());
		executor.setExecuteExistingDelayedTasksAfterShutdownPolicy(false);
		executorBuild(executor);

		TimeUnit.MILLISECONDS.sleep(1200);
		executor.shutdown();
		System.out.println("==over==");
	}

	private static void executorBuild(ScheduledThreadPoolExecutor executor) {
		ScheduledExecutorServiceExample1.exex(executor);
	}
}
