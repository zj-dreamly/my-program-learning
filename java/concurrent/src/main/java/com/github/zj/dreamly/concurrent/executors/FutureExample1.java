package com.github.zj.dreamly.concurrent.executors;

import java.util.concurrent.*;

/**
 * @author 苍海之南
 */
public class FutureExample1 {
    public static void main(String[] args) throws ExecutionException, InterruptedException, TimeoutException {
        testGet();
        testGetWithTimeOut();
    }

    /**
     * {@link Future#get()}
     * Who?
     */
    private static void testGet() throws ExecutionException, InterruptedException {
        ExecutorService executorService = Executors.newCachedThreadPool();

        Future<Integer> future = executorService.submit(() -> {
            try {
                TimeUnit.SECONDS.sleep(10);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            return 10;
        });
        //================================
        System.out.println("=====i will be printed quickly.======");
        //================================
        Thread callerThread = Thread.currentThread();
        new Thread(() -> {
            try {
                TimeUnit.MILLISECONDS.sleep(3);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            callerThread.interrupt();
        }).start();
        Integer result = future.get();
        System.out.println("==============" + result + "=======");
    }


    /**
     * dist cp
     *
     * yarn applicationJobId
     *
     * process
     *
     * kill -9 process
     * yarn -kill applicationJobId
     * <p>
     * 5 hours
     */
    private static void testGetWithTimeOut() throws ExecutionException, InterruptedException, TimeoutException {
        ExecutorService executorService = Executors.newCachedThreadPool();

        Future<Integer> future = executorService.submit(() -> {
            try {
                TimeUnit.SECONDS.sleep(10);
                System.out.println("===============");
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            return 10;
        });

        Integer result = future.get(5, TimeUnit.SECONDS);
        System.out.println(result);
    }
}
