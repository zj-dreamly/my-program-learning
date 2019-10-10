package com.github.zj.dreamly.datastructure.hash;

/**
 * <h2>HashTableTest</h2>
 *
 * @author: 苍海之南
 * @since: 2019-10-10 16:11
 **/
public class HashTableTest {
	public static void main(String[] args) {
		HashTable<Integer, Integer> hashTable = new HashTable<>();

		hashTable.add(1, 1);

		final Integer integer = hashTable.get(1);
		System.out.println(integer);

	}
}
