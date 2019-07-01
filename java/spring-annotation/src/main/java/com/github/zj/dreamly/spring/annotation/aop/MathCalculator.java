package com.github.zj.dreamly.spring.annotation.aop;

/**
 * <h2>MathCalculator</h2>
 *
 * @author: 苍海之南
 * @since: 2019-06-30 09:52
 **/
public class MathCalculator {
	public int div(int i, int j) {
		System.out.println("MathCalculator...div...");
		return i / j;
	}
}
