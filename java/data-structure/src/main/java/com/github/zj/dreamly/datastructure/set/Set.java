package com.github.zj.dreamly.datastructure.set;

/**
 * <h2>Set</h2>
 *
 * @author: 苍海之南
 * @since: 2019-09-23 10:54
 **/
public interface Set<E> {
	/**
	 * 添加元素
	 *
	 * @param e 待添加元素
	 */
	void add(E e);

	/**
	 * 是否包含某个元素
	 *
	 * @param e 元素
	 * @return 是否包含
	 */
	boolean contains(E e);

	/**
	 * 删除某个元素
	 *
	 * @param e 待删除元素
	 */
	void remove(E e);

	/**
	 * 集合长度
	 *
	 * @return 长度
	 */
	int getSize();

	/**
	 * 集合是否为空
	 *
	 * @return 是否为空
	 */
	boolean isEmpty();
}
