package com.github.zj.dreamly.design.pattern.pattern.behavioral.strategy.spring;

import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@Service
public class FactoryForStrategy {

    @Resource
    private final Map<String, Strategy> STRATEGY_MAP = new ConcurrentHashMap<>(8);

    public Strategy getStrategy(String component) {
        Strategy strategy = STRATEGY_MAP.get(component);
        if (strategy == null) {
            throw new RuntimeException("no strategy defined");
        }
        return strategy;
    }

}
