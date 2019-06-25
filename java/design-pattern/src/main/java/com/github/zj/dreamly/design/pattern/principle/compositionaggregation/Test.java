package com.github.zj.dreamly.design.pattern.principle.compositionaggregation;

/**
 * @author 苍海之南
 */
public class Test {
	public static void main(String[] args) {
		ProductDao productDao = new ProductDao();
		productDao.setDbConnection(new PostgreSQLConnection());
		productDao.addProduct();
	}
}
