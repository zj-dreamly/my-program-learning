package com.github.zj.dreamly.socket.chatroom.library.frames;

import com.github.zj.dreamly.socket.chatroom.library.core.Frame;
import com.github.zj.dreamly.socket.chatroom.library.core.IoArgs;
import com.github.zj.dreamly.socket.chatroom.library.core.SendPacket;

import java.io.IOException;

/**
 * @author 苍海之南
 */
public abstract class AbsSendPacketFrame extends AbsSendFrame {
	protected volatile SendPacket<?> packet;

	public AbsSendPacketFrame(int length, byte type, byte flag, short identifier, SendPacket packet) {
		super(length, type, flag, identifier);
		this.packet = packet;
	}

	/**
	 * 获取当前对应的发送Packet
	 *
	 * @return SendPacket
	 */
	public synchronized SendPacket getPacket() {
		return packet;
	}

	@Override
	public synchronized boolean handle(IoArgs args) throws IOException {
		if (packet == null && !isSending()) {
			// 已取消，并且未发送任何数据，直接返回结束，发送下一帧
			return true;
		}
		return super.handle(args);
	}

	/**
	 * 构建下一帧时做一次判断，如果已经终止，则没有下一帧；
	 * 如果没有则尝试进行构建操作
	 *
	 * @return 下一帧
	 */
	@Override
	public final synchronized Frame nextFrame() {
		return packet == null ? null : buildNextFrame();
	}

	/**
	 * 终止当前帧
	 * 需要在当前方法中做一些操作，以及状态的维护
	 * 后续可以扩展{@link #fillDirtyDataOnAbort()}方法对数据进行填充操作
	 *
	 * @return True：完美终止，可以顺利的移除当前帧；False：已发送部分数据
	 */
	public final synchronized boolean abort() {
		// True, 当前帧没有发送任何数据
		// 1234, 12,34
		boolean isSending = isSending();
		if (isSending) {
			fillDirtyDataOnAbort();
		}

		packet = null;

		return !isSending;
	}

	protected void fillDirtyDataOnAbort() {

	}

	/**
	 * 构建下一帧
	 *
	 * @return NULL：没有下一帧
	 */
	protected abstract Frame buildNextFrame();

}
