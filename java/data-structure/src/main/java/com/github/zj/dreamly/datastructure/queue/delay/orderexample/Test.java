package com.github.zj.dreamly.datastructure.queue.delay.orderexample;

import java.util.concurrent.TimeUnit;

/**
 * @author 苍海之南
 */
public class Test {
	public static void main(String[] args) {
		DelayOrderWorker work1 = new DelayOrderWorker();
		DelayOrderWorker work2 = new DelayOrderWorker();
		DelayOrderWorker work3 = new DelayOrderWorker();
		// 延迟队列管理类，将任务转化消息体并将消息体放入延迟对列中等待执行
		DelayOrderQueueManager manager = DelayOrderQueueManager.getInstance();
		manager.put(work1, 3000, TimeUnit.MILLISECONDS);
		manager.put(work2, 6000, TimeUnit.MILLISECONDS);
		manager.put(work3, 9000, TimeUnit.MILLISECONDS);
	}

}
