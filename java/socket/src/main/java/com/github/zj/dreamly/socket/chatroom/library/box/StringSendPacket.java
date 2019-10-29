package com.github.zj.dreamly.socket.chatroom.library.box;

/**
 * 字符串发送包
 *
 * @author 苍海之南
 */
public class StringSendPacket extends BytesSendPacket {

	/**
	 * 字符串发送时就是Byte数组，所以直接得到Byte数组，并按照Byte的发送方式发送即可
	 *
	 * @param msg 字符串
	 */
	public StringSendPacket(String msg) {
		super(msg.getBytes());
	}

	@Override
	public byte type() {
		return TYPE_MEMORY_STRING;
	}
}
