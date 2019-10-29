package com.github.zj.dreamly.socket.chatroom.library.core;

import java.io.Closeable;
import java.nio.channels.SocketChannel;

/**
 * @author 苍海之南
 */
public interface IoProvider extends Closeable {
	boolean registerInput(SocketChannel channel, HandleInputCallback callback);

	boolean registerOutput(SocketChannel channel, HandleOutputCallback callback);

	void unRegisterInput(SocketChannel channel);

	void unRegisterOutput(SocketChannel channel);

	abstract class HandleInputCallback implements Runnable {
		@Override
		public final void run() {
			canProviderInput();
		}

		protected abstract void canProviderInput();
	}

	abstract class HandleOutputCallback implements Runnable {

		@Override
		public final void run() {
			canProviderOutput();
		}

		protected abstract void canProviderOutput();
	}

}
