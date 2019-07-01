package com.github.zj.dreamly.spring.annotation.bean;

import org.springframework.stereotype.Component;

/**
 * <h2>Boss</h2>
 * <p>
 * 默认加在ioc容器中的组件，容器启动会调用无参构造器创建对象，再进行初始化赋值等操作
 *
 * @author: 苍海之南
 * @since: 2019-06-29 10:20
 **/
@Component
public class Boss {

	private Car car;

	/**
	 * 构造器要用的组件，都是从容器中获取
	 */
	public Boss(Car car) {
		this.car = car;
		System.out.println("Boss...constrer");
	}

	public Car getCar() {
		return car;
	}

	/**
	 * @see {Autowired} 标注在方法，Spring容器创建当前对象，就会调用方法，完成赋值；
	 * 方法使用的参数，自定义类型的值从ioc容器中获取
	 */
	public void setCar(Car car) {
		this.car = car;
	}

	@Override
	public String toString() {
		return "Boss [car=" + car + "]";
	}

}
