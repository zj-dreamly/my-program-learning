package com.github.zj.dreamly.design.pattern.principle.singleresponsibility;

/**
 * @author 苍海之南
 */
public interface ICourse {
	String getCourseName();

	byte[] getCourseVideo();

	void studyCourse();

	void refundCourse();

}
