package com.github.zj.dreamly.concurrent.future;

import java.util.function.Consumer;

/**
 * @author 苍海之南
 */
public class FutureService {

	public <T> Future<T> submit(final FutureTask<T> task) {
		AsyncFuture<T> asyncFuture = new AsyncFuture<>();
		new Thread(() -> {
			T result = task.call();
			asyncFuture.done(result);
		}).start();
		return asyncFuture;
	}

	public <T> Future<T> submit(final FutureTask<T> task, final Consumer<T> consumer) {
		AsyncFuture<T> asyncFuture = new AsyncFuture<>();
		new Thread(() -> {
			T result = task.call();
			asyncFuture.done(result);
			consumer.accept(result);
		}).start();
		return asyncFuture;
	}
}
