package com.github.zj.dreamly.design.pattern.pattern.structural.proxy.staticproxy;

import com.github.zj.dreamly.design.pattern.pattern.structural.proxy.Order;

/**
 * @author 苍海之南
 */
public class Test {
    public static void main(String[] args) {
        Order order = new Order();
        order.setUserId(2);

        OrderServiceStaticProxy orderServiceStaticProxy = new OrderServiceStaticProxy();
        orderServiceStaticProxy.saveOrder(order);
    }
}
