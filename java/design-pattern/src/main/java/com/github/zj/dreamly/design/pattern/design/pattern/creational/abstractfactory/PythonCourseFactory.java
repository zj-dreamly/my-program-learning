package com.github.zj.dreamly.design.pattern.design.pattern.creational.abstractfactory;

/**
 * @author 苍海之南
 */
public class PythonCourseFactory implements CourseFactory {
	@Override
	public Video getVideo() {
		return new PythonVideo();
	}

	@Override
	public Article getArticle() {
		return new PythonArticle();
	}
}
