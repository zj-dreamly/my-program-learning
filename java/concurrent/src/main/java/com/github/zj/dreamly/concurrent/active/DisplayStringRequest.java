package com.github.zj.dreamly.concurrent.active;

/**
 * @author 苍海之南
 */
public class DisplayStringRequest extends MethodRequest {

	private final String text;

	public DisplayStringRequest(Servant servant, final String text) {
		super(servant, null);
		this.text = text;
	}

	@Override
	public void execute() {
		this.servant.displayString(text);
	}
}
