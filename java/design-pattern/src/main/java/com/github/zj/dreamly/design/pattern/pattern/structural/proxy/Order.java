package com.github.zj.dreamly.design.pattern.pattern.structural.proxy;

/**
 * @author 苍海之南
 */
public class Order {
	private Object orderInfo;
	private Integer userId;

	public Object getOrderInfo() {
		return orderInfo;
	}

	public void setOrderInfo(Object orderInfo) {
		this.orderInfo = orderInfo;
	}

	public Integer getUserId() {
		return userId;
	}

	public void setUserId(Integer userId) {
		this.userId = userId;
	}
}
