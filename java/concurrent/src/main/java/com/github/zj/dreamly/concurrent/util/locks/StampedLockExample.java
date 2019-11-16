package com.github.zj.dreamly.concurrent.util.locks;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.StampedLock;
import java.util.stream.Collectors;

/**
 * @author 苍海之南
 */
public class StampedLockExample {

    /**
     * ReentrantLock VS Synchronized
     * <p>
     * <p>
     * ReentrantReadWriteLock
     * <p>
     * R W X
     * W W X
     * W R X
     * R R O
     * <p>
     * 100 threads
     * 99 threads need read LOCK
     * 1  threads need write LOCK
     */

    private final static StampedLock LOCK = new StampedLock();

    private final static List<Long> DATA = new ArrayList<>();

    public static void main(String[] args) {

        final ExecutorService executor = Executors.newFixedThreadPool(10);
        Runnable readTask = () -> {
            for (; ; ) {
                read();
            }
        };

        Runnable writeTask = () -> {
            for (; ; ) {
                write();
            }
        };

        executor.submit(readTask);
        executor.submit(readTask);
        executor.submit(readTask);
        executor.submit(readTask);
        executor.submit(readTask);
        executor.submit(readTask);
        executor.submit(readTask);
        executor.submit(readTask);
        executor.submit(readTask);
        executor.submit(writeTask);
    }

    private static void read() {
        long stamped = -1;
        try {
            stamped = LOCK.readLock();
            Optional.of(
                    DATA.stream().map(String::valueOf).collect(Collectors.joining("#", "R-", ""))
            ).ifPresent(System.out::println);
            TimeUnit.SECONDS.sleep(1);
        } catch (InterruptedException e) {
            e.printStackTrace();
        } finally {
            LOCK.unlockRead(stamped);
        }
    }

    private static void optimisticRead() {

        long stamp = LOCK.tryOptimisticRead();
        if (LOCK.validate(stamp)) {
            try {
                stamp = LOCK.readLock();
                System.err.println(stamp);
                Optional.of(
                        DATA.stream().map(String::valueOf).collect(Collectors.joining("#", "R-", ""))
                ).ifPresent(System.out::println);
                TimeUnit.SECONDS.sleep(1);
            } catch (InterruptedException e) {
                e.printStackTrace();
            } finally {
                LOCK.unlockRead(stamp);
            }
        }
    }

    private static void write() {
        long stamp = -1;
        try {
            stamp = LOCK.writeLock();
            DATA.add(System.currentTimeMillis());
            TimeUnit.SECONDS.sleep(1);
        } catch (InterruptedException e) {
            e.printStackTrace();
        } finally {
            LOCK.unlockWrite(stamp);
        }
    }
}
