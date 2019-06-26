package com.github.zj.dreamly.design.pattern.pattern.behavioral.interpreter;

/**
 * @author 苍海之南
 */
public class OperatorUtil {
	public static boolean isOperator(String symbol) {
		return (symbol.equals("+") || symbol.equals("*"));

	}

	public static Interpreter getExpressionObject(Interpreter firstExpression, Interpreter secondExpression, String symbol) {
		if (symbol.equals("+")) {
			return new AddInterpreter(firstExpression, secondExpression);
		} else if (symbol.equals("*")) {
			return new MultiInterpreter(firstExpression, secondExpression);
		}
		return null;
	}
}
