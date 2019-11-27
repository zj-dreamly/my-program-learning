package com.github.zj.dreamly.socket.chatroom.library.box;

import java.io.ByteArrayOutputStream;

/**
 * 纯Byte数组接收包
 *
 * @author 苍海之南
 */
public class BytesReceivePacket extends AbsByteArrayReceivePacket<byte[]> {

	public BytesReceivePacket(long len) {
		super(len);
	}

	@Override
	public byte type() {
		return TYPE_MEMORY_BYTES;
	}

	@Override
	protected byte[] buildEntity(ByteArrayOutputStream stream) {
		return stream.toByteArray();
	}
}
