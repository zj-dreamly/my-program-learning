package com.github.zj.dreamly.netty.netty.server.handler;

import com.github.zj.dreamly.netty.netty.common.Operation;
import com.github.zj.dreamly.netty.netty.common.RequestMessage;
import com.github.zj.dreamly.netty.netty.common.auth.AuthOperation;
import com.github.zj.dreamly.netty.netty.common.auth.AuthOperationResult;
import io.netty.channel.ChannelHandler;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@ChannelHandler.Sharable
public class AuthHandler extends SimpleChannelInboundHandler<RequestMessage> {

	@Override
	protected void channelRead0(ChannelHandlerContext ctx, RequestMessage msg) throws Exception {
		try {
			Operation operation = msg.getMessageBody();
			if (operation instanceof AuthOperation) {
				AuthOperation authOperation = (AuthOperation) operation;
				AuthOperationResult authOperationResult = authOperation.execute();
				if (authOperationResult.isPassAuth()) {
					log.info("pass auth");
				} else {
					log.error("fail to auth");
					ctx.close();
				}
			} else {
				log.error("expect first msg is auth");
				ctx.close();
			}
		} catch (Exception e) {
			log.error("exception happen for: " + e.getMessage(), e);
			ctx.close();
		} finally {
			ctx.pipeline().remove(this);
		}

	}
}
