package com.github.zj.dreamly.socket.chatroom.library.frames;

import com.github.zj.dreamly.socket.chatroom.library.core.Frame;
import com.github.zj.dreamly.socket.chatroom.library.core.IoArgs;

import java.io.IOException;

/**
 * @author 苍海之南
 */
public abstract class AbsReceiveFrame extends Frame {
	/**
	 * 帧体可读写区域大小
	 */
	volatile int bodyRemaining;

	AbsReceiveFrame(byte[] header) {
		super(header);
		bodyRemaining = getBodyLength();
	}

	@Override
	public synchronized boolean handle(IoArgs args) throws IOException {
		if (bodyRemaining == 0) {
			// 已读取所有数据
			return true;
		}

		bodyRemaining -= consumeBody(args);

		return bodyRemaining == 0;
	}

	@Override
	public final Frame nextFrame() {
		return null;
	}

	@Override
	public int getConsumableLength() {
		return bodyRemaining;
	}

	protected abstract int consumeBody(IoArgs args) throws IOException;
}
