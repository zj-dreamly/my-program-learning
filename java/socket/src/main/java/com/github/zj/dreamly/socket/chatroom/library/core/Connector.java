package com.github.zj.dreamly.socket.chatroom.library.core;

import com.github.zj.dreamly.socket.chatroom.library.box.BytesReceivePacket;
import com.github.zj.dreamly.socket.chatroom.library.box.FileReceivePacket;
import com.github.zj.dreamly.socket.chatroom.library.box.StringReceivePacket;
import com.github.zj.dreamly.socket.chatroom.library.box.StringSendPacket;
import com.github.zj.dreamly.socket.chatroom.library.impl.SocketChannelAdapter;
import com.github.zj.dreamly.socket.chatroom.library.impl.async.AsyncReceiveDispatcher;
import com.github.zj.dreamly.socket.chatroom.library.impl.async.AsyncSendDispatcher;

import java.io.Closeable;
import java.io.File;
import java.io.IOException;
import java.nio.channels.SocketChannel;
import java.util.UUID;

/**
 * @author 苍海之南
 */
public abstract class Connector implements Closeable, SocketChannelAdapter.OnChannelStatusChangedListener {
	protected UUID key = UUID.randomUUID();
	private SocketChannel channel;
	private Sender sender;
	private Receiver receiver;
	private SendDispatcher sendDispatcher;
	private ReceiveDispatcher receiveDispatcher;

	public void setup(SocketChannel socketChannel) throws IOException {
		this.channel = socketChannel;

		IoContext context = IoContext.get();
		SocketChannelAdapter adapter = new SocketChannelAdapter(channel, context.getIoProvider(), this);

		this.sender = adapter;
		this.receiver = adapter;

		sendDispatcher = new AsyncSendDispatcher(sender);
		receiveDispatcher = new AsyncReceiveDispatcher(receiver, receivePacketCallback);

		// 启动接收
		receiveDispatcher.start();
	}

	public void send(String msg) {
		SendPacket packet = new StringSendPacket(msg);
		sendDispatcher.send(packet);
	}

	public void send(SendPacket packet) {
		sendDispatcher.send(packet);
	}

	@Override
	public void close() throws IOException {
		receiveDispatcher.close();
		sendDispatcher.close();
		sender.close();
		receiver.close();
		channel.close();
	}

	@Override
	public void onChannelClosed(SocketChannel channel) {

	}

	protected void onReceivedPacket(ReceivePacket packet) {
		System.out.println(key.toString() + ":[New Packet]-Type:" + packet.type() + ", Length:" + packet.length);
	}

	protected abstract File createNewReceiveFile();

	private ReceiveDispatcher.ReceivePacketCallback receivePacketCallback = new ReceiveDispatcher.ReceivePacketCallback() {
		@Override
		public ReceivePacket<?, ?> onArrivedNewPacket(byte type, long length) {
			switch (type) {
				case Packet.TYPE_MEMORY_BYTES:
					return new BytesReceivePacket(length);
				case Packet.TYPE_MEMORY_STRING:
					return new StringReceivePacket(length);
				case Packet.TYPE_STREAM_FILE:
					return new FileReceivePacket(length, createNewReceiveFile());
				case Packet.TYPE_STREAM_DIRECT:
					return new BytesReceivePacket(length);
				default:
					throw new UnsupportedOperationException("Unsupported packet type:" + type);
			}
		}

		@Override
		public void onReceivePacketCompleted(ReceivePacket packet) {
			onReceivedPacket(packet);
		}
	};

}
