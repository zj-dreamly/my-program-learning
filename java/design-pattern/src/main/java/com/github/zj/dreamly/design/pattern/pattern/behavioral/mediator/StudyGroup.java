package com.github.zj.dreamly.design.pattern.pattern.behavioral.mediator;

import java.util.Date;

/**
 * @author 苍海之南
 */
public class StudyGroup {

	public static void showMessage(User user, String message) {
		System.out.println(new Date().toString() + " [" + user.getName() + "] : " + message);
	}
}
