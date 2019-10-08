package com.github.zj.dreamly.datastructure.map;

import com.github.zj.dreamly.datastructure.tree.balancebinarytree.BalanceBinaryTree;

/**
 * <h2>BalanceBinaryMap</h2>
 *
 * @author: 苍海之南
 * @since: 2019-09-23 15:38
 **/
public class BalanceBinaryMap<K extends Comparable<K>, V> implements Map<K, V> {

	private BalanceBinaryTree<K, V> balanceBinaryTree;

	public BalanceBinaryMap() {
		balanceBinaryTree = new BalanceBinaryTree<>();
	}

	@Override
	public int getSize() {
		return balanceBinaryTree.getSize();
	}

	@Override
	public boolean isEmpty() {
		return balanceBinaryTree.isEmpty();
	}

	@Override
	public void add(K key, V value) {
		balanceBinaryTree.add(key, value);
	}

	@Override
	public boolean contains(K key) {
		return balanceBinaryTree.contains(key);
	}

	@Override
	public V get(K key) {
		return balanceBinaryTree.get(key);
	}

	@Override
	public void set(K key, V newValue) {
		balanceBinaryTree.set(key, newValue);
	}

	@Override
	public V remove(K key) {
		return balanceBinaryTree.remove(key);
	}
}
