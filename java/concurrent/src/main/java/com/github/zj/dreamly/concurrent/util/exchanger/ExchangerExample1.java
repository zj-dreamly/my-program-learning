package com.github.zj.dreamly.concurrent.util.exchanger;

import java.util.concurrent.Exchanger;
import java.util.concurrent.TimeUnit;

/**
 * @author 苍海之南
 */
public class ExchangerExample1 {
	/**
	 * V r=exchange(V v)
	 * &nbsp;&nbsp;v：indicate the object the current thread wanted transfer.
	 * &nbsp;&nbsp;r：indicate the other thread(pair) return object.
	 * <p>
	 * <p>
	 * <pre>
	 *      NOTE:
	 *          1.if the pair thread not reached exchange point, the thread will WAITING.
	 *          2.use the {@link Exchanger} must be paired.
	 * </pre>
	 */
	public static void main(String[] args) {
		final Exchanger<String> exchanger = new Exchanger<String>();

		new Thread(() -> {
			System.out.println(Thread.currentThread().getName() + " start.");
			try {
				String result = exchanger.exchange("I am come from T-A");
				System.out.println(Thread.currentThread().getName() + " Get value [" + result + "]");
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			System.out.println(Thread.currentThread().getName() + " end.");
		}, "==A==").start();

		new Thread(() -> {
			System.out.println(Thread.currentThread().getName() + " start.");
			try {
				String result = exchanger.exchange("I am come from T-A1");
				System.out.println(Thread.currentThread().getName() + " Get value [" + result + "]");
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			System.out.println(Thread.currentThread().getName() + " end.");
		}, "==A=1=").start();

		new Thread(() -> {
			System.out.println(Thread.currentThread().getName() + " start.");
			try {
				TimeUnit.SECONDS.sleep(20);
				String result = exchanger.exchange("I am come from T-B");
				System.out.println(Thread.currentThread().getName() + " Get value [" + result + "]");
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			System.out.println(Thread.currentThread().getName() + " end.");
		}, "==B==").start();
	}
}
