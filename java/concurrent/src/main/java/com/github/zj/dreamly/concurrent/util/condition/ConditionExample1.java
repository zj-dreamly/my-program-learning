package com.github.zj.dreamly.concurrent.util.condition;

import java.util.Optional;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.ReentrantLock;

/**
 * @author 苍海之南
 */
public class ConditionExample1 {

	private final static ReentrantLock LOCK = new ReentrantLock();

	private final static Condition CONDITION = LOCK.newCondition();

	private static int data = 0;

	private static volatile boolean noUse = true;

	public static void main(String[] args) {
		new Thread(() -> {
			for (; ; ) {
				buildData();
			}
		}).start();
		for (int i = 0; i < 2; i++) {
			new Thread(() -> {
				for (; ; ) {
					useData();
				}
			}).start();
		}
	}

	private static void buildData() {
		try {
			LOCK.lock();    //synchronized key word #monitor enter
			while (noUse) {
				CONDITION.await();  //monitor.wait()
			}

			data++;
			Optional.of("P:" + data).ifPresent(System.out::println);
			TimeUnit.SECONDS.sleep(1);
			noUse = true;
			CONDITION.signalAll();     //monitor.notify
		} catch (InterruptedException e) {
			e.printStackTrace();
		} finally {
			LOCK.unlock();      //synchronized end #monitor end
		}
	}

	private static void useData() {
		try {
			LOCK.lock();
			while (!noUse) {
				CONDITION.await();
			}

            ///TimeUnit.SECONDS.sleep(1);
			Optional.of("C:" + data).ifPresent(System.out::println);
			noUse = false;

			CONDITION.signalAll();
		} catch (InterruptedException e) {
			e.printStackTrace();
		} finally {
			LOCK.unlock();
		}
	}

}
