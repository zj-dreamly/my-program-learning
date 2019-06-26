package com.github.zj.dreamly.design.pattern.pattern.behavioral.strategy;

/**
 * @author 苍海之南
 */
public class FanXianPromotionStrategy implements PromotionStrategy {
	@Override
	public void doPromotion() {
		System.out.println("返现促销,返回的金额存放到慕课网用户的余额中");
	}
}
