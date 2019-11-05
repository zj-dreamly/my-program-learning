package com.github.zj.dreamly.concurrent.future;

/**
 * @author 苍海之南
 */
public interface FutureTask<T> {

	/**
	 * task method
	 * @return the result
	 */
    T call();
}
