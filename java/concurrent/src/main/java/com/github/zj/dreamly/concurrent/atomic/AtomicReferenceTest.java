package com.github.zj.dreamly.concurrent.atomic;

import javax.swing.*;
import java.util.concurrent.atomic.AtomicReference;

/**
 * @author 苍海之南
 */
public class AtomicReferenceTest {

	public static void main(String[] args) {
		AtomicReference<Simple> atomic = new AtomicReference<Simple>(new Simple("Alex", 12));
		System.out.println(atomic.get());

		boolean result = atomic.compareAndSet(new Simple("Alex", 12),
			new Simple("sdfs", 234));
		System.out.println(result);

		JButton button = new JButton();

		//default
		final AtomicReference<Simple> s = new AtomicReference<Simple>(new Simple("test", 12));

		button.addActionListener(e -> {
			//invoke restful service
			//s=new Simple("sdfs",24);
			s.set(new Simple("qwe", 324));
		});

	}

	static class ObjectWrap<T> {

		private T t;

		public ObjectWrap(T t) {
			this.t = t;
		}

		public T get() {
			return t;
		}

		public void set(T t) {
			this.t = t;
		}
	}

	static class Simple {
		private String name;
		private int age;

		public Simple(String name, int age) {
			this.name = name;
			this.age = age;
		}

		public String getName() {
			return name;
		}

		public int getAge() {
			return age;
		}
	}
}
