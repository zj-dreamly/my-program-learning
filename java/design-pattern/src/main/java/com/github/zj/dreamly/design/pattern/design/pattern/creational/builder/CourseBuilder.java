package com.github.zj.dreamly.design.pattern.design.pattern.creational.builder;

/**
 * @author 苍海之南
 */
public abstract class CourseBuilder {

	public abstract void buildCourseName(String courseName);

	public abstract void buildCoursePPT(String coursePPT);

	public abstract void buildCourseVideo(String courseVideo);

	public abstract void buildCourseArticle(String courseArticle);

	public abstract void buildCourseQA(String courseQA);

	public abstract Course makeCourse();

}
