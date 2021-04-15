package com.github.zj.dreamly.design.pattern.pattern.behavioral.chainofresponsibility.spring;

public interface Handler {

    /**
     * 处理接收到前端请求的逻辑
     */
    default void receiveTask(HandlerContext ctx, Request request) {
        ctx.fireTaskReceived(request);
    }

    /**
     * 查询到task之后，进行task过滤的逻辑
     */
    default void filterTask(HandlerContext ctx, Task task) {
        ctx.fireTaskFiltered(task);
    }

    /**
     * task过滤完成之后，处理执行task的逻辑
     */
    default void executeTask(HandlerContext ctx, Task task) {
        ctx.fireTaskExecuted(task);
    }

    /**
     * 当实现的前面的方法抛出异常时，将使用当前方法进行异常处理，这样可以将每个handler的异常
     * 都只在该handler内进行处理，而无需额外进行捕获
     */
    default void exceptionCaught(HandlerContext ctx, Throwable e) {
        throw new RuntimeException(e);
    }

    /**
     * 在整个流程中，保证最后一定会执行的代码，主要是用于一些清理工作
     */
    default void afterCompletion(HandlerContext ctx) {
        ctx.fireAfterCompletion(ctx);
    }
}
