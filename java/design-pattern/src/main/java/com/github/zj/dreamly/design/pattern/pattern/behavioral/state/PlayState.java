package com.github.zj.dreamly.design.pattern.pattern.behavioral.state;

/**
 * @author 苍海之南
 */
public class PlayState extends CourseVideoState {

    @Override
    public void play() {
        System.out.println("正常播放课程视频状态");
    }

    @Override
    public void speed() {
        super.courseVideoContext.setCourseVideoState(CourseVideoContext.SPEED_STATE);
    }

    @Override
    public void pause() {
        super.courseVideoContext.setCourseVideoState(CourseVideoContext.PAUSE_STATE);
    }

    @Override
    public void stop() {
        super.courseVideoContext.setCourseVideoState(CourseVideoContext.STOP_STATE);
    }
}
