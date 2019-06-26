package com.github.zj.dreamly.design.pattern.pattern.behavioral.iterator;

/**
 * @author 苍海之南
 */
public interface CourseAggregate {

	void addCourse(Course course);

	void removeCourse(Course course);

	CourseIterator getCourseIterator();

}
