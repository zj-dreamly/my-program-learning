package com.github.zj.dreamly.design.pattern.design.principle.compositionaggregation;

/**
 * @author 苍海之南
 */
public class MySQLConnection extends DBConnection {
	@Override
	public String getConnection() {
		return "MySQL数据库连接";
	}
}
