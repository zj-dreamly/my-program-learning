package com.github.zj.dreamly.guava.eventbus.service;

import com.github.zj.dreamly.guava.eventbus.events.Request;
import com.github.zj.dreamly.guava.eventbus.events.Response;
import com.google.common.eventbus.EventBus;
import com.google.common.eventbus.Subscribe;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author 苍海之南
 */
public class RequestQueryHandler {

	private final static Logger LOGGER = LoggerFactory.getLogger(RequestQueryHandler.class);

	private final EventBus eventBus;

	public RequestQueryHandler(EventBus eventBus) {
		this.eventBus = eventBus;
	}

	@Subscribe
	public void doQuery(Request request) {
		LOGGER.info("start query the orderNo [{}]", request.toString());
		Response response = new Response();
		this.eventBus.post(response);
	}
}
