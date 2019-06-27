package com.github.zj.dreamly.design.pattern.pattern.behavioral.mediator;

/**
 * @author 苍海之南
 */
public class User {
	private String name;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public User(String name) {
		this.name = name;
	}

	public void sendMessage(String message) {
		StudyGroup.showMessage(this, message);
	}
}
