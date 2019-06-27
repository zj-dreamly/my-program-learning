package com.github.zj.dreamly.design.pattern.pattern.behavioral.chainofresponsibility;

/**
 * @author 苍海之南
 */
public abstract class Approver {
	protected Approver approver;

	public void setNextApprover(Approver approver) {
		this.approver = approver;
	}

	public abstract void deploy(Course course);
}
