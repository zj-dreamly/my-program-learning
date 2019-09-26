package com.github.zj.dreamly.datastructure.queue;

import com.github.zj.dreamly.datastructure.maxheap.MaxHeap;

/**
 * <h2>PriorityQueue</h2>
 *
 * @author: 苍海之南
 * @since: 2019-08-28 14:48
 **/
public class PriorityQueue<E extends Comparable<E>> implements Queue<E> {

	private MaxHeap<E> maxHeap;

	public PriorityQueue() {
		maxHeap = new MaxHeap<>();
	}

	@Override
	public int getSize() {
		return maxHeap.size();
	}

	@Override
	public boolean isEmpty() {
		return maxHeap.isEmpty();
	}

	@Override
	public E getFront() {
		return maxHeap.findMax();
	}

	@Override
	public void enqueue(E e) {
		maxHeap.add(e);
	}

	@Override
	public E dequeue() {
		return maxHeap.extractMax();
	}
}
