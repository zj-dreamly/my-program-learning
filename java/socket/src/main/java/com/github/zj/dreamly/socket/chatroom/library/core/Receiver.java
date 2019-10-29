package com.github.zj.dreamly.socket.chatroom.library.core;

import java.io.Closeable;
import java.io.IOException;

public interface Receiver extends Closeable {
	void setReceiveListener(IoArgs.IoArgsEventProcessor processor);

	boolean postReceiveAsync() throws IOException;
}
