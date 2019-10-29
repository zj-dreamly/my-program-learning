package com.github.zj.dreamly.socket.chatroom.foo.constants;

/**
 * @author 苍海之南
 */
public class UDPConstants {
	/**
	 * 公用头部
	 */
	public static byte[] HEADER = new byte[]{7, 7, 7, 7, 7, 7, 7, 7};
	/**
	 * 服务器固化UDP接收端口
	 */
	public static int PORT_SERVER = 30201;
	/**
	 * 客户端回送端口
	 */
	public static int PORT_CLIENT_RESPONSE = 30202;
}
