package com.github.zj.dreamly.design.pattern.pattern.structural.decorator.v2;

/**
 * @author 苍海之南
 */
public class SausageDecorator extends AbstractDecorator {
	public SausageDecorator(ABattercake aBattercake) {
		super(aBattercake);
	}

	@Override
	protected void doSomething() {

	}

	@Override
	protected String getDesc() {
		return super.getDesc() + " 加一根香肠";
	}

	@Override
	protected int cost() {
		return super.cost() + 2;
	}
}
