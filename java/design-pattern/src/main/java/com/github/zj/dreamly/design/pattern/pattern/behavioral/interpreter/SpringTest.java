package com.github.zj.dreamly.design.pattern.pattern.behavioral.interpreter;

import org.springframework.expression.Expression;
import org.springframework.expression.spel.standard.SpelExpressionParser;

/**
 * @author 苍海之南
 */
public class SpringTest {
	public static void main(String[] args) {
		org.springframework.expression.ExpressionParser parser = new SpelExpressionParser();
		Expression expression = parser.parseExpression("100 * 2 + 400 * 1 + 66");
		int result = (Integer) expression.getValue();
		System.out.println(result);

	}
}
