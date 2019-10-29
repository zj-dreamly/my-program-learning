package com.github.zj.dreamly.socket.chatroom.server.handle;

import com.github.zj.dreamly.socket.chatroom.foo.Foo;
import com.github.zj.dreamly.socket.chatroom.library.core.Connector;
import com.github.zj.dreamly.socket.chatroom.library.core.Packet;
import com.github.zj.dreamly.socket.chatroom.library.core.ReceivePacket;
import com.github.zj.dreamly.socket.chatroom.library.utils.CloseUtils;

import java.io.File;
import java.io.IOException;
import java.nio.channels.SocketChannel;

/**
 * @author 苍海之南
 */
public class ClientHandler extends Connector {
	private final File cachePath;
	private final ClientHandlerCallback clientHandlerCallback;
	private final String clientInfo;

	public ClientHandler(SocketChannel socketChannel, ClientHandlerCallback clientHandlerCallback, File cachePath) throws IOException {
		this.clientHandlerCallback = clientHandlerCallback;
		this.clientInfo = socketChannel.getRemoteAddress().toString();
		this.cachePath = cachePath;

		System.out.println("新客户端连接：" + clientInfo);

		setup(socketChannel);
	}

	public void exit() {
		CloseUtils.close(this);
		System.out.println("客户端已退出：" + clientInfo);
	}

	@Override
	public void onChannelClosed(SocketChannel channel) {
		super.onChannelClosed(channel);
		exitBySelf();
	}

	@Override
	protected File createNewReceiveFile() {
		return Foo.createRandomTemp(cachePath);
	}

	@Override
	protected void onReceivedPacket(ReceivePacket packet) {
		super.onReceivedPacket(packet);
		if (packet.type() == Packet.TYPE_MEMORY_STRING) {
			String string = (String) packet.entity();
			System.out.println(key.toString() + ":" + string);
			clientHandlerCallback.onNewMessageArrived(this, string);
		}
	}

	private void exitBySelf() {
		exit();
		clientHandlerCallback.onSelfClosed(this);
	}

	public interface ClientHandlerCallback {
		// 自身关闭通知
		void onSelfClosed(ClientHandler handler);

		// 收到消息时通知
		void onNewMessageArrived(ClientHandler handler, String msg);
	}
}
