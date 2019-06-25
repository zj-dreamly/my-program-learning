package com.github.zj.dreamly.design.pattern.pattern.creational.factorymethod;

/**
 * @author 苍海之南
 */
public class FEVideoFactory extends VideoFactory {
	@Override
	public Video getVideo() {
		return new FEVideo();
	}
}
