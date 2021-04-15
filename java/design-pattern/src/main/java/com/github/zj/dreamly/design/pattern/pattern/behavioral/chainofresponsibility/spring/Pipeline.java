package com.github.zj.dreamly.design.pattern.pattern.behavioral.chainofresponsibility.spring;

public interface Pipeline {

    Pipeline fireTaskReceived();

    Pipeline fireTaskFiltered();

    Pipeline fireTaskExecuted();

    Pipeline fireAfterCompletion();
}
