package com.github.zj.dreamly.design.pattern.pattern.structural.facade;

/**
 * @author 苍海之南
 */
public class PointsPaymentService {
	public boolean pay(PointsGift pointsGift) {
		//扣减积分
		System.out.println("支付" + pointsGift.getName() + " 积分成功");
		return true;
	}

}
