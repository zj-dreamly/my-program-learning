package com.github.zj.dreamly.datastructure.queue.delay.orderexample;

/**
 * 具体执行相关业务的业务类
 *
 * @author 苍海之南
 */
public class DelayOrderWorker implements Runnable {

	@Override
	public void run() {
		//相关业务逻辑处理
		System.out.println(Thread.currentThread().getName() + " do something ……");
	}
}
