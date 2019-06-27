package com.github.zj.dreamly.design.pattern.pattern.behavioral.command;

/**
 * @author 苍海之南
 */
public class OpenCourseVideoCommand implements Command {
	private CourseVideo courseVideo;

	public OpenCourseVideoCommand(CourseVideo courseVideo) {
		this.courseVideo = courseVideo;
	}

	@Override
	public void execute() {
		courseVideo.open();
	}
}
