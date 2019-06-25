package com.github.zj.dreamly.design.pattern.design.principle.demeter;

/**
 * @author 苍海之南
 */
public class Boss {

	public void commandCheckNumber(TeamLeader teamLeader) {
		teamLeader.checkNumberOfCourses();
	}

}
