package com.github.zj.dreamly.design.pattern.principle.singleresponsibility;

/**
 * @author 苍海之南
 */
public class Bird {
	public void mainMoveMode(String birdName) {
		if ("鸵鸟".equals(birdName)) {
			System.out.println(birdName + "用脚走");
		} else {
			System.out.println(birdName + "用翅膀飞");
		}
	}
}
