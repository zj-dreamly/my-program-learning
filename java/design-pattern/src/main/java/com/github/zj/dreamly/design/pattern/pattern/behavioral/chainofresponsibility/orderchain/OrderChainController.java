package com.github.zj.dreamly.design.pattern.pattern.behavioral.chainofresponsibility.orderchain;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class OrderChainController {

    @Autowired
    InitPrintChainPattern chainPattern;

    @GetMapping("/order-chain")
    public String orderChain() {
        chainPattern.print(3);
        return "success";
    }

}
