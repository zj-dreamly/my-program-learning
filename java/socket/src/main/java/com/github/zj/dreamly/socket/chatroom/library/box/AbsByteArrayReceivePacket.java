package com.github.zj.dreamly.socket.chatroom.library.box;

import com.github.zj.dreamly.socket.chatroom.library.core.ReceivePacket;

import java.io.ByteArrayOutputStream;

/**
 * 定义最基础的基于{@link ByteArrayOutputStream}的输出接收包
 *
 * @param <Entity> 对应的实体范性，需定义{@link ByteArrayOutputStream}流最终转化为什么数据实体
 * @author 苍海之南
 */
public abstract class AbsByteArrayReceivePacket<Entity> extends ReceivePacket<ByteArrayOutputStream, Entity> {

	public AbsByteArrayReceivePacket(long len) {
		super(len);
	}

	/**
	 * 创建流操作直接返回一个{@link ByteArrayOutputStream}流
	 *
	 * @return {@link ByteArrayOutputStream}
	 */
	@Override
	protected final ByteArrayOutputStream createStream() {
		return new ByteArrayOutputStream((int) length);
	}
}
