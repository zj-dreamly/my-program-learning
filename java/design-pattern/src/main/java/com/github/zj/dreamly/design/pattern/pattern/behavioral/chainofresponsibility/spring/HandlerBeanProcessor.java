package com.github.zj.dreamly.design.pattern.pattern.behavioral.chainofresponsibility.spring;

import org.springframework.beans.factory.config.BeanPostProcessor;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.core.annotation.AnnotationAwareOrderComparator;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Component
public class HandlerBeanProcessor implements BeanPostProcessor, ApplicationContextAware {

    private ApplicationContext context;

    /**
     * 该方法会在一个bean初始化完成后调用，这里主要是在Pipeline初始化完成之后获取所有实现了
     * Handler接口的bean，然后通过调用Pipeline.addLast()方法将其添加到pipeline中
     */
    @Override
    public Object postProcessAfterInitialization(Object bean, String beanName) {
        if (bean instanceof DefaultPipeline) {
            DefaultPipeline pipeline = (DefaultPipeline) bean;
            Map<String, Handler> handlerMap = context.getBeansOfType(Handler.class);

            List<Handler> handlerList = new ArrayList<>();
            handlerMap.forEach((name, handler) -> handlerList.add(handler));
            handlerList.sort(AnnotationAwareOrderComparator.INSTANCE);
            handlerList.forEach(pipeline::addLast);
        }

        return bean;
    }

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) {
        this.context = applicationContext;
    }
}
