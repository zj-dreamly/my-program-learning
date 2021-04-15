package com.github.zj.dreamly.design.pattern.pattern.behavioral.chainofresponsibility.orderchain;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.AnnotationAwareOrderComparator;

import javax.annotation.PostConstruct;
import java.util.List;

@Configuration
public class InitPrintChainPattern {

    @Autowired
    private List<PrintChainPattern> printChainPatterns;

    @PostConstruct
    private void initPrintChainPattern() {
        printChainPatterns.sort(AnnotationAwareOrderComparator.INSTANCE);

        int size = printChainPatterns.size();
        for (int i = 0; i < size; i++) {
            if (i == size - 1) {
                printChainPatterns.get(i).setNext(null);
            } else {
                printChainPatterns.get(i).setNext(printChainPatterns.get(i + 1));
            }
        }
    }

    public void print(int index) {
        printChainPatterns.get(index - 1).print();
    }
}
