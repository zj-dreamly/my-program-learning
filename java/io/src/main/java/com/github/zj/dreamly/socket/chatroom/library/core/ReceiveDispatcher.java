package com.github.zj.dreamly.socket.chatroom.library.core;

import java.io.Closeable;

/**
 * 接收的数据调度封装
 * 把一份或者多分IoArgs组合成一份Packet
 * @author 苍海之南
 */
public interface ReceiveDispatcher extends Closeable {
	void start();

	void stop();

	interface ReceivePacketCallback {
		ReceivePacket<?, ?> onArrivedNewPacket(byte type, long length);

		void onReceivePacketCompleted(ReceivePacket packet);
	}
}
