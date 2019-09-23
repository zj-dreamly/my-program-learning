package com.github.zj.dreamly.datastructure.map;

import com.github.zj.dreamly.datastructure.set.FileOperation;

import java.util.ArrayList;

/**
 * <h2>MapTest</h2>
 *
 * @author: 苍海之南
 * @since: 2019-09-23 15:52
 **/
public class MapTest {
	private static double testMap(Map<String, Integer> map, String filename) {

		long startTime = System.nanoTime();

		System.out.println(filename);
		ArrayList<String> words = new ArrayList<>();
		if (FileOperation.readFile(filename, words)) {
			System.out.println("Total words: " + words.size());

			for (String word : words) {
				if (map.contains(word)) {
					map.set(word, map.get(word) + 1);
				} else {
					map.add(word, 1);
				}
			}

			System.out.println("Total different words: " + map.getSize());
			System.out.println("Frequency of PRIDE: " + map.get("pride"));
			System.out.println("Frequency of PREJUDICE: " + map.get("prejudice"));
		}

		long endTime = System.nanoTime();

		return (endTime - startTime) / 1000000000.0;
	}

	public static void main(String[] args) {

		String filename = "pride-and-prejudice.txt";

		BinarySearchTreeMap<String, Integer> bstMap = new BinarySearchTreeMap<>();
		double time1 = testMap(bstMap, filename);
		System.out.println("BST Map: " + time1 + " s");

		System.out.println();

		LinkedListMap<String, Integer> linkedListMap = new LinkedListMap<>();
		double time2 = testMap(linkedListMap, filename);
		System.out.println("Linked List Map: " + time2 + " s");

	}
}
