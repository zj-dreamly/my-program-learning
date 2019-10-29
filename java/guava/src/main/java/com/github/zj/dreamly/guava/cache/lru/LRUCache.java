package com.github.zj.dreamly.guava.cache.lru;

/**
 * <h2>LRUCache</h2>
 *
 * @author: 苍海之南
 * @since: 2019-10-29 14:59
 **/
public interface LRUCache<K, V> {
	void put(K key, V value);

	V get(K key);

	void remove(K key);

	int size();

	void clear();

	int limit();
}
