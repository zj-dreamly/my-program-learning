package com.github.zj.dreamly.design.pattern.pattern.creational.singleton;

/**
 * @author 苍海之南
 */
public class StaticInnerClassSingleton {
	private static class InnerClass {
		private static StaticInnerClassSingleton staticInnerClassSingleton = new StaticInnerClassSingleton();
	}

	public static StaticInnerClassSingleton getInstance() {
		return InnerClass.staticInnerClassSingleton;
	}

	private StaticInnerClassSingleton() {
		if (InnerClass.staticInnerClassSingleton != null) {
			throw new RuntimeException("单例构造器禁止反射调用");
		}
	}

}
