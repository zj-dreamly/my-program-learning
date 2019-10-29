package com.github.zj.dreamly.guava.eventbus;

import com.github.zj.dreamly.guava.eventbus.service.QueryService;
import com.github.zj.dreamly.guava.eventbus.service.RequestQueryHandler;
import com.google.common.eventbus.EventBus;

/**
 * @author 苍海之南
 */
public class ComEachOtherEventBusExample {

	public static void main(String[] args) {
		final EventBus eventBus = new EventBus();
		QueryService queryService = new QueryService(eventBus);
		eventBus.register(new RequestQueryHandler(eventBus));
		queryService.query("werwersdf");
	}
}
