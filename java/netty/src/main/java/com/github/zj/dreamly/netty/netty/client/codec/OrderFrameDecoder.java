package com.github.zj.dreamly.netty.netty.client.codec;


import io.netty.handler.codec.LengthFieldBasedFrameDecoder;

public class OrderFrameDecoder extends LengthFieldBasedFrameDecoder {
    public OrderFrameDecoder() {
        super(Integer.MAX_VALUE, 0, 2, 0, 2);
    }
}
