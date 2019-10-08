package com.github.zj.dreamly.datastructure.tree.segmenttree;

/**
 * <h2>NumArray</h2>
 * <p>
 * Leetcode 307. Range Sum Query - Mutable
 * https://leetcode.com/problems/range-sum-query-mutable/description/
 *
 * @author: 苍海之南
 * @since: 2019-09-29 08:57
 **/
public class NumArrayTest {
	private SegmentTree<Integer> segTree;

	public NumArrayTest(int[] nums) {

		if (nums.length != 0) {
			Integer[] data = new Integer[nums.length];
			for (int i = 0; i < nums.length; i++) {
				data[i] = nums[i];
			}
			segTree = new SegmentTree<>(data, Integer::sum);
		}
	}

	public void update(int i, int val) {
		if (segTree == null) {
			throw new IllegalArgumentException("Error");
		}
		segTree.set(i, val);
	}

	public int sumRange(int i, int j) {
		if (segTree == null) {
			throw new IllegalArgumentException("Error");
		}
		return segTree.query(i, j);
	}
}
