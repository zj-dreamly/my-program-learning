package com.github.zj.dreamly.design.pattern.pattern.behavioral.interpreter;

/**
 * @author 苍海之南
 */
public class Test {
	public static void main(String[] args) {
		String geelyInputStr = "6 100 11 + *";
		GeelyExpressionParser expressionParser = new GeelyExpressionParser();
		int result = expressionParser.parse(geelyInputStr);
		System.out.println("解释器计算结果: " + result);
	}
}
