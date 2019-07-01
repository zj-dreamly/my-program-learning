package com.github.zj.dreamly.spring.annotation.bean;

import org.springframework.stereotype.Component;

/**
 * <h2>Car</h2>
 *
 * @author: 苍海之南
 * @since: 2019-06-28 10:33
 **/
@Component
public class Car {

	public Car() {
		System.out.println("car constructor...");
	}

	public void init() {
		System.out.println("car init...");
	}

	public void detroy() {
		System.out.println("car detroy...");
	}
}
