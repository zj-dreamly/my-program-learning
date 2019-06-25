package com.github.zj.dreamly.design.pattern.pattern.structural.decorator.v2;

/**
 * @author 苍海之南
 */
public class Battercake extends ABattercake {
	@Override
	protected String getDesc() {
		return "煎饼";
	}

	@Override
	protected int cost() {
		return 8;
	}
}
