package com.github.zj.dreamly.datastructure.queue.delay.orderexample;

import cn.hutool.core.thread.ThreadUtil;

import java.util.Map;
import java.util.concurrent.DelayQueue;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.TimeUnit;

/**
 * 延时队列管理类，用来添加任务、执行任务
 *
 * @author 苍海之南
 */
public class DelayOrderQueueManager {

	/**
	 * 固定大小线程池
	 */
	private ExecutorService executor;

	/**
	 * 守护线程
	 */
	private Thread daemonThread;
	/**
	 * 延时队列
	 */
	private DelayQueue<DelayOrderTask<?>> delayQueue;
	private static DelayOrderQueueManager instance = new DelayOrderQueueManager();

	private DelayOrderQueueManager() {
		int threadNum = 5;
		executor = ThreadUtil.newExecutor(threadNum);
		delayQueue = new DelayQueue<>();
		init();
	}

	public static DelayOrderQueueManager getInstance() {
		return instance;
	}

	public void init() {
		daemonThread = new Thread(this::execute);
		daemonThread.setName("DelayQueueMonitor");
		daemonThread.start();
	}

	private void execute() {
		while (true) {
			Map<Thread, StackTraceElement[]> map = Thread.getAllStackTraces();
			System.out.println("当前存活线程数量:" + map.size());
			int taskNum = delayQueue.size();
			System.out.println("当前延时任务数量:" + taskNum);
			try {
				// 从延时队列中获取任务
				DelayOrderTask<?> delayOrderTask = delayQueue.take();
				Runnable task = delayOrderTask.getTask();
				if (null == task) {
					continue;
				}
				// 提交到线程池执行task
				executor.execute(task);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}

	/**
	 * 添加任务
	 *
	 * @param task task
	 * @param time 延时时间
	 * @param unit 时间单位
	 */
	public void put(Runnable task, long time, TimeUnit unit) {
		// 获取延时时间
		long timeout = TimeUnit.NANOSECONDS.convert(time, unit);
		// 将任务封装成实现Delayed接口的消息体
		DelayOrderTask<?> delayOrder = new DelayOrderTask<>(timeout, task);
		// 将消息体放到延时队列中
		delayQueue.put(delayOrder);
	}

	/**
	 * 删除任务
	 *
	 * @param task task
	 * @return 是否success
	 */
	public boolean removeTask(DelayOrderTask task) {

		return delayQueue.remove(task);
	}
}
