package com.github.zj.dreamly.design.pattern.pattern.behavioral.command;

/**
 * @author 苍海之南
 */
public class CloseCourseVideoCommand implements Command {
	private CourseVideo courseVideo;

	public CloseCourseVideoCommand(CourseVideo courseVideo) {
		this.courseVideo = courseVideo;
	}

	@Override
	public void execute() {
		courseVideo.close();
	}
}
