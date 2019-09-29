package com.github.zj.dreamly.datastructure.segmenttree;

/**
 * <h2>Merger</h2>
 *
 * @author: 苍海之南
 * @since: 2019-09-29 08:52
 **/
public interface Merger<E> {
	/**
	 * 合并操作
	 * @param a 左节点
	 * @param b 右节点
	 * @return E
	 */
	E merge(E a, E b);
}
