package com.github.zj.dreamly.datastructure.set;

import com.github.zj.dreamly.datastructure.binarysearchtree.BinarySearchTree;

/**
 * <h2>BSTSet</h2>
 *
 * @author: 苍海之南
 * @since: 2019-09-23 10:57
 **/
public class BinarySearchTreeSet<E extends Comparable<E>> implements Set<E> {

	private BinarySearchTree<E> bst;

	public BinarySearchTreeSet() {
		bst = new BinarySearchTree<>();
	}

	@Override
	public int getSize() {
		return bst.size();
	}

	@Override
	public boolean isEmpty() {
		return bst.isEmpty();
	}

	@Override
	public void add(E e) {
		bst.add(e);
	}

	@Override
	public boolean contains(E e) {
		return bst.contains(e);
	}

	@Override
	public void remove(E e) {
		bst.remove(e);
	}

}
