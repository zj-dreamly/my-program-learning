package com.github.zj.dreamly.datastructure.stack;

/**
 * <h2>stack</h2>
 * 后进先出(LIFO)
 *
 * @author: 苍海之南
 * @since: 2019-08-28 10:41
 **/
public interface Stack<E> {

	/**
	 * 获取栈大小
	 *
	 * @return 大小
	 */
	int getSize();

	/**
	 * 栈是否为空
	 *
	 * @return 是否为空
	 */
	boolean isEmpty();

	/**
	 * 入栈
	 *
	 * @param e 元素
	 */
	void push(E e);

	/**
	 * 出栈
	 *
	 * @return 元素
	 */
	E pop();

	/**
	 * 查看元素
	 *
	 * @return 元素
	 */
	E peek();
}
