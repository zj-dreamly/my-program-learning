package com.github.zj.dreamly.design.pattern.pattern.structural.flyweight;

/**
 * @author 苍海之南
 */
@SuppressWarnings("all")
public class Test {
	private static final String[] departments = {"RD", "QA", "PM", "BD"};

	public static void main(String[] args) {
//        for(int i=0; i<10; i++){
//            String department = departments[(int)(Math.random() * departments.length)];
//            Manager manager = (Manager) EmployeeFactory.getManager(department);
//            manager.report();
//
//        }

		/**
		 * 下面这就是为何要使用equals方法而不是===
		 */
		Integer a = Integer.valueOf(100);
		Integer b = 100;

		Integer c = Integer.valueOf(1000);
		Integer d = 1000;

		System.out.println("a==b:" + (a == b));

		System.out.println("c==d:" + (c == d));

		System.out.println("a==b:" + (a.equals(b)));

		System.out.println("c==d:" + (c.equals(d)));

	}
}
