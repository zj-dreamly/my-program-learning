package com.github.zj.dreamly.design.pattern.pattern.behavioral.visitor;

/**
 * @author 苍海之南
 */
public class CodingCourse extends Course {
	private int price;

	public int getPrice() {
		return price;
	}

	public void setPrice(int price) {
		this.price = price;
	}

	@Override
	public void accept(IVisitor visitor) {
		visitor.visit(this);
	}
}
