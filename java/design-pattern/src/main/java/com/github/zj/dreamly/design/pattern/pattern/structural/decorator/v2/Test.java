package com.github.zj.dreamly.design.pattern.pattern.structural.decorator.v2;

/**
 * @author 苍海之南
 */
public class Test {
	public static void main(String[] args) {
		ABattercake aBattercake;
		aBattercake = new Battercake();
		aBattercake = new EggDecorator(aBattercake);
		aBattercake = new EggDecorator(aBattercake);
		aBattercake = new SausageDecorator(aBattercake);

		System.out.println(aBattercake.getDesc() + " 销售价格:" + aBattercake.cost());

	}
}
