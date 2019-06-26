package com.github.zj.dreamly.design.pattern.pattern.structural.adapter;

/**
 * @author 苍海之南
 */
public class Test {
	public static void main(String[] args) {
		DC5 dc5 = new PowerAdapter();
		dc5.outputDC5V();

	}
}
