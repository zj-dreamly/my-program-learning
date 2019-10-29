package com.github.zj.dreamly.guava.eventbus.events;

import com.google.common.base.MoreObjects;

/**
 * @author 苍海之南
 */
public class Fruit {

	private final String name;

	public Fruit(String name) {
		this.name = name;
	}

	public String getName() {
		return name;
	}

	@Override
	public String toString() {
		return MoreObjects.toStringHelper(this).add("Name", name).toString();
	}
}
