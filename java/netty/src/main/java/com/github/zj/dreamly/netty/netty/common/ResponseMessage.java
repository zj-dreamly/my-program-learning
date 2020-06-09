package com.github.zj.dreamly.netty.netty.common;

public class ResponseMessage extends Message<OperationResult> {
    @Override
    public Class getMessageBodyDecodeClass(int opcode) {
        return OperationType.fromOpCode(opcode).getOperationResultClazz();
    }
}
