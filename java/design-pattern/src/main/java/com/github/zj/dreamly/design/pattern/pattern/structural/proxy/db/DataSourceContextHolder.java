package com.github.zj.dreamly.design.pattern.pattern.structural.proxy.db;

/**
 * @author 苍海之南
 */
public class DataSourceContextHolder {
	private static final ThreadLocal<String> CONTEXT_HOLDER = new ThreadLocal<String>();

	public static void setDBType(String dbType) {
		CONTEXT_HOLDER.set(dbType);
	}

	public static String getDBType() {
		return (String) CONTEXT_HOLDER.get();
	}

	public static void clearDBType() {
		CONTEXT_HOLDER.remove();
	}

}
