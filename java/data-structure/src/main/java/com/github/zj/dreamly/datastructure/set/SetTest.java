package com.github.zj.dreamly.datastructure.set;

import java.util.ArrayList;

/**
 * <h2>SetTest</h2>
 *
 * @author: 苍海之南
 * @since: 2019-09-23 11:03
 **/
public class SetTest {
	private static double testSet(Set<String> set, String filename) {

		long startTime = System.nanoTime();

		System.out.println(filename);
		ArrayList<String> words = new ArrayList<>();
		if (FileOperation.readFile(filename, words)) {
			System.out.println("Total words: " + words.size());

			for (String word : words) {
				set.add(word);
			}
			System.out.println("Total different words: " + set.getSize());
		}
		long endTime = System.nanoTime();

		return (endTime - startTime) / 1000000000.0;
	}

	public static void main(String[] args) {

		String filename = "pride-and-prejudice.txt";

		BinarySearchTreeSet<String> bstSet = new BinarySearchTreeSet<>();
		double time1 = testSet(bstSet, filename);
		System.out.println("BST Set: " + time1 + " s");

		System.out.println();

		LinkedListSet<String> linkedListSet = new LinkedListSet<>();
		double time2 = testSet(linkedListSet, filename);
		System.out.println("Linked List Set: " + time2 + " s");

	}
}
