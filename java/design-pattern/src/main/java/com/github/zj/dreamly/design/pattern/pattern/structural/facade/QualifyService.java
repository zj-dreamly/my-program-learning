package com.github.zj.dreamly.design.pattern.pattern.structural.facade;

/**
 * @author 苍海之南
 */
public class QualifyService {
	public boolean isAvailable(PointsGift pointsGift) {
		System.out.println("校验" + pointsGift.getName() + " 积分资格通过,库存通过");
		return true;
	}
}
