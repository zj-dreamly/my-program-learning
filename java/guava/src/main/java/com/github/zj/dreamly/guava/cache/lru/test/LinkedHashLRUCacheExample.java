package com.github.zj.dreamly.guava.cache.lru.test;

import com.github.zj.dreamly.guava.cache.lru.LRUCache;
import com.github.zj.dreamly.guava.cache.lru.LinkedHashLRUCache;

/**
 * <h2>LinkedHashLRUCacheExample</h2>
 *
 * @author: 苍海之南
 * @since: 2019-10-29 15:01
 **/
public class LinkedHashLRUCacheExample {
	public static void main(String[] args) {
		LRUCache<String, String> cache = new LinkedHashLRUCache<>(3);
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
