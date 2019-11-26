package com.github.zj.dreamly.concurrent.collections;

import java.util.concurrent.atomic.AtomicReference;

/**
 * @author 苍海之南
 */
public class LockFreeQueue<E> {

	private AtomicReference<Node<E>> head, tail;

	public LockFreeQueue() {
		Node<E> node = new Node<>(null);
		this.head = new AtomicReference<>(node);
		this.tail = new AtomicReference<>(node);
	}

	public void addLast(E e) {
		if (e == null) {
			throw new NullPointerException("The null element not allow");
		}
		Node<E> newNode = new Node<>(e);
		Node<E> previousNode = tail.getAndSet(newNode);
		previousNode.next = newNode;
	}

	public E removeFirst() {

		Node<E> headNode, valueNode;
		do {
			headNode = head.get();
			valueNode = headNode.next;
		} while (valueNode != null && !head.compareAndSet(headNode, valueNode));

		E value = (valueNode != null ? valueNode.element : null);
		if (valueNode != null) {
			valueNode.element = null;
		}

		return value;
	}

	public boolean isEmpty() {
		synchronized (this) {
			return head.get().next == null;
		}
	}

	private static class Node<E> {
		E element;
		volatile Node<E> next;

		public Node(E element) {
			this.element = element;
		}

		@Override
		public String toString() {
			return element == null ? "" : element.toString();
		}
	}
}
