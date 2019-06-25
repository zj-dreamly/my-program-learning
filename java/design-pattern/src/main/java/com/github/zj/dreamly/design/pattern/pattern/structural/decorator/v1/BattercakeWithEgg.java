package com.github.zj.dreamly.design.pattern.pattern.structural.decorator.v1;

/**
 * @author 苍海之南
 */
public class BattercakeWithEgg extends Battercake {
	@Override
	public String getDesc() {
		return super.getDesc() + " 加一个鸡蛋";
	}

	@Override
	public int cost() {
		return super.cost() + 1;
	}
}
