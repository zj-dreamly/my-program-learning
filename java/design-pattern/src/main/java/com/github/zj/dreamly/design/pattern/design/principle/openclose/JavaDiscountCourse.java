package com.github.zj.dreamly.design.pattern.design.principle.openclose;

/**
 * @author 苍海之南
 */
public class JavaDiscountCourse extends JavaCourse {

	public JavaDiscountCourse(Integer id, String name, Double price) {
		super(id, name, price);
	}

	public Double getDiscountPrice() {
		return super.getPrice() * 0.8;
	}

}
