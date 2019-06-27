package com.github.zj.dreamly.design.pattern.pattern.behavioral.visitor;

/**
 * @author 苍海之南
 */
public class FreeCourse extends Course {

	@Override
	public void accept(IVisitor visitor) {
		visitor.visit(this);
	}
}
