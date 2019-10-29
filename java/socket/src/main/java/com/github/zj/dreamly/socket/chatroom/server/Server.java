package com.github.zj.dreamly.socket.chatroom.server;

import com.github.zj.dreamly.socket.chatroom.foo.Foo;
import com.github.zj.dreamly.socket.chatroom.foo.constants.TCPConstants;
import com.github.zj.dreamly.socket.chatroom.library.core.IoContext;
import com.github.zj.dreamly.socket.chatroom.library.impl.IoSelectorProvider;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;

/**
 * <h2>Server</h2>
 *
 * @author: 苍海之南
 * @since: 2019-10-29 18:13
 **/
public class Server {
	public static void main(String[] args) throws IOException {
		File cachePath = Foo.getCacheDir("server");

		IoContext.setup()
			.ioProvider(new IoSelectorProvider())
			.start();

		TCPServer tcpServer = new TCPServer(TCPConstants.PORT_SERVER, cachePath);
		boolean isSucceed = tcpServer.start();
		if (!isSucceed) {
			System.out.println("Start TCP server failed!");
			return;
		}

		UDPProvider.start(TCPConstants.PORT_SERVER);

		BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(System.in));
		String str;
		do {
			str = bufferedReader.readLine();
			if ("00bye00".equalsIgnoreCase(str)) {
				break;
			}
			// 发送字符串
			tcpServer.broadcast(str);
		} while (true);

		UDPProvider.stop();
		tcpServer.stop();

		IoContext.close();
	}
}
