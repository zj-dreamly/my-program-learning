package com.github.zj.dreamly.socket.chatroom.library.frames;

import com.github.zj.dreamly.socket.chatroom.library.core.IoArgs;

import java.io.IOException;
import java.nio.channels.WritableByteChannel;

/**
 * @author 苍海之南
 */
public class ReceiveEntityFrame extends AbsReceiveFrame {
	private WritableByteChannel channel;

	ReceiveEntityFrame(byte[] header) {
		super(header);
	}

	public void bindPacketChannel(WritableByteChannel channel) {
		this.channel = channel;
	}

	@Override
	protected int consumeBody(IoArgs args) throws IOException {
		return channel == null ? args.setEmpty(bodyRemaining) : args.writeTo(channel);
	}
}
