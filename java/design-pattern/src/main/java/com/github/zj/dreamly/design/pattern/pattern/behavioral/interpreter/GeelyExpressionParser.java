package com.github.zj.dreamly.design.pattern.pattern.behavioral.interpreter;

import java.util.Stack;

/**
 * @author 苍海之南
 */
public class GeelyExpressionParser {
	private Stack<Interpreter> stack = new Stack<Interpreter>();

	public int parse(String str) {
		String[] strItemArray = str.split(" ");
		for (String symbol : strItemArray) {
			if (!OperatorUtil.isOperator(symbol)) {
				Interpreter numberExpression = new NumberInterpreter(symbol);
				stack.push(numberExpression);
				System.out.println(String.format("入栈: %d", numberExpression.interpret()));
			} else {
				//是运算符号，可以计算
				Interpreter firstExpression = stack.pop();
				Interpreter secondExpression = stack.pop();
				System.out.println(String.format("出栈: %d 和 %d",
					firstExpression.interpret(), secondExpression.interpret()));
				Interpreter operator = OperatorUtil.getExpressionObject(firstExpression, secondExpression, symbol);
				System.out.println(String.format("应用运算符: %s", operator));
				int result = operator.interpret();
				NumberInterpreter resultExpression = new NumberInterpreter(result);
				stack.push(resultExpression);
				System.out.println(String.format("阶段结果入栈: %d", resultExpression.interpret()));
			}
		}
		int result = stack.pop().interpret();
		return result;

	}
}
