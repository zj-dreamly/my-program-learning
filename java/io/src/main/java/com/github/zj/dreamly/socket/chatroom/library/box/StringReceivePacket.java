package com.github.zj.dreamly.socket.chatroom.library.box;

import java.io.ByteArrayOutputStream;

/**
 * 字符串接收包
 * @author 苍海之南
 */
public class StringReceivePacket extends AbsByteArrayReceivePacket<String> {

	public StringReceivePacket(long len) {
		super(len);
	}

	@Override
	protected String buildEntity(ByteArrayOutputStream stream) {
		return new String(stream.toByteArray());
	}

	@Override
	public byte type() {
		return TYPE_MEMORY_STRING;
	}
}
