package com.github.zj.dreamly.design.pattern.pattern.behavioral.chainofresponsibility.spring;

import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

@Order(2)
@Component
public class TwoHandler implements Handler {

    private static final int ORDER = 2;

    @Override
    public void filterTask(HandlerContext ctx, Task task) {
        System.out.println("apply 2 filter");
        ctx.fireTaskFiltered(task);
    }
}
