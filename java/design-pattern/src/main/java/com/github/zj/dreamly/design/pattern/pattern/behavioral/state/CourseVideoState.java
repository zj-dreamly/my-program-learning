package com.github.zj.dreamly.design.pattern.pattern.behavioral.state;

/**
 * @author 苍海之南
 */
public abstract class CourseVideoState {
	protected CourseVideoContext courseVideoContext;

	public void setCourseVideoContext(CourseVideoContext courseVideoContext) {
		this.courseVideoContext = courseVideoContext;
	}

	public abstract void play();

	public abstract void speed();

	public abstract void pause();

	public abstract void stop();

}
