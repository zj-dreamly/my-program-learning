package com.github.zj.dreamly.design.pattern.pattern.structural.adapter.objectadapter;

/**
 * @author 苍海之南
 */
public class Test {
	public static void main(String[] args) {
		Target target = new ConcreteTarget();
		target.request();

		Target adapterTarget = new Adapter();
		adapterTarget.request();

	}
}
