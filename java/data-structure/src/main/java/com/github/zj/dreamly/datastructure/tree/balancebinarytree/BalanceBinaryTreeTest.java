package com.github.zj.dreamly.datastructure.tree.balancebinarytree;

import com.github.zj.dreamly.datastructure.set.FileOperation;

import java.util.ArrayList;

/**
 * <h2>BalanceBinaryTreeTest</h2>
 *
 * @author: 苍海之南
 * @since: 2019-10-08 17:39
 **/
public class BalanceBinaryTreeTest {

	public static void main(String[] args) {
		System.out.println("Pride and Prejudice");

		ArrayList<String> words = new ArrayList<>();

		if (FileOperation.readFile("java\\data-structure\\src\\main\\resources\\pride-and-prejudice.txt",
			words)) {
			System.out.println("Total words: " + words.size());

			BalanceBinaryTree<String, Integer> map = new BalanceBinaryTree<>();
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

			System.out.println("is BST : " + map.isBalanceBinaryTree());
			System.out.println("is Balanced : " + map.isBalanced());

			for (String word : words) {
				map.remove(word);
				if (!map.isBalanceBinaryTree() || !map.isBalanced()) {
					throw new RuntimeException();
				}
			}
		}

		System.out.println();
	}
}
