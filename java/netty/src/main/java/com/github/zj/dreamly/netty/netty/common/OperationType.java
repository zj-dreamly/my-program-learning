package com.github.zj.dreamly.netty.netty.common;

import com.github.zj.dreamly.netty.netty.common.auth.AuthOperation;
import com.github.zj.dreamly.netty.netty.common.auth.AuthOperationResult;
import com.github.zj.dreamly.netty.netty.common.keepalive.KeepaliveOperation;
import com.github.zj.dreamly.netty.netty.common.keepalive.KeepaliveOperationResult;
import com.github.zj.dreamly.netty.netty.common.order.OrderOperation;
import com.github.zj.dreamly.netty.netty.common.order.OrderOperationResult;

import java.util.function.Predicate;

public enum OperationType {

	/**
	 * 授权code
	 */
	AUTH(1, AuthOperation.class, AuthOperationResult.class),
	KEEPALIVE(2, KeepaliveOperation.class, KeepaliveOperationResult.class),
	ORDER(3, OrderOperation.class, OrderOperationResult.class);

	private int opCode;
	private Class<? extends Operation> operationClazz;
	private Class<? extends OperationResult> operationResultClazz;

	OperationType(int opCode, Class<? extends Operation> operationClazz, Class<? extends OperationResult> responseClass) {
		this.opCode = opCode;
		this.operationClazz = operationClazz;
		this.operationResultClazz = responseClass;
	}

	public static OperationType fromOpCode(int type) {
		return getOperationType(requestType -> requestType.opCode == type);
	}

	public static OperationType fromOperation(Operation operation) {
		return getOperationType(requestType -> requestType.operationClazz == operation.getClass());
	}

	private static OperationType getOperationType(Predicate<OperationType> predicate) {
		OperationType[] values = values();
		for (OperationType operationType : values) {
			if (predicate.test(operationType)) {
				return operationType;
			}
		}

		throw new AssertionError("no found type");
	}

	public int getOpCode() {
		return opCode;
	}

	public Class<? extends Operation> getOperationClazz() {
		return operationClazz;
	}

	public Class<? extends OperationResult> getOperationResultClazz() {
		return operationResultClazz;
	}

}
