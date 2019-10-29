package com.github.zj.dreamly.guava.cache.lru.test;

import com.github.zj.dreamly.guava.cache.lru.LRUCache;
import com.github.zj.dreamly.guava.cache.lru.LinkedListLRUCache;

/**
 * @author 苍海之南
 */
public class LinkedListLRUCacheExample {

	public static void main(String[] args) {
		LRUCache<String, String> cache = new LinkedListLRUCache<>(3);
		cache.put("1", "1");
		cache.put("2", "2");
		cache.put("3", "3");
		System.out.println(cache);
		cache.put("4", "4");

		System.out.println(cache);
		System.out.println(cache.get("2"));
		System.out.println(cache);

	}
}
