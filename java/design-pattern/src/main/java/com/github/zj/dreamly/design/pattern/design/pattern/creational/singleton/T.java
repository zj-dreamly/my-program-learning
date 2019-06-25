package com.github.zj.dreamly.design.pattern.design.pattern.creational.singleton;

/**
 * @author 苍海之南
 */
public class T implements Runnable {
	@Override
	public void run() {
//        LazySingleton lazySingleton = LazySingleton.getInstance();
//        LazyDoubleCheckSingleton instance = LazyDoubleCheckSingleton.getInstance();
//        StaticInnerClassSingleton instance = StaticInnerClassSingleton.getInstance();;

//        ContainerSingleton.putInstance("object",new Object());
//        Object instance = ContainerSingleton.getInstance("object");
		ThreadLocalInstance instance = ThreadLocalInstance.getInstance();

		System.out.println(Thread.currentThread().getName() + "  " + instance);

	}
}
