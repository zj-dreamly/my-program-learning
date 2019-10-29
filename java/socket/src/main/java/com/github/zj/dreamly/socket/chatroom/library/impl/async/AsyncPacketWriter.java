package com.github.zj.dreamly.socket.chatroom.library.impl.async;

import com.github.zj.dreamly.socket.chatroom.library.core.Frame;
import com.github.zj.dreamly.socket.chatroom.library.core.IoArgs;
import com.github.zj.dreamly.socket.chatroom.library.core.ReceivePacket;
import com.github.zj.dreamly.socket.chatroom.library.frames.*;

import java.io.Closeable;
import java.io.IOException;
import java.nio.channels.Channels;
import java.nio.channels.WritableByteChannel;
import java.util.Collection;
import java.util.HashMap;

/**
 * 写数据到Packet中
 */
class AsyncPacketWriter implements Closeable {
	private final PacketProvider provider;

	private final HashMap<Short, PacketModel> packetMap = new HashMap<>();
	private final IoArgs args = new IoArgs();
	private volatile Frame frameTemp;

	AsyncPacketWriter(PacketProvider provider) {
		this.provider = provider;
	}

	/**
	 * 构建一份数据容纳封装
	 * 当前帧如果没有则返回至少6字节长度的IoArgs，
	 * 如果当前帧有，则返回当前帧未消费完成的区间
	 *
	 * @return IoArgs
	 */
	synchronized IoArgs takeIoArgs() {
		args.limit(frameTemp == null
			? Frame.FRAME_HEADER_LENGTH
			: frameTemp.getConsumableLength());
		return args;
	}

	/**
	 * 消费IoArgs中的数据
	 *
	 * @param args IoArgs
	 */
	synchronized void consumeIoArgs(IoArgs args) {
		if (frameTemp == null) {
			Frame temp;
			do {
				// 还有未消费数据，则重复构建帧
				temp = buildNewFrame(args);
			} while (temp == null && args.remained());

			if (temp == null) {
				// 最终消费数据完成，但没有可消费区间，则直接返回
				return;
			}

			frameTemp = temp;
			if (!args.remained()) {
				// 没有数据，则直接返回
				return;
			}
		}

		// 确保此时currentFrame一定不为null
		Frame currentFrame = frameTemp;
		do {
			try {
				if (currentFrame.handle(args)) {
					// 某帧已接收完成
					if (currentFrame instanceof ReceiveHeaderFrame) {
						// Packet 头帧消费完成，则根据头帧信息构建接收的Packet
						ReceiveHeaderFrame headerFrame = (ReceiveHeaderFrame) currentFrame;
						ReceivePacket packet = provider.takePacket(headerFrame.getPacketType(),
							headerFrame.getPacketLength(),
							headerFrame.getPacketHeaderInfo());
						appendNewPacket(headerFrame.getBodyIdentifier(), packet);
					} else if (currentFrame instanceof ReceiveEntityFrame) {
						// Packet 实体帧消费完成，则将当前帧消费到Packet
						completeEntityFrame((ReceiveEntityFrame) currentFrame);
					}

					// 接收完成后，直接推出循环，如果还有未消费数据则交给外层调度
					frameTemp = null;
					break;
				}
			} catch (IOException e) {
				e.printStackTrace();
			}
		} while (args.remained());
	}

	/**
	 * 根据args创建新的帧
	 * 若当前解析的帧是取消帧，则直接进行取消操作，并返回null
	 *
	 * @param args IoArgs
	 * @return 返回新的帧
	 */
	private Frame buildNewFrame(IoArgs args) {
		AbsReceiveFrame frame = ReceiveFrameFactory.createInstance(args);
		if (frame instanceof CancelReceiveFrame) {
			cancelReceivePacket(frame.getBodyIdentifier());
			return null;
		} else if (frame instanceof ReceiveEntityFrame) {
			WritableByteChannel channel = getPacketChannel(frame.getBodyIdentifier());
			((ReceiveEntityFrame) frame).bindPacketChannel(channel);
		}
		return frame;
	}

	/**
	 * 当某Packet实体帧消费完成时调用
	 *
	 * @param frame 帧信息
	 */
	private void completeEntityFrame(ReceiveEntityFrame frame) {
		synchronized (packetMap) {
			short identifier = frame.getBodyIdentifier();
			int length = frame.getBodyLength();
			PacketModel model = packetMap.get(identifier);
			model.unreceivedLength -= length;
			if (model.unreceivedLength <= 0) {
				provider.completedPacket(model.packet, true);
				packetMap.remove(identifier);
			}
		}
	}

	/**
	 * 添加一个新的Packet到当前缓冲区
	 *
	 * @param identifier Packet标志
	 * @param packet     Packet
	 */
	private void appendNewPacket(short identifier, ReceivePacket packet) {
		synchronized (packetMap) {
			PacketModel model = new PacketModel(packet);
			packetMap.put(identifier, model);
		}
	}

	/**
	 * 获取Packet对应的输出通道，用以设置给帧进行数据传输
	 * 因为关闭当前map的原因，可能存在返回NULL
	 *
	 * @param identifier Packet对应的标志
	 * @return 通道
	 */
	private WritableByteChannel getPacketChannel(short identifier) {
		synchronized (packetMap) {
			PacketModel model = packetMap.get(identifier);
			return model == null ? null : model.channel;
		}
	}

	/**
	 * 取消某Packet继续接收数据
	 *
	 * @param identifier Packet标志
	 */
	private void cancelReceivePacket(short identifier) {
		synchronized (packetMap) {
			PacketModel model = packetMap.get(identifier);
			if (model != null) {
				ReceivePacket packet = model.packet;
				provider.completedPacket(packet, false);
			}
		}
	}

	/**
	 * 关闭操作，关闭时若当前还有正在接收的Packet，则尝试停止对应的Packet接收
	 */
	@Override
	public void close() {
		synchronized (packetMap) {
			Collection<PacketModel> values = packetMap.values();
			for (PacketModel value : values) {
				provider.completedPacket(value.packet, false);
			}
			packetMap.clear();
		}
	}

	/**
	 * Packet提供者
	 */
	interface PacketProvider {

		/**
		 * 拿Packet操作
		 *
		 * @param type       Packet类型
		 * @param length     Packet长度
		 * @param headerInfo Packet headerInfo
		 * @return 通过类型，长度，描述等信息得到一份接收Packet
		 */
		ReceivePacket takePacket(byte type, long length, byte[] headerInfo);

		/**
		 * 结束一份Packet
		 *
		 * @param packet    接收包
		 * @param isSucceed 是否成功接收完成
		 */
		void completedPacket(ReceivePacket packet, boolean isSucceed);
	}

	/**
	 * 对于接收包的简单封装
	 * 用以提供Packet、通道、未接收数据长度信息存储
	 */
	static class PacketModel {
		final ReceivePacket packet;
		final WritableByteChannel channel;
		volatile long unreceivedLength;

		PacketModel(ReceivePacket<?, ?> packet) {
			this.packet = packet;
			this.channel = Channels.newChannel(packet.open());
			this.unreceivedLength = packet.length();
		}
	}
}
