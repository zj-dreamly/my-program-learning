package com.github.zj.dreamly.design.pattern.design.pattern.creational.abstractfactory;

/**
 * @author 苍海之南
 */
public class JavaCourseFactory implements CourseFactory {
	@Override
	public Video getVideo() {
		return new JavaVideo();
	}

	@Override
	public Article getArticle() {
		return new JavaArticle();
	}
}
