package com.github.zj.dreamly.datastructure.queue;

/**
 * <h2>Queue</h2>
 * 先进先出 FIFO
 *
 * @author: 苍海之南
 * @since: 2019-08-28 14:48
 **/
public interface Queue<E> {
	/**
	 * 获取队列长度
	 *
	 * @return 长度
	 */
	int getSize();

	/**
	 * 队列是否为空
	 *
	 * @return 是否为空
	 */
	boolean isEmpty();

	/**
	 * 入队
	 *
	 * @param e e
	 */
	void enqueue(E e);

	/**
	 * 出队
	 *
	 * @return e
	 */
	E dequeue();

	/**
	 * 查看元素
	 *
	 * @return e
	 */
	E getFront();
}
