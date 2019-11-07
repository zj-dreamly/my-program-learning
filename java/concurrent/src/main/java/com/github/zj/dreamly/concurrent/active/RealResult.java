package com.github.zj.dreamly.concurrent.active;

/**
 * @author 苍海之南
 */
public class RealResult implements Result {

	private final Object resultValue;

	public RealResult(Object resultValue) {
		this.resultValue = resultValue;
	}

	@Override
	public Object getResultValue() {
		return this.resultValue;
	}
}
