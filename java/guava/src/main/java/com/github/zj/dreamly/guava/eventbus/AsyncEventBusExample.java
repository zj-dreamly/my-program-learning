package com.github.zj.dreamly.guava.eventbus;

import com.github.zj.dreamly.guava.eventbus.listeners.SimpleListener;
import com.google.common.eventbus.AsyncEventBus;

import java.util.concurrent.Executor;

public class AsyncEventBusExample
{
    public static void main(String[] args)
    {
        AsyncEventBus eventBus = new AsyncEventBus(new SeqExecutor());
        eventBus.register(new SimpleListener());
        eventBus.post("hello");

    }

    static class SeqExecutor implements Executor
    {

        @Override
        public void execute(Runnable command)
        {
            command.run();
        }
    }
}
