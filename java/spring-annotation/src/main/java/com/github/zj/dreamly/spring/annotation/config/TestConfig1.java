package com.github.zj.dreamly.spring.annotation.config;

import com.github.zj.dreamly.spring.annotation.bean.Person;
import com.github.zj.dreamly.spring.annotation.filter.MyTypeFilter;
import com.github.zj.dreamly.spring.annotation.service.BookService;
import org.springframework.context.annotation.*;
import org.springframework.stereotype.Controller;

/**
 * <h2>测试配置文件</h2>
 *
 * @author: 苍海之南
 * @since: 2019-06-27 17:10
 * <p>
 * 相关配置说明
 * @ComponentScans 支持配置多个ComponentScan
 * @ComponentScan value:指定要扫描的包
 * excludeFilters = Filter[]：指定扫描的时候按照什么规则排除那些组件
 * includeFilters = Filter[]：指定扫描的时候只需要包含哪些组件
 * FilterType.ANNOTATION：按照注解
 * FilterType.ASSIGNABLE_TYPE：按照给定的类型；
 * FilterType.ASPECTJ：使用ASPECTJ表达式
 * FilterType.REGEX：使用正则指定
 * FilterType.CUSTOM：使用自定义规则
 **/
@Configuration
@ComponentScans(
	value = {
		@ComponentScan(value = "com.github.zj.dreamly.spring.annotation", includeFilters = {
			@ComponentScan.Filter(type = FilterType.ANNOTATION, classes = {Controller.class}),
			@ComponentScan.Filter(type = FilterType.ASSIGNABLE_TYPE, classes = {BookService.class}),
			@ComponentScan.Filter(type = FilterType.CUSTOM, classes = {MyTypeFilter.class})
		}, useDefaultFilters = false)
	}
)
public class TestConfig1 {

	@Bean
	public Person person() {
		return Person.builder()
			.age(25)
			.name("zj")
			.build();
	}
}
