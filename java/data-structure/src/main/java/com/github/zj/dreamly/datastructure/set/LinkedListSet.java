package com.github.zj.dreamly.datastructure.set;

import com.github.zj.dreamly.datastructure.linkedlist.LinkedList;

/**
 * <h2>LinkedListSet</h2>
 *
 * @author: 苍海之南
 * @since: 2019-09-23 10:55
 **/
public class LinkedListSet<E> implements Set<E> {

	private LinkedList<E> list;

	public LinkedListSet() {
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
	public void add(E e) {
		if (!list.contains(e)) {
			list.addFirst(e);
		}
	}

	@Override
	public boolean contains(E e) {
		return list.contains(e);
	}

	@Override
	public void remove(E e) {
		list.removeElement(e);
	}
}
