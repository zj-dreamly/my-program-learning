package com.github.zj.dreamly.design.pattern.pattern.creational.simplefactory;

/**
 * @author 苍海之南
 */
public class VideoFactory {
	public Video getVideo(Class c) {
		Video video = null;
		try {
			video = (Video) Class.forName(c.getName()).newInstance();
		} catch (InstantiationException e) {
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
		return video;
	}

	public Video getVideo(String type) {
		if ("java".equalsIgnoreCase(type)) {
			return new JavaVideo();
		} else if ("python".equalsIgnoreCase(type)) {
			return new PythonVideo();
		}
		return null;
	}

}
