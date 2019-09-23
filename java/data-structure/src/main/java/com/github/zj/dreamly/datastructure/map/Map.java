package com.github.zj.dreamly.datastructure.map;

/**
 * <h2>Map</h2>
 *
 * @author: 苍海之南
 * @since: 2019-09-23 15:41
 **/
public interface Map<K, V> {

	/**
	 * 添加元素
	 *
	 * @param key   待添加key
	 * @param value 待添加value
	 */
	void add(K key, V value);

	/**
	 * 删除元素
	 *
	 * @param key 待删除key
	 * @return 待删除元素value
	 */
	V remove(K key);

	/**
	 * 是否包含此key
	 *
	 * @param key key
	 * @return 是否包含
	 */
	boolean contains(K key);

	/**
	 * 获取键值
	 *
	 * @param key key
	 * @return 值
	 */
	V get(K key);

	/**
	 * 更新值
	 *
	 * @param key      key
	 * @param newValue value
	 */
	void set(K key, V newValue);

	/**
	 * 获取map长度
	 *
	 * @return 长度
	 */
	int getSize();

	/**
	 * map 是否为空
	 *
	 * @return 是否为空
	 */
	boolean isEmpty();
}
