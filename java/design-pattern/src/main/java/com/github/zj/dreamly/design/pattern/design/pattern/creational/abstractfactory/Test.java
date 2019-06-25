package com.github.zj.dreamly.design.pattern.design.pattern.creational.abstractfactory;

/**
 * @author 苍海之南
 */
public class Test {
	public static void main(String[] args) {
		CourseFactory courseFactory = new JavaCourseFactory();
		Video video = courseFactory.getVideo();
		Article article = courseFactory.getArticle();
		video.produce();
		article.produce();
	}
}
