package com.github.zj.dreamly.design.pattern.pattern.behavioral.interpreter;

/**
 * @author 苍海之南
 */
public class OperatorUtil {
	public static boolean isOperator(String symbol) {
		return ("+".equals(symbol) || "*".equals(symbol));

	}

	public static Interpreter getExpressionObject(Interpreter firstExpression, Interpreter secondExpression, String symbol) {
		if ("+".equals(symbol)) {
			return new AddInterpreter(firstExpression, secondExpression);
		} else if ("*".equals(symbol)) {
			return new MultiInterpreter(firstExpression, secondExpression);
		}
		return null;
	}
}
