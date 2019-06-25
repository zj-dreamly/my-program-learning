package com.github.zj.dreamly.design.pattern.design.principle.liskovsubstitution;

/**
 * @author 苍海之南
 */
public class Square implements Quadrangle {
	private long sideLength;

	public long getSideLength() {
		return sideLength;
	}

	public void setSideLength(long sideLength) {
		this.sideLength = sideLength;
	}

	@Override
	public long getWidth() {
		return sideLength;
	}

	@Override
	public long getLength() {
		return sideLength;
	}
}
