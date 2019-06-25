package com.github.zj.dreamly.design.pattern.design.principle.singleresponsibility;

/**
 * @author 苍海之南
 */
public class Test {
	public static void main(String[] args) {
//        Bird bird = new Bird();
//        bird.mainMoveMode("大雁");
//        bird.mainMoveMode("鸵鸟");

		FlyBird flyBird = new FlyBird();
		flyBird.mainMoveMode("大雁");

		WalkBird walkBird = new WalkBird();
		walkBird.mainMoveMode("鸵鸟");

	}
}
