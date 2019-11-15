package com.github.zj.dreamly.concurrent.semaphore;

import java.util.concurrent.Semaphore;
import java.util.concurrent.TimeUnit;

/**
 * @author 苍海之南
 */
public class SemaphoreExample5 {

    private final static Semaphore SEMAPHORE = new Semaphore(3);

    public static void main(String[] args) {
        for (int i = 0; i < 10; i++) {
            new Thread(SemaphoreExample5::batchHandle).start();
        }
    }

    private static void batchHandle() {
        try {
            SEMAPHORE.acquire();
            System.out.println(Thread.currentThread().getName() + " get the SEMAPHORE.");
            TimeUnit.SECONDS.sleep(5);
        } catch (InterruptedException e) {
            e.printStackTrace();
        } finally {
            SEMAPHORE.release();
        }
        System.out.println(Thread.currentThread().getName() + " OUT.");
    }
}
