package com.github.zj.dreamly.design.pattern.pattern.structural.adapter.objectadapter;

/**
 * @author 苍海之南
 */
public class ConcreteTarget implements Target {
	@Override
	public void request() {
		System.out.println("concreteTarget目标方法");
	}

}
