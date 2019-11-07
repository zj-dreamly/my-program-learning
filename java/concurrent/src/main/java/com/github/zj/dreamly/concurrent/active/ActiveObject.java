package com.github.zj.dreamly.concurrent.active;

/**
 * 接受异步消息的主动对象
 *
 * @author 苍海之南
 */
public interface ActiveObject {

	Result makeString(int count, char fillChar);

	void displayString(String text);
}
