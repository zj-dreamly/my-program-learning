package com.github.zj.dreamly.guava.eventbus;

import com.github.zj.dreamly.guava.eventbus.listeners.ExceptionListener;
import com.google.common.eventbus.EventBus;

/**
 * @author 苍海之南
 */
public class ExceptionEventBusExample {
	public static void main(String[] args) {
		final EventBus eventBus = new EventBus((exception, context) ->
		{
			System.out.println(context.getEvent());
			System.out.println(context.getEventBus());
			System.out.println(context.getSubscriber());
			System.out.println(context.getSubscriberMethod());
		});
		eventBus.register(new ExceptionListener());

		eventBus.post("exception post");
	}

/*
    static class ExceptionHandler implements SubscriberExceptionHandler
    {

        @Override
        public void handleException(Throwable exception, SubscriberExceptionContext context)
        {
            System.out.println(context.getEvent());
            System.out.println(context.getEventBus());
            System.out.println(context.getSubscriber());
            System.out.println(context.getSubscriberMethod());
        }
    }*/

}
