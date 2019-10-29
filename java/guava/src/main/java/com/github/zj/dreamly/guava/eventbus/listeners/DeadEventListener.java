package com.github.zj.dreamly.guava.eventbus.listeners;

import com.google.common.eventbus.DeadEvent;
import com.google.common.eventbus.Subscribe;

/**
 * @author 苍海之南
 */
public class DeadEventListener {
	@Subscribe
	public void handle(DeadEvent event) {
		System.out.println(event.getSource());
		System.out.println(event.getEvent());
	}
}
