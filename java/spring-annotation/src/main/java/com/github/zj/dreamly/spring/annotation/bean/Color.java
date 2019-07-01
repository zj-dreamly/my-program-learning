package com.github.zj.dreamly.spring.annotation.bean;

/**
 * <h2>Color</h2>
 *
 * @author: 苍海之南
 * @since: 2019-06-28 08:59
 **/
public class Color {

	private Car car;

	public Car getCar() {
		return car;
	}

	public void setCar(Car car) {
		this.car = car;
	}

	@Override
	public String toString() {
		return "Color [car=" + car + "]";
	}


}
