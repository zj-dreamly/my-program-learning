package com.github.zj.dreamly.design.pattern.pattern.structural.proxy.dynamicproxy;

import com.github.zj.dreamly.design.pattern.pattern.structural.proxy.IOrderService;
import com.github.zj.dreamly.design.pattern.pattern.structural.proxy.Order;
import com.github.zj.dreamly.design.pattern.pattern.structural.proxy.OrderServiceImpl;

/**
 * @author 苍海之南
 */
public class Test {
	public static void main(String[] args) {
		Order order = new Order();
//        order.setUserId(2);
		order.setUserId(1);
		IOrderService orderServiceDynamicProxy = (IOrderService) new OrderServiceDynamicProxy(new OrderServiceImpl()).bind();

		orderServiceDynamicProxy.saveOrder(order);
	}
}
