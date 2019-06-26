package com.github.zj.dreamly.design.pattern.pattern.behavioral.interpreter;

/**
 * @author 苍海之南
 */
public class NumberInterpreter implements Interpreter {
	private int number;

	public NumberInterpreter(int number) {
		this.number = number;
	}

	public NumberInterpreter(String number) {
		this.number = Integer.parseInt(number);
	}

	@Override
	public int interpret() {
		return this.number;
	}
}
