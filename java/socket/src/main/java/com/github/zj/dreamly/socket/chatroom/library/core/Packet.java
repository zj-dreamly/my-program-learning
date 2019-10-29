package com.github.zj.dreamly.socket.chatroom.library.core;

import java.io.Closeable;
import java.io.IOException;

/**
 * 公共的数据封装
 * 提供了类型以及基本的长度的定义
 * @author 苍海之南
 */
public abstract class Packet<Stream extends Closeable> implements Closeable {
	// BYTES 类型
	public static final byte TYPE_MEMORY_BYTES = 1;
	// String 类型
	public static final byte TYPE_MEMORY_STRING = 2;
	// 文件 类型
	public static final byte TYPE_STREAM_FILE = 3;
	// 长链接流 类型
	public static final byte TYPE_STREAM_DIRECT = 4;

	protected long length;
	private Stream stream;

	public long length() {
		return length;
	}

	/**
	 * 对外的获取当前实例的流操作
	 *
	 * @return {@link java.io.InputStream} or {@link java.io.OutputStream}
	 */
	public final Stream open() {
		if (stream == null) {
			stream = createStream();
		}
		return stream;
	}

	/**
	 * 对外的关闭资源操作，如果流处于打开状态应当进行关闭
	 *
	 * @throws IOException IO异常
	 */
	@Override
	public final void close() throws IOException {
		if (stream != null) {
			closeStream(stream);
			stream = null;
		}
	}

	/**
	 * 类型，直接通过方法得到:
	 * <p>
	 * {@link #TYPE_MEMORY_BYTES}
	 * {@link #TYPE_MEMORY_STRING}
	 * {@link #TYPE_STREAM_FILE}
	 * {@link #TYPE_STREAM_DIRECT}
	 *
	 * @return 类型
	 */
	public abstract byte type();

	/**
	 * 创建流操作，应当将当前需要传输的数据转化为流
	 *
	 * @return {@link java.io.InputStream} or {@link java.io.OutputStream}
	 */
	protected abstract Stream createStream();

	/**
	 * 关闭流，当前方法会调用流的关闭操作
	 *
	 * @param stream 待关闭的流
	 * @throws IOException IO异常
	 */
	protected void closeStream(Stream stream) throws IOException {
		stream.close();
	}

	/**
	 * 头部额外信息，用于携带额外的校验信息等
	 *
	 * @return byte 数组，最大255长度
	 */
	public byte[] headerInfo() {
		return null;
	}
}
