package com.github.zj.dreamly.datastructure.queue.loop;

import com.github.zj.dreamly.datastructure.queue.Queue;

/**
 * <h2>LoopQueue</h2>
 *
 * @author: 苍海之南
 * @since: 2019-08-30 09:24
 **/
public class LoopQueue<E> implements Queue<E> {

	/**
	 * 默认容量大小
	 */
	private static final int DEFAULT_CAPACITY = 10;
	/**
	 * 缩容临界值
	 */
	private static final int REDUCE_CRITICAL = 4;

	/**
	 * 数据
	 */
	private E[] data;
	/**
	 * 数组长度
	 */
	private int size;
	/**
	 * 头
	 */
	private int front;
	/**
	 * 尾
	 */
	private int tail;

	@SuppressWarnings("unchecked")
	public LoopQueue(int capacity) {
		data = (E[]) new Object[capacity + 1];
		front = 0;
		tail = 0;
		size = 0;
	}

	public LoopQueue() {
		this(DEFAULT_CAPACITY);
	}

	public int getCapacity() {
		return data.length - 1;
	}

	@Override
	public boolean isEmpty() {
		return front == tail;
	}

	@Override
	public int getSize() {
		return size;
	}

	@Override
	public void enqueue(E e) {

		if ((tail + 1) % data.length == front) {
			resize(getCapacity() * 2);
		}

		data[tail] = e;
		tail = (tail + 1) % data.length;
		size++;
	}

	@Override
	public E dequeue() {

		if (isEmpty()) {
			throw new IllegalArgumentException("Cannot dequeue from an empty queue.");
		}

		E ret = data[front];
		data[front] = null;
		front = (front + 1) % data.length;
		size--;
		if (size == getCapacity() / REDUCE_CRITICAL && getCapacity() / 2 != 0) {
			resize(getCapacity() / 2);
		}
		return ret;
	}

	@Override
	public E getFront() {
		if (isEmpty()) {
			throw new IllegalArgumentException("Queue is empty.");
		}
		return data[front];
	}

	@SuppressWarnings("unchecked")
	private void resize(int newCapacity) {

		E[] newData = (E[]) new Object[newCapacity + 1];
		for (int i = 0; i < size; i++) {
			newData[i] = data[(i + front) % data.length];
		}

		data = newData;
		front = 0;
		tail = size;
	}

	@Override
	public String toString() {

		StringBuilder res = new StringBuilder();
		res.append(String.format("Queue: size = %d , capacity = %d\n", size, getCapacity()));
		res.append("front [");
		for (int i = front; i != tail; i = (i + 1) % data.length) {
			res.append(data[i]);
			if ((i + 1) % data.length != tail) {
				res.append(", ");
			}
		}
		res.append("] tail");
		return res.toString();
	}
}
