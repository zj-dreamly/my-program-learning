package com.github.zj.dreamly.socket.chatroom.library.core;

import java.io.InputStream;

/**
 * 发送的包定义
 */
public abstract class SendPacket<T extends InputStream> extends Packet<T> {
	private boolean isCanceled;

	public boolean isCanceled() {
		return isCanceled;
	}

	/**
	 * 设置取消发送标记
	 */
	public void cancel() {
		isCanceled = true;
	}
}
