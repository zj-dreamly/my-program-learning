package com.github.zj.dreamly.netty.netty.util;

import java.util.concurrent.atomic.AtomicLong;

public final class IdUtil {

    private static final AtomicLong IDX = new AtomicLong();

    private IdUtil() {
        //no instance
    }

    public static long nextId() {
        return IDX.incrementAndGet();
    }

}
