package com.github.zj.dreamly.concurrent.active;

/**
 * 对应ActiveObject的每一个方法
 *
 * @author 苍海之南
 */
public abstract class MethodRequest {

	protected final Servant servant;

	protected final FutureResult futureResult;

	public MethodRequest(Servant servant, FutureResult futureResult) {
		this.servant = servant;
		this.futureResult = futureResult;
	}

	public abstract void execute();
}
