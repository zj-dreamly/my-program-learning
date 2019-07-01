package com.github.zj.dreamly.spring.annotation.aop;

import com.github.zj.dreamly.spring.annotation.config.TestConfigOfAop;
import org.junit.Test;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

/**
 * <h2>TestAop</h2>
 *
 * @author: 苍海之南
 * @since: 2019-06-30 09:57
 **/
public class TestAop {
	@Test
	public void test01(){
		AnnotationConfigApplicationContext applicationContext = new AnnotationConfigApplicationContext(TestConfigOfAop.class);

		MathCalculator mathCalculator = applicationContext.getBean(MathCalculator.class);
		mathCalculator.div(1, 2);
		applicationContext.close();
	}
}
