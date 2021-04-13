package com.github.zj.dreamly.design.pattern.pattern.behavioral.strategy.spring;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class StrategyController {

    private final FactoryForStrategy factoryForStrategy;

    @GetMapping("/strategy")
    public String strategy(@RequestParam("key") String key) {
        String result;
        try {
            result = factoryForStrategy.getStrategy(key).doOperation();
        } catch (Exception e) {
            result = e.getMessage();
        }
        return result;
    }

}
