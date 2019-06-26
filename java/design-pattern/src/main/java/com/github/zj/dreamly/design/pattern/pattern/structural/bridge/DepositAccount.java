package com.github.zj.dreamly.design.pattern.pattern.structural.bridge;

/**
 * @author 苍海之南
 */
public class DepositAccount implements Account {
	@Override
	public Account openAccount() {
		System.out.println("打开定期账号");
		return new DepositAccount();
	}

	@Override
	public void showAccountType() {
		System.out.println("这是一个定期账号");
	}
}
