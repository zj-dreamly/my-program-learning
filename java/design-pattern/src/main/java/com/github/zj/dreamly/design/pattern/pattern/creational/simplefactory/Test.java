package com.github.zj.dreamly.design.pattern.pattern.creational.simplefactory;

/**
 * @author 苍海之南
 */
public class Test {
	public static void main(String[] args) {
//        VideoFactory videoFactory = new VideoFactory();
//        Video video = videoFactory.getVideo("java");
//        if(video == null){
//            return;
//        }
//        video.produce();

		VideoFactory videoFactory = new VideoFactory();
		Video video = videoFactory.getVideo(JavaVideo.class);
		if (video == null) {
			return;
		}
		video.produce();
	}
}
