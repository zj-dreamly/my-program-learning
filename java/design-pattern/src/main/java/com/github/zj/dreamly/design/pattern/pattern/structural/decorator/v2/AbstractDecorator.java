package com.github.zj.dreamly.design.pattern.pattern.structural.decorator.v2;

/**
 * @author 苍海之南
 */
public abstract class AbstractDecorator extends ABattercake {
	private ABattercake aBattercake;

	public AbstractDecorator(ABattercake aBattercake) {
		this.aBattercake = aBattercake;
	}

	protected abstract void doSomething();

	@Override
	protected String getDesc() {
		return this.aBattercake.getDesc();
	}

	@Override
	protected int cost() {
		return this.aBattercake.cost();
	}
}
