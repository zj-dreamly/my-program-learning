package com.github.zj.dreamly.socket.chatroom.library.frames;

import com.github.zj.dreamly.socket.chatroom.library.core.IoArgs;

import java.io.IOException;

/**
 * 取消传输帧，接收实现
 * @author 苍海之南
 */
public class CancelReceiveFrame extends AbsReceiveFrame {

	CancelReceiveFrame(byte[] header) {
		super(header);
	}

	@Override
	protected int consumeBody(IoArgs args) throws IOException {
		return 0;
	}
}
