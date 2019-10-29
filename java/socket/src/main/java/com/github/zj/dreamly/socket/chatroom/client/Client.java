package com.github.zj.dreamly.socket.chatroom.client;

import com.github.zj.dreamly.socket.chatroom.client.bean.ServerInfo;
import com.github.zj.dreamly.socket.chatroom.foo.Foo;
import com.github.zj.dreamly.socket.chatroom.library.box.FileSendPacket;
import com.github.zj.dreamly.socket.chatroom.library.core.IoContext;
import com.github.zj.dreamly.socket.chatroom.library.impl.IoSelectorProvider;

import java.io.*;

/**
 * <h2>Client</h2>
 *
 * @author: 苍海之南
 * @since: 2019-10-29 17:13
 **/
public class Client {
	public static void main(String[] args) throws IOException {
		File cachePath = Foo.getCacheDir("client");
		IoContext.setup()
			.ioProvider(new IoSelectorProvider())
			.start();

		ServerInfo info = UDPSearcher.searchServer(10000);
		System.out.println("Server:" + info);

		if (info != null) {
			TCPClient tcpClient = null;

			try {
				tcpClient = TCPClient.startWith(info, cachePath);
				if (tcpClient == null) {
					return;
				}

				write(tcpClient);
			} catch (IOException e) {
				e.printStackTrace();
			} finally {
				if (tcpClient != null) {
					tcpClient.exit();
				}
			}
		}

		IoContext.close();
	}

	private static void write(TCPClient tcpClient) throws IOException {
		// 构建键盘输入流
		InputStream in = System.in;
		BufferedReader input = new BufferedReader(new InputStreamReader(in));

		do {
			// 键盘读取一行
			String str = input.readLine();
			if ("00bye00".equalsIgnoreCase(str)) {
				break;
			}

			// --f url
			if (str.startsWith("--f")) {
				String[] array = str.split(" ");
				if (array.length >= 2) {
					String filePath = array[1];
					File file = new File(filePath);
					if (file.exists() && file.isFile()) {
						FileSendPacket packet = new FileSendPacket(file);
						tcpClient.send(packet);
						continue;
					}
				}
			}

			// 发送字符串
			tcpClient.send(str);
		} while (true);
	}
}
