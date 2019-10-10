package com.github.zj.dreamly.datastructure.hash;

import java.util.TreeMap;

/**
 * <h2>HashTable</h2>
 *
 * @author: 苍海之南
 * @since: 2019-10-10 15:15
 **/
public class HashTable<K extends Comparable<K>, V> {

	private final int[] capacity
		= {53, 97, 193, 389, 769, 1543, 3079, 6151, 12289, 24593,
		49157, 98317, 196613, 393241, 786433, 1572869, 3145739, 6291469,
		12582917, 25165843, 50331653, 100663319, 201326611, 402653189, 805306457, 1610612741};

	private static final int UPPER_TOL = 10;
	private static final int LOWER_TOL = 2;
	private int capacityIndex = 0;

	private TreeMap<K, V>[] hashtable;
	private int size;
	private int m;

	@SuppressWarnings("unchecked")
	public HashTable() {
		this.m = capacity[capacityIndex];
		size = 0;
		hashtable = new TreeMap[m];
		for (int i = 0; i < m; i++) {
			hashtable[i] = new TreeMap<>();
		}
	}

	private int hash(K key) {
		return (key.hashCode() & 0x7fffffff) % m;
	}

	public int getSize() {
		return size;
	}

	public void add(K key, V value) {
		TreeMap<K, V> map = hashtable[hash(key)];
		if (map.containsKey(key)) {
			map.put(key, value);
		} else {
			map.put(key, value);
			size++;

			if (size >= UPPER_TOL * m && capacityIndex + 1 < capacity.length) {
				capacityIndex++;
				resize(capacity[capacityIndex]);
			}
		}
	}

	public V remove(K key) {
		V ret = null;
		TreeMap<K, V> map = hashtable[hash(key)];
		if (map.containsKey(key)) {
			ret = map.remove(key);
			size--;

			if (size < LOWER_TOL * m && capacityIndex - 1 >= 0) {
				capacityIndex--;
				resize(capacity[capacityIndex]);
			}
		}
		return ret;
	}

	public void set(K key, V value) {
		TreeMap<K, V> map = hashtable[hash(key)];
		if (!map.containsKey(key)) {
			throw new IllegalArgumentException(key + " doesn't exist!");
		}

		map.put(key, value);
	}

	public boolean contains(K key) {
		return hashtable[hash(key)].containsKey(key);
	}

	public V get(K key) {
		return hashtable[hash(key)].get(key);
	}

	@SuppressWarnings("unchecked")
	private void resize(int newm) {
		TreeMap<K, V>[] newHashTable = new TreeMap[newm];
		for (int i = 0; i < newm; i++) {
			newHashTable[i] = new TreeMap<>();
		}

		int oldm = m;
		this.m = newm;
		for (int i = 0; i < oldm; i++) {
			TreeMap<K, V> map = hashtable[i];
			for (K key : map.keySet()) {
				newHashTable[hash(key)].put(key, map.get(key));
			}
		}

		this.hashtable = newHashTable;
	}
}
