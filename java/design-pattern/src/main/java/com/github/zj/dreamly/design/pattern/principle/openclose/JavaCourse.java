package com.github.zj.dreamly.design.pattern.principle.openclose;

import lombok.Data;

/**
 * @author 苍海之南
 */
@Data
public class JavaCourse implements ICourse {
	private Integer id;
	private String name;
	private Double price;

	public JavaCourse(Integer id, String name, Double price) {
		this.id = id;
		this.name = name;
		this.price = price;
	}

}
