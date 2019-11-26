package com.github.zj.dreamly.concurrent.collections;

/**
 * <h2>MyQueueTest</h2>
 *
 * @author: 苍海之南
 * @since: 2019-11-26 10:29
 **/
public class MyQueueTest {
	/**
	 * FIFO
	 */
	public static void main(String[] args) {
		MyQueue<String> queue = new MyQueue<>();
		queue.addLast("Hello");
		queue.addLast("World");
		queue.addLast("Java");

		System.out.println(queue.removeFirst());
		System.out.println(queue.removeFirst());
		System.out.println(queue.removeFirst());
	}
}
