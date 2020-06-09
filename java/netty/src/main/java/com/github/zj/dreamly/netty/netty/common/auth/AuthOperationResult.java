package com.github.zj.dreamly.netty.netty.common.auth;

import com.github.zj.dreamly.netty.netty.common.OperationResult;
import lombok.Data;

@Data
public class AuthOperationResult extends OperationResult {

	private final boolean passAuth;

}
