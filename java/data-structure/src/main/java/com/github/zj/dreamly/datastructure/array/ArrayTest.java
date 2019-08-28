package com.github.zj.dreamly.datastructure.array;

/**
 * <h2>ArrayTest</h2>
 *
 * @author: 苍海之南
 * @since: 2019-08-28 07:55
 **/
public class ArrayTest {
	public static void main(String[] args) {

		Array<Integer> arr = new Array<>();
		for (int i = 0; i < 10; i++) {
			arr.addLast(i);
		}
		System.out.println("arr：" + arr);

		arr.add(1, 100);
		System.out.println("arr.add(1, 100)：" + arr);

		arr.addFirst(-1);
		System.out.println("arr.addFirst(-1)：" + arr);

		arr.remove(2);
		System.out.println("arr.remove(2)：" + arr);

		arr.removeElement(4);
		System.out.println("arr.removeElement(4)：" + arr);

		arr.removeFirst();
		System.out.println("arr.removeFirst()：" + arr);

		for (int i = 0; i < 4; i++) {
			arr.removeFirst();
			System.out.println("arr.removeFirst()：" + arr);
		}

		arr.removeFirst();
		System.out.println("arr.removeFirst()：" + arr);
		arr.removeLast();
		System.out.println("arr.removeFirst()：" + arr);
	}
}
