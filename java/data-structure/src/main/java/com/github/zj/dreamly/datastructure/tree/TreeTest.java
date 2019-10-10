package com.github.zj.dreamly.datastructure.tree;

import com.github.zj.dreamly.datastructure.hash.HashTable;
import com.github.zj.dreamly.datastructure.set.FileOperation;
import com.github.zj.dreamly.datastructure.tree.balancebinarytree.BalanceBinaryTree;
import com.github.zj.dreamly.datastructure.tree.redblacktree.RedBlackTree;

import java.util.ArrayList;

/**
 * <h2>TreeTest</h2>
 *
 * @author: 苍海之南
 * @since: 2019-10-10 15:19
 **/
public class TreeTest {
	public static void main(String[] args) {

		System.out.println("Pride and Prejudice");

		ArrayList<String> words = new ArrayList<>();
		if (FileOperation.readFile("java\\data-structure\\src\\main\\resources\\pride-and-prejudice.txt", words)) {
			System.out.println("Total words: " + words.size());

			long startTime = System.nanoTime();
			// Test AVL
			startTime = System.nanoTime();

			BalanceBinaryTree<String, Integer> avl = new BalanceBinaryTree<>();
			for (String word : words) {
				if (avl.contains(word)) {
					avl.set(word, avl.get(word) + 1);
				} else {
					avl.add(word, 1);
				}
			}

			for (String word : words) {
				avl.contains(word);
			}

			long endTime = System.nanoTime();
			double time = (endTime - startTime) / 1000000000.0;
			endTime = System.nanoTime();

			time = (endTime - startTime) / 1000000000.0;
			System.out.println("AVL: " + time + " s");

			// Test RBTree
			startTime = System.nanoTime();

			RedBlackTree<String, Integer> rbt = new RedBlackTree<>();
			for (String word : words) {
				if (rbt.contains(word)) {
					rbt.set(word, rbt.get(word) + 1);
				} else {
					rbt.add(word, 1);
				}
			}

			for (String word : words) {
				rbt.contains(word);
			}

			endTime = System.nanoTime();

			time = (endTime - startTime) / 1000000000.0;
			System.out.println("RBTree: " + time + " s");

			// Test HashTable
			startTime = System.nanoTime();

			HashTable<String, Integer> ht = new HashTable<>();
			//HashTable<String, Integer> ht = new HashTable<>(131071);
			for (String word : words) {
				if (ht.contains(word)) {
					ht.set(word, ht.get(word) + 1);
				} else {
					ht.add(word, 1);
				}
			}

			for (String word : words) {
				ht.contains(word);
			}

			endTime = System.nanoTime();

			time = (endTime - startTime) / 1000000000.0;
			System.out.println("HashTable: " + time + " s");
		}

		System.out.println();
	}
}
