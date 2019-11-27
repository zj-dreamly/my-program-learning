package com.github.zj.dreamly.socket.chatroom.library.frames;

import com.github.zj.dreamly.socket.chatroom.library.core.Frame;
import com.github.zj.dreamly.socket.chatroom.library.core.IoArgs;

/**
 * 取消发送帧，用于标志某Packet取消进行发送数据
 * @author 苍海之南
 */
public class CancelSendFrame extends AbsSendFrame {

	public CancelSendFrame(short identifier) {
		super(0, Frame.TYPE_COMMAND_SEND_CANCEL, Frame.FLAG_NONE, identifier);
	}

	@Override
	protected int consumeBody(IoArgs args) {
		return 0;
	}

	@Override
	public Frame nextFrame() {
		return null;
	}
}
