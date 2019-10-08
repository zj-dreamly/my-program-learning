package com.github.zj.dreamly.datastructure.map;

import com.github.zj.dreamly.datastructure.set.Set;
import com.github.zj.dreamly.datastructure.tree.balancebinarytree.BalanceBinaryTree;

/**
 * <h2>BalanceBinarySet</h2>
 *
 * @author: 苍海之南
 * @since: 2019-09-23 15:38
 **/
public class BalanceBinarySet<E extends Comparable<E>> implements Set<E> {

	private BalanceBinaryTree<E, Object> avl;

	public BalanceBinarySet() {
		avl = new BalanceBinaryTree<>();
	}

	@Override
	public int getSize() {
		return avl.getSize();
	}

	@Override
	public boolean isEmpty() {
		return avl.isEmpty();
	}

	@Override
	public void add(E e) {
		avl.add(e, null);
	}

	@Override
	public boolean contains(E e) {
		return avl.contains(e);
	}

	@Override
	public void remove(E e) {
		avl.remove(e);
	}
}
