package com.github.zj.dreamly.datastructure.queue;

import com.github.zj.dreamly.datastructure.queue.array.ArrayQueue;
import com.github.zj.dreamly.datastructure.queue.loop.LoopQueue;

import java.util.Random;

/**
 * <h2>QueueTest</h2>
 *
 * @author: 苍海之南
 * @since: 2019-08-30 09:30
 **/
public class QueueTest {
	private static double testQueue(Queue<Integer> q, int opCount) {

		long startTime = System.nanoTime();

		Random random = new Random();
		for (int i = 0; i < opCount; i++) {
			q.enqueue(random.nextInt(Integer.MAX_VALUE));
		}
		for (int i = 0; i < opCount; i++) {
			q.dequeue();
		}

		long endTime = System.nanoTime();

		return (endTime - startTime) / 1000000000.0;
	}

	public static void main(String[] args) {

		int opCount = 100000;

		ArrayQueue<Integer> arrayQueue = new ArrayQueue<>();
		double time1 = testQueue(arrayQueue, opCount);
		System.out.println("ArrayQueue, time: " + time1 + " s");

		LoopQueue<Integer> loopQueue = new LoopQueue<>();
		double time2 = testQueue(loopQueue, opCount);
		System.out.println("LoopQueue, time: " + time2 + " s");

		LinkedListQueue<Integer> linkedListQueue = new LinkedListQueue<>();
		double time3 = testQueue(linkedListQueue, opCount);
		System.out.println("linkedListQueue, time: " + time3 + " s");
	}
}
