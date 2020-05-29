package com.github.zj.dreamly.netty.nio.groupchat;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.SocketChannel;
import java.util.Iterator;
import java.util.Scanner;

/**
 * @author admin
 */
public class GroupChatClient {

	private final static String HOST = "127.0.0.1";
	private final static int PORT = 6667;
	private Selector selector;
	private SocketChannel socketChannel;
	private String username;

	/**
	 * 构造器, 完成初始化工作
	 */
	public GroupChatClient() throws IOException {

		selector = Selector.open();
		//连接服务器
		socketChannel = SocketChannel.open(new InetSocketAddress(HOST, PORT));
		//设置非阻塞
		socketChannel.configureBlocking(false);
		//将channel 注册到selector
		socketChannel.register(selector, SelectionKey.OP_READ);
		//得到username
		username = socketChannel.getLocalAddress().toString().substring(1);
		System.out.println(username + " is ok...");

	}

	/**
	 * 向服务器发送消息
	 */
	public void sendInfo(String info) {

		info = username + " 说：" + info;

		try {
			socketChannel.write(ByteBuffer.wrap(info.getBytes()));
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	/**
	 * 读取从服务器端回复的消息
	 */
	public void readInfo() {

		try {

			int readChannels = selector.select();
			//有可以用的通道
			if (readChannels > 0) {

				Iterator<SelectionKey> iterator = selector.selectedKeys().iterator();
				while (iterator.hasNext()) {

					SelectionKey key = iterator.next();
					if (key.isReadable()) {
						//得到相关的通道
						SocketChannel sc = (SocketChannel) key.channel();
						//得到一个Buffer
						ByteBuffer buffer = ByteBuffer.allocate(1024);
						//读取
						sc.read(buffer);
						//把读到的缓冲区的数据转成字符串
						String msg = new String(buffer.array());
						System.out.println(msg.trim());
					}
				}
				iterator.remove(); //删除当前的selectionKey, 防止重复操作
			} else {
				//System.out.println("没有可以用的通道...");
			}

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public static void main(String[] args) throws Exception {

		//启动我们客户端
		GroupChatClient chatClient = new GroupChatClient();

		//启动一个线程, 每个3秒，读取从服务器发送数据
		new Thread(() -> {

			while (true) {
				chatClient.readInfo();
			}
		}).start();

		//发送数据给服务器端
		Scanner scanner = new Scanner(System.in);

		while (scanner.hasNextLine()) {
			String s = scanner.nextLine();
			chatClient.sendInfo(s);
		}
	}

}
