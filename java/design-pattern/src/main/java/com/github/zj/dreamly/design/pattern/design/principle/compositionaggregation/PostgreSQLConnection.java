package com.github.zj.dreamly.design.pattern.design.principle.compositionaggregation;

/**
 * @author 苍海之南
 */
public class PostgreSQLConnection extends DBConnection {
	@Override
	public String getConnection() {
		return "PostgreSQL数据库连接";
	}
}
