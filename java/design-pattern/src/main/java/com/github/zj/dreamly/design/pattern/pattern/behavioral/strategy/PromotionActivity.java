package com.github.zj.dreamly.design.pattern.pattern.behavioral.strategy;

/**
 * @author 苍海之南
 */
public class PromotionActivity {
	private PromotionStrategy promotionStrategy;

	public PromotionActivity(PromotionStrategy promotionStrategy) {
		this.promotionStrategy = promotionStrategy;
	}

	public void executePromotionStrategy() {
		promotionStrategy.doPromotion();
	}

}
