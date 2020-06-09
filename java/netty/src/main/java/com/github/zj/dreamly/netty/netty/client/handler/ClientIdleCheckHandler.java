package com.github.zj.dreamly.netty.netty.client.handler;

import io.netty.handler.timeout.IdleStateHandler;

public class ClientIdleCheckHandler extends IdleStateHandler {

	public ClientIdleCheckHandler() {
		super(0, 5, 0);
	}

}
