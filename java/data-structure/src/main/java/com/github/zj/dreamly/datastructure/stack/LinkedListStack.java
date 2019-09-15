package com.github.zj.dreamly.datastructure.stack;

import com.github.zj.dreamly.datastructure.linkedlist.LinkedList;

/**
 * <h2>LinkedListStack</h2>
 *
 * @author: 苍海之南
 * @since: 2019-09-13 19:27
 **/
public class LinkedListStack<E> implements Stack<E> {

	private LinkedList<E> list;

	public LinkedListStack() {
		list = new LinkedList<>();
	}

	@Override
	public int getSize() {
		return list.getSize();
	}

	@Override
	public boolean isEmpty() {
		return list.isEmpty();
	}

	@Override
	public void push(E e) {
		list.addFirst(e);
	}

	@Override
	public E pop() {
		return list.removeFirst();
	}

	@Override
	public E peek() {
		return list.getFirst();
	}

	@Override
	public String toString() {
		return "Stack: top " +
			list;
	}
}
