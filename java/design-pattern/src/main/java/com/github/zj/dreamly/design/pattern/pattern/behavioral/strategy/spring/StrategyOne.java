package com.github.zj.dreamly.design.pattern.pattern.behavioral.strategy.spring;

import org.springframework.stereotype.Component;

@Component("one")
public class StrategyOne implements Strategy {
    @Override
	public String doOperation() {
        return "one";
    }
}
