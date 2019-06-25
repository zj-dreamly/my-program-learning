package com.github.zj.dreamly.design.pattern.pattern.structural.facade;

/**
 * @author 苍海之南
 */
public class Test {
	public static void main(String[] args) {
		PointsGift pointsGift = new PointsGift("T恤");
		GiftExchangeService giftExchangeService = new GiftExchangeService();
		giftExchangeService.giftExchange(pointsGift);
	}
}
