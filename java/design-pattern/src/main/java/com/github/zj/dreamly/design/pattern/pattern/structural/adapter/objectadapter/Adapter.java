package com.github.zj.dreamly.design.pattern.pattern.structural.adapter.objectadapter;

/**
 * @author 苍海之南
 */
public class Adapter implements Target {
	private Adaptee adaptee = new Adaptee();

	@Override
	public void request() {
		//...
		adaptee.adapteeRequest();
		//...
	}
}
