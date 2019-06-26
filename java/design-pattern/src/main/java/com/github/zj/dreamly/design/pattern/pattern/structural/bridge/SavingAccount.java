package com.github.zj.dreamly.design.pattern.pattern.structural.bridge;

/**
 * @author 苍海之南
 */
public class SavingAccount implements Account {
	@Override
	public Account openAccount() {
		System.out.println("打开活期账号");
		//...
		return new SavingAccount();
	}

	@Override
	public void showAccountType() {
		System.out.println("这是一个活期账号");
	}
}

