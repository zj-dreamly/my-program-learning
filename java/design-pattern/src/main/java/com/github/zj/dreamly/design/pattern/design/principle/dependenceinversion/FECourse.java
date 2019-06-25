package com.github.zj.dreamly.design.pattern.design.principle.dependenceinversion;

/**
 * @author 苍海之南
 */
public class FECourse implements ICourse {
	@Override
	public void studyCourse() {
		System.out.println("Geely在学习FE课程");
	}
}
