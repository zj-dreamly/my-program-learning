package com.github.zj.dreamly.design.pattern.pattern.behavioral.strategy;

/**
 * @author 苍海之南
 */
public class EmptyPromotionStrategy implements PromotionStrategy {
	@Override
	public void doPromotion() {
		System.out.println("无促销活动");
	}
}
