package com.github.zj.dreamly.guava.eventbus.monitor;

import com.google.common.eventbus.EventBus;
import org.junit.Test;

import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

/**
 * tail
 * Apache Flume 1.7 Spooling
 * <p>
 * .position
 *
 * @author 苍海之南
 */
public class MonitorClient {

	private final String filePath = MonitorClient.class.getResource("/").getPath();

	@Test
	public void start() throws Exception {
		final EventBus eventBus = new EventBus();
		eventBus.register(new FileChangeListener());

		TargetMonitor monitor = new DirectoryTargetMonitor(eventBus, filePath);
		ScheduledExecutorService executorService = Executors.newSingleThreadScheduledExecutor();
		executorService.schedule(() ->
		{
			try {
				monitor.stopMonitor();
			} catch (Exception e) {
				e.printStackTrace();
			}
		}, 2, TimeUnit.SECONDS);
		executorService.shutdown();
		monitor.startMonitor();

	}
}

