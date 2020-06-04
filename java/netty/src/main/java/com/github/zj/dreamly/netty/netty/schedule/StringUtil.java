package com.github.zj.dreamly.netty.netty.schedule;

import static com.github.zj.dreamly.netty.netty.schedule.ObjectUtil.checkNotNull;

public class StringUtil{

	private static final char PACKAGE_SEPARATOR_CHAR = '.';

	public static String simpleClassName(Class<?> clazz) {
		String className = checkNotNull(clazz, "clazz").getName();
		final int lastDotIdx = className.lastIndexOf(PACKAGE_SEPARATOR_CHAR);
		if (lastDotIdx > -1) {
			return className.substring(lastDotIdx + 1);
		}
		return className;
	}
}
