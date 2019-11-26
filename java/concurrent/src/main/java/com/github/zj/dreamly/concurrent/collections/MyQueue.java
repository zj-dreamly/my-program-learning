package com.github.zj.dreamly.concurrent.collections;

/**
 * @author 苍海之南
 */
public class MyQueue<E> {

	private static class Node<E> {
		private E element;
		private Node<E> next;

		private Node(E element, Node<E> next) {
			this.element = element;
			this.next = next;
		}

		private E getElement() {
			return element;
		}

		public void setElement(E element) {
			this.element = element;
		}

		private Node<E> getNext() {
			return next;
		}

		private void setNext(Node<E> next) {
			this.next = next;
		}

		@Override
		public String toString() {
			return (element == null) ? "" : element.toString();
		}
	}

	private Node<E> first, last;
	private int size;

	public int size() {
		return size;
	}

	public boolean isEmpty() {
		return size() == 0;
	}

	public E peekFirst() {
		return (first == null) ? null : first.getElement();
	}

	public E peekLast() {
		return (last == null) ? null : last.getElement();
	}

	public void addLast(E element) {
		Node<E> newNode = new Node<>(element, null);
		if (size == 0) {
			first = newNode;

		} else {
			last.setNext(newNode);
		}
		last = newNode;
		size++;
	}

	public E removeFirst() {
		if (isEmpty()) {
			return null;
		}
		E answer = first.getElement();
		first = first.getNext();
		size--;
		if (size == 0) {
			last = null;
		}
		return answer;
	}
}
