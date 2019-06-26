package com.github.zj.dreamly.design.pattern.pattern.structural.adapter.classadapter;

/**
 * @author 苍海之南
 */
public class Adapter extends Adaptee implements Target {
	@Override
	public void request() {
		//...
		super.adapteeRequest();
		//...
	}
}
