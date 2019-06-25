package com.github.zj.dreamly.design.pattern.pattern.structural.decorator.v1;

/**
 * @author 苍海之南
 */
public class BattercakeWithEggSausage extends BattercakeWithEgg {
	@Override
	public String getDesc() {
		return super.getDesc() + " 加一根香肠";
	}

	@Override
	public int cost() {
		return super.cost() + 2;
	}
}
