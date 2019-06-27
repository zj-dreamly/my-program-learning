package com.github.zj.dreamly.design.pattern.pattern.behavioral.command;

/**
 * @author 苍海之南
 */
public class CourseVideo {
	private String name;

	public CourseVideo(String name) {
		this.name = name;
	}

	public void open() {
		System.out.println(this.name + "课程视频开放");
	}

	public void close() {
		System.out.println(this.name + "课程视频关闭");
	}
}
