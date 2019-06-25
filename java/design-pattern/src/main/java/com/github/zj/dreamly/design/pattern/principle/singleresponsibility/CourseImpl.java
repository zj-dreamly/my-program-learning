package com.github.zj.dreamly.design.pattern.principle.singleresponsibility;

/**
 * @author 苍海之南
 */
public class CourseImpl implements ICourseManager, ICourseContent {
	@Override
	public void studyCourse() {

	}

	@Override
	public void refundCourse() {

	}

	@Override
	public String getCourseName() {
		return null;
	}

	@Override
	public byte[] getCourseVideo() {
		return new byte[0];
	}
}
