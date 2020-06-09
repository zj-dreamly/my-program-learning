package com.github.zj.dreamly.netty.netty.common.keepalive;

import com.github.zj.dreamly.netty.netty.common.OperationResult;
import lombok.Data;

@Data
public class KeepaliveOperationResult extends OperationResult {

    private final long time;

}
