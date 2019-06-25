package com.github.zj.dreamly.design.pattern.pattern.structural.decorator.v2;

/**
 * @author 苍海之南
 */
public class EggDecorator extends AbstractDecorator {
	public EggDecorator(ABattercake aBattercake) {
		super(aBattercake);
	}

	@Override
	protected void doSomething() {

	}

	@Override
	protected String getDesc() {
		return super.getDesc() + " 加一个鸡蛋";
	}

	@Override
	protected int cost() {
		return super.cost() + 1;
	}
}
