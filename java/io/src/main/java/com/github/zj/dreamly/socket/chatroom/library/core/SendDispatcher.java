package com.github.zj.dreamly.socket.chatroom.library.core;

import java.io.Closeable;

/**
 * 发送数据的调度者
 * 缓存所有需要发送的数据，通过队列对数据进行发送
 * 并且在发送数据时，实现对数据的基本包装
 */
public interface SendDispatcher extends Closeable {
    /**
     * 发送一份数据
     *
     * @param packet 数据
     */
    void send(SendPacket packet);

    /**
     * 取消发送数据
     *
     * @param packet 数据
     */
    void cancel(SendPacket packet);
}
