package com.github.zj.dreamly.socket.chatroom.library.utils;

import java.io.Closeable;
import java.io.IOException;

/**
 * @author 苍海之南
 */
public class CloseUtils {
	public static void close(Closeable... closeables) {
		if (closeables == null) {
			return;
		}
		for (Closeable closeable : closeables) {
			if (closeable == null) {
				continue;
			}
			try {
				closeable.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}
}
