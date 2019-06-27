package com.github.zj.dreamly.design.pattern.pattern.behavioral.visitor;

import java.util.ArrayList;
import java.util.List;

/**
 * @author 苍海之南
 */
public class Test {
	public static void main(String[] args) {
		List<Course> courseList = new ArrayList<Course>();

		FreeCourse freeCourse = new FreeCourse();
		freeCourse.setName("SpringMVC数据绑定");

		CodingCourse codingCourse = new CodingCourse();
		codingCourse.setName("Java设计模式精讲 -- By Geely");
		codingCourse.setPrice(299);

		courseList.add(freeCourse);
		courseList.add(codingCourse);

		for (Course course : courseList) {
			course.accept(new Visitor());
		}

	}
}
