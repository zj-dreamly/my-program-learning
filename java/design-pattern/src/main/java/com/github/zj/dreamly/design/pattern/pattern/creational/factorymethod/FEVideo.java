package com.github.zj.dreamly.design.pattern.pattern.creational.factorymethod;

/**
 * @author 苍海之南
 */
public class FEVideo extends Video{
    @Override
    public void produce() {
        System.out.println("录制FE课程视频");
    }
}
