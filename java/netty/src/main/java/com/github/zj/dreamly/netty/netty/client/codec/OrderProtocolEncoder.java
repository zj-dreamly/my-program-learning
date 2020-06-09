package com.github.zj.dreamly.netty.netty.client.codec;

import com.github.zj.dreamly.netty.netty.common.RequestMessage;
import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.MessageToMessageEncoder;

import java.util.List;

public class OrderProtocolEncoder extends MessageToMessageEncoder<RequestMessage> {


    @Override
    protected void encode(ChannelHandlerContext ctx, RequestMessage requestMessage, List<Object> out) throws Exception {
        ByteBuf buffer = ctx.alloc().buffer();
        requestMessage.encode(buffer);

        out.add(buffer);
    }
}
