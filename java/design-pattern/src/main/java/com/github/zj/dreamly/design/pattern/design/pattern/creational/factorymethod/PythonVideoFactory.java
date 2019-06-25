package com.github.zj.dreamly.design.pattern.design.pattern.creational.factorymethod;

/**
 * @author 苍海之南
 */
public class PythonVideoFactory extends VideoFactory {
    @Override
    public Video getVideo() {
        return new PythonVideo();
    }
}
