package com.github.zj.dreamly.design.pattern.pattern.behavioral.chainofresponsibility.spring;

import org.springframework.beans.factory.InitializingBean;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

@Component("pipeline")
@Scope("prototype")
public class DefaultPipeline implements Pipeline, ApplicationContextAware, InitializingBean {
    /**
     * 创建一个默认的handler，将其注入到首尾两个节点的HandlerContext中，其作用只是将链往下传递
     */
    private static final Handler DEFAULT_HANDLER = new Handler() {
    };

    /**
     * 将ApplicationContext注入进来的主要原因在于，HandlerContext是prototype类型的，因而需要
     * 通过ApplicationContext.getBean()方法来获取其实例
     */
    private ApplicationContext context;

    /**
     * 创建一个头结点和尾节点，这两个节点内部没有做任何处理，只是默认的将每一层级的链往下传递，
     * 这里头结点和尾节点的主要作用就是用于标志整个链的首尾，所有的业务节点都在这两个节点中间
     */
    private HandlerContext head;
    private HandlerContext tail;

    /**
     * 用于业务调用的request对象，其内部封装了业务数据
     */
    private Request request;
    /**
     * 用于执行任务的task对象
     */
    private Task task;

    /**
     * 最初始的业务数据需要通过构造函数传入，因为这是驱动整个pipeline所需要的数据，
     * 一般通过外部调用方的参数进行封装即可
     */
    public DefaultPipeline(Request request) {
        this.request = request;
    }

    /**
     * 这里我们可以看到，每一层级的调用都是通过HandlerContext.invokeXXX(head)的方式进行的，
     * 也就是说我们每一层级链的入口都是从头结点开始的，当然在某些情况下，我们也需要从尾节点开始链
     * 的调用，这个时候传入tail即可。
     */
    @Override
    public Pipeline fireTaskReceived() {
        HandlerContext.invokeTaskReceived(head, request);
        return this;
    }

    /**
     * 触发任务过滤的链调用
     */
    @Override
    public Pipeline fireTaskFiltered() {
        HandlerContext.invokeTaskFiltered(head, task);
        return this;
    }

    /**
     * 触发任务执行的链执行
     */
    @Override
    public Pipeline fireTaskExecuted() {
        HandlerContext.invokeTaskExecuted(head, task);
        return this;
    }

    /**
     * 触发最终完成的链的执行
     */
    @Override
    public Pipeline fireAfterCompletion() {
        HandlerContext.invokeAfterCompletion(head);
        return this;
    }

    /**
     * 用于往Pipeline中添加节点的方法，读者朋友也可以实现其他的方法用于进行链的维护
     */
    void addLast(Handler handler) {
        HandlerContext handlerContext = newContext(handler);
        tail.prev.next = handlerContext;
        handlerContext.prev = tail.prev;
        handlerContext.next = tail;
        tail.prev = handlerContext;
    }

    /**
     * 这里通过实现InitializingBean接口来达到初始化Pipeline的目的，可以看到，这里初始的时候
     * 我们通过ApplicationContext实例化了两个HandlerContext对象，然后将head.next指向tail节点，
     * 将tail.prev指向head节点。也就是说，初始时，整个链只有头结点和尾节点。
     */
    @Override
    public void afterPropertiesSet() throws Exception {
        head = newContext(DEFAULT_HANDLER);
        tail = newContext(DEFAULT_HANDLER);
        head.next = tail;
        tail.prev = head;
    }

    /**
     * 使用默认的Handler初始化一个HandlerContext
     */
    private HandlerContext newContext(Handler handler) {
        HandlerContext context = this.context.getBean(HandlerContext.class);
        context.handler = handler;
        return context;
    }

    /**
     * 注入ApplicationContext对象
     */
    @Override
    public void setApplicationContext(ApplicationContext applicationContext) {
        this.context = applicationContext;
    }
}
