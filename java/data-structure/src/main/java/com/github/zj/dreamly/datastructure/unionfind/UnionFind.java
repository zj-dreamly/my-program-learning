package com.github.zj.dreamly.datastructure.unionfind;

/**
 * <h2>并查集</h2>
 *
 * @author: 苍海之南
 * @since: 2019-10-04 09:17
 **/
public interface UnionFind {
	/**
	 * 大小
	 * @return 返回并查集大小
	 */
	int getSize();
	/**
	 * 是否有关系
	 * @param p p
	 * @param q q
	 * @return  是否有关系
	 */
	boolean isConnected(int p, int q);

	/**
	 * 合并
	 * @param p p
	 * @param q q
	 */
	void unionElements(int p, int q);
}
