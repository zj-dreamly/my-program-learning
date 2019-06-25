package com.github.zj.dreamly.design.pattern.design.pattern.creational.singleton;

/**
 * @author 苍海之南
 */
public enum EnumInstance {
	INSTANCE {
		@Override
		protected void printTest() {
			System.out.println("Geely Print Test");
		}
	};

	protected abstract void printTest();

	private Object data;

	public Object getData() {
		return data;
	}

	public void setData(Object data) {
		this.data = data;
	}

	public static EnumInstance getInstance() {
		return INSTANCE;
	}

}
