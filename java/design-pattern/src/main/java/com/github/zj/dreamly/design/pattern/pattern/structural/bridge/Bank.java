package com.github.zj.dreamly.design.pattern.pattern.structural.bridge;

/**
 * @author 苍海之南
 */
public abstract class Bank {
	protected Account account;

	public Bank(Account account) {
		this.account = account;
	}

	abstract Account openAccount();

}
