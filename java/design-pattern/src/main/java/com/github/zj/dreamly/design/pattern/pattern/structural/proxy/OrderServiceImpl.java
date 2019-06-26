package com.github.zj.dreamly.design.pattern.pattern.structural.proxy;

/**
 * @author 苍海之南
 */
public class OrderServiceImpl implements IOrderService {
	private IOrderDao iOrderDao;

	@Override
	public int saveOrder(Order order) {
		//Spring会自己注入，我们课程中就直接new了
		iOrderDao = new OrderDaoImpl();
		System.out.println("Service层调用Dao层添加Order");
		return iOrderDao.insert(order);
	}

}
