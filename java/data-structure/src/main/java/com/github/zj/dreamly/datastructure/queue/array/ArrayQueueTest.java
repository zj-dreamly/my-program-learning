package com.github.zj.dreamly.datastructure.queue.array;

/**
 * <h2>ArrayQueueTest</h2>
 *
 * @author: 苍海之南
 * @since: 2019-08-28 14:52
 **/
public class ArrayQueueTest {
	public static void main(String[] args) {

		ArrayQueue<Integer> queue = new ArrayQueue<>();
		for (int i = 0; i < 10; i++) {
			queue.enqueue(i);
			System.out.println(queue);
			if (i % 3 == 2) {
				queue.dequeue();
				System.out.println(queue);
			}
		}
	}
}
