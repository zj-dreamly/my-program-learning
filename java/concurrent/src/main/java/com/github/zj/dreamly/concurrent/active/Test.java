package com.github.zj.dreamly.concurrent.active;

/**
 * @author 苍海之南
 */
public class Test {

	/**
	 * A a-> B b
	 * <p>
	 * main
	 */
	public static void main(String[] args) {
		ActiveObject activeObject = ActiveObjectFactory.createActiveObject();
		new MakerClientThread(activeObject, "Alice").start();
		new MakerClientThread(activeObject, "Bobby").start();

		new DisplayClientThread("Chris", activeObject).start();
	}
}
