package com.github.zj.dreamly.concurrent.future;

/**
 * <h2>FutureTest</h2>
 *
 * @author: 苍海之南
 * @since: 2019-11-05 18:02
 **/
public class FutureTest {
	public static void main(String[] args) throws InterruptedException {
        /*String result = get();
        System.out.println(result);*/

		FutureService futureService = new FutureService();
		futureService.submit(() -> {
			try {
				Thread.sleep(10000L);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			return "FINISH";
		}, System.out::println);

		System.out.println("===========");
		System.out.println(" do other thing.");
		Thread.sleep(1000);
		System.out.println("===========");
	}

	private static String get()
		throws InterruptedException {
		Thread.sleep(10000L);
		return "FINISH";
	}
}
