package com.github.zj.dreamly.design.pattern.pattern.behavioral.state;

/**
 * @author 苍海之南
 */
public class Test {
	public static void main(String[] args) {
		CourseVideoContext courseVideoContext = new CourseVideoContext();
		courseVideoContext.setCourseVideoState(new PlayState());

		System.out.println("当前状态:" + courseVideoContext.getCourseVideoState().getClass().getSimpleName());
		courseVideoContext.pause();

		System.out.println("当前状态:" + courseVideoContext.getCourseVideoState().getClass().getSimpleName());

		courseVideoContext.speed();

		System.out.println("当前状态:" + courseVideoContext.getCourseVideoState().getClass().getSimpleName());

		courseVideoContext.stop();

		System.out.println("当前状态:" + courseVideoContext.getCourseVideoState().getClass().getSimpleName());

		courseVideoContext.speed();

	}
}
