package com.github.zj.dreamly.guava.cache.lru.test;

import com.github.zj.dreamly.guava.cache.lru.SoftLRUCache;

import java.util.concurrent.TimeUnit;

/**
 * <h2>LRUCacheExample</h2>
 *
 * @author: 苍海之南
 * @since: 2019-10-29 15:31
 **/
public class LRUCacheExample {
	public static void main(String[] args) throws InterruptedException
	{
		final SoftLRUCache<String, byte[]> cache = new SoftLRUCache<>(100);

		for (int i = 0; i < 1000; i++)
		{
			cache.put(String.valueOf(i), new byte[1024 * 1024 * 2]);
			TimeUnit.MILLISECONDS.sleep(600);
			System.out.println("The " + i + " entity is cached.");
		}
	}
}
