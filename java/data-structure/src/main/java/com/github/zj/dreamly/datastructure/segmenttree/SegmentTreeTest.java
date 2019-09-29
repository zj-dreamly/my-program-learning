package com.github.zj.dreamly.datastructure.segmenttree;

/**
 * <h2>SegmentTreeTest</h2>
 * <p>
 * 该测试用例来源：Leetcode 303. Range Sum Query - Immutable
 * https://leetcode.com/problems/range-sum-query-immutable/description/
 *
 * @author: 苍海之南
 * @since: 2019-09-29 08:59
 **/
public class SegmentTreeTest {
	public static void main(String[] args) {

		Integer[] nums = {-2, 0, 3, -5, 2, -1};

		SegmentTree<Integer> segTree = new SegmentTree<>(nums,
			Integer::sum);
		System.out.println(segTree);

		System.out.println(segTree.query(0, 2));
		System.out.println(segTree.query(2, 5));
		System.out.println(segTree.query(0, 5));
	}
}
