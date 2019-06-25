package com.github.zj.dreamly.design.pattern.design.principle.liskovsubstitution.methodinput;

import java.util.HashMap;

/**
 * @author 苍海之南
 */
public class Base {
	public void method(HashMap map) {
		System.out.println("父类被执行");
	}
}
