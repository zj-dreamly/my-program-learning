package com.github.zj.dreamly.design.pattern.pattern.behavioral.chainofresponsibility.spring;

import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

@Order(3)
@Component
public class ThreeHandler implements Handler {

	private static final int ORDER = 3;

    @Override
    public void filterTask(HandlerContext ctx, Task task) {
        System.out.println("apply 3 filter");
        ctx.fireTaskFiltered(task);
    }
}
