package com.github.zj.dreamly.socket.chatroom.library.box;

import com.github.zj.dreamly.socket.chatroom.library.core.SendPacket;

import java.io.ByteArrayInputStream;

/**
 * 纯Byte数组发送包
 *
 * @author 苍海之南
 */
public class BytesSendPacket extends SendPacket<ByteArrayInputStream> {
	private final byte[] bytes;

	public BytesSendPacket(byte[] bytes) {
		this.bytes = bytes;
		this.length = bytes.length;
	}

	@Override
	public byte type() {
		return TYPE_MEMORY_BYTES;
	}

	@Override
	protected ByteArrayInputStream createStream() {
		return new ByteArrayInputStream(bytes);
	}

}
