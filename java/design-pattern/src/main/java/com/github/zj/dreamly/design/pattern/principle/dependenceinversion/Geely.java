package com.github.zj.dreamly.design.pattern.principle.dependenceinversion;

/**
 * @author 苍海之南
 */
public class Geely {

	public void setiCourse(ICourse iCourse) {
		this.iCourse = iCourse;
	}

	private ICourse iCourse;

	public void studyImoocCourse() {
		iCourse.studyCourse();
	}
}
