package com.github.zj.dreamly.design.pattern.pattern.behavioral.chainofresponsibility.orderchain;

import lombok.Data;
import lombok.extern.slf4j.Slf4j;

@Data
@Slf4j
public abstract class PrintChainPattern {

    private PrintChainPattern next;

    public final void print() {
        String message = getMessage();

        log.info("{} : {}", message, message);
        if (getNext() != null) {
            getNext().print();
        }
    }

    public abstract String getMessage();
}
