package com.github.zj.dreamly.design.pattern.pattern.behavioral.strategy;

/**
 * @author 苍海之南
 */
public class ManJianPromotionStrategy implements PromotionStrategy {
	@Override
	public void doPromotion() {
		System.out.println("满减促销,满200-20元");
	}
}
