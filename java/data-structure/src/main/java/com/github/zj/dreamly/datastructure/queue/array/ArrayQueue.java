package com.github.zj.dreamly.datastructure.queue.array;

import com.github.zj.dreamly.datastructure.array.Array;
import com.github.zj.dreamly.datastructure.queue.Queue;

/**
 * <h2>ArrayQueue</h2>
 *
 * @author: 苍海之南
 * @since: 2019-08-28 14:51
 **/
public class ArrayQueue<E> implements Queue<E> {

	private Array<E> array;

	public ArrayQueue(int capacity) {
		array = new Array<>(capacity);
	}

	public ArrayQueue() {
		array = new Array<>();
	}

	@Override
	public int getSize() {
		return array.getSize();
	}

	@Override
	public boolean isEmpty() {
		return array.isEmpty();
	}

	public int getCapacity() {
		return array.getCapacity();
	}

	@Override
	public void enqueue(E e) {
		array.addLast(e);
	}

	@Override
	public E dequeue() {
		return array.removeFirst();
	}

	@Override
	public E getFront() {
		return array.getFirst();
	}

	@Override
	public String toString() {
		StringBuilder res = new StringBuilder();
		res.append("Queue: ");
		res.append("front [");
		for (int i = 0; i < array.getSize(); i++) {
			res.append(array.get(i));
			if (i != array.getSize() - 1) {
				res.append(", ");
			}
		}
		res.append("] tail");
		return res.toString();
	}
}
