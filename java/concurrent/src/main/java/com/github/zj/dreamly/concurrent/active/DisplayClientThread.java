package com.github.zj.dreamly.concurrent.active;

/**
 * @author 苍海之南
 */
public class DisplayClientThread extends Thread {

	private final ActiveObject activeObject;

	public DisplayClientThread(String name, ActiveObject activeObject) {
		super(name);
		this.activeObject = activeObject;
	}

	@Override
	public void run() {
		try {
			for (int i = 0; true; i++) {
				String text = Thread.currentThread().getName() + "=>" + i;
				activeObject.displayString(text);
				Thread.sleep(200);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
