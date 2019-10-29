package com.github.zj.dreamly.socket.chatroom.library.frames;

import com.github.zj.dreamly.socket.chatroom.library.core.Frame;
import com.github.zj.dreamly.socket.chatroom.library.core.IoArgs;

import java.io.IOException;

/**
 * @author 苍海之南
 */
public abstract class AbsSendFrame extends Frame {
	/**
	 * 帧头可读写区域大小
	 */
	volatile byte headerRemaining = Frame.FRAME_HEADER_LENGTH;
	/**
	 * 帧体可读写区域大小
	 */
	volatile int bodyRemaining;

	public AbsSendFrame(int length, byte type, byte flag, short identifier) {
		super(length, type, flag, identifier);
		bodyRemaining = length;
	}

	@Override
	public synchronized boolean handle(IoArgs args) throws IOException {
		try {
			args.limit(headerRemaining + bodyRemaining);
			args.startWriting();

			if (headerRemaining > 0 && args.remained()) {
				headerRemaining -= consumeHeader(args);
			}

			if (headerRemaining == 0 && args.remained() && bodyRemaining > 0) {
				bodyRemaining -= consumeBody(args);
			}

			return headerRemaining == 0 && bodyRemaining == 0;
		} finally {
			args.finishWriting();
		}
	}

	@Override
	public int getConsumableLength() {
		return headerRemaining + bodyRemaining;
	}

	private byte consumeHeader(IoArgs args) {
		int count = headerRemaining;
		int offset = header.length - count;
		return (byte) args.readFrom(header, offset, count);
	}

	protected abstract int consumeBody(IoArgs args) throws IOException;

	/**
	 * 是否已经处于发送数据中，如果已经发送了部分数据则返回True
	 * 只要头部数据已经开始消费，则肯定已经处于发送数据中
	 *
	 * @return True，已发送部分数据
	 */
	protected synchronized boolean isSending() {
		return headerRemaining < Frame.FRAME_HEADER_LENGTH;
	}
}
