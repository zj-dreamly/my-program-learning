package com.github.zj.dreamly.concurrent.semaphore;

import java.util.concurrent.Semaphore;
import java.util.concurrent.TimeUnit;

/**
 * @author 苍海之南
 */
public class SemaphoreExample3 {
    /**
     * public void acquire(int permits)
     * public void release(int permits)
     * <p>
     * availablePermits()
     * getQueueLength()
     * <p>
     * semaphore.acquireUninterruptibly();
     * semaphore.acquireUninterruptibly(int permits);
     *
     */
    public static void main(String[] args) throws InterruptedException {
        final Semaphore semaphore = new Semaphore(1);

        Thread t1 = new Thread(() -> {
            try {
                semaphore.acquire();
                TimeUnit.SECONDS.sleep(50);
            } catch (InterruptedException e) {
                e.printStackTrace();
            } finally {
                semaphore.release();
            }
            System.out.println("T1 finished");
        });

        t1.start();

        TimeUnit.MILLISECONDS.sleep(50);

        Thread t2 = new Thread(() -> {
            try {
                semaphore.acquireUninterruptibly();
                ///TimeUnit.SECONDS.sleep(5);
            } finally {
                semaphore.release();
            }

            System.out.println("T2 finished");
        });

        t2.start();

        t1.interrupt();

        TimeUnit.MILLISECONDS.sleep(50);
    }
}
