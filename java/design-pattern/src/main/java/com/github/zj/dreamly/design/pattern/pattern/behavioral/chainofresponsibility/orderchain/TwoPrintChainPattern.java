package com.github.zj.dreamly.design.pattern.pattern.behavioral.chainofresponsibility.orderchain;

import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

@Order(2)
@Component
public class TwoPrintChainPattern extends PrintChainPattern {
    @Override
    public String getMessage() {
        return "two";
    }
}
