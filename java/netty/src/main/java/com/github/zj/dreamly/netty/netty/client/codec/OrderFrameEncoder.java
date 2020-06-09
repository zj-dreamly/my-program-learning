package com.github.zj.dreamly.netty.netty.client.codec;


import io.netty.handler.codec.LengthFieldPrepender;

public class OrderFrameEncoder extends LengthFieldPrepender {
    public OrderFrameEncoder() {
        super(2);
    }
}
