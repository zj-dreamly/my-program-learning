package com.github.zj.dreamly.design.pattern.pattern.structural.proxy;

/**
 * @author 苍海之南
 */
public class OrderDaoImpl implements IOrderDao {
    @Override
    public int insert(Order order) {
        System.out.println("Dao层添加Order成功");
        return 1;
    }
}
