package com.github.zj.dreamly.datastructure.linkedlist;

/**
 * <h2>LinkedListTest</h2>
 *
 * @author: 苍海之南
 * @since: 2019-09-01 14:48
 **/
public class LinkedListTest {
	public static void main(String[] args) {
		LinkedList<Integer> linkedList = new LinkedList<>();
		for (int i = 0; i < 5; i++) {
			linkedList.addFirst(i);
			System.out.println(linkedList);
		}

		linkedList.add(2, 666);
		System.out.println(linkedList);

		linkedList.remove(2);
		System.out.println(linkedList);

		linkedList.removeFirst();
		System.out.println(linkedList);

		linkedList.removeLast();
		System.out.println(linkedList);

	}
}
