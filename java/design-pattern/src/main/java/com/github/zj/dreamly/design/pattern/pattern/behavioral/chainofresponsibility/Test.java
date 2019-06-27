package com.github.zj.dreamly.design.pattern.pattern.behavioral.chainofresponsibility;

/**
 * @author 苍海之南
 */
public class Test {
	public static void main(String[] args) {
		Approver articleApprover = new ArticleApprover();
		Approver videoApprover = new VideoApprover();

		Course course = new Course();
		course.setName("Java设计模式精讲 -- By Geely");
		course.setArticle("Java设计模式精讲的手记");
		course.setVideo("Java设计模式精讲的视频");

		articleApprover.setNextApprover(videoApprover);

		articleApprover.deploy(course);
	}
}
