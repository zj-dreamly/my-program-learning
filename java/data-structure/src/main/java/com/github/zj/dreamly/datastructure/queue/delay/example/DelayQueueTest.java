package com.github.zj.dreamly.datastructure.queue.delay.example;

import java.util.concurrent.DelayQueue;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * @author 苍海之南
 */
public class DelayQueueTest {
	public static void main(String[] args) {
		// 创建延时队列
		DelayQueue<Message> queue = new DelayQueue<Message>();
		// 添加延时消息,m1 延时3s
		Message m1 = new Message(1, "world", 3000);
		// 添加延时消息,m2 延时10s
		Message m2 = new Message(2, "hello", 10000);
		//将延时消息放到延时队列中
		queue.offer(m2);
		queue.offer(m1);
		// 启动消费线程 消费添加到延时队列中的消息，前提是任务到了延期时间
		ExecutorService exec = Executors.newFixedThreadPool(1);
		exec.execute(new Consumer(queue));
		exec.shutdown();
	}
}
