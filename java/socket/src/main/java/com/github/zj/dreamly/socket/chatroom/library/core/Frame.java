package com.github.zj.dreamly.socket.chatroom.library.core;

import java.io.IOException;

/**
 * 帧-分片使用
 * @author 苍海之南
 */
public abstract class Frame {
	// 帧头长度
	public static final int FRAME_HEADER_LENGTH = 6;
	// 单帧最大容量 64KB
	public static final int MAX_CAPACITY = 64 * 1024 - 1;

	// Packet头信息帧
	public static final byte TYPE_PACKET_HEADER = 11;
	// Packet数据分片信息帧
	public static final byte TYPE_PACKET_ENTITY = 12;
	// 指令-发送取消
	public static final byte TYPE_COMMAND_SEND_CANCEL = 41;
	// 指令-接受拒绝
	public static final byte TYPE_COMMAND_RECEIVE_REJECT = 42;

	// Flag标记
	public static final byte FLAG_NONE = 0;

	// 头部6字节固定
	protected final byte[] header = new byte[FRAME_HEADER_LENGTH];

	public Frame(int length, byte type, byte flag, short identifier) {
		if (length < 0 || length > MAX_CAPACITY) {
			throw new RuntimeException("The Body length of a single frame should be between 0 and " + MAX_CAPACITY);
		}

		if (identifier < 1 || identifier > 255) {
			throw new RuntimeException("The Body identifier of a single frame should be between 1 and 255");
		}

		// 00000000 00000000 00000000 01000000
		header[0] = (byte) (length >> 8);
		header[1] = (byte) (length);

		header[2] = type;
		header[3] = flag;

		header[4] = (byte) identifier;
		header[5] = 0;

	}

	public Frame(byte[] header) {
		System.arraycopy(header, 0, this.header, 0, FRAME_HEADER_LENGTH);
	}

	/**
	 * 获取Body的长度
	 *
	 * @return 当前帧Body总长度[0~MAX_CAPACITY]
	 */
	public int getBodyLength() {
		// 00000000
		// 01000000

		// 00000000 00000000 00000000 01000000

		// 01000000
		// 11111111 11111111 11111111 01000000
		// 00000000 00000000 00000000 11111111 0xFF
		// 00000000 00000000 00000000 01000000

		return ((((int) header[0]) & 0xFF) << 8) | (((int) header[1]) & 0xFF);
	}

	/**
	 * 获取Body的类型
	 *
	 * @return 类型[0~255]
	 */
	public byte getBodyType() {
		return header[2];
	}

	/**
	 * 获取Body的Flag
	 *
	 * @return Flag
	 */
	public byte getBodyFlag() {
		return header[3];
	}

	/**
	 * 获取Body的唯一标志
	 *
	 * @return 标志[0~255]
	 */
	public short getBodyIdentifier() {
		return (short) (((short) header[4]) & 0xFF);
	}

	/**
	 * 进行数据读或写操作
	 *
	 * @param args 数据
	 * @return 是否已消费完全， True：则无需再传递数据到Frame或从当前Frame读取数据
	 */
	public abstract boolean handle(IoArgs args) throws IOException;

	/**
	 * 基于当前帧尝试构建下一份待消费的帧
	 *
	 * @return NULL：没有待消费的帧
	 */
	public abstract Frame nextFrame();
	// 64MB 64KB 1024+1 6

	public abstract int getConsumableLength();
}
