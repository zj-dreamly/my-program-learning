package com.github.zj.dreamly.design.pattern.pattern.behavioral.templatemethod;

/**
 * @author 苍海之南
 */
public class DesignPatternCourse extends ACourse {
	@Override
	void packageCourse() {
		System.out.println("提供课程Java源代码");
	}

	@Override
	protected boolean needWriteArticle() {
		return true;
	}

}
