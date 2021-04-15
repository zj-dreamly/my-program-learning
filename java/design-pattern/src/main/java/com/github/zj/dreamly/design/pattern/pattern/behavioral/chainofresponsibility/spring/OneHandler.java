package com.github.zj.dreamly.design.pattern.pattern.behavioral.chainofresponsibility.spring;

import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

@Order(1)
@Component
public class OneHandler implements Handler {

    private static final int ORDER = 1;

    @Override
    public void filterTask(HandlerContext ctx, Task task) {
        System.out.println("apply 1 filter");
        ctx.fireTaskFiltered(task);
    }
}
