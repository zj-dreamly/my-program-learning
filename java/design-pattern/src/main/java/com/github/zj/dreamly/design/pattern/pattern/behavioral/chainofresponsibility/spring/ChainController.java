package com.github.zj.dreamly.design.pattern.pattern.behavioral.chainofresponsibility.spring;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class ChainController {

    @Autowired
    private ApplicationContext context;

    @GetMapping("/chain")
    public String mockedClient() {
        // request一般是通过外部调用获取
        Request request = new Request();
        Pipeline pipeline = newPipeline(request);
        try {
            pipeline.fireTaskReceived();
            pipeline.fireTaskFiltered();
            pipeline.fireTaskExecuted();
            return "success";
        } finally {
            pipeline.fireAfterCompletion();
        }
    }

    private Pipeline newPipeline(Request request) {
        return context.getBean(DefaultPipeline.class, request);
    }
}
