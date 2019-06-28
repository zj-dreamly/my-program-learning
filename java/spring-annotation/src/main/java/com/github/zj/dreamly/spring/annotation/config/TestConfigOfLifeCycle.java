package com.github.zj.dreamly.spring.annotation.config;

import com.github.zj.dreamly.spring.annotation.bean.Car;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

/**
 * <h2>TestConfigOfLifeCycle</h2>
 *
 * @author: 苍海之南
 * @since: 2019-06-28 10:35
 **/
@Configuration
@ComponentScan("com.github.zj.dreamly.spring.annotation")
public class TestConfigOfLifeCycle {

	@Bean(initMethod = "init", destroyMethod = "detroy")
	public Car car() {
		return new Car();
	}
}
