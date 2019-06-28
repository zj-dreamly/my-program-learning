package com.github.zj.dreamly.spring.annotation.config;

import com.github.zj.dreamly.spring.annotation.bean.Person;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;

/**
 * <h2>TestConfigOfPropertyValue</h2>
 * <p>
 * 使用@PropertySource读取外部配置文件中的k/v保存到运行的环境变量中;加载完外部的配置文件以后使用${}取出配置文件的值
 *
 * @author: 苍海之南
 * @since: 2019-06-28 14:47
 **/
@PropertySource(value = {"classpath:/person.properties"})
@Configuration
public class TestConfigOfPropertyValue {
	@Bean
	public Person person() {
		return new Person();
	}

}
