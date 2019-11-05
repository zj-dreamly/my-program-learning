package com.github.zj.dreamly.concurrent.future;

/**
 * @author 苍海之南
 */
public interface Future<T> {

	/**
	 * get a result ,this method will blocked
	 * @return result
	 * @throws InterruptedException InterruptedException
	 */
    T get() throws InterruptedException;

}
