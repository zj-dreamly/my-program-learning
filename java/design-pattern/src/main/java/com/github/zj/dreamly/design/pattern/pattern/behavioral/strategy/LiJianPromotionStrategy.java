package com.github.zj.dreamly.design.pattern.pattern.behavioral.strategy;

/**
 * @author 苍海之南
 */
public class LiJianPromotionStrategy implements PromotionStrategy {
    @Override
    public void doPromotion() {
        System.out.println("立减促销,课程的价格直接减去配置的价格");
    }
}
