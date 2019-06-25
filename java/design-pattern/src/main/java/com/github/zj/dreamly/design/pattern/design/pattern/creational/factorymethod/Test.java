package com.github.zj.dreamly.design.pattern.design.pattern.creational.factorymethod;

/**
 * @author 苍海之南
 */
public class Test {
	public static void main(String[] args) {
		VideoFactory videoFactory = new PythonVideoFactory();
		VideoFactory videoFactory2 = new JavaVideoFactory();
		VideoFactory videoFactory3 = new FEVideoFactory();
		Video video = videoFactory.getVideo();
		video.produce();

	}

}
