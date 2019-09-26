package com.github.zj.dreamly.datastructure.queue.delay.orderexample;

import java.util.concurrent.Delayed;
import java.util.concurrent.TimeUnit;

/**
 * 延时队列中的消息体将任务封装为消息体
 *
 * @author 苍海之南
 */
public class DelayOrderTask<T extends Runnable> implements Delayed {
	private final long time;
	/**
	 * 任务类，也就是之前定义的任务类
	 */
	private final T task;

	/**
	 * @param timeout 超时时间(秒)
	 * @param task    任务
	 */
	public DelayOrderTask(long timeout, T task) {
		this.time = System.nanoTime() + timeout;
		this.task = task;
	}

	@Override
	public int compareTo(Delayed o) {
		DelayOrderTask other = (DelayOrderTask) o;
		long diff = time - other.time;
		if (diff > 0) {
			return 1;
		} else if (diff < 0) {
			return -1;
		} else {
			return 0;
		}
	}

	@Override
	public long getDelay(TimeUnit unit) {
		return unit.convert(this.time - System.nanoTime(), TimeUnit.NANOSECONDS);
	}

	@Override
	public int hashCode() {
		return task.hashCode();
	}

	public T getTask() {
		return task;
	}
}
