package com.github.zj.dreamly.netty.netty.server.codec;


import io.netty.handler.codec.LengthFieldBasedFrameDecoder;

public class OrderFrameDecoder extends LengthFieldBasedFrameDecoder {
    public OrderFrameDecoder() {
        super(10240, 0, 2, 0, 2);
    }
}
