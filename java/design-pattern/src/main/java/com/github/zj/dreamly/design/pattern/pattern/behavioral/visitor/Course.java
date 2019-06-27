package com.github.zj.dreamly.design.pattern.pattern.behavioral.visitor;

/**
 * @author 苍海之南1
 */
public abstract class Course {
	private String name;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public abstract void accept(IVisitor visitor);

}
