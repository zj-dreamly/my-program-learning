package com.github.zj.dreamly.design.pattern.principle.demeter;

/**
 * @author 苍海之南
 */
public class Test {
	public static void main(String[] args) {
		Boss boss = new Boss();
		TeamLeader teamLeader = new TeamLeader();
		boss.commandCheckNumber(teamLeader);

	}
}
